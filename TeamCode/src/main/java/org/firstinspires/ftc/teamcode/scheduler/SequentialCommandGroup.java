package org.firstinspires.ftc.teamcode.scheduler;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public class SequentialCommandGroup implements Command {

    private final List<Supplier<Command>> suppliers;
    private int currentIndex = 0;

    private Command currentCommand = null;

    public SequentialCommandGroup(Supplier<Command>... cmds) {
        this.suppliers = Arrays.asList(cmds);
    }

    @Override
    public void start() {
        if (!suppliers.isEmpty()) {
            currentCommand = suppliers.get(0).get();
            currentCommand.start();
        }
    }

    @Override
    public void update() {
        if (currentCommand == null) return;

        currentCommand.update();

        if (currentCommand.isFinished()) {
            currentCommand.end();
            currentIndex++;

            if (currentIndex < suppliers.size()) {
                currentCommand = suppliers.get(currentIndex).get();
                currentCommand.start();
            } else {
                currentCommand = null;
            }
        }
    }

    @Override
    public boolean isFinished() {
        return currentIndex >= suppliers.size();
    }

    @Override
    public void end() {
        if (currentCommand != null) {
            currentCommand.end();
            currentCommand = null;
        }
        currentIndex = suppliers.size();
    }
}
