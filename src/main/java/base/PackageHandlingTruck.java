package base;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import ev3dev.actuators.lego.motors.EV3LargeRegulatedMotor;
import ev3dev.actuators.lego.motors.EV3MediumRegulatedMotor;
import ev3dev.sensors.ev3.EV3ColorSensor;
import ev3dev.sensors.Battery;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.utility.Delay;

import lejos.robotics.SampleProvider;

public class PackageHandlingTruck{

    static boolean isRunning = true;


    public static void main(final String[] args) throws IOException{
		
		//implement this:
		//https://github.com/ev3dev-lang-java/examples/blob/master/ev3dev-lang-java/src/main/java/lejos/navigation/pathfinding/pathfind.java

        //System.out.println("Creating Motor A & B & C");
        final EV3LargeRegulatedMotor motorLeft = new EV3LargeRegulatedMotor(MotorPort.B);
        final EV3LargeRegulatedMotor motorRight = new EV3LargeRegulatedMotor(MotorPort.C);
        //final EV3MediumRegulatedMotor liftMotor = new EV3MediumRegulatedMotor(MotorPort.A);
        //final EV3ColorSensor obstacleDetection = new EV3ColorSensor(SensorPort.S2);
        final EV3ColorSensor lineReader = new EV3ColorSensor(SensorPort.S1);

        motorLeft.setSpeed(200);
        motorRight.setSpeed(200);
        motorLeft.backward();
        motorRight.backward();

        SampleProvider lineSample = lineReader.getRedMode();
        int sampleSize = lineSample.sampleSize();
        float[] sample = new float[sampleSize];
        boolean stopFlag = false;

        try
        {
            Thread.sleep(3000);
        }
        catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }

        while (!stopFlag) {
            lineSample.fetchSample(sample, 0);
            if ((int)sample[0] <= 18){
                System.out.println("WARNING 1: Sample[0]=" +  (int)sample[0]);
                motorLeft.setSpeed(200);
                motorRight.setSpeed(0);
                motorLeft.backward();
                motorRight.backward();
            }
            else if ((18 < (int)sample[0]) && ((int)sample[0] <= 25)){
                System.out.println("Sample[0]=" +  (int)sample[0]);
                motorLeft.setSpeed(200);
                motorRight.setSpeed(60);
                motorLeft.backward();
                motorRight.backward();
            }
            else if (25 < (int)sample[0] && (int)sample[0] <= 29){
                motorLeft.setSpeed(200);
                motorRight.setSpeed(150);
                motorLeft.backward();
                motorRight.backward();
            }
            else if (29 < (int)sample[0] && (int)sample[0] < 31){
                System.out.println("WARNING 3: To the infinity and beyond   =" +  (int)sample[0]);
                motorLeft.setSpeed(300);
                motorRight.setSpeed(300);
                motorLeft.backward();
                motorRight.backward();
            }
            else if (31 <= (int)sample[0] && (int)sample[0] < 35){
                motorLeft.setSpeed(150);
                motorRight.setSpeed(200);
                motorLeft.backward();
                motorRight.backward();
            }
            else if (35 <= (int)sample[0] && (int)sample[0] < 42){
                motorLeft.setSpeed(60);
                motorRight.setSpeed(200);
                motorLeft.backward();
                motorRight.backward();
            }
            else if (42 <= (int)sample[0] && (int)sample[0] < 52){
                motorLeft.setSpeed(0);
                motorRight.setSpeed(200);
                motorLeft.backward();
                motorRight.backward();
            }
            else if (42 <= (int)sample[0] && (int)sample[0] < 74){
                System.out.println("Sample[0]=" +  (int)sample[0]);
                motorLeft.setSpeed(0);
                motorRight.setSpeed(150);
                motorLeft.backward();
                motorRight.backward();
            }
            else if (74 <= (int)sample[0]){
                System.out.println("WARNING 2: Sample[0]=" +  (int)sample[0]);
                motorLeft.stop(true);
                motorRight.stop(true);
                stopFlag = true;
            }

            Delay.msDelay(5);
        }


//////////////////////////////////////////////////

//        Runnable stopMotor = () -> {
//            //String threadName = Thread.currentThread().getName();
//            //System.out.println("Hello " + threadName);
//
//            SampleProvider sp = obstacleDetection.getRGBMode();
//
//            int sampleSize = sp.sampleSize();
//            float[] sample = new float[sampleSize];
//            boolean stopFlag = true;
//
//            // Takes some samples and prints them
//            while (stopFlag) {
//                sp.fetchSample(sample, 0);
//                sp.fetchSample(sample, 1);
//                sp.fetchSample(sample, 2);
//                System.out.println("Sample[0]=" +  (int)sample[0]);
//                System.out.println("Sample[1]=" +  (int)sample[1]);
//                System.out.println("Sample[2]=" +  (int)sample[2]);
//                if ((int)sample[0] > 75 || (int)sample[1] > 75 || (int)sample[2] > 75){
//                    motorLeft.stop(true);
//                    motorRight.stop(true);
//                    stopFlag = false;
//                }
//                Delay.msDelay(250);
//            }
//        };
//
//        motorRight.setSpeed(400);
//        motorLeft.setSpeed(400);
//        motorRight.backward();
//        motorLeft.backward();
//
//        stopMotor.run();
//        Thread stopMotorThread = new Thread(stopMotor);
//        stopMotorThread.start();
//
//        liftMotor.setSpeed(200);
//        liftMotor.forward();
//        try
//        {
//            Thread.sleep(2000);
//        }
//        catch(InterruptedException ex)
//        {
//            Thread.currentThread().interrupt();
//        }
//        liftMotor.stop(true);
//
//        motorRight.setSpeed(400);
//        motorLeft.setSpeed(400);
//        motorRight.forward();
//        motorLeft.forward();
//        try
//        {
//            Thread.sleep(2000);
//        }
//        catch(InterruptedException ex)
//        {
//            Thread.currentThread().interrupt();
//        }
//        motorRight.stop(true);
//        motorLeft.stop(true);
//
//        motorRight.setSpeed(400);
//        motorLeft.setSpeed(400);
//        motorRight.forward();
//        motorLeft.backward();
//        try
//        {
//            Thread.sleep(2800);
//        }
//        catch(InterruptedException ex)
//        {
//            Thread.currentThread().interrupt();
//        }
//        motorRight.stop(true);
//        motorLeft.stop(true);
//
//        motorRight.setSpeed(400);
//        motorLeft.setSpeed(400);
//        motorRight.backward();
//        motorLeft.backward();
//        try
//        {
//            Thread.sleep(2000);
//        }
//        catch(InterruptedException ex)
//        {
//            Thread.currentThread().interrupt();
//        }
//        motorRight.stop(true);
//        motorLeft.stop(true);

