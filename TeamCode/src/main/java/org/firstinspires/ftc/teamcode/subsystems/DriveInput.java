package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.Gamepad;
// sa nu uiti sa bagi in loc de imu kalman filter (: daca il mai fac

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
        y = gamepad.left_stick_x * cos - (-gamepad.left_stick_y) * sin;
        x = gamepad.left_stick_x * sin + (-gamepad.left_stick_y) * cos;
    }


    /** nu uita sa dai call la asta */
    public void updateHeading(double headingInRadians){
        cos = Math.cos(headingInRadians);
        sin = Math.sin(headingInRadians);
    }

    public double getYInput(){ return y; }
    public double getXInput(){ return x; }
    public double getRxInput(){ return gamepad.right_stick_x; }
}
