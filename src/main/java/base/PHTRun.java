package base;

//import com.sun.xml.internal.bind.v2.runtime.reflect.Lister;
import lejos.robotics.SampleProvider;
import ev3dev.sensors.BaseSensor;
import lejos.hardware.port.Port;
import lejos.utility.Delay;
//TODO: Do we need those imports?

//import static base.DeliveryTruck.motorSteer;
//import static base.DeliveryTruck.motorDrive;
//TODO: Do we need those imports?


/**
 *  Title: DTRun thread
 *
 *  This is thread where all truck logic for task execution should be implemented.
 *  Use function method to do that (it can be extended with other functions).
 */

class PHTRun extends Thread {
    private Thread t;
    private String threadName;



    PHTRun ( String threadName) {
        this.threadName = threadName;
        System.out.println("Creating " +  this.threadName );
    }

    private boolean runMotors() {

        try {
            while (PackageHandlingTruck.isRunning && !PackageHandlingTruck.runThreadIsExecuted) {

                PackageHandlingTruck.lineReader.setCurrentMode(2);
                boolean stopFlag = false;
                Thread.sleep(3000);

                while (!stopFlag && PackageHandlingTruck.isRunning) {

                    SampleProvider colorSample = PackageHandlingTruck.lineReader.getRGBMode();
                    int sampleSizeColor = colorSample.sampleSize();
                    float[] sampleColor = new float[sampleSizeColor];

                    colorSample.fetchSample(sampleColor, 0);
                    colorSample.fetchSample(sampleColor, 1);
                    colorSample.fetchSample(sampleColor, 2);

                    int colorSampleRed = (int)sampleColor[0];
                    int colorSampleGreen = (int)sampleColor[1];
                    int colorSampleBlue = (int)sampleColor[2];

                    int[] speed = LineFollower.motorsSpeed(colorSampleRed, colorSampleGreen, colorSampleBlue);
                    PackageHandlingTruck.motorLeft.setSpeed(speed[0]);
                    PackageHandlingTruck.motorRight.setSpeed(speed[1]);
                    PackageHandlingTruck.motorLeft.backward();
                    PackageHandlingTruck.motorRight.backward();

                    if (colorSampleRed > 125 && colorSampleBlue < 50 && colorSampleGreen < 50){
                        PackageHandlingTruck.motorLeft.stop(true);
                        PackageHandlingTruck.motorRight.stop(true);
                        stopFlag = true;
                    }

                    Thread.sleep(5);
                }

                if (!PackageHandlingTruck.isRunning) {
                    break;
                }

                PackageHandlingTruck.liftMotor.setSpeed(50);
                PackageHandlingTruck.liftMotor.rotateTo(-50, true);

                PackageHandlingTruck.obstacleDetection.setCurrentMode(2);
                boolean obstacleFlag = false;
                Thread.sleep(3000);

                while (!obstacleFlag && PackageHandlingTruck.isRunning) {

                    SampleProvider obstacleSample = PackageHandlingTruck.obstacleDetection.getRGBMode();
                    int obstacleSampleSize = obstacleSample.sampleSize();
                    float[] obstacleSampleColor = new float[obstacleSampleSize];
                    Thread.sleep(500);
                    obstacleSample.fetchSample(obstacleSampleColor, 0);
                    obstacleSample.fetchSample(obstacleSampleColor, 1);
                    obstacleSample.fetchSample(obstacleSampleColor, 2);

                    int obstacleColorSampleRed = (int) obstacleSampleColor[0];
                    int obstacleColorSampleGreen = (int) obstacleSampleColor[1];
                    int obstacleColorSampleBlue = (int) obstacleSampleColor[2];

                    float average = (obstacleColorSampleRed + obstacleColorSampleGreen + obstacleColorSampleBlue) / 3;

                    if (average < 25) {
                        PackageHandlingTruck.motorLeft.setSpeed(150);
                        PackageHandlingTruck.motorRight.setSpeed(150);
                        PackageHandlingTruck.motorLeft.backward();
                        PackageHandlingTruck.motorRight.backward();
                    }
                    else{
                        PackageHandlingTruck.motorLeft.stop(true);
                        PackageHandlingTruck.motorRight.stop(true);
                        obstacleFlag = true;
                    }

                    Thread.sleep(5);
                }

                if (!PackageHandlingTruck.isRunning) {
                    break;
                }

                PackageHandlingTruck.liftMotor.setSpeed(50);
                PackageHandlingTruck.liftMotor.rotateTo(70, true);

                Thread.sleep(3000);

                PackageHandlingTruck.motorLeft.setSpeed(200);
                PackageHandlingTruck.motorRight.setSpeed(200);
                PackageHandlingTruck.motorLeft.forward();
                PackageHandlingTruck.motorRight.forward();
                Thread.sleep(2000);
                PackageHandlingTruck.motorLeft.stop(true);
                PackageHandlingTruck.motorRight.stop(true);

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
                        PackageHandlingTruck.motorLeft.stop(true);
                        PackageHandlingTruck.motorRight.stop(true);
                        turnFlag = true;
                    }
                    else {
                        PackageHandlingTruck.motorLeft.setSpeed(150);
                        PackageHandlingTruck.motorRight.setSpeed(150);
                        PackageHandlingTruck.motorLeft.forward();
                        PackageHandlingTruck.motorRight.backward();
                    }

                    Thread.sleep(5);
                }

                if (!PackageHandlingTruck.isRunning) {
                    break;
                }

                PackageHandlingTruck.lineReader.setCurrentMode(2);
                boolean deliveryStopFlag = false;
                Thread.sleep(3000);

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

                    int[] speed = LineFollower.motorsSpeed(deliveryColorSampleRed, deliveryColorSampleGreen, deliveryColorSampleBlue);
                    PackageHandlingTruck.motorLeft.setSpeed(speed[0]);
                    PackageHandlingTruck.motorRight.setSpeed(speed[1]);
                    PackageHandlingTruck.motorLeft.backward();
                    PackageHandlingTruck.motorRight.backward();

                    if (deliveryColorSampleRed < 30 && deliveryColorSampleBlue < 50 && deliveryColorSampleGreen > 70){
                        PackageHandlingTruck.motorLeft.stop(true);
                        PackageHandlingTruck.motorRight.stop(true);
                        deliveryStopFlag = true;
                    }

                    Thread.sleep(5);

                }

                if (!PackageHandlingTruck.isRunning) {
                    break;
                }

                PackageHandlingTruck.liftMotor.setSpeed(50);
                PackageHandlingTruck.liftMotor.rotateTo(-90, true);

                Thread.sleep(1000);

                PackageHandlingTruck.motorLeft.setSpeed(200);
                PackageHandlingTruck.motorRight.setSpeed(200);
                PackageHandlingTruck.motorLeft.forward();
                PackageHandlingTruck.motorRight.forward();
                Thread.sleep(3000);
                PackageHandlingTruck.motorLeft.stop(true);
                PackageHandlingTruck.motorRight.stop(true);

                PackageHandlingTruck.runThreadIsExecuted = true;
                PackageHandlingTruck.outputCommandSCS = "FINISHED";
                System.out.println("Task Executed");

            }

        } catch (InterruptedException e) {
            System.out.println("Thread " +  this.threadName + " interrupted.");
        }

        PackageHandlingTruck.motorLeft.stop(true);
        PackageHandlingTruck.motorRight.stop(true);

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