// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.TowerSubsystem;

public class AutoShoot extends SequentialCommandGroup {
  /** Creates a new AutoShoot. */
  public AutoShoot(TowerSubsystem tower) {
    super(
        new InstantCommand(() -> tower.setTowerSpeedManual(1.0), tower),
        new RunCommand(() -> tower.towerMove(false), tower).withTimeout(0.3),
        new RunCommand(() -> tower.towerMove(true), tower).until(() -> tower.topLimitSwitchTrigger()),
        // 0.3 is to long, 0.2 is to short.
        // 0.3 can sometimes shoot 2 balls
        // 0.2 can sometimes not shoot a ball.
        // This is here because the limit switch is slightly lower than the shooter
        new RunCommand(() -> tower.towerMove(true), tower).withTimeout(1));
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(tower);
  }

}
