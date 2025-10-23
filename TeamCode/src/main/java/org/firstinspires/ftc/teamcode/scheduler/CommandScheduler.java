package org.firstinspires.ftc.teamcode.scheduler;

import java.util.*;

public class CommandScheduler {
    private final List<Command> activeCommands = new ArrayList<>();
    private final List<Command> toSchedule = new ArrayList<>();
    private final List<Command> toCancel = new ArrayList<>();

    public void schedule(Command command) {
        if (command == null || activeCommands.contains(command)) return;
        toSchedule.add(command);
    }

    public void update() {
        if (!toSchedule.isEmpty()) {
            for (Command c : toSchedule) {
                c.start();
                activeCommands.add(c);
            }
            toSchedule.clear();
        }

        Iterator<Command> it = activeCommands.iterator();
        while (it.hasNext()) {
            Command c = it.next();
            c.update();

            if (c.isFinished() || toCancel.contains(c)) {
                c.end();
                it.remove();
            }
        }

        toCancel.clear();
    }

    public void cancel(Command command) {
        if (command != null) {
            toCancel.add(command);
        }
    }

    public void cancelAll() {
        for (Command c : activeCommands) c.end();
        activeCommands.clear();
        toSchedule.clear();
        toCancel.clear();
    }

    public boolean isScheduled(Command command) {
        return activeCommands.contains(command);
    }
}
