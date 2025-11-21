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

    public void setRed() {
        light.setPosition(0.28);

    }

    public void setGreen() {
        light.setPosition(0.5);
    }

    public void setYellow() {
        light.setPosition(0.388);
    }

    public void setBlue() {
        light.setPosition(0.611);
    }

    public void setViolet() {
        light.setPosition(0.722);
    }

    public void setWhite() {
        light.setPosition(1.0);
    }

    public void setBlack() {
        light.setPosition(0.0);
    }
}



