// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

// Climber will use two Redlines
// Controllers: Victors
// Goal: Be able to move the motor at a set speed one direction, and then 
//    reverse and do the same in the opposite direction.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

/** Add your docs here. */
public class ClimberSubsystem extends SubsystemBase {
    /** Creates a new ClimberSubsystem. */
    public ClimberSubsystem() {}

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
    }

    public void climbStop() {

    }

    // Note: isClimbing needs to be TRUE for the climber to tighten and pull itself up. FALSE makes it let itself down.
    public void climbUp(boolean isClimbing) {

    }
}
