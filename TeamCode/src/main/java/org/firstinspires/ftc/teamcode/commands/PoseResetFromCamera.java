package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.hardware.Robot;
import org.firstinspires.ftc.teamcode.scheduler.Command;
import org.firstinspires.ftc.teamcode.subsystems.CameraLocalization;
import org.firstinspires.ftc.teamcode.subsystems.Pinpoint;

public class PoseResetFromCamera implements Command {
    private final CameraLocalization cameraLocalization;
    private final Pinpoint pinpoint;

    public PoseResetFromCamera(CameraLocalization cameraLocalization, Pinpoint pinpoint){
        this.cameraLocalization = cameraLocalization;
        this.pinpoint = pinpoint;
    }

    @Override
    public void start() {
        cameraLocalization.start();
        Pose2D pose2D = cameraLocalization.getPose2d();
        pinpoint.resetPose(pose2D);
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
        cameraLocalization.stop();
    }
}
