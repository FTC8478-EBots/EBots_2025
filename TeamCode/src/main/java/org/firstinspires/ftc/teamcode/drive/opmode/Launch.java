package org.firstinspires.ftc.teamcode.drive.opmode;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Launch {
    DcMotorEx launchMotor;
    Gamepad gamepad;

    Launch(HardwareMap hardwareMap, Gamepad gamepad) {
        launchMotor = hardwareMap.get(DcMotorEx.class, "launch");
        this.gamepad = gamepad;


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