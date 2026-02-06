package org.firstinspires.ftc.teamcode.scheduler;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.ArrayList;

public class RaceParallelCommand implements Command {

    private final List<Supplier<Command>> suppliers;
    private final List<Command> activeCommands = new ArrayList<>();

    public RaceParallelCommand(Supplier<Command>... cmds) {
        this.suppliers = Arrays.asList(cmds);
    }

    @Override
    public void start() {
        activeCommands.clear();

        for (Supplier<Command> s : suppliers) {
            Command c = s.get();
            activeCommands.add(c);
            c.start();
        }
    }

    @Override
    public void update() {
        for (Command c : activeCommands) {
            if (!c.isFinished()) {
                c.update();
            }
        }
    }

    @Override
    public boolean isFinished() {
        for (Command c : activeCommands) {
            if (c.isFinished()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void end() {
        for (Command c : activeCommands) {
            c.end();
        }
        activeCommands.clear();
    }
}
