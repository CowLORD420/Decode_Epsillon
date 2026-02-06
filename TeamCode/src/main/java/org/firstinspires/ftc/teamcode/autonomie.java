package org.firstinspires.ftc.teamcode;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.commands.IntakeNext;
import org.firstinspires.ftc.teamcode.commands.OuttakeNext;
import org.firstinspires.ftc.teamcode.commands.PinpointSetPose;
import org.firstinspires.ftc.teamcode.commands.Push;
import org.firstinspires.ftc.teamcode.commands.Shoot;
import org.firstinspires.ftc.teamcode.commands.StopShooter;
import org.firstinspires.ftc.teamcode.hardware.Robot;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import org.firstinspires.ftc.teamcode.subsystems.Pinpoint;

@Autonomous
public class autonomie extends OpMode {
    Robot robot;
    IntakeNext intakeNext;
    StopShooter stopShooter;
    Follower follower;
    PathChain path1;
    boolean started = false;
    boolean pathFinished = false;
    boolean isFull = false;

    @Override
    public void init() {
        robot = new Robot(hardwareMap, gamepad1);
        robot.init();

        follower = Constants.createFollower(hardwareMap);

        Pose startPose = new Pose(55.727, 7.063, Math.toRadians(90));
        follower.setPose(startPose);

        robot.scheduler.schedule(new PinpointSetPose(
                robot.pinpoint,
                new Pose2D(DistanceUnit.INCH,
                        55.727,
                        7.063,
                        AngleUnit.RADIANS,
                        Math.toRadians(90)
                )
        ));
        robot.scheduler.schedule(intakeNext);
        robot.scheduler.update();

        path1 = follower.pathBuilder()
                .addPath(new BezierCurve(
                        new Pose(55.727, 7.063),
                        new Pose(53.010, 67.200),
                        new Pose(95.337, 95.707)
                ))
                .setLinearHeadingInterpolation(
                        Math.toRadians(90),
                        Math.toRadians(45)
                )
                .build();
    }

    @Override
    public void loop() {
        if (!started) {
            follower.followPath(path1);
            started = true;
        }

        if (follower.atParametricEnd() && !pathFinished) {
            robot.scheduler.schedule(new Shoot(robot.shooter, robot.pinpoint, Pinpoint.Color.RED));
            pathFinished = true;
        }
        if (robot.spindex.isFull() && !isFull){
            robot.scheduler.schedule(new OuttakeNext(robot.colorSensor, robot.spindex));
            isFull = true;
            //robot.scheduler.schedule(new Push(robot.picior));
        }
        robot.update();
        follower.update();
        robot.scheduler.update();
    }
}
