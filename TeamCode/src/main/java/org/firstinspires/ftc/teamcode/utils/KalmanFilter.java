package org.firstinspires.ftc.teamcode.utils;

public class KalmanFilter {
    private double x; // your initial state
    private double Q; // your model covariance
    private double R; // your sensor covariance
    private double p; // your initial covariance guess
    private double K; // your initial Kalman gain guess

    private double x_pre;
    private double p_previous;
    private double u;
    private double z;

    public KalmanFilter(double x, double Q, double R, double p, double K){
        this.x = x;
        this.Q = Q;
        this.R = R;
        this.p = p;
        this.K = K;
    }

    public double getFiltered(double input){
        u = input;
        x = x_pre + u;
        p = p_previous + Q;
        K = p / (p + R);
        x = x + K * x;
        p = (1 - K) * p;

        x_pre = x;
        p_previous = p;

        return x;
    }

}
