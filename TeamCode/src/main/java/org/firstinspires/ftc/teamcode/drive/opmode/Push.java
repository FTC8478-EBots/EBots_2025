package org.firstinspires.ftc.teamcode.drive.opmode;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
@Config
public class Push {

    double position = 0;
    Gamepad gamepad;
    Servo pushServo;
    Hopper hopper;
    Launch launch;

    public static double park = .7;
    public static double push = 1.0;
    public static double PUSH_OFFSET = -.02;
    public Push(HardwareMap hardwareMap, Gamepad gamepad, Hopper hopper, Launch launch) {
        this.gamepad = gamepad;
        this.hopper = hopper;
        this.launch = launch;
        pushServo = hardwareMap.get(Servo.class, "push");
        pushServo.setPosition(park);
    }

    void processGamepad() {
        if (gamepad.dpad_up && launch.isFast()) {
            hopper.setPusherOffset(PUSH_OFFSET);
            pushServo.setPosition(push);
        }
        else {
            hopper.setPusherOffset(0);
            pushServo.setPosition(park);
        }
    }

    public SequentialAction getLaunchSequence() {
        return new SequentialAction(new PushAction(push), new SleepAction(.5),new PushAction(park));
    }

    public class PushAction implements Action {
        private boolean initialized = false;
        private double position = 0;
        PushAction(double position) {
            this.position = position;
        }

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
                hopper.setPusherOffset(PUSH_OFFSET);
                pushServo.setPosition(position);

            return false;
        }
    }
    /*public Action getAction() {
        return new PushAction();
    }*/

}

