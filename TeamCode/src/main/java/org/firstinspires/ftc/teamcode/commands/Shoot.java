package org.firstinspires.ftc.teamcode.commands;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.scheduler.Command;
import org.firstinspires.ftc.teamcode.subsystems.Pinpoint;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;

public class Shoot implements Command {
    private final Shooter shooter;
    private final Pinpoint pinpoint;
    private final Pinpoint.Color color;

    public Shoot(Shooter shooter, Pinpoint pinpoint, Pinpoint.Color color){
        this.shooter = shooter;
        this.pinpoint = pinpoint;
        this.color = color;
    }


    @Override
    public void start() {

    }

    @Override
    public void update() {
        shooter.setTargetRPM(pinpoint.getDistanceToTarget(color));
        shooter.update();
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end() {
        shooter.stop();
    }
}
