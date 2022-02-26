package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.subsystems.*;

public class RobotContainer {
    private final ShooterSubsystem m_shooter = new ShooterSubsystem();
    private final TowerSubsystem m_tower = new TowerSubsystem();
    private final IntakeSubsystem m_intake = new IntakeSubsystem();
    private final DriveBaseSubsystem m_robotDrive = new DriveBaseSubsystem();

    private final XboxController m_driverController = new XboxController(Constants.IO.kDriverControllerPort);
    private final XboxController m_operatorController = new XboxController(Constants.IO.kOperatorControllerPort);

    public RobotContainer() {
        this.configureButtonBindings();
        this.configureDefaultCommands();
    }

    private void configureButtonBindings() {
        // (May switch InstantCommand to RunCommand)
        new JoystickButton(m_operatorController, Button.kY.value).whenHeld(new InstantCommand(() -> m_tower.towerMove(true), m_tower), false);
        new JoystickButton(m_operatorController, Button.kX.value).whenHeld(new InstantCommand(() -> m_tower.towerMove(false), m_tower), false);
    }

    private void configureDefaultCommands() {
        m_tower.setDefaultCommand(
            new RunCommand(() -> m_tower.towerStop(), m_tower));
    }
}