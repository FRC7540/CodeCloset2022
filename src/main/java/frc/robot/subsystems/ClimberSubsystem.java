// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

// Climber one redline
// Controller: victor
// CLIMBER: Driver controller button Y to go up, and button X to go down.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

/** Add your docs here. */
public class ClimberSubsystem extends SubsystemBase {
    /** Creates a new ClimberSubsystem. */

    private static final WPI_VictorSPX climberMotor = new WPI_VictorSPX(Constants.ClimberConstants.kClimberMotorCanID);

    public ClimberSubsystem() {
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
    }

    public void climbStop() {
        climberMotor.stopMotor();
    }
}
