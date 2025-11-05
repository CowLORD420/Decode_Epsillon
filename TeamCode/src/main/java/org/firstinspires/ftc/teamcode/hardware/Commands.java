package org.firstinspires.ftc.teamcode.hardware;

import org.firstinspires.ftc.teamcode.commands.CameraScan;
import org.firstinspires.ftc.teamcode.commands.FreeRide;
import org.firstinspires.ftc.teamcode.commands.PoseResetFromCamera;
import org.firstinspires.ftc.teamcode.commands.UpdateFromPinpoint;
import org.firstinspires.ftc.teamcode.subsystems.CameraLocalization;
import org.firstinspires.ftc.teamcode.subsystems.DriveInput;
import org.firstinspires.ftc.teamcode.subsystems.MecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.Pedro;
import org.firstinspires.ftc.teamcode.subsystems.Pinpoint;
import org.firstinspires.ftc.teamcode.subsystems.Pipeline;
import org.firstinspires.ftc.teamcode.utils.Paths;

public class Commands {

    public final FreeRide freeRide;
    public final CameraScan cameraScan;
    public final PoseResetFromCamera poseResetFromCamera;
    public final UpdateFromPinpoint updateFromPinpoint;

    public Commands(Robot robot){
        Pedro pedro = robot.pedro;
        Paths paths = robot.paths;
        Pipeline pipeline = robot.pipeline;
        CameraLocalization camLocal = robot.cameraLocalization;
        MecanumDrive mecanumDrive = robot.mecanumDrive;
        DriveInput driveInput = robot.input;
        Pinpoint pinpoint = robot.pinpoint;

        freeRide = new FreeRide(mecanumDrive, driveInput, pinpoint);
        cameraScan = new CameraScan(pipeline);
        poseResetFromCamera = new PoseResetFromCamera(camLocal, pinpoint);
        updateFromPinpoint = new UpdateFromPinpoint(pinpoint, paths, pedro, driveInput, camLocal);
    }

}
