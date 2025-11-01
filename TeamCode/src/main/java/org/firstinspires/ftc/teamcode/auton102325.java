package org.firstinspires.ftc.teamcode;

// RR-specific imports

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.drive.opmode.Launch;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;

@Config
@Autonomous(name = "auton102325", group = "Autonomous")
public class auton102325 extends LinearOpMode {

    @Override
    public void runOpMode() {

        // instantiate your MecanumDrive at a particular pose.
        Pose2d initialPose = (new Pose2d(-50, -50, Math.toRadians(45)));
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);
        Launch launch = new Launch(hardwareMap, null);



        int visionOutputPosition = 1;

        TrajectoryActionBuilder blueTrajectory = drive.actionBuilder(initialPose)
                .strafeTo(new Vector2d(-29, -29))
                //launch 3 balls
                .stopAndAdd(new SequentialAction(launch.autoLaunch()))
                .waitSeconds(1)
                .stopAndAdd(new SequentialAction(launch.stopLaunch()))
                .strafeTo(new Vector2d(-11, -11) )
                .turn(Math.toRadians(-135))
                .strafeTo(new Vector2d(-11,-51))
                .strafeTo(new Vector2d(-29,-29))
                .turn(Math.toRadians(135))

                //launch 3 balls
                .stopAndAdd(new SequentialAction(launch.autoLaunch()))
                .waitSeconds(1)
                .stopAndAdd(new SequentialAction(launch.stopLaunch()))
                ;
        TrajectoryActionBuilder redTrajectory = drive.actionBuilder(initialPose)
                .strafeTo(new Vector2d(-29, 29) )
                //launch 3 balls
                .stopAndAdd(new SequentialAction(launch.autoLaunch()))
                .waitSeconds(1)
                .stopAndAdd(new SequentialAction(launch.stopLaunch()))
                .strafeTo(new Vector2d(-11, 11) )
                .turn(Math.toRadians(135))
                .strafeTo(new Vector2d(-11,51))
                .strafeTo(new Vector2d(-29,29))
                .turn(Math.toRadians(-135))
                //launch 3 balls
                .stopAndAdd(new SequentialAction(launch.autoLaunch()))
                .waitSeconds(1)
                .stopAndAdd(new SequentialAction(launch.stopLaunch()))
                ;
        TrajectoryActionBuilder tab3 = drive.actionBuilder(initialPose)
                .strafeTo(new Vector2d(-20,-20))
                .strafeTo(new Vector2d(-29,-29))
                //launch 3 balls
                .stopAndAdd(new SequentialAction(launch.autoLaunch()))
                .waitSeconds(1)
                .stopAndAdd(new SequentialAction(launch.stopLaunch()))
                .strafeTo(new Vector2d(-11,-11))
                .strafeTo(new Vector2d(-12,-52))
                .strafeTo(new Vector2d(-29,-29))
                //launch 3 balls
                .stopAndAdd(new SequentialAction(launch.autoLaunch()))
                .waitSeconds(1)
                .stopAndAdd(new SequentialAction(launch.stopLaunch()))
                /*
                .strafeTo(new Vector2d(-11, -11) )
                .turn(Math.toRadians(-135))
                .strafeTo(new Vector2d(-11,-51))
                .splineToLinearHeading(new Pose2d(-29,-29, Math.toRadians(45)),)
                */
                ;
        TrajectoryActionBuilder tab4 = drive.actionBuilder(initialPose)
                .strafeTo(new Vector2d(-20,20))
                .strafeTo(new Vector2d(-29,29))
                //launch 3 balls
                .stopAndAdd(new SequentialAction(launch.autoLaunch()))
                .waitSeconds(1)
                .stopAndAdd(new SequentialAction(launch.stopLaunch()))
                .strafeTo(new Vector2d(-11,11))
                .strafeTo(new Vector2d(-12,52))
                .strafeTo(new Vector2d(-29,29))
                //launch 3 balls
                .stopAndAdd(new SequentialAction(launch.autoLaunch()))
                .waitSeconds(1)
                .stopAndAdd(new SequentialAction(launch.stopLaunch()))
        ;
                /*
                .strafeTo(new Vector2d(-11, 11) )
                .turn(Math.toRadians(135))
                .strafeTo(new Vector2d(-11,51));
                */
        //launch.activateLaunch();
        while (!isStopRequested() && !opModeIsActive()) {
            int position = visionOutputPosition;
            telemetry.addData("Position during Init", position);
            telemetry.update();
        }
        int startPosition = visionOutputPosition;
        telemetry.addData("Starting Position", startPosition);
        telemetry.update();
        //?
        AprilTagProcessor myAprilTagProcessor;
    myAprilTagProcessor =  new AprilTagProcessor.Builder().build();
        VisionPortal myVisionPortal;
        myVisionPortal = new VisionPortal.Builder()
                .setCamera(hardwareMap.get(WebcamName.class,"Camera"))
                .addProcessor(myAprilTagProcessor).build();
        //myVisionPortal.addProcessor(myAprilTagProcessor);
        List<AprilTagDetection> myAprilTagDetections;
        AprilTagDetection myAprilTagDetection;
        int myAprilTagIdCode;
        // Get a list of AprilTag detections.
        myAprilTagDetections = myAprilTagProcessor.getDetections();

// Cycle through through the list and process each AprilTag.
        for (AprilTagDetection a : myAprilTagDetections) {
            myAprilTagDetection = a;
            if (myAprilTagDetection.metadata != null) {  // This check for non-null Metadata is not needed for reading only ID code.
                myAprilTagIdCode = myAprilTagDetection.id;
                telemetry.addData("ID", myAprilTagIdCode);

                // Now take action based on this tag's ID code, or store info for later action.

            }
            telemetry.update();
        }
        waitForStart();

        if (isStopRequested()) return;
        Action trajectoryActionChosen;
        trajectoryActionChosen = blueTrajectory.build();
        Actions.runBlocking(
                new SequentialAction(
                        trajectoryActionChosen)
        );

    }
}
