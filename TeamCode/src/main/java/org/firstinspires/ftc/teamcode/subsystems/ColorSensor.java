package org.firstinspires.ftc.teamcode.subsystems;

import java.util.Arrays;

public class ColorSensor {

    private final com.qualcomm.robotcore.hardware.ColorSensor colorSensor;

    public ColorSensor(com.qualcomm.robotcore.hardware.ColorSensor colorSensor) {
        this.colorSensor = colorSensor;
    }

    public enum DetectedColor {
        GREEN,
        PURPLE
    }

    private boolean[] taken = new boolean[3]; // track which balls have been shot
    private DetectedColor[] colorOrder = new DetectedColor[3];// pattern
    private DetectedColor[] colorArray = new DetectedColor[3]; // from sensor

    private int position = 0;

    public void setColorOrder(DetectedColor[] colorOrder) {
        this.colorOrder = colorOrder;
    }

    public void updateColorAtIndex(int index) {
        if (index < 0 || index >= colorArray.length) return;

        int red = colorSensor.red();
        int green = colorSensor.green();
        int blue = colorSensor.blue();

        if (green > red && green > blue && green > 250) {
            colorArray[index] = DetectedColor.GREEN;
        } else if ((red + blue) > green && (red + blue) > 400) {
            colorArray[index] = DetectedColor.PURPLE;
        }
    }

    public boolean detected(){
        if (colorSensor.red() > 200 || colorSensor.green() > 200) return true;
        else return false;
    }

    public int getNextBallIndex() {
        // Target color based on pattern
        DetectedColor targetColor = colorOrder[position];

        int selectedIndex = -1;

        // Find any untaken ball that matches target color, **pick first found**
        for (int i = 0; i < colorArray.length; i++) {
            if (!taken[i] && colorArray[i] == targetColor) {
                selectedIndex = i;
                break;
            }
        }

        // If no matching color found, pick **first untaken ball**
        if (selectedIndex == -1) {
            for (int i = 0; i < colorArray.length; i++) {
                if (!taken[i]) {
                    selectedIndex = i;
                    break;
                }
            }
        }

        // Mark as taken
        if (selectedIndex != -1) {
            taken[selectedIndex] = true;
        }


        // Move to next color in the pattern (wrap around)
        position = (position + 1) % colorOrder.length;

        return selectedIndex;
    }

    public int peekNextBallIndex() {
        DetectedColor targetColor = colorOrder[position];

        for (int i = 0; i < colorArray.length; i++) {
            if (!taken[i] && colorArray[i] == targetColor) {
                return i;
            }
        }

        for (int i = 0; i < colorArray.length; i++) {
            if (!taken[i]) {
                return i;
            }
        }

        return -1;
    }


    public void resetTaken() {
        Arrays.fill(taken, false);
        position = 0;
    }

    public void setColorOrder(DetectedColor color1, DetectedColor color2, DetectedColor color3){
        colorOrder[1] = color1;
        colorOrder[2] = color2;
        colorOrder[3] = color3;
    }

    public DetectedColor getColorAt(int index){
        return colorArray[index];
    }


    public DetectedColor[] getColorArray() {
        return colorArray;
    }
}
