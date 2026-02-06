package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp
public class TestMotor extends OpMode {
    DcMotor lb;
    DcMotor lf;
    DcMotor rb;
    DcMotor rf;

    @Override
    public void init() {
        lb = hardwareMap.get(DcMotor.class, "lf");
        lf = hardwareMap.get(DcMotor.class, "lb");
        rb = hardwareMap.get(DcMotor.class, "rf");
        rf = hardwareMap.get(DcMotor.class, "rb");
    }

    @Override
    public void loop() {
        if(gamepad1.a){
            lb.setPower(1);
        }else lb.setPower(0);

        if (gamepad1.b) lf.setPower(1);
        else lf.setPower(0);

        if(gamepad1.x){
            rb.setPower(1);
        }else rb.setPower(0);

        if (gamepad1.y) rf.setPower(1);
        else rf.setPower(0);

        telemetry.addData("pos", lf.getCurrentPosition());

    }
}
