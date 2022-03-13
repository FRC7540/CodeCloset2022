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

    public ShooterSubsystem() {
        shooterMotor1.setInverted(false);
        shooterMotor2.setInverted(true);
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
    }

    public void shooterStop() {
            baseSpeed = 0; 
            shooterMotors.stopMotor();
    }

        // increments baseSpeed of shooter motors in 4 zones of left trigger axis
    public void shooterVelocity (double shooterVelocity) {
        baseSpeed = shooterVelocity;
        updateMotors();
    }

    // if increase = true, increase modifier. If false, decrease
    public void shooterAngleModifier(boolean increase) {
        if (increase) {
            modifier = modifier + 0.05;
        } else {
            modifier = modifier - 0.05;
        }
        updateMotors();
    }

    // positive modifier spins motor 1 faster than motor 2, negative spins 2 faster than 1
    private void updateMotors() {
        shooterMotor1.set(baseSpeed + modifier);
        shooterMotor2.set(baseSpeed - modifier);
    }
}
