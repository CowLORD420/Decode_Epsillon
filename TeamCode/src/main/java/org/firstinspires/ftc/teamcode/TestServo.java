package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class TestServo extends OpMode {
    Servo servoLeft;
    Servo servoRight;
    boolean pressed;
    boolean pressed2;
    boolean pressed3;
    boolean first = true;
    double pose;

    @Override
    public void init() {
        servoLeft = hardwareMap.get(Servo.class, "servoLeft");
        servoRight = hardwareMap.get(Servo.class, "servoRight");
        servoLeft.setDirection(Servo.Direction.REVERSE);
    }

    @Override
    public void loop() {
        if (gamepad1.x && !pressed){
            pose += 0.01;
            pressed = true;
        } else if (!gamepad1.x) {
            pressed = false;
        }

        if (gamepad1.b && !pressed2){
            pose -= 0.005;
            pressed2 = true;
        } else if (!gamepad1.b){
            pressed2 = false;
        }

        if (gamepad1.a && !pressed3) {
            pressed3 = true;
            first = !first;
        } else if (!gamepad1.a) {
            pressed3 = false;
        }
        if (first){
            servoLeft.setPosition(pose);
        }else {
            servoRight.setPosition(pose);
        }

        telemetry.addData("pos", pose);
        telemetry.update();
    }
}
