package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.hardware.Robot;
import org.firstinspires.ftc.teamcode.scheduler.Command;
import org.firstinspires.ftc.teamcode.subsystems.Pedro;
import org.firstinspires.ftc.teamcode.utils.Paths;

public class GoToLazy implements Command {
    private final Paths.LazyPaths path;
    private final Pedro pedro;
    private final Paths paths;

    public GoToLazy(Robot robot, Paths.LazyPaths path) {
        this.path = path;
        pedro = robot.pedro;
        paths = robot.paths;
    }

    @Override
    public void start() {
        paths.registerLazyPath(path.getName(), path.getPose());
        pedro.followLazyPath(path.getName());
    }

    @Override
    public void update() { }

    @Override
    public boolean isFinished() { return false; }

    @Override
    public void end() {
        pedro.removeLazyPath(path.getName());
        pedro.stop();
    }
}
