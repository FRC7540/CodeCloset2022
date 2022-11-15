/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

import edu.wpi.first.cameraserver.*;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private RobotContainer m_robotContainer;

  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {

    m_robotContainer = new RobotContainer();


    // Put the autonomous mode selector on the config tab in the shuffleboard
    Shuffleboard.getTab(Constants.ShuffleboardConstants.kConfigTabName)
        .add("Auto mode", m_chooser)
        .withWidget(BuiltInWidgets.kComboBoxChooser);
    m_chooser.setDefaultOption(Constants.ShuffleboardConstants.kCustomAuto1ShuffleBoardName, kCustomAuto);
    m_chooser.addOption(Constants.ShuffleboardConstants.kDefaultAutoShuffleBoardName, kDefaultAuto);

    Shuffleboard.getTab(Constants.ShuffleboardConstants.kGameTabName)
        .add(CameraServer.startAutomaticCapture(1))
        .withWidget(BuiltInWidgets.kCameraStream)
        .withPosition(4, 0)
        .withSize(4, 4);
    Shuffleboard.getTab(Constants.ShuffleboardConstants.kGameTabName)
        .add(CameraServer.startAutomaticCapture(0))
        .withWidget(BuiltInWidgets.kCameraStream)
        .withPosition(0, 0)
        .withSize(4, 4);
  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>
   * This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString line to get the auto name from the text box below the Gyro
   *
   * <p>
   * You can add additional auto modes by adding additional comparisons to
   * the switch structure below with additional strings. If using the
   * SendableChooser make sure to add them to the chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
    m_robotContainer.autonomous();
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    switch (m_autoSelected) {
      case kCustomAuto:
        // Put custom auto code here
        CommandScheduler.getInstance().run();
        break;
      case kDefaultAuto:
      default:
        // Put default auto code here
        break;
    }
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    m_robotContainer.scheduleManualCommands();
    CommandScheduler.getInstance().run();
  }

  @Override
  public void testInit() {
    m_robotContainer.fieldSimSetup();
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
    m_robotContainer.scheduleManualCommands();
    m_robotContainer.drivetrainSimulation();
    CommandScheduler.getInstance().run();
  }
}
