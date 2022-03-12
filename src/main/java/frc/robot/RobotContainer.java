package frc.robot;

import java.util.concurrent.DelayQueue;

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

    double kTowerSpeed = 0.5;

    public RobotContainer() {
        this.configureButtonBindings();
        this.configureDefaultCommands();
    }

    private void configureButtonBindings() {
        // (May switch InstantCommand to RunCommand, if this doesn't work.)
        new JoystickButton(m_operatorController, Button.kY.value).whenHeld(new RunCommand(() -> m_tower.towerMove(true), m_tower), false);
        new JoystickButton(m_operatorController, Button.kA.value).whenHeld(new RunCommand(() -> m_tower.towerMove(false), m_tower), false);
        new JoystickButton(m_operatorController, Button.kX.value).whenPressed(new InstantCommand(() -> m_tower.setTowerSpeed(false)), false); //left button - decreases speed
        new JoystickButton(m_operatorController, Button.kB.value).whenPressed(new InstantCommand(() -> m_tower.setTowerSpeed(true)), false); //right button - increases speed
        new JoystickButton(m_operatorController, Button.kRightBumper.value).whenPressed(new InstantCommand(() -> m_shooter.shooterAngleModifier(true)), false);
        new JoystickButton(m_operatorController, Button.kLeftBumper.value).whenPressed(new InstantCommand(() -> m_shooter.shooterAngleModifier(false)), false);
        
        new JoystickButton(m_driverController, Button.kA.value).whenPressed(new LowerFeeder(m_intake), true);
        new JoystickButton(m_driverController, Button.kY.value).whenPressed(new RaiseFeeder(m_intake), true);
        new JoystickButton(m_driverController, Button.kX.value).whenPressed(new StopFeeder(m_intake), false);
        new JoystickButton(m_driverController, Button.kRightBumper.value).whenPressed(new InstantCommand(() -> m_intake.intakeStop()), false); //Left bumper - stops intake roller

        new JoystickButton(m_operatorController, Button.kStart.value).whenPressed(new InstantCommand(() -> setCommandScheduler(false)), false);
        new JoystickButton(m_operatorController, Button.kBack.value).whenPressed(new InstantCommand(() -> setCommandScheduler(true)), false);

    }

    private void configureDefaultCommands() {
        m_tower.setDefaultCommand(
            new RunCommand(() -> m_tower.towerStop(), m_tower));
        m_robotDrive.setDefaultCommand(
            new RunCommand(() -> m_robotDrive.drive(m_driverController.getLeftY(), m_driverController.getLeftX(), m_driverController.getRightX()), m_robotDrive)); // m_robotDrive might be useless here.
        m_shooter.setDefaultCommand(
            new RunCommand(() -> m_shooter.shooterVelocity(m_operatorController.getRightTriggerAxis()), m_shooter));
        m_intake.setDefaultCommand(
            new RunCommand(() -> m_intake.intakeSpeedSet(m_driverController.getRightTriggerAxis()), m_intake));
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

    public void autonomous() {
        Command script = new SequentialCommandGroup(
            new InstantCommand(() -> m_tower.setTowerSpeedManual(1), m_tower),
            new ParallelRaceGroup(
                new RunCommand(() -> m_shooter.shooterVelocity(0.6), m_shooter).withTimeout(14), //keep running the shooter for the whole 15 second teleop
                new SequentialCommandGroup(
                    new ParallelCommandGroup (
                        new RunCommand(() -> m_robotDrive.drive(-0.5, 0, 0), m_robotDrive).withTimeout(3), // [drive to pick up ball (out of initial position)]
                        new RunCommand(() -> m_intake.intakeIn(false), m_intake).withTimeout(4),//add a bottom limit argument here [run intake to pick up ball]
                        new RunCommand(() -> m_tower.towerMove(true), m_tower).withTimeout(4)//add a top limit argument here [run tower to keep ball in robot]
                    ),
                    new InstantCommand(() -> m_tower.towerStop(), m_tower),
                    new WaitCommand(1),
                    new RunCommand(() -> m_robotDrive.drive(-0.5, 0, 0), m_robotDrive).withTimeout(2), // [drive to firing position]
                    new RunCommand(() -> m_tower.towerMove(true), m_tower).withTimeout(1), // [tower up (first ball)]
                    new RunCommand(() -> m_tower.towerMove(false), m_tower).withTimeout(2), //add bottom limit [tower down (second ball, if there is one)]
                    new RunCommand(() -> m_tower.towerMove(true), m_tower).withTimeout(2)// [tower up (second ball, if there is one)]
                )
            ),
            new InstantCommand(() -> m_shooter.shooterStop(), m_shooter),
            new InstantCommand(() -> m_intake.intakeStop(), m_intake)
        );
        CommandScheduler.getInstance().schedule(false, script);
    }
}