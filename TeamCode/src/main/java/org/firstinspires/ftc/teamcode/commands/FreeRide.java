package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.scheduler.Command;
import org.firstinspires.ftc.teamcode.subsystems.DriveInput;
import org.firstinspires.ftc.teamcode.subsystems.MecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.Pinpoint;

public class FreeRide implements Command {
    private final MecanumDrive mecanumDrive;
    private final DriveInput input;
    private final Pinpoint pinpoint;
    public FreeRide(MecanumDrive mecanumDrive, DriveInput driveInput, Pinpoint pinpoint){
        this.mecanumDrive = mecanumDrive;
        input = driveInput;
        this.pinpoint = pinpoint;
    }

    @Override
    public void start() {

    }

    @Override
    public void update() {
        input.updateHeading(pinpoint.getYaw());
        double y = input.getY();
        double x = input.getX();
        double rx = input.getRx();
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
