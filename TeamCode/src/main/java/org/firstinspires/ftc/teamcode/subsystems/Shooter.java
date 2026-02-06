package org.firstinspires.ftc.teamcode.subsystems;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.utils.PID;

@Configurable
public class Shooter {

    private final DcMotorEx left;
    private final DcMotorEx right;

    // CHANGE to your motor's encoder value
    private static final double TICKS_PER_REV = 3125/2;

    public static double kP = 0.0026;
    public static double kI = 0.0;
    public static double kD = 0.0004;
    public static double kF = 0.000006;

    private final PID pid = new PID(kP, kI, kD, 0);

    public static double targetRPM = 0;

    // RPM calculation state
    private double lastPosition = 0;
    private long lastTime = 0;
    private double currentRPM = 0;

    public Shooter(DcMotorEx left, DcMotorEx right) {
        pid.setMaxOutput(1.0);

        left.setDirection(DcMotorSimple.Direction.REVERSE);
        right.setDirection(DcMotorSimple.Direction.FORWARD);
        this.left = left;
        this.right = right;

        lastPosition = right.getCurrentPosition();
        lastTime = System.nanoTime();
    }

    private static final double[][] DISTANCE_RPM = {
            {40, 4000},
            {50, 4100},
            {60, 4200},
            {70, 4500},
            {80, 4700},
            {90, 4900},
            {99, 5300}
    };

    private double rpmFromDistance(double distance) {
        if (distance <= DISTANCE_RPM[0][0]) {
            return DISTANCE_RPM[0][1];
        }

        if (distance >= DISTANCE_RPM[DISTANCE_RPM.length - 1][0]) {
            return DISTANCE_RPM[DISTANCE_RPM.length - 1][1];
        }

        for (int i = 0; i < DISTANCE_RPM.length - 1; i++) {
            double d1 = DISTANCE_RPM[i][0];
            double r1 = DISTANCE_RPM[i][1];
            double d2 = DISTANCE_RPM[i + 1][0];
            double r2 = DISTANCE_RPM[i + 1][1];

            if (distance >= d1 && distance <= d2) {
                double t = (distance - d1) / (d2 - d1);
                return r1 + t * (r2 - r1);
            }
        }

        return 0;
    }

    // Called ONCE per loop
    private void updateRPM() {
        long now = System.nanoTime();
        double deltaTime = (now - lastTime) / 1e9;

        double currentPosition = right.getCurrentPosition();

        double deltaTicks = currentPosition - lastPosition;

        lastTime = now;
        lastPosition = currentPosition;

        double ticksPerSecond = deltaTicks / deltaTime;
        currentRPM = (ticksPerSecond / TICKS_PER_REV) * 60.0;
    }

    public double getRPM() {
        return currentRPM;
    }

    public void setTargetRPM(double distanceInInches) {
        targetRPM = rpmFromDistance(distanceInInches);
    }

    public double getTargetRPM() {
        return targetRPM;
    }

    public void stop() {
        targetRPM = 0;
        pid.reset();
        left.setPower(0);
        right.setPower(0);
    }

    public double getTicks() {
        return right.getCurrentPosition();
    }

    public void update() {
        updateRPM();

        double error = targetRPM - currentRPM;
        double feedforward = targetRPM * kF;

        double output = Range.clip(
                pid.calculate(error),
                0.0,
                1.0
        );

        left.setPower(output);
        right.setPower(output);
    }
}
