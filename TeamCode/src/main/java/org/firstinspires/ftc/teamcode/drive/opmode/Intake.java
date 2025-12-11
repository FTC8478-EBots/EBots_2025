package org.firstinspires.ftc.teamcode.drive.opmode;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;


public class Intake {
    DcMotorEx intakeMotor;
    Gamepad gamepad;
    Hopper hopper;
    ArtifactDetector artifactDetector;
    LightIndicator indicator;
    boolean buttonWasPressed = false;
    Servo intakeServo;
    Intake(HardwareMap hardwareMap, Gamepad gamepad, Hopper hopper, ArtifactDetector artifactDetector, LightIndicator indicator, Telemetry telemetry) {
        intakeMotor = hardwareMap.get(DcMotorEx.class, "intake");
        intakeServo = hardwareMap.get(Servo.class, "intakeServo");
        this.gamepad = gamepad;
        this.hopper = hopper;
        this.artifactDetector = artifactDetector;
        this.indicator = indicator;
    }

    void processGamepad() {

        if (gamepad.circle) {
            intakeMotor.setPower(1.0);

        }
        else if (gamepad.cross) {
            intakeMotor.setPower(-1.0);
            intakeServo.setPosition(1);
            if (artifactDetector.isArtifactDetected()) {
                hopper.timedNextPosition();
            }
                }
        else {
            // Turn off intake
            intakeMotor.setPower(0);
            intakeServo.setPosition(0.5);
            // Rotate hopper to  next position
            //hopper.nextPosition();

        }

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
}
