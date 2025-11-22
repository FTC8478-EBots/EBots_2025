
package org.firstinspires.ftc.teamcode.drive.opmode;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
@Config
public class Hopper {
    public static double HOPPER_OFFSET = 0.04;
    static int MAX_POS = 13;
    static double MIN_TIME = .5;
    double earliestActivation = -1;
    double position = 1;
    double pusherOffset = 0;
    int pos = MAX_POS;
    Servo hopperServo;
    Gamepad gamepad;
    Telemetry telemetry;
    OpMode opMode;
    LightIndicator lightIndicator;

    boolean buttonWasPressed = false;
    double[] positionLookup = {
            0,
            0.07,
            .14,
            0.21,
            0.28,
            0.36,
            .44,
            .52,
            .60,
            0.675,
            0.75,
            .82,
            0.89,
            .96,
    };

    public Hopper(HardwareMap hardwareMap, Gamepad gamepad, Telemetry telemetry, OpMode opMode, LightIndicator lightIndicator) {
        this.gamepad = gamepad;
        hopperServo = hardwareMap.get(Servo.class, "hopper");
        hopperServo.setPosition(position);
        this.telemetry = telemetry;
        this.opMode = opMode;
        this.lightIndicator = lightIndicator;
    }
    void setPusherOffset(double offset) {
        pusherOffset = offset;
    }
    void processGamepad() {

        if (gamepad.right_bumper) {
            // TODO: Set hopper to position 2 (so that ball 2 is at the top)
            if (! buttonWasPressed) {
                pos++;
                if (pos>MAX_POS) pos = MAX_POS;
                if (pos == MAX_POS)
                    lightIndicator.setRed();
                else
                    lightIndicator.setBlack();

                buttonWasPressed = true;
            }
        } else if (gamepad.left_bumper) {
            // TODO: Set hopped to position 3  (so that ball 3 is at the top)
            if (! buttonWasPressed) {

                pos--;
                if (pos<0) pos = 0;
                if (pos == 0)
                    lightIndicator.setRed();
                else
                    lightIndicator.setBlack();

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
    if (pos<0) pos = 0;

    updatePosition();
    }
    void timedNextPosition() {
        if (opMode.time>earliestActivation) {
            earliestActivation = opMode.time+MIN_TIME;
            nextPosition();
        }
    }

    void updatePosition() {
        if (pos<0) pos = 0;
        //hopperServo.setPosition(((double) pos) /15.0*10.0/9.0+ HOPPER_OFFSET + pusherOffset);
        hopperServo.setPosition(positionLookup[pos]+pusherOffset);
    }
}