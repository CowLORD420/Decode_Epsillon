package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.hardware.Robot;
import org.firstinspires.ftc.teamcode.scheduler.Command;

public class UpdateHeading implements Command {
    Robot robot;

    public UpdateHeading(Robot robot){
        this.robot = robot;
    }

    @Override
    public void start() {

    }

    @Override
    public void update() {
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
