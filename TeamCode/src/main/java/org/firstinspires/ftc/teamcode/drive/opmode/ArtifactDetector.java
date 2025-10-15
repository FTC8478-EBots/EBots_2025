package org.firstinspires.ftc.teamcode.drive.opmode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad2;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class ArtifactDetector {
    private NormalizedColorSensor test_color;
Telemetry telemetry ;




    ArtifactDetector(HardwareMap hardwareMap, Gamepad gamepad, Telemetry telemetry) {
        test_color = hardwareMap.get(NormalizedColorSensor.class, "testColor");
this.telemetry = telemetry;

    }



        public void recognizeColor() {


                telemetry.addData("Light Detected", ((OpticalDistanceSensor) test_color).getLightDetected());
                NormalizedRGBA colors = test_color.getNormalizedColors();

                //Determining the amount of red, green, and blue
                telemetry.addData("Red", "%.3f", colors.red);
                telemetry.addData("Green", "%.3f", colors.green);
                telemetry.addData("Blue", "%.3f", colors.blue);

            }
        }





