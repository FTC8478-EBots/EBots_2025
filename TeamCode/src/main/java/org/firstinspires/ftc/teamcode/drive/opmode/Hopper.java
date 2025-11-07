
package org.firstinspires.ftc.teamcode.drive.opmode;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
@Config
public class Hopper {
    public static double HOPPER_OFFSET = 0.02;
    double position = 0;
    double pusherOffset = 0;
    int pos = 0;
    Servo hopperServo;
    Gamepad gamepad;
    Telemetry telemetry;
    boolean buttonWasPressed = false;

    Hopper(HardwareMap hardwareMap, Gamepad gamepad, Telemetry telemetry) {
        this.gamepad = gamepad;
        hopperServo = hardwareMap.get(Servo.class, "hopper");
        hopperServo.setPosition(0);
        this.telemetry = telemetry;
    }
    void setPusherOffset(double offset) {
        pusherOffset = offset;
    }
    void processGamepad() {

        if (gamepad.right_bumper) {
            // TODO: Set hopper to position 2 (so that ball 2 is at the top)
            if (! buttonWasPressed) {
                pos = (pos + 1) % 16;
                buttonWasPressed = true;
            }
        } else if (gamepad.left_bumper) {
            // TODO: Set hopped to position 3  (so that ball 3 is at the top)
            if (! buttonWasPressed) {

                pos = (pos + 15) % 16;
                buttonWasPressed = true;

            }
        } else {
            buttonWasPressed = false;
        }

        telemetry.addData("position",pos);
        hopperServo.setPosition(((double) pos) /15.0*10.0/9.0+ HOPPER_OFFSET + pusherOffset);

        }
    }



