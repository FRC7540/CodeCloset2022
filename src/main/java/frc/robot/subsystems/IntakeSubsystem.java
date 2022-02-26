// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

// Intake will use a single Redline.
// Controllers: Unsure. Will probably just be a relay.
// Goal: Be able to go forward at a set speed, backward at a set speed, and stop.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

/** Add your docs here. */
public class IntakeSubsystem extends SubsystemBase {
    /** Creates a new IntakeSubsystem. */
    public IntakeSubsystem() {}

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
    }

    public void intakeStop() {

    }

    // Note: isReverse should be FALSE in order to intake balls. TRUE makes it spit them out.
    public void intakeIn(boolean isReverse) {

    }
}
