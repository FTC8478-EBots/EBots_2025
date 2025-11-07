package org.firstinspires.ftc.teamcode.drive.opmode;

import com.acmerobotics.dashboard.config.Config;
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
    Push (HardwareMap hardwareMap, Gamepad gamepad, Hopper hopper, Launch launch) {
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

}

