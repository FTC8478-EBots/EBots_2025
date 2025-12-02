package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;

public class AutoSteerCamera {
    private Limelight3A limelight;
    private Telemetry telemetry;

    public AutoSteerCamera (HardwareMap hardwareMap, Telemetry telemetry) {
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        this.telemetry = telemetry;

    }
public double distancetogoal(){
    LLResult result = limelight.getLatestResult();
    if (result != null) {
        if (result.isValid()) {
            result.getBotposeAvgDist();
            double distance = result.getBotposeAvgDist();
            telemetry.addData("distance", distance);
return distance;

        }

}
        return -1;
}

public void updateResults() {
    LLResult result = limelight.getLatestResult();
            if (result != null) {
        if (result.isValid()) {
            Pose3D botpose = result.getBotpose();
            telemetry.addData("Angle left-right", result.getTx());
            telemetry.addData("Angle up-down", result.getTy());
            telemetry.addData("Robot position", botpose.toString());

        }
    }
    }
}