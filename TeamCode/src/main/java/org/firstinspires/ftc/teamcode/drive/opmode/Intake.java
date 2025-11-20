package org.firstinspires.ftc.teamcode.drive.opmode;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Intake {
    DcMotorEx intakeMotor;
    Gamepad gamepad;
    Telemetry telemetry;

    public Intake(HardwareMap hardwareMap, Gamepad gamepad, Telemetry telemetry) {
        intakeMotor = hardwareMap.get(DcMotorEx.class, "intake");
        this.gamepad = gamepad;
        this.telemetry = telemetry;


    }
    public class IntakeAction implements Action {
        private boolean initialized = false;
        public IntakeAction() {
        }

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            if (!initialized) {
                intakeMotor.setVelocity(1);
                initialized = true;
            }
            double vel = intakeMotor.getVelocity();
            packet.put("intakeVelocity",vel);
            //return true when still slow
            return vel<0.7;
        }
    }
    public Action intakeAction() {
        return new Intake.IntakeAction();
    }
    void processGamepad() {
        if (gamepad.circle) {
            intakeMotor.setPower(1.0);
        }
        if (gamepad.cross) {
            intakeMotor.setPower(-1.0);
        }
        else {
            intakeMotor.setPower(0);
        }

    }
}
