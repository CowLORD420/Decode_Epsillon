package org.firstinspires.ftc.teamcode.hardware;

import com.pedropathing.follower.Follower;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import org.firstinspires.ftc.teamcode.subsystems.CameraLocalization;
import org.firstinspires.ftc.teamcode.subsystems.DriveInput;
import org.firstinspires.ftc.teamcode.subsystems.IMU;
import org.firstinspires.ftc.teamcode.subsystems.MecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.Pedro;
import org.firstinspires.ftc.teamcode.subsystems.Pinpoint;
import org.firstinspires.ftc.teamcode.subsystems.Pipeline;
import org.firstinspires.ftc.teamcode.utils.GoBildaPinpointDriver;
import org.firstinspires.ftc.teamcode.utils.Paths;

public class Robot {
    public final Pipeline pipeline;
    public final MecanumDrive mecanumDrive;
    public final IMU imu;
    public final DriveInput input;
    public final CameraLocalization cameraLocalization;
    public final Pinpoint pinpoint;
    public final Pedro pedro;
    public final Paths paths;
    public final Follower follower;

    public Robot(HardwareMap hmap, Gamepad gamepad1){

        pipeline = new Pipeline(
                hmap.get(Limelight3A.class, "Limelight")
        );

        mecanumDrive = new MecanumDrive(
                hmap.get(DcMotorEx.class, "lf"),
                hmap.get(DcMotorEx.class, "lb"),
                hmap.get(DcMotorEx.class, "rf"),
                hmap.get(DcMotorEx.class, "rb")
        );

        imu = new IMU(
          hmap.get(com.qualcomm.robotcore.hardware.IMU.class, "IMU")
        );

        cameraLocalization = new CameraLocalization(
                hmap.get(Limelight3A.class, "limelight_2")
        );

        input = new DriveInput(gamepad1);

        pinpoint = new Pinpoint(
                hmap.get(GoBildaPinpointDriver.class, "pinpoint")
        );
        follower = Constants.createFollower(hmap);

        pedro = new Pedro(follower);

        paths = new Paths(follower, pedro);

        paths.registerPaths();
    }

    public void update(){
        pinpoint.update();
        cameraLocalization.update();
        pipeline.update();
        input.update();
        pedro.update();
    }

}
