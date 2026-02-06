package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import java.util.Arrays;

public class Spindex {

    public enum Mode {
        INTAKE,
        OUTTAKE
    }

    private final DcMotorEx motor;

    private static final int POSITIONS = 3;
    private static final int TOLERANCE = 6;
    private static final double POWER = 0.6;
    private static final int TICKS_PER_POSITION = 100;
    private static final int OFFSET = 43;
    private boolean isMoving = false;
    private int currentMotorPos = 0;
    private boolean[] boxState = new boolean[3];

    // Absolute encoder targets
    private static final int[] INTAKE_POSITIONS = {
            0,
            TICKS_PER_POSITION,
            2 * TICKS_PER_POSITION
    };

    private static final int[] OUTTAKE_POSITIONS = {
            OFFSET + TICKS_PER_POSITION,
            OFFSET + 2 * TICKS_PER_POSITION,
            OFFSET
    };

    private Mode mode = Mode.INTAKE;

    private int targetTicks = 0;
    private int currentPosition = 0;

    private int lastPosition = 0;
    private boolean wentToNextPosition = true;

    public Spindex(DcMotorEx motor){
        this.motor = motor;
        motor.setDirection(DcMotorEx.Direction.FORWARD);
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void setMode(Mode mode){
        this.mode = mode;
    }

    public void goToPosition(int targetPosition) {
        targetPosition = ((targetPosition % POSITIONS) + POSITIONS) % POSITIONS;

        if (mode == Mode.INTAKE) {
            targetTicks = INTAKE_POSITIONS[targetPosition];
        } else {
            targetTicks = OUTTAKE_POSITIONS[targetPosition];
        }
    }

    public void goToNextPosition(){
        lastPosition = currentPosition;
        wentToNextPosition = false;
        goToPosition(currentPosition + 1);
    }

    public void update() {
        currentMotorPos = motor.getCurrentPosition();
        int error = targetTicks - currentMotorPos;
        isMoving = error >= 10;

        if (Math.abs(error) > TOLERANCE) {
            motor.setPower(error > 0 ? POWER : -POWER);
        } else {
            motor.setPower(0);
            currentPosition = getNearestPosition();

            if (currentPosition != lastPosition) {
                wentToNextPosition = true;
            }
        }
    }

    private int getNearestPosition() {
        int[] positions = (mode == Mode.INTAKE)
                ? INTAKE_POSITIONS
                : OUTTAKE_POSITIONS;

        int encoder = currentMotorPos;
        int closest = 0;
        int minError = Integer.MAX_VALUE;

        for (int i = 0; i < positions.length; i++) {
            int err = Math.abs(encoder - positions[i]);
            if (err < minError) {
                minError = err;
                closest = i;
            }
        }
        return closest;
    }

    public boolean wentToNextPosition() {
        return wentToNextPosition;
    }

    public void resetWentToNextPosition() {
        wentToNextPosition = false;
    }

    public boolean isFull(){
        for (boolean state : boxState) {
            if (!state) return false;
        }
        return true;
    }

    public boolean isEmpty(){
        for (boolean state : boxState) {
            if (state) return false;
        }
        return true;
    }

    public void setBoxFull(int index){
        boxState[index] = true;
    }

    public void setBoxEmpty(int index){
        boxState[index] = false;
    }

    public void setAllEmpty() {
        Arrays.fill(boxState, false);
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public Mode getMode(){
        return mode;
    }
}
