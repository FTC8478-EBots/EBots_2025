package org.firstinspires.ftc.teamcode.drive.opmode;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Launch {
    public DcMotorEx launchMotor;
    Gamepad gamepad;

    public Launch(HardwareMap hardwareMap, Gamepad gamepad) {
        this.launchMotor = hardwareMap.get(DcMotorEx.class, "launch");
        this.gamepad = gamepad;


    }
    public class AutoLaunch implements Action {
        private boolean initialized = false;

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            if (!initialized) {
                launchMotor.setPower(0.8);
                initialized = true;
            }

            double vel = launchMotor.getVelocity();
            packet.put("launchVelocity", vel);
            return vel < 10_000.0;
        }
    }

    public Action autoLaunch() {
        return new  AutoLaunch();
    }
    public class StopLaunch implements Action {
        private boolean initialized = false;

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            if (!initialized) {
                launchMotor.setPower(0);
                initialized = true;
            }

            double vel = launchMotor.getVelocity();
            packet.put("launchVelocity", vel);
            return vel < 10_000.0;
        }
    }

    public Action stopLaunch() {
        return new  StopLaunch();
    }
    public void activateLaunch() {
        launchMotor.setPower(0.5);
    }
    void deactivateLaunch() {
        launchMotor.setPower(0);
    }
    void processGamepad() {
        if (gamepad.square) {
            launchMotor.setPower(0.5);
        }else if (gamepad.triangle){
            launchMotor.setPower(-0.5);
        }else {
            launchMotor.setPower(0);
        }
    }

}