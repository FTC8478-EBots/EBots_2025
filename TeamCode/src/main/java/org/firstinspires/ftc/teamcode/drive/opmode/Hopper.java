
package org.firstinspires.ftc.teamcode.drive.opmode;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
@Config
public class Hopper {
    public static double HOPPER_OFFSET = 0.02;
    double position = 1;
    double pusherOffset = 0;
    int pos = 15;
    Servo hopperServo;
    Gamepad gamepad;
    Telemetry telemetry;
    boolean buttonWasPressed = false;

    public Hopper(HardwareMap hardwareMap, Gamepad gamepad, Telemetry telemetry) {
        this.gamepad = gamepad;
        hopperServo = hardwareMap.get(Servo.class, "hopper");
        hopperServo.setPosition(position);
        this.telemetry = telemetry;
    }
    void setPusherOffset(double offset) {
        pusherOffset = offset;
    }
    void processGamepad() {

        if (gamepad.right_bumper) {
            // TODO: Set hopper to position 2 (so that ball 2 is at the top)
            if (! buttonWasPressed) {
                pos = (pos + 1);
                if (pos>15) pos = 15;

                buttonWasPressed = true;
            }
        } else if (gamepad.left_bumper) {
            // TODO: Set hopped to position 3  (so that ball 3 is at the top)
            if (! buttonWasPressed) {

                pos = (pos -1);
                if (pos<0) pos = 0;
                buttonWasPressed = true;

            }
        } else {
            buttonWasPressed = false;
        }

        telemetry.addData("position",pos);
            updatePosition();
        }

        class HopperAction implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                nextPosition();
                return false;
            }
        }

    public Action getAction() {
        return new HopperAction();
    }



        void nextPosition() {
        pos--;
        updatePosition();
        }

        void updatePosition() {
            hopperServo.setPosition(((double) pos) /15.0*10.0/9.0+ HOPPER_OFFSET + pusherOffset);
        }
    }