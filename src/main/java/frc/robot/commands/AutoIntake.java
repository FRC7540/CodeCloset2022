// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.TowerSubsystem;

public class AutoIntake extends SequentialCommandGroup {
  /** Creates a new AutoIntake. */
  public AutoIntake(TowerSubsystem tower, IntakeSubsystem intake) {
    // Use addRequirements() here to declare subsystem dependencies.
    super(
      new ParallelCommandGroup(
        new InstantCommand(() -> tower.setTowerSpeedManual(0.4), tower),
        new InstantCommand(() -> intake.intakeSpeedSetManual(0.75), intake)
      ),
      new ParallelCommandGroup(
        new RunCommand(() -> tower.towerMove(true), tower),
        new RunCommand(() -> intake.intakeIn(false), intake)
      )
    );
    addRequirements(tower, intake);
  }

}
