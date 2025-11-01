
package org.firstinspires.ftc.teamcode.drive.opmode;

import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Hopper {
    double position = 0;
    Servo hopperServo;
    Gamepad gamepad;

    Hopper(HardwareMap hardwareMap, Gamepad gamepad) {
        this.gamepad = gamepad;
        hopperServo = hardwareMap.get(Servo.class, "hopper");
        hopperServo.setPosition(0);

    }

    void processGamepad() {

        if (gamepad.right_bumper) {
            // TODO: Set hopper to position 2 (so that ball 2 is at the top)
            position = 0;

      } else if (gamepad.left_bumper)
        {
            // TODO: Set hopped to position 3  (so that ball 3 is at the top)
           position = 1.0;
        } else {
            // TODO: Set hopper to default position 1  (so that ball 1 is at the top)
           position = 0.5;
        }
        hopperServo.setPosition(position);
    }

}
