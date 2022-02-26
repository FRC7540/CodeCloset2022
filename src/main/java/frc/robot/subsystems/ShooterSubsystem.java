// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

// Shooter will use two Redlines.
// Controllers: Victors
// Goal: Be able to run both motors at the same absolute value of speed, in 
//    opposite directions, and stop.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

/** Add your docs here. */
public class ShooterSubsystem extends SubsystemBase {
    /** Creates a new ShooterSubsystem. */
    public ShooterSubsystem() {}

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
    }

    public void shooterStop() {

    }

    public void shooterOut(double shooterSpeed) {

    }
}
