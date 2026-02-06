package org.firstinspires.ftc.teamcode.subsystems;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.utils.PID;

@Configurable
public class Turret {

    private final DcMotorEx motor;

    private final double MAX_ANGLE = 180; // degrees
    private final double MIN_ANGLE = -180; // degrees
    private final double SAFETY_BUFFER = 10; // degrees before hitting limit

    // encoder ticks per revolution
    private final double TICKS_PER_REV = 537.6;
    private final double DEGREES_PER_TICK = 360.0 / TICKS_PER_REV;

    private double currentAngle = 0;

    private double gotoTargetAngle = 0;
    private final double GOTO_TOLERANCE = 10.0;

    private double error;

    public static double kP = 0.0;
    public static double kI = 0.0;
    public static double kD = 0.0;

    private PID pid = new PID(kP, kI, kD, 1);

    private enum TurretState {
        TRACKING,
        WRAPPING,
        GOTO
    }

    private int wrapDirection = 0; // -1 or +1

    private TurretState state = TurretState.TRACKING;


    public Turret(DcMotorEx turretMotor){
        motor = turretMotor;
        motor.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        motor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        motor.setDirection(DcMotorSimple.Direction.FORWARD);
    }

    public void init(){
        motor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        currentAngle = 0;
    }

    // Update current angle from encoder
    private void updateAngle(){
        currentAngle = motor.getCurrentPosition() * DEGREES_PER_TICK;
    }
    
    public void trackTarget() {
        updateAngle();

        switch (state) {

            case TRACKING:
                // Check if we hit limits while still needing to move further
                if (currentAngle >= MAX_ANGLE - SAFETY_BUFFER && error > 0) {
                    // Start wrapping to left
                    state = TurretState.WRAPPING;
                    wrapDirection = -1;
                    pid.reset();
                    break;
                }

                if (currentAngle <= MIN_ANGLE + SAFETY_BUFFER && error < 0) {
                    // Start wrapping to right
                    state = TurretState.WRAPPING;
                    wrapDirection = 1;
                    pid.reset();
                    break;
                }

                double power = pid.calculate(error);
                motor.setPower(clamp(power));
                break;

            case WRAPPING:
                motor.setPower(wrapDirection);

                if (wrapDirection == -1 && currentAngle <= MIN_ANGLE + SAFETY_BUFFER) {
                    state = TurretState.TRACKING;
                    pid.reset();
                }

                if (wrapDirection == 1 && currentAngle >= MAX_ANGLE - SAFETY_BUFFER) {
                    state = TurretState.TRACKING;
                    pid.reset();
                }
                break;

            case GOTO:
                double error = normalizeAngle(gotoTargetAngle - currentAngle);

                if (Math.abs(error) <= GOTO_TOLERANCE) {
                    motor.setPower(0);
                    break;
                }

                if (currentAngle >= MAX_ANGLE - SAFETY_BUFFER && error > 0) {
                    motor.setPower(0);
                    break;
                }

                if (currentAngle <= MIN_ANGLE + SAFETY_BUFFER && error < 0) {
                    motor.setPower(0);
                    break;
                }

                motor.setPower(1 * Math.signum(error));
                break;
        }
    }

    private double clamp(double value) {
        return Math.max(-1, Math.min(1, value));
    }

    private double normalizeAngle(double angle) {
        while (angle > 180) angle -= 360;
        while (angle < -180) angle += 360;
        return angle;
    }

    public void goToAngle(double targetAngle) {
        gotoTargetAngle = Math.max(MIN_ANGLE, Math.min(MAX_ANGLE, targetAngle));
        state = TurretState.GOTO;
    }

    public void enableTracking() {
        state = TurretState.TRACKING;
        pid.reset();
    }

    public double getCurrentAngle(){
        updateAngle();
        return currentAngle;
    }

    public void updateError(double error){ this.error = error; }
}
