package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.scheduler.Command;
import org.firstinspires.ftc.teamcode.subsystems.CameraLocalization;
import org.firstinspires.ftc.teamcode.subsystems.Turret;

public class TurretTrack implements Command {
    private final Turret turret;
    private final CameraLocalization camera;

    public TurretTrack(Turret turret, CameraLocalization camera){
        this.turret = turret;
        this.camera = camera;
    }

    @Override
    public void start() {
        camera.start();
    }

    @Override
    public void update() {
        camera.update();
        turret.updateError(camera.getTxError());
        turret.trackTarget();
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end() {

    }
}
