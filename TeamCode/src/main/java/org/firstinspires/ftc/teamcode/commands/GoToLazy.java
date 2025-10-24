package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.hardware.Robot;
import org.firstinspires.ftc.teamcode.scheduler.Command;
import org.firstinspires.ftc.teamcode.utils.Paths;

public class GoToLazy implements Command {
    private final Robot robot;
    private final Paths.LazyPaths path;

    public GoToLazy(Robot robot, Paths.LazyPaths path) {
        this.robot = robot;
        this.path = path;
    }

    @Override
    public void start() {
        robot.pedro.start();
        robot.paths.registerLazyPath(path.getName(), path.getPose());
        robot.pedro.followLazyPath(path.getName());
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
        robot.pedro.removeLazyPath(path.getName());
        robot.pedro.stop();
    }
}
