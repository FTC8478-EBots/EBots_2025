package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;

import org.rowlandhall.meepmeep.MeepMeep;
import org.rowlandhall.meepmeep.roadrunner.DefaultBotBuilder;
import org.rowlandhall.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .followTrajectorySequence(drive -> drive.trajectorySequenceBuilder(new Pose2d(-50, -50, Math.toRadians(45)))
                        .forward(30)
                        //add launch here
                        .waitSeconds(1)
                        .forward(25)
                        .turn(Math.toRadians(-135))
                        .forward(40)
                        .lineToLinearHeading(new Pose2d(-29,-29, Math.toRadians(45)))
                        .waitSeconds(1)
                        .build());
        RoadRunnerBotEntity mySecondBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .followTrajectorySequence(drive -> drive.trajectorySequenceBuilder(new Pose2d(-50, 50, Math.toRadians(-45)))
                        .forward(30)
                        //add launch here
                        .waitSeconds(1)
                        .forward(25)
                        .turn(Math.toRadians(135))
                        .forward(40)
                        .lineToLinearHeading(new Pose2d(-29,29, Math.toRadians(-45)))
                        .waitSeconds(1)
                        .build());
        RoadRunnerBotEntity myThirdBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .followTrajectorySequence(drive -> drive.trajectorySequenceBuilder(new Pose2d(70, -21, Math.toRadians(180)))
                        .forward(90)
                        .turn(Math.toRadians(-135))
                        .back(10)
                        //add launch
                        .waitSeconds(1)
                        .forward(25)
                        .turn(Math.toRadians(-135))
                        .forward(40)
                        .lineToLinearHeading(new Pose2d(-29,-29, Math.toRadians(45)))
                        .waitSeconds(1)
                        .build());
        RoadRunnerBotEntity myFourthBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .followTrajectorySequence(drive -> drive.trajectorySequenceBuilder(new Pose2d(70, 21, Math.toRadians(180)))
                        .forward(90)
                        .turn(Math.toRadians(135))
                        .back(10)
                        //add launch
                        .waitSeconds(1)
                        .forward(25)
                        .turn(Math.toRadians(135))
                        .forward(40)
                        .lineToLinearHeading(new Pose2d(-29,29, Math.toRadians(45)))
                        .waitSeconds(1)
                        .build());

        Image img = null;
        try { img = ImageIO.read(new File("C:\\Users\\ebots\\Downloads\\DECODE Black Field.png\\"));}
        catch (IOException e) {};
        meepMeep.setBackground(img)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                //.addEntity(myBot)
                .addEntity(mySecondBot)
                /*.addEntity(myThirdBot)*/
                //.addEntity(myFourthBot)
                .start();
    }
}
