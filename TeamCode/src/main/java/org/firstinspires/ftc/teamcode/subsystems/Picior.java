package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.Servo;

public class Picior {
    Servo servo;

    public Picior (Servo servo){
        this.servo = servo;
        servo.setDirection(Servo.Direction.REVERSE);
    }

    final double JOS = 0;
    final double SUS = 0.45;

    public void down(){
        servo.setPosition(JOS);
    }

    public void up(){
        servo.setPosition(SUS);
    }

}
