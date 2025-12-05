package org.firstinspires.ftc.teamcode.drive.opmode;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;
import org.firstinspires.ftc.teamcode.AutoSteerCamera;

@Config
public class Launch {
    public static double LAUNCH_VELOCITY = 1900; //-2300
    double calculatedLaunchVelocity = LAUNCH_VELOCITY;
    static double defaultDistance = 1.0;
    double velocityPerMeter = 300;
    double targetVelocity;
    DcMotorEx launchMotor;
    Gamepad gamepad;
    Telemetry telemetry;
    AutoSteerCamera autoSteerCamera;
    public Launch(HardwareMap hardwareMap, Gamepad gamepad, Telemetry telemetry, AutoSteerCamera autoSteerCamera) {
        this.autoSteerCamera = autoSteerCamera;
        launchMotor = hardwareMap.get(DcMotorEx.class, "launch");
        this.gamepad = gamepad;
        launchMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //launchMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        this.telemetry = telemetry;


    }
    private void calculateLaunchVelocity(){
        if (autoSteerCamera == null) return;
        double distance = autoSteerCamera.distancetogoal();
        if (distance > 0){
            calculatedLaunchVelocity = LAUNCH_VELOCITY + (distance - defaultDistance) * velocityPerMeter;

        } else {
            calculatedLaunchVelocity = LAUNCH_VELOCITY;

        }

    }



    void processGamepad() {
        if (gamepad.square) {
            launchMotor.setVelocity(-LAUNCH_VELOCITY);
        }else if (gamepad.triangle) {
            launchMotor.setVelocity(LAUNCH_VELOCITY);
        } else {
            launchMotor.setVelocity(0);
        }
    //    telemetry.addData("LAUNCH_VELOCITY:", launchMotor.getVelocity());
    }

    boolean isFast() {
        return (launchMotor.getVelocity()/targetVelocity)>.8;
    }

    public class LaunchAction implements Action {
        private boolean initialized = false;
        public LaunchAction(double launchVelocity) {
            targetVelocity = launchVelocity;
        }

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            if (!initialized) {
                launchMotor.setVelocity(targetVelocity);
                initialized = true;
            }
            double vel = launchMotor.getVelocity();
      //      packet.put("launchVelocity",vel);
            //return true when still slow
            return !isFast();
        }
    }
    public Action launchAction() {
        return new LaunchAction(LAUNCH_VELOCITY);
    }
    
    public Action stopAction() {
        return new LaunchAction(0);
    }

}
