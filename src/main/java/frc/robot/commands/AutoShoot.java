// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.TowerSubsystem;

public class AutoShoot extends SequentialCommandGroup{
  /** Creates a new AutoShoot. */
  public AutoShoot(TowerSubsystem tower) {
    // Use addRequirements() here to declare subsystem dependencies.
    super(
      new InstantCommand(() -> tower.setTowerSpeedManual(1.0), tower),
      new RunCommand(() -> tower.towerMove(false), tower).withTimeout(0.2),
      new RunCommand(() -> tower.towerMove(true), tower)
    );
    addRequirements(tower);
  }


}
