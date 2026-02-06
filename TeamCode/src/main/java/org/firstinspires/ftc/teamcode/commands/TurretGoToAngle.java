package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.scheduler.Command;
import org.firstinspires.ftc.teamcode.subsystems.Turret;

public class TurretGoToAngle implements Command {
    private final Turret turret;
    private final double angle;

    public TurretGoToAngle(Turret turret, double angle){
        this.turret = turret;
        this.angle = angle;
    }

    @Override
    public void start() {
        turret.goToAngle(angle);
    }

    @Override
    public void update() {
        turret.trackTarget();
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end() {
        turret.enableTracking();
    }
}
