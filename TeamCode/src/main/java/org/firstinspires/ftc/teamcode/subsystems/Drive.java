package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.Gamepad;
/** sa nu uiti sa bagi in loc de imu kalman filter (: */

public class Drive {
    private final Gamepad gamepad;
    private final IMU imu;
    double x = 0;
    double y = 0;
    double cos = 0;
    double sin = 0;

    public Drive(Gamepad gamepad, IMU imu){
        this.gamepad = gamepad;
        this.imu = imu;
    }

    public void update(){
        sin = Math.sin(imu.getYaw());
        cos = Math.cos(imu.getYaw());

        y = gamepad.left_stick_x * cos + gamepad.left_stick_y * sin;
        x = gamepad.left_stick_x * sin - gamepad.left_stick_y * cos;
    }

    public double getYInput(){ return y; }
    public double getXInput(){ return x; }
    public double getRxInput(){ return gamepad.right_stick_x; }
}
