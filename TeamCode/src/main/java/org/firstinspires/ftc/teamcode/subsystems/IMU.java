package org.firstinspires.ftc.teamcode.subsystems;

public class IMU {
    private final com.qualcomm.robotcore.hardware.IMU imu;
    private double rawHeading;
    private double camHeading;
    private double lastCamHeading = 0;
    private double EPSILON = 1e-3;
    private double offset = 0;

    public IMU(com.qualcomm.robotcore.hardware.IMU imu){
        this.imu = imu;
    }

    public void update() {
        rawHeading = Math.toRadians(imu.getRobotYawPitchRollAngles().getYaw());
    }

    public void setCameraHeading(double headingRadians) {
        if (Math.abs(headingRadians - camHeading) > EPSILON) {
            camHeading = headingRadians;
            offset = camHeading - rawHeading;
        }
    }

    private double normalizeRadians(double angle) {
        while (angle <= -Math.PI) angle += 2 * Math.PI;
        while (angle > Math.PI) angle -= 2 * Math.PI;
        return angle;
    }

    public double getYaw(){ return normalizeRadians(rawHeading + offset); }
}
