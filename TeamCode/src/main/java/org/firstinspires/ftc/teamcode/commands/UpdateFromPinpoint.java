package org.firstinspires.ftc.teamcode.commands;

import com.pedropathing.geometry.Pose;

import org.firstinspires.ftc.teamcode.hardware.Robot;
import org.firstinspires.ftc.teamcode.scheduler.Command;
import org.firstinspires.ftc.teamcode.subsystems.CameraLocalization;
import org.firstinspires.ftc.teamcode.subsystems.DriveInput;
import org.firstinspires.ftc.teamcode.subsystems.Pedro;
import org.firstinspires.ftc.teamcode.subsystems.Pinpoint;
import org.firstinspires.ftc.teamcode.utils.Paths;

public class UpdateFromPinpoint implements Command {
    private final Paths paths;
    private final Pedro pedro;
    private final DriveInput input;
    private final CameraLocalization cameraLocalization;
    private final Pinpoint pinpoint;

    public UpdateFromPinpoint(
            Pinpoint pinpoint,
            Paths paths,
            Pedro pedro,
            DriveInput input,
            CameraLocalization cameraLocalization
    )
    {
        this.pinpoint = pinpoint;
        this.paths = paths;
        this.pedro = pedro;
        this.input = input;
        this.cameraLocalization = cameraLocalization;
    }

    @Override
    public void start() {

    }

    @Override
    public void update() {
        Pose currentPose = new Pose(pinpoint.getX(), pinpoint.getY(), pinpoint.getYaw());
        paths.updatePose(currentPose);
        pedro.updatePose(currentPose);
        input.updateHeading(pinpoint.getYaw());
        cameraLocalization.updateHeading(pinpoint.getYaw());
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end() {

    }
}
