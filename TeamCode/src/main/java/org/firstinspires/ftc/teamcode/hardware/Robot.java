package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.subsystems.CameraLocalization;
import org.firstinspires.ftc.teamcode.subsystems.Drive;
import org.firstinspires.ftc.teamcode.subsystems.IMU;
import org.firstinspires.ftc.teamcode.subsystems.MecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.Pipeline;

public class Robot {
    public final Pipeline pipeline;
    public final MecanumDrive mecanumDrive;
    public final IMU imu;
    public final Drive input;
    public final CameraLocalization cameraLocalization;

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
                hmap.get(Limelight3A.class, "Limelight_2"),
                imu
        );

        input = new Drive(gamepad1, imu);


    }

    public void update(){
        cameraLocalization.update();
        pipeline.update();
        imu.update();
        input.update();
    }

}
