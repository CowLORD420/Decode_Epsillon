package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.scheduler.Command;
import org.firstinspires.ftc.teamcode.subsystems.Pinpoint;

public class PinpointSetPose implements Command{
    private final Pinpoint pinpoint;
    private final Pose2D pose;

    public PinpointSetPose(Pinpoint pinpoint, Pose2D pose) {
        this.pinpoint = pinpoint;
        this.pose = pose;
    }

    @Override
    public void start() {
        pinpoint.setPosition(pose);
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

    }
}
