package org.firstinspires.ftc.teamcode.scheduler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CommandScheduler {
    private List<Command> activeCommands = new ArrayList<>();

    public void schedule(Command command) {
        command.start();
        activeCommands.add(command);
    }

    public void update() {
        Iterator<Command> it = activeCommands.iterator();

        while (it.hasNext()) {
            Command c = it.next();
            c.update();

            if (c.isFinished()) {
                c.end();
                it.remove();
            }
        }
    }

    public void cancel(Command command){
        if (activeCommands.remove(command)) {
            command.end();
        }
    }

    public void cancelAll() {
        for (Command c : activeCommands) {
            c.end();
        }
        activeCommands.clear();
    }
}