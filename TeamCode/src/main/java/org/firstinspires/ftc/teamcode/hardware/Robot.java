package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.scheduler.CommandScheduler;
import org.firstinspires.ftc.teamcode.subsystems.CameraLocalization;
import org.firstinspires.ftc.teamcode.subsystems.ColorSensor;
import org.firstinspires.ftc.teamcode.subsystems.DriveInput;
import org.firstinspires.ftc.teamcode.subsystems.GamepadWrapper;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.MecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.Picior;
import org.firstinspires.ftc.teamcode.subsystems.Pinpoint;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;
import org.firstinspires.ftc.teamcode.subsystems.Spindex;
import org.firstinspires.ftc.teamcode.subsystems.Turret;
import org.firstinspires.ftc.teamcode.utils.GoBildaPinpointDriver;

public class Robot {
    public MecanumDrive mecanumDrive;
    public DriveInput input;
    public GamepadWrapper wrapper;
    public CommandScheduler scheduler;
    public Shooter shooter;
    public ColorSensor colorSensor;
    public Spindex spindex;
    public Intake intake;
    public Pinpoint pinpoint;
    public Picior picior;
    public Turret turret;
    public CameraLocalization cameraLocalization;

    private final HardwareMap hmap;
    private final Gamepad gamepad;

    public Robot(HardwareMap hmap, Gamepad gamepad){
        this.hmap = hmap;
        this.gamepad = gamepad;
    }

    public void init (){
        scheduler = new CommandScheduler();

        wrapper = new GamepadWrapper.Builder(gamepad, scheduler)
                .build();

        mecanumDrive = new MecanumDrive(
                hmap.get(DcMotorEx.class, "lf"),
                hmap.get(DcMotorEx.class, "lb"),
                hmap.get(DcMotorEx.class, "rf"),
                hmap.get(DcMotorEx.class, "rb")
        );

        input = new DriveInput(gamepad);

        pinpoint = new Pinpoint(
                hmap.get(GoBildaPinpointDriver.class, "pinpoint")
        );


        shooter = new Shooter(
                hmap.get(DcMotorEx.class, "shooter_left"),
                hmap.get(DcMotorEx.class, "shooter_right")
        );

        cameraLocalization = new CameraLocalization(hmap.get(Limelight3A.class, "limelight"));

        colorSensor = new ColorSensor(hmap.get(com.qualcomm.robotcore.hardware.ColorSensor.class, "color"));

        spindex = new Spindex(hmap.get(DcMotorEx.class, "spindex"));

        intake = new Intake(hmap.get(DcMotorEx.class, "intake"));

        picior = new Picior(hmap.get(Servo.class, "picior"));
    }

    public void initRobot(){
        turret.init();
    }

    public void update(){
        spindex.update();
        scheduler.update();
        pinpoint.update();
    }

    public void stop(){
        scheduler.cancelAll();
    }
}
