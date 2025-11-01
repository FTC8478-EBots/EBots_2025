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
@Config
public class Launch {
   public static int LAUNCH_VELOCITY = 30000;
    DcMotorEx launchMotor;
    Gamepad gamepad;
    Telemetry telemetry;

    public Launch(HardwareMap hardwareMap, Gamepad gamepad, Telemetry telemetry) {
        launchMotor = hardwareMap.get(DcMotorEx.class, "launch");
        this.gamepad = gamepad;
        launchMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        launchMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        this.telemetry = telemetry;

    }

    void processGamepad() {
        if (gamepad.square) {
            launchMotor.setVelocity(0);
        }else if (gamepad.triangle) {
            launchMotor.setVelocity(LAUNCH_VELOCITY);
        }
        telemetry.addData("LAUNCH_VELOCITY:", launchMotor.getVelocity());

    }
    public class LaunchAction implements Action {
        private boolean initialized = false;

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            if (!initialized) {
                launchMotor.setVelocity(6000);
               // try {
               //     this.wait(1);
               // } catch (InterruptedException e) {
                    //Ignore this exception.
                    //throw new RuntimeException(e);
                //}
               // launchMotor.setVelocity(0);
                initialized = true;
            }

            double vel = launchMotor.getVelocity();
            packet.put("launchVelocity", vel);
            return vel > 5000;
        }
    }

    public Action launchAction() {
        return new LaunchAction();
    }
}