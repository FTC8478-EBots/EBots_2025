package org.firstinspires.ftc.teamcode.drive.opmode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad2;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.qualcomm.hardware.rev.RevColorSensorV3;
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
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class ArtifactDetector {
    double DISTANCE_TO_TARGET = 3.4;
    private RevColorSensorV3 test_color;
Telemetry telemetry ;
LightIndicator lightIndicator;
String deviceName;




    public ArtifactDetector(HardwareMap hardwareMap, Telemetry telemetry, String deviceName, LightIndicator lightIndicator) {
        this.deviceName = deviceName;
        test_color = hardwareMap.get(RevColorSensorV3.class, deviceName);
        this.telemetry = telemetry;
        this.lightIndicator = lightIndicator;
    }

    boolean isArtifactDetected() {
        double distance = test_color.getDistance(DistanceUnit.CM);
        telemetry.addData("ArtifactDistance" + deviceName, distance);
        return distance< DISTANCE_TO_TARGET;
    }
public void updateIndicator(){
        if (lightIndicator == null) return;
        if (isArtifactDetected()){
            if (isArtifactdetectorPurple()){
                lightIndicator.setViolet();

            }
            else{
                lightIndicator.setGreen();
            }
    } else {
            lightIndicator.setBlack();
        }
}



        public boolean isArtifactdetectorPurple() {


     //       telemetry.addData("Light Detected", ((OpticalDistanceSensor) test_color).getLightDetected());
            NormalizedRGBA colors = test_color.getNormalizedColors();

            //Determining the amount of red, green, and blue
     //       telemetry.addData("Red", "%.3f", colors.red);
     //       telemetry.addData("Green", "%.3f", colors.green);
     //       telemetry.addData("Blue", "%.3f", colors.blue);

            if (colors.blue > colors.green) {
                return true;
            }
            return false;
        }


}





