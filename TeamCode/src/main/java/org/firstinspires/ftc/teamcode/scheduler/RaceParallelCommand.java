package org.firstinspires.ftc.teamcode.scheduler;

import java.util.ArrayList;
import java.util.List;

public class RaceParallelCommand implements Command{
    private final List<Command> commands = new ArrayList<>();

    public RaceParallelCommand(Command... cmds) {
        for (Command c : cmds) {
            commands.add(c);
        }
    }

    @Override
    public void start() {
        for (Command c : commands) {
            c.start();
        }
    }

    @Override
    public void update() {
        for (Command c : commands) {
            if (!c.isFinished()) {
                c.update();
            }
        }
    }

    @Override
    public boolean isFinished() {
        for (Command c : commands) {
            if (!c.isFinished()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void end() {
        for (Command c : commands) {
            c.end();
        }
    }
}
