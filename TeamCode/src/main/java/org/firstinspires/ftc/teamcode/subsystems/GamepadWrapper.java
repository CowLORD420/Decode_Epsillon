package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.Gamepad;
import org.firstinspires.ftc.teamcode.scheduler.Command;
import org.firstinspires.ftc.teamcode.scheduler.CommandScheduler;

import java.util.HashMap;
import java.util.Map;

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

    private final Map<Button, Command> onPress = new HashMap<>();
    private final Map<Button, Command> whileHeld = new HashMap<>();
    private final Map<Analog, AnalogBinding> analogThresholds = new HashMap<>();
    private final Map<Button, Boolean> previousButtonStates = new HashMap<>();

    private static class AnalogBinding {
        double threshold;
        Command command;

        AnalogBinding(double threshold, Command command) {
            this.threshold = threshold;
            this.command = command;
        }
    }

    private GamepadWrapper(Gamepad gamepad, CommandScheduler scheduler) {
        this.gamepad = gamepad;
        this.scheduler = scheduler;
    }

    /** Call this every loop */
    public void update() {
        for (Map.Entry<Button, Command> entry : onPress.entrySet()) {
            boolean current = getButtonState(entry.getKey());
            boolean previous = previousButtonStates.getOrDefault(entry.getKey(), false);

            if (current && !previous) {
                scheduler.schedule(entry.getValue());
            }

            previousButtonStates.put(entry.getKey(), current);
        }

        // whileHeld (keep scheduling as long as held)
        for (Map.Entry<Button, Command> entry : whileHeld.entrySet()) {
            if (getButtonState(entry.getKey())) {
                if (!scheduler.isScheduled(entry.getValue())) {
                    scheduler.schedule(entry.getValue());
                }
            } else {
                if (scheduler.isScheduled(entry.getValue())) {
                    scheduler.cancel(entry.getValue());
                }
            }
        }

        for (Map.Entry<Analog, AnalogBinding> entry : analogThresholds.entrySet()) {
            double value = getAnalogInput(entry.getKey());
            AnalogBinding binding = entry.getValue();

            boolean above = Math.abs(value) > binding.threshold;

            if (above && !scheduler.isScheduled(binding.command)) {
                scheduler.schedule(binding.command);
            }
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

        public Builder(GamepadWrapper wrapper){
            this.wrapper = wrapper;
        }

        public Builder onPress(Button button, Command command) {
            wrapper.onPress.put(button, command);
            return this;
        }

        public Builder whileHeld(Button button, Command command) {
            wrapper.whileHeld.put(button, command);
            return this;
        }

        public Builder whenAbove(Analog analog, double threshold, Command command) {
            wrapper.analogThresholds.put(analog, new AnalogBinding(threshold, command));
            return this;
        }

        public GamepadWrapper build() {
            return wrapper;
        }
    }
}
