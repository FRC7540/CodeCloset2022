// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

// Shooter will use two Redlines.
// Controllers: Victors
// Goal: Be able to run both motors at different, VARYING speeds, in 
//    opposite directions, and stop.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;

/** Add your docs here. */
public class ShooterSubsystem extends SubsystemBase {
    /** Creates a new ShooterSubsystem. */

    private static final WPI_VictorSPX shooterMotor1 = new WPI_VictorSPX(Constants.ShooterConstants.kShooterMotor1CanID);
    private static final WPI_VictorSPX shooterMotor2 = new WPI_VictorSPX(Constants.ShooterConstants.kShooterMotor2CanID);

    private static final MotorControllerGroup shooterMotors = new MotorControllerGroup(shooterMotor1, shooterMotor2);

    private static double baseSpeed = 0;
    private static double modifier = 0;

    public ShooterSubsystem() {}

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
    }

    public void shooterStop() {
            shooterMotors.stopMotor();
    }

    public void shooterOut(double shooterSpeed) {
        if (RobotContainer.kOperateRobot) {
            shooterMotors.set(shooterSpeed);
        }
    }

        // increments baseSpeed of shooter motors in 4 zones of left trigger axis
    public static void shooterVelocity (double shooterVelocity) {
        double incrementedSpeed = 0;
        if (shooterVelocity < 0.25 && shooterVelocity > 0) {
            incrementedSpeed = Constants.ShooterConstants.lowestSpeed;
        } else if (shooterVelocity < 0.5 && shooterVelocity > 0) {
            incrementedSpeed = (Constants.ShooterConstants.lowestSpeed + Constants.ShooterConstants.increment);
        } else if (shooterVelocity < 0.75 && shooterVelocity > 0) {
            incrementedSpeed = (Constants.ShooterConstants.lowestSpeed + Constants.ShooterConstants.increment * 2);
        } else if (shooterVelocity <= 1 && shooterVelocity > 0) {
            incrementedSpeed = (Constants.ShooterConstants.lowestSpeed + Constants.ShooterConstants.increment * 3);
        } else {
            incrementedSpeed = 0;
        }

        if (incrementedSpeed > baseSpeed) {
            baseSpeed = incrementedSpeed;
            updateMotors();
        }
    }

    public static void stopShooter() {
        shooterMotors.stopMotor();
    }

    // if increase = true, increase modifier. If false, decrease
    public static void shooterAngleModifier(boolean increase) {
        if (increase) {
            modifier = modifier + 0.05;
        } else {
            modifier = modifier - 0.05;
        }
        updateMotors();
    }

    private static void updateMotors() {
        shooterMotor1.set(baseSpeed + modifier);
        shooterMotor2.set(baseSpeed - modifier);
    }
}
