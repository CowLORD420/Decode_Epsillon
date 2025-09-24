package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;

public class CameraLocalization {
    private final Limelight3A limelight;
    private Pose3D pose = null;
    private final IMU imu;
    private long delay = 0;
    private long now = System.nanoTime();
    private boolean running;

    public CameraLocalization(Limelight3A limelight3A, IMU imu){
        this.imu = imu;
        this.limelight = limelight3A;
        limelight.pipelineSwitch(0);
        limelight.setPollRateHz(100);
    }

    public void update (){
        if(!running) return;

        limelight.updateRobotOrientation(imu.getYaw());
        LLResult result = limelight.getLatestResult();

        if (result != null) {
            delay = (long) (result.getCaptureLatency() + result.getParseLatency());
            pose = result.getBotpose_MT2();
            return;
        }

        pose = null;
    }

    public double getCamX() { return pose.getPosition().x; }
    public double getCamY() { return pose.getPosition().y; }
    public double getCamYaw () { return pose.getOrientation().getYaw(AngleUnit.RADIANS); }

    public long captureTime() {
        return now - (long)(delay * 1e6);}

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
