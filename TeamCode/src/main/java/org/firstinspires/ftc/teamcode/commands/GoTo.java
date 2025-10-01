package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.hardware.Robot;
import org.firstinspires.ftc.teamcode.scheduler.Command;

public class GoTo implements Command {
    private final Robot robot;
    private final String pathname;

    public GoTo(Robot robot, String pathName){
        this.robot = robot;
        this.pathname = pathName;
    }

    @Override
    public void start() {
        robot.pedro.start();
        robot.pedro.followPath(pathname);
    }

    @Override
    public void update() {

    }

    @Override
    public boolean isFinished() {
        return robot.pedro.finished();
    }

    @Override
    public void end() {
        robot.pedro.stop();
    }
}
