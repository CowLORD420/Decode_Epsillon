package org.firstinspires.ftc.teamcode.scheduler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ParallelCommand implements Command {
    private final List<Command> commands = new ArrayList<>();

    public ParallelCommand(Command... cmds) {
        commands.addAll(Arrays.asList(cmds));
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