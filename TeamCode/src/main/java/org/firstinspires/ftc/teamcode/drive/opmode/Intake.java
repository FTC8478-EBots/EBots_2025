package org.firstinspires.ftc.teamcode.drive.opmode;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Intake {
    DcMotorEx intakeMotor;
    Gamepad gamepad;

    Intake(HardwareMap hardwareMap, Gamepad gamepad) {
        intakeMotor = hardwareMap.get(DcMotorEx.class, "intake");
        this.gamepad = gamepad;


    }

    void processGamepad() {
        if (gamepad.circle) {
            intakeMotor.setPower(0.5);
        }
        if (gamepad.cross) {
            intakeMotor.setPower(-0.5);
        }

    }
}