
package org.firstinspires.ftc.teamcode.drive.opmode;

import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Hopper {
    double position = 0;
    Servo hopperServo;
    Gamepad gamepad;

    Hopper (HardwareMap hardwareMap, Gamepad gamepad) {
         hopperServo = hardwareMap.get(Servo.class, "hopper");
        this.gamepad = gamepad;


    }

    void processGamepad() {
        if (gamepad.right_bumper) {
            position = .5;//position +1) %3;
        }else if (gamepad.left_bumper){
            position = -.5;//(position -1) %3;
        }else {
            position = 0;
       }
        moveServo(position);
    }
    void moveServo(double p){
        hopperServo.setPosition(p);
    }

}
