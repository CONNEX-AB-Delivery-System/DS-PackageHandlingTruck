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
 *
 */



public class PackageHandlingTruck {

    //Configuration
    private static int HALF_SECOND = 500;

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

    public static EV3LargeRegulatedMotor motorLeft;
    public static EV3LargeRegulatedMotor motorRight;
    public static EV3MediumRegulatedMotor liftMotor;

    public static EV3ColorSensor obstacleDetection;
    public static EV3ColorSensor lineReader;


    public static void main(final String[] args){
        //        // getting reference to Main thread
        Thread t = Thread.currentThread();

        double minVoltage = 7.200;

        //Always check if battery voltage is enougth
        System.out.println("Battery Voltage: " + Battery.getInstance().getVoltage());
        System.out.println("Battery Current: " + Battery.getInstance().getBatteryCurrent());
        if (Battery.getInstance().getVoltage() < minVoltage) {
            System.out.println("Battery voltage to low, shutdown");
            System.exit(0);
        }

        //initalize all motors here
        motorLeft = new EV3LargeRegulatedMotor(MotorPort.B);
        motorRight = new EV3LargeRegulatedMotor(MotorPort.C);
        liftMotor = new EV3MediumRegulatedMotor(MotorPort.A);
        System.out.println("Motors initialized");

        //initalize all sensors here
        obstacleDetection = new EV3ColorSensor(SensorPort.S2);
        lineReader = new EV3ColorSensor(SensorPort.S1);
        System.out.println("Sensors initialized");

        //open thread for socket server to listen/send commands to SCS
        PHTThreadPooledServer server = new PHTThreadPooledServer("ServerThread-1", 8000);
        new Thread(server).start();

        while (isRunning) {

            //check if have recieved command from SCS and have not executed run thread before
            if (inputCommandSCS.equals("RUN") && (runThreadIsStarted == false)) {
                //open thread for executing task
                //TODO: start only after SCS has send command
                PHTRun runThread = new PHTRun( "RunThread-1");
                //runThread.setDaemon(false);
                runThreadIsStarted = true;
                runThread.start();
            }

            //wait till run thread is executed
            if (runThreadIsExecuted == false) {
                //TODO: not sure about this
                try {
                    //stop if no connection from SCS after 30 seconds
                    Thread.sleep(1 * 1000);
                    //DeliveryTruck.isRunning = false;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        System.out.println("Stopping Server");
        server.stop();

        System.exit(0);


    }
}
