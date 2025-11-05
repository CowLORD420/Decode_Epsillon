package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.Gamepad;

public class DriveInput {
    private final Gamepad gamepad;
    private double x = 0;
    private double y = 0;
    private double cos = 0;
    private double sin = 0;

    public DriveInput(Gamepad gamepad){
        this.gamepad = gamepad;
    }

    public void update(){
        y = -gamepad.left_stick_y * cos - gamepad.left_stick_x * sin;
        x = -gamepad.left_stick_y * sin + gamepad.left_stick_x * cos;
    }


    /** nu uita sa dai call la asta */
    public void updateHeading(double headingInRadians){
        cos = Math.cos(headingInRadians);
        sin = Math.sin(headingInRadians);
    }

    public double getY(){ return y; }
    public double getX(){ return x; }
    public double getRx(){ return gamepad.right_stick_x; }
}
