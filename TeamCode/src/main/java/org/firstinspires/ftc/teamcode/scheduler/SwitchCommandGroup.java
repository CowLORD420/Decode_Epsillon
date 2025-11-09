package org.firstinspires.ftc.teamcode.scheduler;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

public class SwitchCommandGroup<E extends Enum<E>> implements Command {

    private final Map<E, Supplier<Command>> commandSuppliers;
    private final Supplier<Command> defaultCommandSupplier;

    private E nameCommand;
    private E lastCommand;
    private Command activeCommand;

    private SwitchCommandGroup(Supplier<Command> defaultCommandSupplier, Map<E, Supplier<Command>> commandSuppliers) {
        this.defaultCommandSupplier = defaultCommandSupplier;
        this.commandSuppliers = new EnumMap<>(commandSuppliers);
    }

    public void setCommand(E name) {
        this.nameCommand = (name != null && commandSuppliers.containsKey(name)) ? name : null;
    }

    private Command getNewCommandInstance(E name) {
        if (name == null) return defaultCommandSupplier.get();
        Supplier<Command> supplier = commandSuppliers.get(name);
        return (supplier != null) ? supplier.get() : defaultCommandSupplier.get();
    }

    @Override
    public void start() {
        lastCommand = nameCommand;
        activeCommand = getNewCommandInstance(nameCommand);
        if (activeCommand != null) activeCommand.start();
    }

    @Override
    public void update() {
        // --- Case 1: The selected command changed ---
        if (!Objects.equals(nameCommand, lastCommand)) {
            if (activeCommand != null) activeCommand.end();

            activeCommand = getNewCommandInstance(nameCommand);
            if (activeCommand != null) activeCommand.start();

            lastCommand = nameCommand;
            return;
        }

        // --- Case 2: Active command finished ---
        if (activeCommand != null && activeCommand.isFinished()) {
            activeCommand.end();

            // Immediately return to default
            nameCommand = null;
            lastCommand = null;

            activeCommand = getNewCommandInstance(null); // default command
            if (activeCommand != null) activeCommand.start();
            return;
        }

        // --- Case 3: Normal update ---
        if (activeCommand != null) activeCommand.update();
    }

    @Override
    public boolean isFinished() {
        return false; // the group itself never finishes
    }

    @Override
    public void end() {
        if (activeCommand != null) activeCommand.end();
    }

    // -------------------- Builder --------------------
    public static class Builder<E extends Enum<E>> {
        private final Map<E, Supplier<Command>> commandSuppliers;
        private Supplier<Command> defaultCommandSupplier;

        public Builder(Class<E> enumClass) {
            this.commandSuppliers = new EnumMap<>(enumClass);
        }

        public Builder<E> setDefault(Supplier<Command> defaultCommandSupplier) {
            this.defaultCommandSupplier = defaultCommandSupplier;
            return this;
        }

        public Builder<E> add(E name, Supplier<Command> commandSupplier) {
            commandSuppliers.put(name, commandSupplier);
            return this;
        }

        public SwitchCommandGroup<E> build() {
            if (defaultCommandSupplier == null) {
                throw new IllegalStateException("Default command supplier must be set.");
            }
            return new SwitchCommandGroup<>(defaultCommandSupplier, commandSuppliers);
        }
    }
}
