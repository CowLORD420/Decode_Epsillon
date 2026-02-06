package org.firstinspires.ftc.teamcode.subsystems;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;

import java.util.List;

public class CameraLocalization {
    private final Limelight3A limelight;
    private Pose3D pose = null;
    private boolean running = false;
    private int latestTagId = 0;
    private double yaw;
    private LLResult result;
    private static final double EPSILON = 0;

    public CameraLocalization(Limelight3A limelight3A){
        this.limelight = limelight3A;
        limelight.pipelineSwitch(0);
        limelight.setPollRateHz(100);
    }

    public void update (){
        if(!running) return;

        limelight.updateRobotOrientation(Math.toDegrees(yaw));
        result = limelight.getLatestResult();

        if (result != null) {
            List<LLResultTypes.FiducialResult> fiducials = result.getFiducialResults();

            if (fiducials != null && !fiducials.isEmpty()) {
                latestTagId = fiducials.get(0).getFiducialId();
            }
            return;
        }

        pose = null;
    }

    public double getTxError() {
        if (result == null) return 0;

        if (Math.abs(result.getTx()) < EPSILON) return 0;
        return result.getTx(); // degrees
    }


    public void changePipeline(int pipelineNumber){
        limelight.pipelineSwitch(pipelineNumber);
    }

    public void updateHeading(double headingInRadians){ yaw = headingInRadians; }

    private Pose3D megaTag(){ return result.getBotpose(); }

    private Pose3D megaTag2(){ return result.getBotpose_MT2(); }
    public int getLatestTagId() { return latestTagId; }

    public double getX1(){ return megaTag().getPosition().x; }
    public double getY1() { return megaTag().getPosition().y; }

    public double getX2() { return megaTag2().getPosition().x; }
    public double getY2() { return megaTag2().getPosition().y; }
    public double getCamYaw () { return pose.getOrientation().getYaw(AngleUnit.RADIANS); }

    public ColorSensor.DetectedColor getColor1(){
        if (getLatestTagId() == 22) return  ColorSensor.DetectedColor.PURPLE;
        else if (getLatestTagId() == 23) return ColorSensor.DetectedColor.PURPLE;
        else return ColorSensor.DetectedColor.GREEN;
    }

    public ColorSensor.DetectedColor getColor2(){
        if (getLatestTagId() == 22) return  ColorSensor.DetectedColor.GREEN;
        else if (getLatestTagId() == 23) return ColorSensor.DetectedColor.PURPLE;
        else return ColorSensor.DetectedColor.PURPLE;
    }
    public ColorSensor.DetectedColor getColor3(){
        if (getLatestTagId() == 22) return  ColorSensor.DetectedColor.PURPLE;
        else if (getLatestTagId() == 23) return ColorSensor.DetectedColor.GREEN;
        else return ColorSensor.DetectedColor.PURPLE;
    }

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
