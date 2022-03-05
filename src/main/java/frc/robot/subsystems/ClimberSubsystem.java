// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

// Climber will use two Redlines
// Controllers: Victors
// CLIMBER: Driver controller button Y to go up, and button X to go down.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;

/** Add your docs here. */
public class ClimberSubsystem extends SubsystemBase {
    /** Creates a new ClimberSubsystem. */

    private static final WPI_VictorSPX climberMotor = new WPI_VictorSPX(Constants.ClimberConstants.kClimberMotorCanID);

    public ClimberSubsystem() {}

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
    }

    public void climbStop() {
        climberMotor.stopMotor();
    }

    // Note: isUp needs to be TRUE for the climber to tighten and pull itself up. FALSE makes it let itself down.
    public void climbUp(boolean isUp) {
        if(RobotContainer.kOperateRobot) {
            if(isUp) {
                climberMotor.set(Constants.ClimberConstants.kClimberSpeed);
            } else {
                climberMotor.set(-Constants.ClimberConstants.kClimberSpeed + Constants.ClimberConstants.kClimberLetDownModifier);
            }
        }
    }
}
