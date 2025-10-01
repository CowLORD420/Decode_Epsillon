package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.hardware.Robot;
import org.firstinspires.ftc.teamcode.scheduler.Command;

public class PoseResetFromCamera implements Command {
    private final Robot robot;

    public PoseResetFromCamera(Robot robot){
        this.robot = robot;
    }

    @Override
    public void start() {
        robot.cameraLocalization.start();
        Pose2D pose2D = robot.cameraLocalization.getPose2d();
        robot.pinpoint.resetPose(pose2D);
    }

    @Override
    public void update() {

    }

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public void end() {
        robot.cameraLocalization.stop();
    }
}
