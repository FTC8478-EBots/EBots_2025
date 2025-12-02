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
import org.firstinspires.ftc.teamcode.drive.opmode.Intake;
import org.firstinspires.ftc.teamcode.drive.opmode.Launch;
import org.firstinspires.ftc.teamcode.drive.opmode.Push;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagPoseFtc;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;

@Config
@Autonomous(name = "Rochester2025", group = "Autonomous", preselectTeleOp = "TestTeleop")
public class Rochester2025 extends LinearOpMode {
    //INIT STEP

    private boolean determineAreWeFar() {
        boolean currentlyFar = false;
        while (!gamepad1.cross && !isStopRequested()) {
            telemetry.addLine("if close click right bumper");
            telemetry.addLine("if far click left bumper");
            telemetry.addLine("click cross (or A) to confirm");

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
            telemetry.addLine("click B (or circle) to confirm");

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
    private boolean determineTest() {
        boolean currentlyTest = false;
        while (!gamepad1.triangle && !isStopRequested()) {
            telemetry.addLine("if non-test click right bumper");
            telemetry.addLine("if Test click left bumper");
            telemetry.addLine("click Triangle (or Y) to confirm");

            if (currentlyTest == true)
                telemetry.addLine("currently set test.");
            else
                telemetry.addLine("currently set nontest.");
            telemetry.update();

            if (gamepad1.right_bumper)
                currentlyTest = false;
            if (gamepad1.left_bumper)
                currentlyTest = true;

        }

        telemetry.addLine("Ready to run Auton ");
        if (currentlyTest == true)
            telemetry.addLine(" Test.");
        else
            telemetry.addLine(" Non-test.");
        telemetry.update();

        return currentlyTest;
    }
    @Override
    public void runOpMode() {
        Launch launch = new Launch(hardwareMap, null,telemetry);
        Hopper hopper = new Hopper(hardwareMap,null,telemetry);
        Push push = new Push(hardwareMap,null,hopper,launch);
        Intake intake = new Intake(hardwareMap,null,telemetry);

        boolean far = determineAreWeFar();
        boolean red = determineAreWeRed();
        boolean test = determineTest();


        // instantiate your MecanumDrive at a particular pose.
        Pose2d initialPose = null;
        if (!far) {
            if (red) {
                initialPose = (new Pose2d(-50, 50, Math.toRadians(45)));
            } else {
                initialPose = (new Pose2d(-50, -50, Math.toRadians(45)));

            }
        } else {
            if (red) {
                initialPose = (new Pose2d(70, 21, Math.toRadians(45)));
            } else {
                initialPose = (new Pose2d(70, -21, Math.toRadians(45)));

            }
        }
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);




        int visionOutputPosition = 1;


        TrajectoryActionBuilder blueFarDrive = drive.actionBuilder(new Pose2d(70, -21, Math.toRadians(180)))
                //.strafeTo(new Vector2d(-20,-20))
                //.strafeTo(new Vector2d(-29, -29) )
                //-8.turn(Math.toRadians(-90))
                .strafeTo(new Vector2d(-9, -9))
                .turn(Math.toRadians(-135))
                .stopAndAdd(new SequentialAction(intake.intakeAction()))
                .stopAndAdd(new SequentialAction(hopper.getAction(),new SleepAction(1), launch.launchAction(), new SleepAction(.5), push.getLaunchSequence(),new SleepAction(.5)))
                .stopAndAdd(new SequentialAction(hopper.getAction(),new SleepAction(1), launch.launchAction(), new SleepAction(.5), push.getLaunchSequence(),new SleepAction(.5)))
                .stopAndAdd(new SequentialAction(hopper.getAction(),new SleepAction(1), launch.launchAction(), new SleepAction(.5), push.getLaunchSequence(),new SleepAction(.5)))
                .stopAndAdd(launch.stopAction())
                ;

        TrajectoryActionBuilder blueCloseDrive = drive.actionBuilder(new Pose2d(-50, -50, Math.toRadians(45)))
                .strafeTo(new Vector2d(-9, -9))
                .stopAndAdd(new SequentialAction(intake.intakeAction()))
                .stopAndAdd(new SequentialAction(hopper.getAction(),new SleepAction(1), launch.launchAction(), new SleepAction(.5), push.getLaunchSequence(),new SleepAction(.5)))
                .stopAndAdd(new SequentialAction(hopper.getAction(),new SleepAction(1), launch.launchAction(), new SleepAction(.5), push.getLaunchSequence(),new SleepAction(.5)))
                .stopAndAdd(new SequentialAction(hopper.getAction(),new SleepAction(1), launch.launchAction(), new SleepAction(.5), push.getLaunchSequence(),new SleepAction(.5)))
                .stopAndAdd(launch.stopAction())

                //was -29 -29
                //.turn(Math.toRadians(-90))
                ;
        TrajectoryActionBuilder redFarDrive = drive.actionBuilder(new Pose2d(70, 21, Math.toRadians(180)))
                //.strafeTo(new Vector2d(-20,20))
                //.strafeTo(new Vector2d(-29, 29) )
                //.turn(Math.toRadians(90))
                .strafeTo(new Vector2d(-9, 9))
                .turn(Math.toRadians(135))
                .stopAndAdd(new SequentialAction(intake.intakeAction()))
                .stopAndAdd(new SequentialAction(hopper.getAction(),new SleepAction(1), launch.launchAction(), new SleepAction(.5), push.getLaunchSequence(),new SleepAction(.5)))
                .stopAndAdd(new SequentialAction(hopper.getAction(),new SleepAction(1), launch.launchAction(), new SleepAction(.5), push.getLaunchSequence(),new SleepAction(.5)))
                .stopAndAdd(new SequentialAction(hopper.getAction(),new SleepAction(1), launch.launchAction(), new SleepAction(.5), push.getLaunchSequence(),new SleepAction(.5)))
                .stopAndAdd(launch.stopAction())
                ;
        TrajectoryActionBuilder redCloseDrive = drive.actionBuilder(new Pose2d(-50, 50, Math.toRadians(45)))
                .strafeTo(new Vector2d(-9, 9) )
                .stopAndAdd(new SequentialAction(intake.intakeAction()))
                .stopAndAdd(new SequentialAction(hopper.getAction(),new SleepAction(1), launch.launchAction(), new SleepAction(.5), push.getLaunchSequence(),new SleepAction(.5)))
                .stopAndAdd(new SequentialAction(hopper.getAction(),new SleepAction(1), launch.launchAction(), new SleepAction(.5), push.getLaunchSequence(),new SleepAction(.5)))
                .stopAndAdd(new SequentialAction(hopper.getAction(),new SleepAction(1), launch.launchAction(), new SleepAction(.5), push.getLaunchSequence(),new SleepAction(.5)))
                .stopAndAdd(launch.stopAction())

                //was -29 29
                //.turn(Math.toRadians(90))
                ;
        TrajectoryActionBuilder blueTrajectory = drive.actionBuilder(new Pose2d(new Vector2d(-15, -15),Math.toRadians(-45)))
                //.turn(Math.toRadians(90))
                //.stopAndAdd(new SequentialAction(hopper.getAction()))
                //launch 3 balls
                //no: .stopAndAdd(new SequentialAction(launch.launchAction(),new SleepAction(.5),push.getLaunchSequence(),hopper.getAction(), new SleepAction(3)))

                //better: .stopAndAdd(new SequentialAction(hopper.getAction(),hopper.getAction(),new SleepAction(2), launch.launchAction(), new SleepAction(.5), push.getLaunchSequence(),hopper.getAction(),new SleepAction(3)))

                //.strafeTo(new Vector2d(-44, -14))
                .turn(Math.toRadians(-135))
                .strafeTo(new Vector2d(-11,-51))
                .strafeTo(new Vector2d(-29,-29))
                .turn(Math.toRadians(135))
                //launch 3 balls
                .stopAndAdd(new SequentialAction(launch.launchAction()))
                .strafeTo(new Vector2d(0,-50))
                .stopAndAdd(new SequentialAction(intake.intakeAction()))
                .stopAndAdd(new SequentialAction(hopper.getAction(),new SleepAction(1), launch.launchAction(), new SleepAction(.5), push.getLaunchSequence(),new SleepAction(.5)))
                .stopAndAdd(new SequentialAction(hopper.getAction(),new SleepAction(1), launch.launchAction(), new SleepAction(.5), push.getLaunchSequence(),new SleepAction(.5)))
                .stopAndAdd(new SequentialAction(hopper.getAction(),new SleepAction(1), launch.launchAction(), new SleepAction(.5), push.getLaunchSequence(),new SleepAction(.5)))
                .stopAndAdd(launch.stopAction())
                ;
        TrajectoryActionBuilder redTrajectory = drive.actionBuilder(new Pose2d(new Vector2d(-15, 15),Math.toRadians(45)))
                //.turn(Math.toRadians(-90))
                //launch 3 balls
                //no: .stopAndAdd(new SequentialAction(launch.launchAction(),new SleepAction(.5),push.getLaunchSequence(),hopper.getAction(),new SleepAction(3)))
                //better: .stopAndAdd(new SequentialAction(hopper.getAction(),hopper.getAction(),new SleepAction(2), launch.launchAction(), new SleepAction(.5), push.getLaunchSequence(),hopper.getAction(),new SleepAction(3)))


                //.strafeTo(new Vector2d(-44, 14) )

                .turn(Math.toRadians(135))
                .strafeTo(new Vector2d(-11,51))
                .strafeTo(new Vector2d(-29,29))
                .turn(Math.toRadians(-135))
                //launch 3 balls
                .stopAndAdd(new SequentialAction(launch.launchAction()))
                .strafeTo(new Vector2d(0,50))
                .stopAndAdd(new SequentialAction(intake.intakeAction()))
                .stopAndAdd(new SequentialAction(hopper.getAction(),new SleepAction(1), launch.launchAction(), new SleepAction(.5), push.getLaunchSequence(),new SleepAction(.5)))
                .stopAndAdd(new SequentialAction(hopper.getAction(),new SleepAction(1), launch.launchAction(), new SleepAction(.5), push.getLaunchSequence(),new SleepAction(.5)))
                .stopAndAdd(new SequentialAction(hopper.getAction(),new SleepAction(1), launch.launchAction(), new SleepAction(.5), push.getLaunchSequence(),new SleepAction(.5)))
                .stopAndAdd(launch.stopAction())
                ;

        TrajectoryActionBuilder testTrajectory = drive.actionBuilder(new Pose2d(new Vector2d(-29, -29),Math.toRadians(-45)))
                .stopAndAdd(new SequentialAction(hopper.getAction()))
                .stopAndAdd(new SequentialAction(new SleepAction(4)))
                .stopAndAdd(new SequentialAction(hopper.getAction()))
                .stopAndAdd(new SequentialAction(new SleepAction(4)))
                .stopAndAdd(new SequentialAction(launch.launchAction()))
                .stopAndAdd(new SequentialAction(new SleepAction(4)))
                .stopAndAdd(new SequentialAction(push.getLaunchSequence()))
                .stopAndAdd(new SequentialAction(new SleepAction(4)))
                .stopAndAdd(new SequentialAction(hopper.getAction()))
                .stopAndAdd(new SequentialAction(new SleepAction(4)))
                .stopAndAdd(new SequentialAction(
                        launch.launchAction(), new SleepAction(4), push.getLaunchSequence(), new SleepAction(4),
                        hopper.getAction(),new SleepAction(4)))
                //launch 3 balls
                /*.stopAndAdd(new SequentialAction(hopper.getAction()))
                .stopAndAdd(new SequentialAction(new SleepAction(2)))*/
                /*.stopAndAdd(new SequentialAction(launch.launchAction()))
                .stopAndAdd(new SequentialAction(new SleepAction(.5)))*/
                //.stopAndAdd(new SequentialAction(push.getLaunchSequence()))
                /*.stopAndAdd(new SequentialAction(hopper.getAction()))
                .stopAndAdd(new SequentialAction(new SleepAction(1)))*/


                /*.turn(Math.toRadians(-135))
                .strafeTo(new Vector2d(-11,-51))
                .strafeTo(new Vector2d(-29,-29))
                .turn(Math.toRadians(135))
                //launch 3 balls
                .stopAndAdd(new SequentialAction(launch.launchAction()))*/
                ;
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
        while (!isStarted()){
        for (AprilTagDetection a : myAprilTagDetections) {
            myAprilTagDetection = a;
            if (myAprilTagDetection.metadata != null) {  // This check for non-null Metadata is not needed for reading only ID code.
                myAprilTagIdCode = myAprilTagDetection.id;
                AprilTagPoseFtc myAprilTagPosition = myAprilTagDetection.ftcPose;
                telemetry.addData("ID", myAprilTagIdCode);
                telemetry.addData("X-Value", myAprilTagPosition.x);
                telemetry.addData("Y-Value", myAprilTagPosition.y);
                telemetry.addData("Rotation", myAprilTagPosition.yaw);
                // Now take action based on this tag's ID code, or store info for later action.

            }
            telemetry.update();
        }
        }
        waitForStart();
        //AUTON STEPaw
        if (isStopRequested()) return;
        Action trajectoryActionChosen = null;
        if (test) {
            trajectoryActionChosen = testTrajectory.build();
        } else {
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
        if (!test) {
            if (!red) {
                trajectoryActionChosen = blueTrajectory.build();
            } else {
                trajectoryActionChosen = redTrajectory.build();
            }
        }
        Actions.runBlocking(
                new SequentialAction(
                        trajectoryActionChosen)
        );

    }
}