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

                PackageHandlingTruck.lineReader.setCurrentMode(2);
                boolean stopFlag = false;
                Thread.sleep(THREE_SECOND);

                while (!stopFlag && PackageHandlingTruck.isRunning) {

                    SampleProvider colorSample = PackageHandlingTruck.lineReader.getRGBMode();
                    int sampleSizeColor = colorSample.sampleSize();
                    float[] sampleColor = new float[sampleSizeColor];

                    colorSample.fetchSample(sampleColor, 0);
                    //TODO:Refine the codes after testing!
                    //colorSample.fetchSample(sampleColor, 1);
                    //colorSample.fetchSample(sampleColor, 2);

                    int colorSampleRed = (int)sampleColor[0];
                    int colorSampleGreen = (int)sampleColor[1];
                    int colorSampleBlue = (int)sampleColor[2];

                    int[] speed = LineFollower.LineFollower.motorsSpeed(colorSampleRed, colorSampleGreen, colorSampleBlue);
                    PackageHandlingTruck.leftMotor.setSpeed(speed[0]);
                    PackageHandlingTruck.rightMotor.setSpeed(speed[1]);
                    PackageHandlingTruck.leftMotor.backward();
                    PackageHandlingTruck.rightMotor.backward();

                    if (colorSampleRed > 125 && colorSampleBlue < 50 && colorSampleGreen < 50){
                        PackageHandlingTruck.leftMotor.stop(true);
                        PackageHandlingTruck.rightMotor.stop(true);
                        stopFlag = true;
                    }

                    Thread.sleep(5);
                }

                if (!PackageHandlingTruck.isRunning) {
                    break;
                }

                PackageHandlingTruck.liftMotor.setSpeed(50);
                PackageHandlingTruck.liftMotor.rotateTo(-50, true);

                PackageHandlingTruck.palletDetector.setCurrentMode(2);
                boolean palletFlag = false;
                Thread.sleep(THREE_SECOND);

                while (!palletFlag && PackageHandlingTruck.isRunning) {

                    SampleProvider palletSample = PackageHandlingTruck.palletDetector.getRGBMode();
                    int palletSampleSize = palletSample.sampleSize();
                    float[] palletSampleColor = new float[palletSampleSize];
                    Thread.sleep(500);
                    palletSample.fetchSample(palletSampleColor, 0);
                    palletSample.fetchSample(palletSampleColor, 1);
                    palletSample.fetchSample(palletSampleColor, 2);

                    int palletColorSampleRed = (int) palletSampleColor[0];
                    int palletColorSampleGreen = (int) palletSampleColor[1];
                    int palletColorSampleBlue = (int) palletSampleColor[2];

                    float average = (palletColorSampleRed + palletColorSampleGreen + palletColorSampleBlue) / 3;

                    if (average < 25) {
                        PackageHandlingTruck.leftMotor.setSpeed(150);
                        PackageHandlingTruck.rightMotor.setSpeed(150);
                        PackageHandlingTruck.leftMotor.backward();
                        PackageHandlingTruck.rightMotor.backward();
                    }
                    else{
                        PackageHandlingTruck.leftMotor.stop(true);
                        PackageHandlingTruck.rightMotor.stop(true);
                        palletFlag = true;
                    }

                    Thread.sleep(5);
                }

                if (!PackageHandlingTruck.isRunning) {
                    break;
                }

                PackageHandlingTruck.liftMotor.setSpeed(50);
                PackageHandlingTruck.liftMotor.rotateTo(70, true);

                Thread.sleep(THREE_SECOND);

                PackageHandlingTruck.leftMotor.setSpeed(200);
                PackageHandlingTruck.rightMotor.setSpeed(200);
                PackageHandlingTruck.leftMotor.forward();
                PackageHandlingTruck.rightMotor.forward();
                Thread.sleep(TWO_SECOND);
                PackageHandlingTruck.leftMotor.stop(true);
                PackageHandlingTruck.rightMotor.stop(true);

                PackageHandlingTruck.lineReader.setCurrentMode(2);
                boolean turnFlag = false;
                Thread.sleep(3000);

                while (!turnFlag && PackageHandlingTruck.isRunning){

                    SampleProvider turnSample = PackageHandlingTruck.lineReader.getRGBMode();
                    int turnSampleSizeColor = turnSample.sampleSize();
                    float[] turnSampleColor = new float[turnSampleSizeColor];

                    turnSample.fetchSample(turnSampleColor, 0);
                    turnSample.fetchSample(turnSampleColor, 1);
                    turnSample.fetchSample(turnSampleColor, 2);

                    int turnColorSampleRed = (int)turnSampleColor[0];
                    int turnColorSampleGreen = (int)turnSampleColor[1];
                    int turnColorSampleBlue = (int)turnSampleColor[2];

                    float turnAverage = (turnColorSampleRed + turnColorSampleGreen + turnColorSampleBlue) / 3;

                    if ((int)turnAverage < 80){
                        PackageHandlingTruck.leftMotor.stop(true);
                        PackageHandlingTruck.rightMotor.stop(true);
                        turnFlag = true;
                    }
                    else {
                        PackageHandlingTruck.leftMotor.setSpeed(150);
                        PackageHandlingTruck.rightMotor.setSpeed(150);
                        PackageHandlingTruck.leftMotor.forward();
                        PackageHandlingTruck.rightMotor.backward();
                    }

                    Thread.sleep(5);
                }

                if (!PackageHandlingTruck.isRunning) {
                    break;
                }

                PackageHandlingTruck.lineReader.setCurrentMode(2);
                boolean deliveryStopFlag = false;
                Thread.sleep(THREE_SECOND);

                while (!deliveryStopFlag && PackageHandlingTruck.isRunning) {

                    SampleProvider deliveryColorSample = PackageHandlingTruck.lineReader.getRGBMode();
                    int deliverySampleSizeColor = deliveryColorSample.sampleSize();
                    float[] deliverySampleColor = new float[deliverySampleSizeColor];

                    deliveryColorSample.fetchSample(deliverySampleColor, 0);
                    deliveryColorSample.fetchSample(deliverySampleColor, 1);
                    deliveryColorSample.fetchSample(deliverySampleColor, 2);

                    int deliveryColorSampleRed = (int)deliverySampleColor[0];
                    int deliveryColorSampleGreen = (int)deliverySampleColor[1];
                    int deliveryColorSampleBlue = (int)deliverySampleColor[2];

                    int[] speed = LineFollower.LineFollower.motorsSpeed(deliveryColorSampleRed, deliveryColorSampleGreen, deliveryColorSampleBlue);
                    PackageHandlingTruck.leftMotor.setSpeed(speed[0]);
                    PackageHandlingTruck.rightMotor.setSpeed(speed[1]);
                    PackageHandlingTruck.leftMotor.backward();
                    PackageHandlingTruck.rightMotor.backward();

                    if (deliveryColorSampleRed < 30 && deliveryColorSampleBlue < 50 && deliveryColorSampleGreen > 70){
                        PackageHandlingTruck.leftMotor.stop(true);
                        PackageHandlingTruck.rightMotor.stop(true);
                        deliveryStopFlag = true;
                    }

                    Thread.sleep(5);

                }

                if (!PackageHandlingTruck.isRunning) {
                    break;
                }

                PackageHandlingTruck.liftMotor.setSpeed(50);
                PackageHandlingTruck.liftMotor.rotateTo(-90, true);

                Thread.sleep(ONE_SECOND);

                PackageHandlingTruck.leftMotor.setSpeed(200);
                PackageHandlingTruck.rightMotor.setSpeed(200);
                PackageHandlingTruck.leftMotor.forward();
                PackageHandlingTruck.rightMotor.forward();
                Thread.sleep(THREE_SECOND);
                PackageHandlingTruck.leftMotor.stop(true);
                PackageHandlingTruck.rightMotor.stop(true);

                PackageHandlingTruck.runThreadIsExecuted = true;
                PackageHandlingTruck.outputCommandSCS = "FINISHED.";
                System.out.println("Task Executed.");

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