package base;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import ev3dev.actuators.lego.motors.EV3LargeRegulatedMotor;
import ev3dev.actuators.lego.motors.EV3MediumRegulatedMotor;
import ev3dev.sensors.Battery;
import lejos.hardware.port.MotorPort;
import lejos.utility.Delay;

public class PackageHandlingTruck {

    static boolean isRunning = true;

    public static void main(final String[] args) throws IOException{
		
		//implement this:
		//https://github.com/ev3dev-lang-java/examples/blob/master/ev3dev-lang-java/src/main/java/lejos/navigation/pathfinding/pathfind.java

        //System.out.println("Creating Motor A & B & C");
        final EV3LargeRegulatedMotor motorLeft = new EV3LargeRegulatedMotor(MotorPort.B);
        final EV3LargeRegulatedMotor motorRight = new EV3LargeRegulatedMotor(MotorPort.C);
        //final EV3MediumRegulatedMotor liftMotor = new EV3MediumRegulatedMotor(MotorPort.A);

        System.out.println("Motors initialized");

        //https://docs.oracle.com/javase/7/docs/api/java/net/ServerSocket.html
//        ServerSocket serv = new ServerSocket(19232);
//
//        Socket socket = serv.accept();
//
//        BufferedReader reader = new BufferedReader(new InputStreamReader(
//                socket.getInputStream()));
//        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
//                socket.getOutputStream()));
//
//        String outputValue = socket.getLocalSocketAddress().toString();
//
//        writer.write(outputValue+"\n"); writer.flush();
//
//        System.out.println("Checking Battery");
//        System.out.println("Voltage: " + Battery.getInstance().getVoltage());

        motorLeft.setSpeed(500);
        motorRight.setSpeed(500);
        motorLeft.backward();
        motorRight.backward();

        try
        {
            Thread.sleep(3000);
        }
        catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }

        motorLeft.stop(true);
        motorRight.stop(true);


        motorLeft.setSpeed(500);
        motorRight.setSpeed(500);
        motorLeft.forward();
        motorRight.forward();

        try
        {
            Thread.sleep(3000);
        }
        catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }

        motorLeft.stop(true);
        motorRight.stop(true);


//        liftMotor.setSpeed(300);
//        liftMotor.forward();
//
//        new java.util.Timer().schedule(
//                new java.util.TimerTask() {
//                    @Override
//                    public void run() {
//                        liftMotor.stop();
//                    }
//                },
//                3000
//        );
//
//        liftMotor.setSpeed(300);
//        liftMotor.backward();
//
//        new java.util.Timer().schedule(
//                new java.util.TimerTask() {
//                    @Override
//                    public void run() {
//                        liftMotor.stop();
//                    }
//                },
//                3000
//        );

//        serv.close();

//        Runtime.getRuntime().addShutdownHook(new Thread(){public void run(){
//            try {
//                serv.close();
//                socket.close();
//                System.out.println("The server is shut down!");
//            } catch (IOException e) { /* failed */ }
//        }});

        // the listener with the while readline
//        String line;
//        while ((line = reader.readLine()) != "STOP" && isRunning) {
//            System.out.println("RECIEVED " + line);
//            switch (line) {
//                case "UP-PRESS":
//                    motorLeft.setSpeed(500);
//                    motorRight.setSpeed(500);
//                    motorLeft.forward();
//                    motorRight.forward();
//                    break;
//                case "UP-RELEASE":
//                    motorLeft.stop(true);
//                    motorRight.stop(true);
//                    break;
//                case "DOWN-PRESS":
//                    motorLeft.setSpeed(500);
//                    motorRight.setSpeed(500);
//                    motorLeft.backward();
//                    motorRight.backward();
//                    break;
//                case "DOWN-RELEASE":
//                    motorLeft.stop(true);
//                    motorRight.stop(true);
//                    break;
//                case "LEFT-PRESS":
//                    liftMotor.setSpeed(400);
//                    liftMotor.backward();
//                    break;
//                case "LEFT-RELEASE":
//                    liftMotor.stop(true);
//                    break;
//                case "RIGHT-PRESS":
//                    liftMotor.setSpeed(400);
//                    liftMotor.forward();
//                    break;
//                case "RIGHT-RELEASE":
//                    liftMotor.stop(true);
//                    break;
//                case "STOP":
//                    PackageHandlingTruck.isRunning = false;
//                    break;
//            }
//        }


//        //To Stop the motor in case of pkill java for example
//        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
//            public void run() {
//                System.out.println("Emergency Stop");
//                motorLeft.stop();
//                motorRight.stop();
//            }
//        }));
//
//        System.out.println("Defining the Stop mode");
//        motorLeft.brake();
//        motorRight.brake();
//
//        System.out.println("Defining motor speed");
//        final int motorSpeed = 200;
//        motorLeft.setSpeed(motorSpeed);
//        motorRight.setSpeed(motorSpeed);
//
//        System.out.println("Go Forward with the motors");
//        motorLeft.forward();
//        motorRight.forward();
//
//        Delay.msDelay(2000);
//
//        System.out.println("Stop motors");
//        motorLeft.stop();
//        motorRight.stop();
//
//        System.out.println("Go Backward with the motors");
//        motorLeft.backward();
//        motorRight.backward();
//
//        Delay.msDelay(2000);
//
//        System.out.println("Stop motors");
//        motorLeft.stop();
//        motorRight.stop();
//
//        System.out.println("Checking Battery");
//        System.out.println("Votage: " + Battery.getInstance().getVoltage());
//
//        System.exit(0);
    }
}
