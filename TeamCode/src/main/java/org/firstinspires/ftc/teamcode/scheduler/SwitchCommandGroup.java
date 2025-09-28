package org.firstinspires.ftc.teamcode.scheduler;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SwitchCommandGroup implements Command {

    private final Map<String, Command> commands = new HashMap<>();
    private String nameCommand;
    private String lastCommand;
    private final Command defaultCommand;

    private SwitchCommandGroup(Command defaultCommand, Map<String, Command> commands) {
        this.defaultCommand = defaultCommand;
        this.commands.putAll(commands);
        this.nameCommand = null;
    }

    public void setCommand(String name) {
        this.nameCommand = (name != null && commands.containsKey(name)) ? name : null;
    }

    private Command getActiveCommand() {
        if (nameCommand == null) return defaultCommand;
        return commands.get(nameCommand);
    }

    @Override
    public void start() {
        lastCommand = nameCommand;
        Command active = getActiveCommand();
        if (active != null) active.start();
    }

    @Override
    public void update() {
        Command active = getActiveCommand();


        if (!Objects.equals(nameCommand, lastCommand)) {

            Command oldCommand = (lastCommand == null) ? defaultCommand : commands.get(lastCommand);
            if (oldCommand != null) oldCommand.end();

            if (active != null) active.start();
            lastCommand = nameCommand;
            return;
        }

        if (active != null && active.isFinished()) {
            active.end();

            lastCommand = null;
            return;
        }

        if (active != null) active.update();
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end() {
        Command active = getActiveCommand();
        if (active != null) active.end();
    }

    public static class Builder {
        private final Map<String, Command> commands = new HashMap<>();
        private Command defaultCommand;

        public Builder setDefault(Command defaultCommand) {
            this.defaultCommand = defaultCommand;
            return this;
        }

        public Builder add(String name, Command command) {
            commands.put(name, command);
            return this;
        }

        public SwitchCommandGroup build() {
            return new SwitchCommandGroup(defaultCommand, commands);
        }
    }
}
