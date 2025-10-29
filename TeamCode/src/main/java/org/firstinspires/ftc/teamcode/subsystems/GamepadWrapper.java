package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.Gamepad;
import org.firstinspires.ftc.teamcode.scheduler.Command;
import org.firstinspires.ftc.teamcode.scheduler.CommandScheduler;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class GamepadWrapper {
    public enum Button {
        A, B, X, Y,
        LEFT_BUMPER, RIGHT_BUMPER,
        DPAD_DOWN, DPAD_UP, DPAD_LEFT, DPAD_RIGHT
    }

    public enum Analog {
        LEFT_STICK_X, LEFT_STICK_Y,
        RIGHT_STICK_X, RIGHT_STICK_Y,
        LEFT_TRIGGER, RIGHT_TRIGGER
    }

    private final Gamepad gamepad;
    private final CommandScheduler scheduler;

    private final Map<Button, Supplier<Command>> onPress = new HashMap<>();
    private final Map<Button, Supplier<Command>> whileHeld = new HashMap<>();
    private final Map<Analog, AnalogBinding> analogThresholds = new HashMap<>();
    private final Map<Button, Boolean> previousButtonStates = new HashMap<>();
    private final Map<Analog, Boolean> previousAnalogAbove = new HashMap<>();
    private final Map<Button, Command> activeHeldCommands = new HashMap<>();

    private static class AnalogBinding {
        double threshold;
        Supplier<Command> commandSupplier;

        AnalogBinding(double threshold, Supplier<Command> commandSupplier) {
            this.threshold = threshold;
            this.commandSupplier = commandSupplier;
        }
    }

    private GamepadWrapper(Gamepad gamepad, CommandScheduler scheduler) {
        this.gamepad = gamepad;
        this.scheduler = scheduler;
    }

    /** Call this every loop */
    public void update() {
        // onPress
        for (Map.Entry<Button, Supplier<Command>> entry : onPress.entrySet()) {
            Button button = entry.getKey();
            boolean current = getButtonState(button);
            boolean previous = previousButtonStates.getOrDefault(button, false);

            if (current && !previous) {
                scheduler.schedule(entry.getValue().get());
            }

            previousButtonStates.put(button, current);
        }

        // whileHeld
        for (Map.Entry<Button, Supplier<Command>> entry : whileHeld.entrySet()) {
            Button button = entry.getKey();
            boolean pressed = getButtonState(button);
            Command activeCommand = activeHeldCommands.get(button);

            if (pressed) {
                if (activeCommand == null || !scheduler.isScheduled(activeCommand)) {
                    Command cmd = entry.getValue().get();
                    scheduler.schedule(cmd);
                    activeHeldCommands.put(button, cmd);
                }
            } else {
                if (activeCommand != null && scheduler.isScheduled(activeCommand)) {
                    scheduler.cancel(activeCommand);
                }
                activeHeldCommands.remove(button);
            }
        }

        // analog thresholds
        for (Map.Entry<Analog, AnalogBinding> entry : analogThresholds.entrySet()) {
            Analog analog = entry.getKey();
            double value = getAnalogInput(analog);
            boolean above = Math.abs(value) > entry.getValue().threshold;
            boolean wasAbove = previousAnalogAbove.getOrDefault(analog, false);

            if (above && !wasAbove) {
                scheduler.schedule(entry.getValue().commandSupplier.get());
            }

            previousAnalogAbove.put(analog, above);
        }
    }

    private boolean getButtonState(Button button) {
        switch (button) {
            case A: return gamepad.a;
            case B: return gamepad.b;
            case X: return gamepad.x;
            case Y: return gamepad.y;
            case DPAD_UP: return gamepad.dpad_up;
            case DPAD_DOWN: return gamepad.dpad_down;
            case DPAD_LEFT: return gamepad.dpad_left;
            case DPAD_RIGHT: return gamepad.dpad_right;
            case LEFT_BUMPER: return gamepad.left_bumper;
            case RIGHT_BUMPER: return gamepad.right_bumper;
            default: return false;
        }
    }

    private double getAnalogInput(Analog analog) {
        switch (analog) {
            case LEFT_STICK_X: return gamepad.left_stick_x;
            case RIGHT_STICK_X: return gamepad.right_stick_x;
            case LEFT_STICK_Y: return -gamepad.left_stick_y;
            case RIGHT_STICK_Y: return -gamepad.right_stick_y;
            case LEFT_TRIGGER: return gamepad.left_trigger;
            case RIGHT_TRIGGER: return gamepad.right_trigger;
            default: return 0;
        }
    }

    public static class Builder {
        private final GamepadWrapper wrapper;

        public Builder(Gamepad gamepad, CommandScheduler scheduler) {
            wrapper = new GamepadWrapper(gamepad, scheduler);
        }

        public Builder(GamepadWrapper wrapper) {
            this.wrapper = wrapper;
        }

        public Builder onPress(Button button, Supplier<Command> commandSupplier) {
            wrapper.onPress.put(button, commandSupplier);
            return this;
        }

        public Builder whileHeld(Button button, Supplier<Command> commandSupplier) {
            wrapper.whileHeld.put(button, commandSupplier);
            return this;
        }

        public Builder whenAbove(Analog analog, double threshold, Supplier<Command> commandSupplier) {
            wrapper.analogThresholds.put(analog, new AnalogBinding(threshold, commandSupplier));
            return this;
        }

        public GamepadWrapper build() {
            return wrapper;
        }
    }
}
