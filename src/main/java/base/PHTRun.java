package base;

import lejos.robotics.SampleProvider;
import ev3dev.sensors.BaseSensor;
import lejos.hardware.port.Port;
import lejos.utility.Delay;


//TODO: Do we need those imports?

/**
 *  Title: PHTRun thread
 *
 *  This is the thread where all the truck logic for task execution should be implemented.
 *  Use function method to do that (it can be extended with other functions).
 */

class PHTRun extends Thread {
    private Thread t;
    private String threadName;

    private static int HALF_SECOND = 500;
    private static int ONE_SECOND = 1000;
    private static int TWO_SECOND = 2000;
    private static int THREE_SECOND = 3000;

    PHTRun ( String threadName) {
        this.threadName = threadName;
        System.out.println("Creating " +  this.threadName );
    }

    private boolean runMotors() {

        try {
            while (PackageHandlingTruck.isRunning && !PackageHandlingTruck.runThreadIsExecuted) {

                //TODO: YOUR CODE HERE
                //TODO: CHECK THIS DOCUMENTATION TO UNDERSTAND HOW TO RUN THIS TRUCK
                //TODO: AND HOW TO WRITE CODE:
                //https://github.com/CONNEX-AB-Delivery-System/DS-DeliveryTruck/blob/master/README.md
                
                //int[] speed = LineFollower.LineFollower.motorsSpeed(1,1,1);

                PackageHandlingTruck.liftMotor.setSpeed(200);
                PackageHandlingTruck.liftMotor.rotateTo(20, true);

                Thread.sleep(500);

                PackageHandlingTruck.liftMotor.setSpeed(200);
                PackageHandlingTruck.liftMotor.rotateTo(-20, true);

                PackageHandlingTruck.runThreadIsExecuted = true;
                PackageHandlingTruck.outputCommandSCS = "FINISHED";
                System.out.println("Task Executed");

            }

        } catch (InterruptedException e) {
            System.out.println("Thread " +  this.threadName + " interrupted.");
        }

        PackageHandlingTruck.leftMotor.stop(true);
        PackageHandlingTruck.rightMotor.stop(true);

        return true;
    }

    public void run() {
        System.out.println("Running " +  this.threadName );

        this.runMotors();

        System.out.println("Thread " +  this.threadName + " exiting.");
    }

    public void start () {
        System.out.println("Starting " +  this.threadName );
        if (t == null) {
            t = new Thread (this, this.threadName);
            t.start ();
        }
    }
}