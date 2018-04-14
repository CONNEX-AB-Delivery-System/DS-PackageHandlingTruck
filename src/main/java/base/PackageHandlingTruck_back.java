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
import javax.sound.sampled.Line;

public class PackageHandlingTruck_back{

    static boolean isRunning = true;

    public static void main(final String[] args){
		
		//implement this:
		//https://github.com/ev3dev-lang-java/examples/blob/master/ev3dev-lang-java/src/main/java/lejos/navigation/pathfinding/pathfind.java

        //System.out.println("Creating Motor A & B & C");
        final EV3LargeRegulatedMotor motorLeft = new EV3LargeRegulatedMotor(MotorPort.B);
        final EV3LargeRegulatedMotor motorRight = new EV3LargeRegulatedMotor(MotorPort.C);
        final EV3MediumRegulatedMotor liftMotor = new EV3MediumRegulatedMotor(MotorPort.A);
        final EV3ColorSensor obstacleDetection = new EV3ColorSensor(SensorPort.S2);
        final EV3ColorSensor lineReader = new EV3ColorSensor(SensorPort.S1);

        lineReader.setCurrentMode(2);
        boolean stopFlag = false;
        Delay.msDelay(3000);

        while (!stopFlag) {

            SampleProvider colorSample = lineReader.getRGBMode();
            int sampleSizeColor = colorSample.sampleSize();
            float[] sampleColor = new float[sampleSizeColor];

            colorSample.fetchSample(sampleColor, 0);
            colorSample.fetchSample(sampleColor, 1);
            colorSample.fetchSample(sampleColor, 2);

            int colorSampleRed = (int)sampleColor[0];
            int colorSampleGreen = (int)sampleColor[1];
            int colorSampleBlue = (int)sampleColor[2];

            int[] speed = LineFollower.motorsSpeed(colorSampleRed, colorSampleGreen, colorSampleBlue);
            motorLeft.setSpeed(speed[0]);
            motorRight.setSpeed(speed[1]);
            motorLeft.backward();
            motorRight.backward();

            if (colorSampleRed > 125 && colorSampleBlue < 50 && colorSampleGreen < 50){
                motorLeft.stop(true);
                motorRight.stop(true);
                stopFlag = true;
            }

            Delay.msDelay(5);

        }

        liftMotor.setSpeed(50);
        liftMotor.rotateTo(-50, true);

        obstacleDetection.setCurrentMode(2);
        boolean obstacleFlag = false;
        Delay.msDelay(3000);

        while (!obstacleFlag) {

            SampleProvider obstacleSample = obstacleDetection.getRGBMode();
            int obstacleSampleSize = obstacleSample.sampleSize();
            float[] obstacleSampleColor = new float[obstacleSampleSize];
            Delay.msDelay(500);
            obstacleSample.fetchSample(obstacleSampleColor, 0);
            obstacleSample.fetchSample(obstacleSampleColor, 1);
            obstacleSample.fetchSample(obstacleSampleColor, 2);

            int obstacleColorSampleRed = (int) obstacleSampleColor[0];
            int obstacleColorSampleGreen = (int) obstacleSampleColor[1];
            int obstacleColorSampleBlue = (int) obstacleSampleColor[2];

            float average = (obstacleColorSampleRed + obstacleColorSampleGreen + obstacleColorSampleBlue) / 3;

            if (average < 25) {
                motorLeft.setSpeed(150);
                motorRight.setSpeed(150);
                motorLeft.backward();
                motorRight.backward();
            }
            else{
                motorLeft.stop(true);
                motorRight.stop(true);
                obstacleFlag = true;
            }

            Delay.msDelay(5);
        }

        liftMotor.setSpeed(50);
        liftMotor.rotateTo(70, true);

        Delay.msDelay(3000);

        motorLeft.setSpeed(200);
        motorRight.setSpeed(200);
        motorLeft.forward();
        motorRight.forward();
        Delay.msDelay(2000);
        motorLeft.stop(true);
        motorRight.stop(true);

        lineReader.setCurrentMode(2);
        boolean turnFlag = false;
        Delay.msDelay(3000);

        while (!turnFlag){

            SampleProvider turnSample = lineReader.getRGBMode();
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
                motorLeft.stop(true);
                motorRight.stop(true);
                turnFlag = true;
            }
            else {
                motorLeft.setSpeed(150);
                motorRight.setSpeed(150);
                motorLeft.forward();
                motorRight.backward();
            }

            Delay.msDelay(5);
        }

        lineReader.setCurrentMode(2);
        boolean deliveryStopFlag = false;
        Delay.msDelay(3000);

        while (!deliveryStopFlag) {

            SampleProvider deliveryColorSample = lineReader.getRGBMode();
            int deliverySampleSizeColor = deliveryColorSample.sampleSize();
            float[] deliverySampleColor = new float[deliverySampleSizeColor];

            deliveryColorSample.fetchSample(deliverySampleColor, 0);
            deliveryColorSample.fetchSample(deliverySampleColor, 1);
            deliveryColorSample.fetchSample(deliverySampleColor, 2);

            int deliveryColorSampleRed = (int)deliverySampleColor[0];
            int deliveryColorSampleGreen = (int)deliverySampleColor[1];
            int deliveryColorSampleBlue = (int)deliverySampleColor[2];

            int[] speed = LineFollower.motorsSpeed(deliveryColorSampleRed, deliveryColorSampleGreen, deliveryColorSampleBlue);
            motorLeft.setSpeed(speed[0]);
            motorRight.setSpeed(speed[1]);
            motorLeft.backward();
            motorRight.backward();

            if (deliveryColorSampleRed < 30 && deliveryColorSampleBlue < 50 && deliveryColorSampleGreen > 70){
                motorLeft.stop(true);
                motorRight.stop(true);
                deliveryStopFlag = true;
            }

            Delay.msDelay(5);

        }

        liftMotor.setSpeed(50);
        liftMotor.rotateTo(-90, true);

        Delay.msDelay(1000);

        motorLeft.setSpeed(200);
        motorRight.setSpeed(200);
        motorLeft.forward();
        motorRight.forward();
        Delay.msDelay(3000);
        motorLeft.stop(true);
        motorRight.stop(true);


    }
}
