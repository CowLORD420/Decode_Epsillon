package org.firstinspires.ftc.teamcode.subsystems;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.utils.GoBildaPinpointDriver;

public class Pinpoint {
    GoBildaPinpointDriver pinpoint;
    private Pose2D pose;
    private double yaw;
    private double x;
    private double y;

    private final static double X_BLUE= 12.68;
    private final static double Y_BLUE= 135.74;

    private final static double X_RED = 131.62;
    private final static double Y_RED = 134.80;

    public enum Color{
        BLUE,
        RED
    }

    public Pinpoint(GoBildaPinpointDriver pinpoint){
        this.pinpoint = pinpoint;
        pinpoint.setOffsets(-56.1, -24.6, DistanceUnit.MM);
        pinpoint.setEncoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        pinpoint.setEncoderDirections(
                GoBildaPinpointDriver.EncoderDirection.REVERSED,
                GoBildaPinpointDriver.EncoderDirection.REVERSED
        );

        pinpoint.resetPosAndIMU();
    }

    public void update(){
        pinpoint.update();
        pose = pinpoint.getPosition();
        yaw = pose.getHeading(AngleUnit.RADIANS);
        x = pose.getX(DistanceUnit.INCH);
        y = pose.getY(DistanceUnit.INCH);
    }

    public double getDistanceToTarget(Color color){
        switch (color){
            case RED:
                return Math.sqrt(Math.pow((X_RED - x), 2) + Math.pow((Y_RED - y), 2));

            case BLUE:
                return Math.sqrt(Math.pow((X_BLUE - x), 2) + Math.pow((Y_BLUE - y), 2));

            default:
                return 0.0;
        }
    }

    public void setPosition(Pose2D pose){
        pinpoint.setPosition(pose);
    }

    public void resetPose(Pose2D updatedPose){ pinpoint.setPosition(updatedPose); }

    public double getYaw(){ return yaw; }
    public double getX(){ return x; }
    public double getY(){ return y; }

    public Pose2D getPose(){
        return pose;
    }

}
