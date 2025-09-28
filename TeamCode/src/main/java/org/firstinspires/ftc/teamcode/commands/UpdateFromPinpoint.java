package org.firstinspires.ftc.teamcode.commands;

import com.pedropathing.geometry.Pose;

import org.firstinspires.ftc.teamcode.hardware.Robot;
import org.firstinspires.ftc.teamcode.scheduler.Command;

public class UpdateFromPinpoint implements Command {
    Robot robot;

    public UpdateFromPinpoint(Robot robot){
        this.robot = robot;
    }

    @Override
    public void start() {

    }

    @Override
    public void update() {
        Pose currentPose = new Pose(robot.pinpoint.getX(), robot.pinpoint.getY(), robot.pinpoint.getYaw());
        robot.paths.updatePose(currentPose);
        robot.pedro.updatePose(currentPose);
        robot.input.updateHeading(robot.pinpoint.getYaw());
        robot.cameraLocalization.updateHeading(robot.pinpoint.getYaw());
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end() {

    }
}
