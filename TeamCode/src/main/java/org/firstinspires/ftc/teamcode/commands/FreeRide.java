package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.hardware.Robot;
import org.firstinspires.ftc.teamcode.scheduler.Command;
import org.firstinspires.ftc.teamcode.subsystems.DriveInput;
import org.firstinspires.ftc.teamcode.subsystems.MecanumDrive;

public class FreeRide implements Command {
    private final MecanumDrive mecanumDrive;
    private final DriveInput input;

    public FreeRide(MecanumDrive mecanumDrive, DriveInput driveInput){
        this.mecanumDrive = mecanumDrive;
        input = driveInput;
    }

    @Override
    public void start() {

    }

    @Override
    public void update() {
        double y = input.getYInput();
        double x = input.getXInput();
        double rx = input.getRxInput();
        mecanumDrive.drive(y, x, rx);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end() {
        mecanumDrive.drive(0, 0, 0);
    }
}
