package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
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

}
