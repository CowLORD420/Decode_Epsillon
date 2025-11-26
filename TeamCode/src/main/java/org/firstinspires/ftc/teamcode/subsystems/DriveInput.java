package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.Gamepad;

/** sa nu uiti sa pui sa poti sa apesi buton sa fie slowed gen sa faci comanda **/

public class DriveInput {
    private final Gamepad gamepad;
    private double x = 0;
    private double y = 0;
    private double rot = 0;
    private double cos = 0;
    private double sin = 0;
    private final double epsilon = 0.39;

    public DriveInput(Gamepad gamepad){
        this.gamepad = gamepad;
    }

    public void update(){
        rot = gamepad.right_stick_x;
        y = -gamepad.left_stick_y * cos - gamepad.left_stick_x * sin;
        x = -gamepad.left_stick_y * sin + gamepad.left_stick_x * cos;

        x = Math.copySign(Math.pow(Math.abs(x), epsilon), x);
        y = Math.copySign(Math.pow(Math.abs(y), epsilon), y);
        rot = Math.copySign(Math.pow(Math.abs(rot), epsilon), rot);
    }


    /** nu uita sa dai call la asta */
    public void updateHeading(double headingInRadians){
        cos = Math.cos(headingInRadians);
        sin = Math.sin(headingInRadians);
    }

    public double getY(){ return y; }
    public double getX(){ return x; }
    public double getRx(){ return rot; }
}
