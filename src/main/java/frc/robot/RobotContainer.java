package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.AutoIntake;
import frc.robot.commands.AutoShoot;
import frc.robot.commands.LowerFeeder;
import frc.robot.commands.RaiseFeeder;
import frc.robot.commands.StopFeeder;
import frc.robot.subsystems.*;

public class RobotContainer {
    public final ShooterSubsystem m_shooter = new ShooterSubsystem();
    private final TowerSubsystem m_tower = new TowerSubsystem();
    private final IntakeSubsystem m_intake = new IntakeSubsystem();
    private final DriveBaseSubsystem m_robotDrive = new DriveBaseSubsystem();
    // private final ClimberSubsystem m_climber = new ClimberSubsystem();

    private final XboxController m_driverController = new XboxController(Constants.IO.kDriverControllerPort);
    public final XboxController m_operatorController = new XboxController(Constants.IO.kOperatorControllerPort);

    public RobotContainer() {
        this.configureButtonBindings();
        this.configureDefaultCommands();
    }

    private void configureButtonBindings() {
        // (May switch InstantCommand to RunCommand, if this doesn't work.)
        new JoystickButton(m_operatorController, Button.kY.value).whenHeld(new AutoShoot(m_tower), false); 
        new JoystickButton(m_operatorController, Button.kX.value).whenHeld(new AutoIntake(m_tower, m_intake), false); 
        
        new JoystickButton(m_operatorController, Button.kA.value).whenPressed(new LowerFeeder(m_intake), true);
        new JoystickButton(m_operatorController, Button.kB.value).whenPressed(new RaiseFeeder(m_intake), true);
        
        new JoystickButton(m_operatorController, Button.kStart.value).whenPressed(new StopFeeder(m_intake), false);

        new JoystickButton(m_driverController, Button.kStart.value).whenPressed(new InstantCommand(() -> setCommandScheduler(false)), false);
        new JoystickButton(m_driverController, Button.kBack.value).whenPressed(new InstantCommand(() -> setCommandScheduler(true)), false);
    }

    private void configureDefaultCommands() {
        m_tower.setDefaultCommand(
            new RunCommand(() -> m_tower.towerStop(), m_tower));
        m_robotDrive.setDefaultCommand(
            new RunCommand(() -> m_robotDrive.drive(m_driverController.getLeftY(), m_driverController.getLeftX(), m_driverController.getRightX(), m_driverController.getRightTriggerAxis() > 0.1), m_robotDrive)); // m_robotDrive might be useless here.
        m_shooter.setDefaultCommand(
            new RunCommand(() -> m_shooter.shooterStop(), m_shooter));
        m_intake.setDefaultCommand(
            new RunCommand(() -> m_intake.intakeStop(), m_intake));
    }

    // if start is true, start command scheduler, if false, stop
    private void setCommandScheduler(boolean start) {
        CommandScheduler commandScheduler = CommandScheduler.getInstance();
        if (start) {
            commandScheduler.enable();
        } else {
            commandScheduler.disable();
        }
    }

    public void scheduleManualCommands() {
        CommandScheduler commandScheduler = CommandScheduler.getInstance();
        double rightTriggerLevel = m_operatorController.getRightTriggerAxis();
        double leftTriggerLevel = m_operatorController.getLeftTriggerAxis();
        
        if (leftTriggerLevel > 0.1) {
            if (m_operatorController.getLeftBumper()){
                commandScheduler.schedule(new InstantCommand(() -> m_shooter.shooterVelocity(0.2), m_shooter));
            }
            else if (m_operatorController.getRightBumper()){
                commandScheduler.schedule(new InstantCommand(() -> m_shooter.shooterVelocity(0.46), m_shooter));
            }
            else {
                commandScheduler.schedule(new InstantCommand(() -> m_shooter.shooterVelocity(0.34), m_shooter));
            }
        }
        else if (rightTriggerLevel > 0.1) {
            if (m_operatorController.getLeftBumper()){
                commandScheduler.schedule(new InstantCommand(() -> m_shooter.shooterVelocity(0.5), m_shooter));
            }
            else if (m_operatorController.getRightBumper()){
                commandScheduler.schedule(new InstantCommand(() -> m_shooter.shooterVelocity(0.6), m_shooter));
            }
            else {
                commandScheduler.schedule(new InstantCommand(() -> m_shooter.shooterVelocity(0.55), m_shooter));
            }
        }

    }

    public void autonomous() {
        Command script = new SequentialCommandGroup(
            new LowerFeeder(m_intake).withTimeout(9),
            new StopFeeder(m_intake),
            new ParallelRaceGroup(
                new RunCommand(() -> m_shooter.shooterVelocity(0.505), m_shooter).withTimeout(6), //keep running the shooter for the whole 15 second teleop
                new SequentialCommandGroup(
                    new ParallelCommandGroup (
                        new RunCommand(() -> m_robotDrive.drive(-0.5, 0, 0), m_robotDrive).withTimeout(1.3), // [drive to pick up ball (out of initial position)]
                        new AutoIntake(m_tower, m_intake).withTimeout(2)
                    ),
                    new InstantCommand(() -> m_tower.towerStop(), m_tower),
                    new WaitCommand(1),
                    new AutoShoot(m_tower).withTimeout(0.7),
                    new AutoShoot(m_tower).withTimeout(0.7)
                )
            ),
            new InstantCommand(() -> m_shooter.shooterStop(), m_shooter),
            new InstantCommand(() -> m_intake.intakeStop(), m_intake)
        ).withTimeout(15);
        CommandScheduler.getInstance().schedule(false, script);
    }
}