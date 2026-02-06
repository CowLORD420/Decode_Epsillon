package org.firstinspires.ftc.teamcode.commands;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.scheduler.Command;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;

public class StopShooter implements Command {
    private final Shooter shooter;

    public StopShooter(Shooter shooter){
        this.shooter = shooter;
    }

    @Override
    public void start() {
        shooter.stop();
    }

    @Override
    public void update() {
    }

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public void end() {

    }
}
