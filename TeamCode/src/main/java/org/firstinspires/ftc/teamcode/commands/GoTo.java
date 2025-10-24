package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.hardware.Robot;
import org.firstinspires.ftc.teamcode.scheduler.Command;
import org.firstinspires.ftc.teamcode.utils.Paths;

public class GoTo implements Command {
    private final Robot robot;
    private final Paths.PrebuiltPaths path;

    public GoTo(Robot robot, Paths.PrebuiltPaths path) {
        this.robot = robot;
        this.path = path;
    }

    @Override
    public void start() {
        robot.pedro.start();
        robot.pedro.followPath(path.getName());
    }

    @Override
    public void update() { }

    @Override
    public boolean isFinished() {
        return robot.pedro.finished();
    }

    @Override
    public void end() {
        robot.pedro.removeLazyPath(path.getName());
        robot.pedro.stop();
    }
}