        //////////////////////////////////////////////////

//        liftMotor.rotate(-40);

//        final EV3ColorSensor lineReader = new EV3ColorSensor(SensorPort.S1);
//        SampleProvider sp = lineReader.getRGBMode();
//
//        int sampleSize = sp.sampleSize();
//        float[] sample = new float[sampleSize];
//
//        // Takes some samples and prints them
//        for (int i = 0; i < 100; i++) {
//            sp.fetchSample(sample, 0);
//            System.out.println("N=" + i + " Sample=" +  (int)sample[0]);
//
//            Delay.msDelay(500);
//        }


        //System.out.println("Motors initialized");

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


//        motorLeft.setSpeed(500);
//        motorRight.setSpeed(500);
//        motorLeft.backward();
//        motorRight.forward();
//
//        try
//        {
//            Thread.sleep(2500);
//        }
//        catch(InterruptedException ex)
//        {
//            Thread.currentThread().interrupt();
//        }
//
//        motorLeft.stop(true);
//        motorRight.stop(true);


//        motorLeft.setSpeed(500);
//        motorRight.setSpeed(500);
//        motorLeft.backward();
//        motorRight.backward();
//
//        try
//        {
//            Thread.sleep(3000);
//        }
//        catch(InterruptedException ex)
//        {
//            Thread.currentThread().interrupt();
//        }
//
//        motorLeft.stop(true);
//        motorRight.stop(true);
//
//
//        motorLeft.setSpeed(500);
//        motorRight.setSpeed(500);
//        motorLeft.forward();
//        motorRight.forward();
//
//        try
//        {
//            Thread.sleep(3000);
//        }
//        catch(InterruptedException ex)
//        {
//            Thread.currentThread().interrupt();
//        }
//
//        motorLeft.stop(true);
//        motorRight.stop(true);


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
