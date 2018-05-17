package base;

import ev3dev.sensors.ev3.EV3ColorSensor;
import lejos.hardware.port.SensorPort;
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



                SampleProvider sp = PackageHandlingTruck.lineReader.getRGBMode();
                int sampleSize = sp.sampleSize();
                float[] sample = new float[sampleSize];

                boolean stopFlag = false;

                Thread.sleep(3000);


                for (int i = 0; i < 30; i++) {
                    sp.fetchSample(sample, 0);
                    int[] speed = LineFollower.motorsSpeed((int) sample[0], (int) sample[1], (int) sample[2]);

                    System.out.println("Speed2 = " + speed[0] + "Speed2 = " + speed[1]);
                    Thread.sleep(100);

                }


//FOLLOW THE LINE
               /*
                PackageHandlingTruck.leftMotor.setSpeed(200);
                PackageHandlingTruck.rightMotor.setSpeed(200);
                PackageHandlingTruck.leftMotor.backward();
                PackageHandlingTruck.rightMotor.backward();
                Thread.sleep(100);

                while(!stopFlag)
                {
                    sp.fetchSample(sample, 0);
                    //System.out.println("R = " + (int)sample[0]);
                    //System.out.println("G = " + (int)sample[1]);
                    //System.out.println("B = " + (int)sample[2]);
                    //System.out.println("============================");

                    //RED
                    if ((int)sample[0] > 180 && (int)sample[1] < 50 && (int)sample[2] < 25) {
                        PackageHandlingTruck.rightMotor.stop(true);
                        PackageHandlingTruck.leftMotor.stop(true);
                        stopFlag = true;
                    }
                    //GREEN
                    if ((int)sample[0] < 40 && (int)sample[1] > 65 && (int)sample[2] < 45) {
                        PackageHandlingTruck.rightMotor.stop(true);
                        PackageHandlingTruck.leftMotor.stop(true);
                        stopFlag = true;
                    }
                    int[] speed = LineFollower.motorsSpeed((int) sample[0], (int) sample[1], (int) sample[2]);
                    PackageHandlingTruck.leftMotor.setSpeed(speed[0]);
                    PackageHandlingTruck.rightMotor.setSpeed(speed[1]);
                    PackageHandlingTruck.leftMotor.backward();
                    PackageHandlingTruck.rightMotor.backward();
                    Thread.sleep(100);
                }
*/
                //ROTATE
              for(int i=0; i<4; i++)
                {

                    PackageHandlingTruck.leftMotor.setSpeed(100);
                    PackageHandlingTruck.rightMotor.setSpeed(100);
                    PackageHandlingTruck.leftMotor.forward();
                    PackageHandlingTruck.rightMotor.forward();
                    Thread.sleep(100);
                }


                PackageHandlingTruck.leftMotor.setSpeed(60);
                PackageHandlingTruck.rightMotor.setSpeed(70);
                PackageHandlingTruck.leftMotor.backward();
                PackageHandlingTruck.rightMotor.forward();
                Thread.sleep(3000);
                while(!stopFlag)
                {
                    sp.fetchSample(sample, 0);

                    if ((int)sample[0] < 120 && (int)sample[1] < 95 && (int)sample[2] < 95)
                    {
                        PackageHandlingTruck.rightMotor.stop(true);
                        PackageHandlingTruck.leftMotor.stop(true);
                        stopFlag = true;
                    }
                    System.out.println("R = " + (int)sample[0]);
                    System.out.println("G = " + (int)sample[1]);
                    System.out.println("B = " + (int)sample[2]);
                    System.out.println("============================");
                    //Thread.sleep(50);

                }

                while(!stopFlag)
                {
                    sp.fetchSample(sample, 0);
                    //System.out.println("R = " + (int)sample[0]);
                    //System.out.println("G = " + (int)sample[1]);
                    //System.out.println("B = " + (int)sample[2]);
                    //System.out.println("============================");

                    //RED
                    if ((int)sample[0] > 180 && (int)sample[1] < 50 && (int)sample[2] < 25) {
                        PackageHandlingTruck.rightMotor.stop(true);
                        PackageHandlingTruck.leftMotor.stop(true);
                        stopFlag = true;
                    }
                    //GREEN
                    if ((int)sample[0] < 40 && (int)sample[1] > 65 && (int)sample[2] < 45) {
                        PackageHandlingTruck.rightMotor.stop(true);
                        PackageHandlingTruck.leftMotor.stop(true);
                        stopFlag = true;
                    }
                    int[] speed = LineFollower.motorsSpeed((int) sample[0], (int) sample[1], (int) sample[2]);
                    PackageHandlingTruck.leftMotor.setSpeed(speed[0]);
                    PackageHandlingTruck.rightMotor.setSpeed(speed[1]);
                    PackageHandlingTruck.leftMotor.backward();
                    PackageHandlingTruck.rightMotor.backward();
                    Thread.sleep(100);
                }


                Thread.sleep(3000);




                PackageHandlingTruck.liftMotor.setSpeed(200);
                PackageHandlingTruck.liftMotor.rotateTo(100, true);
                Thread.sleep(1000);
                PackageHandlingTruck.liftMotor.hold(); //Aivars told us
                Thread.sleep(1000);
                PackageHandlingTruck.liftMotor.stop(true);
                Thread.sleep(3000);


                PackageHandlingTruck.liftMotor.rotateTo(-50, true);
                Thread.sleep(1000);
                PackageHandlingTruck.liftMotor.hold(); //Aivars told us
                Thread.sleep(1000);
                PackageHandlingTruck.liftMotor.stop(true);
                Thread.sleep(3000);


                PackageHandlingTruck.liftMotor.setSpeed(200);
                PackageHandlingTruck.liftMotor.rotateTo(0, true);

                Thread.sleep(100);

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
