package LineFollower;

public class LineFollower {

    public static int[] motorsSpeed(int R, int G, int B) {

        float average = (R + G + B) / 3;
        float sensorValue = average * 100 / 255;

        int[] speeds;
        speeds = new int[2];

        if (sensorValue <= 18) {
            speeds[0] = 200;
            speeds[1] = 0;
        } else if (sensorValue > 18 && sensorValue <= 25) {
            speeds[0] = 200;
            speeds[1] = 60;
        } else if (sensorValue > 25 && sensorValue <= 29) {
            speeds[0] = 200;
            speeds[1] = 150;
        } else if (sensorValue > 29 && sensorValue < 31) {
            speeds[0] = 200;
            speeds[1] = 200;
        } else if (sensorValue >= 31 && sensorValue < 35) {
            speeds[0] = 150;
            speeds[1] = 200;
        } else if (sensorValue >= 35 && sensorValue < 42) {
            speeds[0] = 60;
            speeds[1] = 200;
        } else if (sensorValue >= 42 && sensorValue < 52) {
            speeds[0] = 0;
            speeds[1] = 200;
        }

        return speeds;
    }
}
