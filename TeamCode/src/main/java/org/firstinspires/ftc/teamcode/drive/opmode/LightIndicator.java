package org.firstinspires.ftc.teamcode.drive.opmode;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class LightIndicator {
    Servo hopperServo;
   Servo light;
   Telemetry telemetry;
   int position;
    public LightIndicator(HardwareMap hardwareMap, Telemetry telemetry) {
        //this.gamepad = gamepad;
        light = hardwareMap.get(Servo.class, "light");
        light.setPosition(position);
        this.telemetry = telemetry;
    }



    }





