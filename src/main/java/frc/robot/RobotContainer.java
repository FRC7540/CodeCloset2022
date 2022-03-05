package frc.robot;

import javax.swing.ButtonGroup;

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
    private final ClimberSubsystem m_climber = new ClimberSubsystem();

    private final XboxController m_driverController = new XboxController(Constants.IO.kDriverControllerPort);
    private final XboxController m_operatorController = new XboxController(Constants.IO.kOperatorControllerPort);

    public static boolean kOperateRobot = true;

    double kTowerSpeed = 0.5;

    public RobotContainer() {
        this.configureButtonBindings();
        this.configureDefaultCommands();
    }

    private void configureButtonBindings() {
        // (May switch InstantCommand to RunCommand, if this doesn't work.)
        new JoystickButton(m_operatorController, Button.kY.value).whenHeld(new InstantCommand(() -> m_tower.towerMove(true, kTowerSpeed), m_tower), false);
        new JoystickButton(m_operatorController, Button.kA.value).whenHeld(new InstantCommand(() -> m_tower.towerMove(false, kTowerSpeed), m_tower), false);
        new JoystickButton(m_operatorController, Button.kX.value).whenPressed(new InstantCommand(() -> setTowerSpeed(true)), false); //left button - decreases speed
        new JoystickButton(m_operatorController, Button.kB.value).whenPressed(new InstantCommand(() -> setTowerSpeed(false)), false); //right button - increases speed
        new JoystickButton(m_operatorController, Button.kBack.value).whenPressed(new InstantCommand(() -> m_intake.intakePosition()), false); //select button - moves intake up/down when pressed
        new JoystickButton(m_operatorController, Button.kStart.value).whenPressed(new InstantCommand(() -> m_intake.intakeSpoolStop()), false); //start button - stops intake up/down movement
        new JoystickButton(m_operatorController, Button.kLeftBumper.value).whenPressed(new InstantCommand(() -> m_intake.intakeStop()), false); //Left bumper - stops intake roller 

        new JoystickButton(m_driverController, Button.kStart.value).whenPressed(new InstantCommand(() -> stopAll()), false);
    }

    private void setTowerSpeed (boolean up) {
        if (up && !(kTowerSpeed >= 1)) {
            kTowerSpeed = kTowerSpeed + 0.1;
        } else if (!up && !(kTowerSpeed <= 0)) {
            kTowerSpeed = kTowerSpeed - 0.1;
        } else {
        }
    }

    private void configureDefaultCommands() {
        m_tower.setDefaultCommand(
            new RunCommand(() -> m_tower.towerStop(), m_tower));
        m_robotDrive.setDefaultCommand(
            new RunCommand(() -> m_robotDrive.driveStop(), m_robotDrive)); // m_robotDrive might be useless here.
    }

    private void stopAll() {
        if (kOperateRobot) {
            kOperateRobot = false;
            m_intake.intakeStop();
            m_intake.intakeSpoolStop();
            m_tower.towerStop();
            m_climber.climbStop();
            m_shooter.shooterStop();
            m_robotDrive.driveStop();
        } else {
            kOperateRobot = true;
        }
        
    }
}