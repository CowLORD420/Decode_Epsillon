package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;

import java.util.List;

public class CameraLocalization {
    private final Limelight3A limelight;
    private Pose3D pose = null;
    private boolean running;
    private int latestTagId;
    private double yaw;

    public CameraLocalization(Limelight3A limelight3A){
        this.limelight = limelight3A;
        limelight.pipelineSwitch(0);
        limelight.setPollRateHz(100);
    }

    public void update (){
        if(!running) return;

        limelight.updateRobotOrientation(yaw);
        LLResult result = limelight.getLatestResult();

        if (result != null) {
            List<LLResultTypes.FiducialResult> fiducials = result.getFiducialResults();

            if (fiducials != null && !fiducials.isEmpty()) {
                latestTagId = fiducials.get(0).getFiducialId();
            }

            pose = result.getBotpose_MT2();
            return;
        }

        pose = null;
    }

    public void changePipeline(int pipelineNumber){
        limelight.pipelineSwitch(pipelineNumber);
    }

    public void updateHeading(double headingInRadians){
        yaw = headingInRadians;
    }

    public Pose2D getPose2d(){ return Pose3Dto2D(pose); }
    public int getLatestTagId() { return latestTagId; }
    public double getCamX() { return pose.getPosition().x; }
    public double getCamY() { return pose.getPosition().y; }
    public double getCamYaw () { return pose.getOrientation().getYaw(AngleUnit.RADIANS); }

    public void start() {
        limelight.start();
        running = true;
    }

    public void stop() {
        limelight.stop();
        running = false;
        pose = null;
    }

    private Pose2D Pose3Dto2D (Pose3D pose3D){
        Pose2D pose2D = new Pose2D(DistanceUnit.INCH, pose3D.getPosition().x, pose3D.getPosition().y, AngleUnit.RADIANS, pose3D.getOrientation().getYaw(AngleUnit.RADIANS));
        return pose2D;
    }
}
