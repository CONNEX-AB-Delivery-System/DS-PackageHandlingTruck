package base;

import ev3dev.actuators.lego.motors.EV3LargeRegulatedMotor;
import ev3dev.actuators.lego.motors.EV3MediumRegulatedMotor;
import ev3dev.sensors.ev3.EV3ColorSensor;
import ev3dev.sensors.Battery;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;

/**
 * Title: PackageHandlingTruck
 *
 * The main class for the Package Handling Truck. All the initialization for motors and sensors will happen here.
 * Also starts serverSocket thread and respective Socket connections from SCS.
 * After command "run" from SCS starts DTRun thread execution.
 *
 * NOTE: Nothing should be changed in this class.
 */


public class PackageHandlingTruck {

    //Configuration
    private static int HALF_SECOND = 500;
    private static int ONE_SECOND = 1000;
    private static int TWO_SECOND = 2000;
    private static int THREE_SECOND = 3000;

    //TODO: synhronize isRunning variable between threads
    //Synchronization variables between threads to allow intra thread communication
    //Main variable for stopping execution

    static boolean isRunning = true;
    //Variables for commands from/to SCS

    static String inputCommandSCS = "";
    static String outputCommandSCS = "none";

    //Variables for controlling task thread
    static boolean runThreadIsStarted = false;
    static boolean runThreadIsExecuted = false;

    public static EV3LargeRegulatedMotor leftMotor;
    public static EV3LargeRegulatedMotor rightMotor;
    public static EV3MediumRegulatedMotor liftMotor;

    public static EV3ColorSensor palletDetector;
    public static EV3ColorSensor lineReader;


    public static void main(final String[] args){
        //        // getting reference to Main thread
        //Thread t = Thread.currentThread();
        PHTRun runThread;

        double minVoltage = 7.200;

        //Always check if battery voltage is enougth
        System.out.println("Battery Voltage: " + Battery.getInstance().getVoltage());
        System.out.println("Battery Current: " + Battery.getInstance().getBatteryCurrent());
        if (Battery.getInstance().getVoltage() < minVoltage) {
            System.out.println("Battery voltage to low. Shutdown down and change the batteries.");
            System.exit(0);
        }

        //initalize all motors here
        leftMotor = new EV3LargeRegulatedMotor(MotorPort.B);
        rightMotor = new EV3LargeRegulatedMotor(MotorPort.C);
        liftMotor = new EV3MediumRegulatedMotor(MotorPort.A);
        System.out.println("Motors initialized.");

        //initialize all sensors here
        palletDetector = new EV3ColorSensor(SensorPort.S2);
        lineReader = new EV3ColorSensor(SensorPort.S1);
        System.out.println("Sensors initialized.");

        //open thread for socket server to listen/send commands to SCS
        //PHTThreadPooledServer server = new PHTThreadPooledServer("ServerThread-1", 8000);
        //server.start();

        //open thread for executing "run" task
        runThread = new PHTRun( "RunThread-1");
        //add "run" task and "run executed" flags
        runThreadIsExecuted = false;
        runThreadIsStarted = true;
        runThread.start();

        //wait for some time till run thread is executed
        while (!runThreadIsExecuted)
        {
            System.out.println("thread exe" + runThreadIsExecuted);
            try {
                Thread.sleep(10 * 100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (runThreadIsExecuted) {
                    inputCommandSCS = "";
                    runThreadIsStarted = false;
                    isRunning = false;
            }
        }


        System.exit(0);

        /*
        while (isRunning) {
            //first, check if have received "kill" command from SCS
            if (inputCommandSCS.equals("KILL")) {
                //then stop everything
                //isRunning = false;
            }

            //check if have received "run" command from SCS and have not executed run thread before
            if (inputCommandSCS.equals("RUN") && (!runThreadIsStarted)) {
                //open thread for executing "run" task
                runThread = new PHTRun( "RunThread-1");
                //add "run" task and "run executed" flags
                runThreadIsExecuted = false;
                runThreadIsStarted = true;
                runThread.start();
            }

            //wait for some time till run thread is executed
            if (!runThreadIsExecuted) {
                try {
                    Thread.sleep(10 * 100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                inputCommandSCS = "";
                runThreadIsStarted = false;
            }

            if (PackageHandlingTruck.outputCommandSCS.equals("FINISHED")) {

                System.out.println("main-FINISHED");
                server.isRunning();

            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        System.out.println("Stopping Server");
        server.stopServerSocket();

        */

        //System.exit(0);


        //TODO:To Stop the motor in case of pkill java for example
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                System.out.println("Emergency Stop");
                PackageHandlingTruck.leftMotor.stop();
                PackageHandlingTruck.rightMotor.stop();
                PackageHandlingTruck.liftMotor.stop();
            }
        }));
    }
}
