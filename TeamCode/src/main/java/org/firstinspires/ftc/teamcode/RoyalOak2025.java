package org.firstinspires.ftc.teamcode;

// RR-specific imports

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.drive.opmode.Hopper;
import org.firstinspires.ftc.teamcode.drive.opmode.Launch;
import org.firstinspires.ftc.teamcode.drive.opmode.Push;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;

@Config
@Autonomous(name = "RoyalOak2025", group = "Autonomous", preselectTeleOp = "TestTeleop")
public class RoyalOak2025 extends LinearOpMode {
    private boolean determineAreWeFar() {
        boolean currentlyFar = false;
        while (!gamepad1.cross && !isStopRequested()) {
            telemetry.addLine("if close click right bumper");
            telemetry.addLine("if far click left bumper");
            telemetry.addLine("click X to confirm");

            if (currentlyFar == true)
                telemetry.addLine("currently set far.");
            else
                telemetry.addLine("currently set close.");
            telemetry.update();

            if (gamepad1.right_bumper)
                currentlyFar = false;
            if (gamepad1.left_bumper)
                currentlyFar = true;

        }

        telemetry.addLine("Ready to run Auton ");
        if (currentlyFar == true)
            telemetry.addLine(" far.");
        else
            telemetry.addLine(" close.");
        telemetry.update();

        return currentlyFar;
    }
    private boolean determineAreWeRed() {
        boolean currentlyRed = false;
        while (!gamepad1.circle && !isStopRequested()) {
            telemetry.addLine("if Blue click right bumper");
            telemetry.addLine("if Red click left bumper");
            telemetry.addLine("click O to confirm");

            if (currentlyRed == true)
                telemetry.addLine("currently set Red.");
            else
                telemetry.addLine("currently set Blue.");
            telemetry.update();

            if (gamepad1.right_bumper)
                currentlyRed = false;
            if (gamepad1.left_bumper)
                currentlyRed = true;

        }

        telemetry.addLine("Ready to run Auton ");
        if (currentlyRed == true)
            telemetry.addLine(" Red.");
        else
            telemetry.addLine(" Blue.");
        telemetry.update();

        return currentlyRed;
    }
    @Override
    public void runOpMode() {

        // instantiate your MecanumDrive at a particular pose.
        Pose2d initialPose = (new Pose2d(-50, -50, Math.toRadians(45)));
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);
        Launch launch = new Launch(hardwareMap, null,telemetry);
        Hopper hopper = new Hopper(hardwareMap,null,telemetry);
        Push push = new Push(hardwareMap,null,hopper,launch);



        int visionOutputPosition = 1;


        TrajectoryActionBuilder blueFarDrive = drive.actionBuilder(initialPose)
                .strafeTo(new Vector2d(-20,-20))
                .strafeTo(new Vector2d(-29, -29) )
                .turn(Math.toRadians(-90))
                ;
        TrajectoryActionBuilder blueCloseDrive = drive.actionBuilder(initialPose)
                .strafeTo(new Vector2d(-29, -29) )
                .turn(Math.toRadians(-90))
                ;
        TrajectoryActionBuilder redFarDrive = drive.actionBuilder(initialPose)
                .strafeTo(new Vector2d(-20,20))
                .strafeTo(new Vector2d(-29, 29) )
                .turn(Math.toRadians(90))
                ;
        TrajectoryActionBuilder redCloseDrive = drive.actionBuilder(initialPose)
                .strafeTo(new Vector2d(-29, 29) )
                .turn(Math.toRadians(90))
                ;
        TrajectoryActionBuilder blueTrajectory = drive.actionBuilder(new Pose2d(new Vector2d(-29, -29),Math.toRadians(-45)))
                .turn(Math.toRadians(90))
                //launch 3 balls
                .stopAndAdd(new SequentialAction(launch.launchAction()new SleepAction(.5),push.getAction(),hopper.getAction(), new SleepAction(3)))

                /*.strafeTo(new Vector2d(-11, -11) )
                .turn(Math.toRadians(-135))
                .strafeTo(new Vector2d(-11,-51))
                .strafeTo(new Vector2d(-29,-29))
                .turn(Math.toRadians(135))
                //launch 3 balls
                .stopAndAdd(new SequentialAction(launch.launchAction()))*/
                ;
        TrajectoryActionBuilder redTrajectory = drive.actionBuilder(new Pose2d(new Vector2d(-29, 29),Math.toRadians(45)))
                .turn(Math.toRadians(-90))
                //launch 3 balls
                .stopAndAdd(new SequentialAction(launch.launchAction(),new SleepAction(.5),push.getAction(),hopper.getAction(),new SleepAction(3)))
                /*.strafeTo(new Vector2d(-11, 11) )
                .turn(Math.toRadians(135))
                .strafeTo(new Vector2d(-11,51))
                .strafeTo(new Vector2d(-29,29))
                .turn(Math.toRadians(-135))
                //launch 3 balls
                .stopAndAdd(new SequentialAction(launch.launchAction()))*/
                ;

//INIT STEP
        //launch.activateLaunch();
        //while (!isStopRequested() && !opModeIsActive()) {
        //     int position = visionOutputPosition;
        //     telemetry.addData("Position during Init", position);
        //     telemetry.update();
        // }
        int startPosition = visionOutputPosition;
        telemetry.addData("Starting Position", startPosition);
        telemetry.update();
        //
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
        /*for (AprilTagDetection a : myAprilTagDetections) {
            myAprilTagDetection = a;
            if (myAprilTagDetection.metadata != null) {  // This check for non-null Metadata is not needed for reading only ID code.
                myAprilTagIdCode = myAprilTagDetection.id;
                telemetry.addData("ID", myAprilTagIdCode);

                // Now take action based on this tag's ID code, or store info for later action.

            }
            telemetry.update();
        }*/
        boolean far = determineAreWeFar();
        boolean red = determineAreWeRed();
        waitForStart();
        //AUTON STEP
        if (isStopRequested()) return;
        Action trajectoryActionChosen = null;
        if (red && far) {
            trajectoryActionChosen = redFarDrive.build();
        }
        if (!red && far) {
            trajectoryActionChosen = blueFarDrive.build();
        }
        if (red && !far) {
            trajectoryActionChosen = redCloseDrive.build();
        }
        if (!red && !far) {
            trajectoryActionChosen = blueCloseDrive.build();
        }
        Actions.runBlocking(
                new SequentialAction(
                        trajectoryActionChosen)
        );
        //Scan the April Tag.
        int good=-1;
        for (AprilTagDetection a : myAprilTagDetections) {
            myAprilTagDetection = a;
            if (myAprilTagDetection.metadata != null) {
                myAprilTagIdCode = myAprilTagDetection.id;
                telemetry.addData("ID", myAprilTagIdCode);
                if(21 <= myAprilTagIdCode&& 23>=  myAprilTagIdCode){
                    good =  myAprilTagIdCode;
                }
            }
            telemetry.update();
        }
        if(good == 22){
            // Sorter rotate (1)
        }
        if(good == 23) {
            // sorter rotate (2)
        }
        if (!red){
            trajectoryActionChosen = blueTrajectory.build();
        }else{
            trajectoryActionChosen = redTrajectory.build();
        }
        Actions.runBlocking(
                new SequentialAction(
                        trajectoryActionChosen)
        );

    }
}
