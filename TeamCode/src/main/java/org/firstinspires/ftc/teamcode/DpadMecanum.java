package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp
public class DpadMecanum extends OpMode {
    private DcMotorEx lf;
    private DcMotorEx lb;
    private DcMotorEx rf;
    private DcMotorEx rb;

    private final double POWER = 0.5;

    enum Button{
        DPAD_LEFT,
        DPAD_RIGHT,
        DPAD_UP,
        DPAD_DOWN,
        RIGHT_BUMPER,
        LEFT_BUMPER,
        STOP
    }

    Button button = Button.STOP;

    @Override
    public void init() {
        lf = hardwareMap.get(DcMotorEx.class, "lf");
        lb = hardwareMap.get(DcMotorEx.class, "lb");
        rf = hardwareMap.get(DcMotorEx.class, "rf");
        rb = hardwareMap.get(DcMotorEx.class, "rb");

        lb.setDirection(DcMotorSimple.Direction.REVERSE);
        lf.setDirection(DcMotorSimple.Direction.REVERSE);
        rf.setDirection(DcMotorSimple.Direction.FORWARD);
        rb.setDirection(DcMotorSimple.Direction.FORWARD);
    }

    @Override
    public void loop() {
        switch (button){
            case DPAD_UP:
                forward(POWER);
                break;

            case DPAD_DOWN:
                backwards(POWER);
                break;

            case DPAD_LEFT:
                strafeLeft(POWER);
                break;

            case DPAD_RIGHT:
                strafeRight(POWER);
                break;

            case LEFT_BUMPER:
                turnLeft(POWER);
                break;

            case RIGHT_BUMPER:
                turnRight(POWER);
                break;

            case STOP:
                stop();
                break;
        }

        if (gamepad1.dpad_up) button = Button.DPAD_UP;
        else if (gamepad1.dpad_down) button = Button.DPAD_DOWN;
        else if (gamepad1.dpad_left) button = Button.DPAD_LEFT;
        else if (gamepad1.dpad_right) button = Button.DPAD_RIGHT;
        else if (gamepad1.left_bumper) button = Button.LEFT_BUMPER;
        else if (gamepad1.right_bumper) button = Button.RIGHT_BUMPER;
        else button = Button.STOP;

    }

    private void forward(double power){
        lb.setPower(power);
        lf.setPower(power);
        rb.setPower(power);
        rf.setPower(power);
    }

    private void backwards(double power){
        lb.setPower(-power);
        lf.setPower(-power);
        rb.setPower(-power);
        rf.setPower(-power);
    }

    private void turnLeft(double power){
        lb.setPower(-power);
        lf.setPower(-power);
        rb.setPower(power);
        rf.setPower(power);
    }

    private void turnRight(double power){
        lb.setPower(power);
        lf.setPower(power);
        rb.setPower(-power);
        rf.setPower(-power);
    }

    private void strafeLeft(double power){
        lb.setPower(power);
        lf.setPower(-power);
        rb.setPower(-power);
        rf.setPower(power);
    }

    private void strafeRight(double power){
        lb.setPower(-power);
        lf.setPower(power);
        rb.setPower(power);
        rf.setPower(-power);
    }

    public void stop(){
        lb.setPower(0);
        lf.setPower(0);
        rb.setPower(0);
        rf.setPower(0);
    }
}
