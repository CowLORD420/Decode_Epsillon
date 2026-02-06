package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class Intake {
    private final DcMotorEx motor;

    public Intake(DcMotorEx motor){
        this.motor = motor;
        motor.setDirection(DcMotorSimple.Direction.FORWARD);
        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void start(){
        motor.setPower(1);
    }

    public void stop(){
        motor.setPower(0);
    }

}
