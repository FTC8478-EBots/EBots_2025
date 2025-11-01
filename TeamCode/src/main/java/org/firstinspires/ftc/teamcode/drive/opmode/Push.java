package org.firstinspires.ftc.teamcode.drive.opmode;

import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Push {
    double position = 0;
    Gamepad gamepad;
    Servo pushServo;

    Push (HardwareMap hardwareMap, Gamepad gamepad) {
        this.gamepad = gamepad;
        pushServo = hardwareMap.get(Servo.class, "push");
        pushServo.setPosition(0);
    }

    void processGamepad() {
        if (gamepad.dpad_up) {
            pushServo.setPosition(1);
        }
        else {
            pushServo.setPosition(0);
        }
    }

}

