package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.hardware.Robot;
import org.firstinspires.ftc.teamcode.scheduler.Command;
import org.firstinspires.ftc.teamcode.subsystems.Pedro;
import org.firstinspires.ftc.teamcode.utils.Paths;

public class GoTo implements Command {
    private final Paths.PrebuiltPaths path;
    private final Pedro pedro;

    public GoTo(Pedro pedro, Paths.PrebuiltPaths path) {
        this.path = path;
        this.pedro = pedro;
    }

    @Override
    public void start() {
        pedro.followPath(path.getName());
    }

    @Override
    public void update() {
        pedro.update();
    }

    @Override
    /** trebuie sa verific altfel asta ca nu e recomandata metoda asta da whatever **/
    public boolean isFinished() {
        return pedro.finished();
    }

    @Override
    public void end() {
        pedro.removePrebuiltPath(path.getName());
        pedro.stop();
    }
}
