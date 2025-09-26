package org.firstinspires.ftc.teamcode.subsystems;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.utils.GoBildaPinpointDriver;

public class Pinpoint {
    GoBildaPinpointDriver pinpoint;
    private Pose2D pose;
    private double yaw;

    public Pinpoint(GoBildaPinpointDriver pinpoint){
        this.pinpoint = pinpoint;

        pinpoint.setOffsets(0, 0, DistanceUnit.MM);
        pinpoint.setEncoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        pinpoint.setEncoderDirections(
                GoBildaPinpointDriver.EncoderDirection.FORWARD,
                GoBildaPinpointDriver.EncoderDirection.FORWARD
        );

        pinpoint.resetPosAndIMU();
    }

    public void update(){
        pose = pinpoint.getPosition();
        yaw = pose.getHeading(AngleUnit.RADIANS);
    }

    public void resetPose(Pose2D updatedPose){
        pinpoint.setPosition(updatedPose);
    }

    public double getYaw(){
        return yaw;
    }

    public Pose2D getPose(){
        return pose;
    }

}
