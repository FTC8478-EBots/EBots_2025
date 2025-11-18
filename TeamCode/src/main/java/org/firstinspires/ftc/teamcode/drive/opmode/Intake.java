package org.firstinspires.ftc.teamcode.drive.opmode;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;


public class Intake {
    DcMotorEx intakeMotor;
    Gamepad gamepad;
    Hopper hopper;
    boolean buttonWasPressed = false;

    Intake(HardwareMap hardwareMap, Gamepad gamepad, Hopper hopper) {
        intakeMotor = hardwareMap.get(DcMotorEx.class, "intake");
        this.gamepad = gamepad;
        this.hopper = hopper;
    }

    void processGamepad() {

        if (gamepad.circle) {
            intakeMotor.setPower(1.0);
        }
        else if (gamepad.cross) {
            intakeMotor.setPower(-1.0);
        }
        else {
            // Turn off intake
            intakeMotor.setPower(0);

            // Rotate hopper to  next position
            //hopper.nextPosition();

        }

    }
}