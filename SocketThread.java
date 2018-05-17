package base;

import java.io.*;
import java.net.Socket;

/**
 *  Title SocketThread
 *
 *  Implements single socket connection via thread that listens to SCS commands and sends commands to SCS.
 *
 *  NOTE: Nothing should be changed in this class.
 */

public class SocketThread extends Thread {

    private Thread t;

    protected Socket clientSocket = null;
    protected String serverText   = null;
    protected BufferedReader reader;
    protected BufferedWriter writer;

    public SocketThread(Socket clientSocket, String serverText) {
        this.clientSocket = clientSocket;
        this.serverText   = serverText;
    }


    private void openReaders() {
        try {
            reader = new BufferedReader(new InputStreamReader(
                    clientSocket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(
                    clientSocket.getOutputStream()));

            String outputValue = clientSocket.getLocalSocketAddress().toString();

            writer.write(outputValue+"\n");writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void isRunningWR() {
        if (PackageHandlingTruck.outputCommandSCS.equals("FINISHED")) {

            System.out.println("worker-FINISHED");

            try {
                writer.write(PackageHandlingTruck.outputCommandSCS+"\n");writer.flush();
                PackageHandlingTruck.outputCommandSCS = "none";
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public void start () {
        System.out.println("Starting Worker Thread"  );
        if (t == null) {
            t = new Thread (this, "Worker Thread");
            t.start ();
        }
    }

    public void run() {

        this.openReaders();

        long time = System.currentTimeMillis();

        String line = null;
        while (PackageHandlingTruck.isRunning) {

            System.out.println("worker running");

            if (PackageHandlingTruck.outputCommandSCS.equals("FINISHED")) {

                System.out.println("worker-FINISHED");
                PackageHandlingTruck.outputCommandSCS = "none";
            }

            try {
                line = reader.readLine();
                System.out.println("RECEIVED " + line);
            } catch (IOException e) {
                e.printStackTrace();
            }

            switch (line) {
                case "RUN":
                    PackageHandlingTruck.inputCommandSCS = line;
                    break;
                case "LEFT-PRESS":
                    PackageHandlingTruck.inputCommandSCS = line;
                    break;
                case "STOP":
                    PackageHandlingTruck.inputCommandSCS = line;
                    PackageHandlingTruck.runThreadIsExecuted = true;
                    break;
                case "KILL":
                    PackageHandlingTruck.inputCommandSCS = line;
                    PackageHandlingTruck.isRunning = false;
                    break;
            }

            System.out.println("Request processed: " + time);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        try {
            System.out.println("Worker closed");
            reader.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}