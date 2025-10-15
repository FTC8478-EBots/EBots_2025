package org.firstinspires.ftc.teamcode.drive.opmode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.Velocity;

public class Launch {
    DcMotorEx launchMotor;
    Gamepad gamepad;


    Launch(HardwareMap hardwareMap, Gamepad gamepad) {
        launchMotor = hardwareMap.get(DcMotorEx.class, "launch");
        this.gamepad = gamepad;
        launchMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        launchMotor.setDirection(DcMotorSimple.Direction.REVERSE);

    }

    void processGamepad() {
        if (gamepad.square) {
            launchMotor.setVelocity(0);
        }else if (gamepad.triangle) {
            launchMotor.setVelocity(51200);
        }
    }

}