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

                //Your code here

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