package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class MecanumDrive {
    private final DcMotorEx lf;
    private final DcMotorEx lb;
    private final DcMotorEx rf;
    private final DcMotorEx rb;
    private double last_front_left = 0;
    private double last_front_right = 0;
    private double last_back_left = 0;
    private double last_back_right = 0;

    public MecanumDrive(DcMotorEx lf, DcMotorEx lb, DcMotorEx rf, DcMotorEx rb){
        this.lb = lb;
        this.rb = rb;
        this.lf = lf;
        this.rf = rf;

        lb.setDirection(DcMotorSimple.Direction.REVERSE);
        lf.setDirection(DcMotorSimple.Direction.REVERSE);
        rf.setDirection(DcMotorSimple.Direction.FORWARD);
        rb.setDirection(DcMotorSimple.Direction.FORWARD);
    }

        public void drive(double y, double x, double rot){
            double threshold = 0.005;

            double front_left  = y + x + rot;
            double front_right = y - x - rot;
            double back_left   = y - x + rot;
            double back_right  = y + x - rot;

            double max = Math.max(1.0, Math.max(Math.abs(front_left), Math.max(Math.abs(front_right), Math.max(Math.abs(back_left), Math.abs(back_right)))));

            front_left  /= max;
            front_right /= max;
            back_left   /= max;
            back_right  /= max;

            if (Math.abs(front_left - last_front_left) > threshold) {
                lf.setPower(front_left);
                last_front_left = front_left;
            }

            if (Math.abs(front_right - last_front_right) > threshold) {
                rf.setPower(front_right);
                last_front_right = front_right;
            }

            if (Math.abs(back_left - last_back_left) > threshold) {
                lb.setPower(back_left);
                last_back_left = back_left;
            }

            if (Math.abs(back_right - last_back_right) > threshold) {
                rb.setPower(back_right);
                last_back_right = back_right;
            }

        }
}
