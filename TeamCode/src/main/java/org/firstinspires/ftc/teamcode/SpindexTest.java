package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.commands.IntakeNext;
import org.firstinspires.ftc.teamcode.commands.OuttakeNext;
import org.firstinspires.ftc.teamcode.scheduler.CommandScheduler;
import org.firstinspires.ftc.teamcode.subsystems.ColorSensor;
import org.firstinspires.ftc.teamcode.subsystems.Spindex;
@TeleOp
public class SpindexTest extends OpMode {
    Spindex spindex;
    ColorSensor sensor;
    IntakeNext intakeNext;
    OuttakeNext outtakeNext;

    CommandScheduler scheduler = new CommandScheduler();

    boolean alreadyPressed = false;
    boolean alreadyPressed1 = false;
    boolean alreadyPressed2 = false;
    boolean alreadyPressed3 = false;

    @Override
    public void init() {
        spindex = new Spindex(hardwareMap.get(DcMotorEx.class, "spindex"));
        sensor = new ColorSensor(hardwareMap.get(com.qualcomm.robotcore.hardware.ColorSensor.class, "color"));
        intakeNext = new IntakeNext(sensor, spindex);
        outtakeNext = new OuttakeNext(sensor, spindex);
    }

    @Override
    public void loop() {
        if (gamepad1.a && !alreadyPressed){
            scheduler.schedule(intakeNext);
            alreadyPressed = true;
        }else if (!gamepad1.a) alreadyPressed = false;

        if (gamepad1.b && !alreadyPressed1){
            scheduler.schedule(outtakeNext);
            alreadyPressed1 = true;
        }else if (!gamepad1.b) alreadyPressed1 = false;

        if (gamepad1.x && !alreadyPressed2){
            scheduler.schedule(outtakeNext);
            alreadyPressed2 = true;
        }else if (!gamepad1.x) alreadyPressed2 = false;

        if (gamepad1.y && !alreadyPressed3){
            scheduler.schedule(outtakeNext);
            alreadyPressed3 = true;
        }else if (!gamepad1.y) alreadyPressed3 = false;

        if (gamepad1.dpad_right)spindex.setMode(Spindex.Mode.INTAKE);
        if (gamepad1.dpad_left)spindex.setMode(Spindex.Mode.OUTTAKE);

        if (gamepad1.dpad_up)spindex.goToNextPosition();
        if (gamepad1.dpad_down)spindex.goToPosition(0);

        scheduler.update();
        telemetry.addData("dfshgtasd", spindex.getMode());
        telemetry.addData("1", sensor.getColorAt(0));
        telemetry.addData("2", sensor.getColorAt(1));
        telemetry.addData("3", sensor.getColorAt(2));
        telemetry.addData("full", spindex.isFull());
        telemetry.addData("pos", spindex.getCurrentPosition());
        telemetry.update();
        spindex.update();
    }
}
