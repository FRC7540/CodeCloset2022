// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

// Intake will use a single Redline.
// Controllers: Unsure. Will probably just be a relay.
// Goal: Be able to go forward at VARYING speed, backward at VARYING speed, and stop.

package frc.robot.subsystems;

import frc.robot.Constants;

import java.util.Map;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/** Add your docs here. */
public class IntakeSubsystem extends SubsystemBase {
    /** Creates a new IntakeSubsystem. */

    private final DigitalInput upLimitSwitch = new DigitalInput(Constants.IntakeConstants.kIntakeLimitSwitchUp);
    private final DigitalInput downLimitSwitch = new DigitalInput(Constants.IntakeConstants.kIntakeLimitSwitchDown);

    private static final WPI_VictorSPX spoolMotor = new WPI_VictorSPX(Constants.IntakeConstants.kIntakeSpoolMotorCanID);
    private static final WPI_VictorSPX rollerMotor = new WPI_VictorSPX(
            Constants.IntakeConstants.kIntakeRollerMotorCanID);

    private final NetworkTableEntry intakeRollerTargetSpeed = Shuffleboard
            .getTab(Constants.ShuffleboardConstants.kGameTabName)
            .add(Constants.IntakeConstants.kIntakeRollerVoltage, 0.0)
            .withWidget(BuiltInWidgets.kVoltageView)
            .withSize(2, 1)
            .withPosition(0, 5)
            .withProperties(Map.of("min", 0, "max", 12))
            .getEntry();
    private static double rollerSpeed = 0;

    public IntakeSubsystem() {
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
        intakeRollerTargetSpeed.setDouble(rollerSpeed);
    }

    public void intakeStop() {
        rollerSpeed = 0;
        rollerMotor.stopMotor();
        spoolMotor.stopMotor();
    }

    // Note: isReverse should be FALSE in order to intake balls. TRUE makes it spit
    // them out.
    public void intakeIn(boolean isReverse) {
        if (isReverse) {
            rollerMotor.set(-Constants.IntakeConstants.kIntakeRollerMotorSpeed);
        } else {
            rollerMotor.set(Constants.IntakeConstants.kIntakeRollerMotorSpeed);
        }
    }

    // Note: isUp should be TRUE to spool paracord. FALSE unspools, setting the
    // intake rollers down.
    public void intakePosition(boolean isUp) {
        // NOTE: negative constant speed for down, positive for up
        if (isUp && upLimitSwitch.get()) {
            spoolMotor.set(0.9); //Constants.IntakeConstants.kIntakeSpoolMotorSpeed);
        } else if (!isUp && downLimitSwitch.get()) {
            spoolMotor.set(-0.5);
        } else {
            spoolMotor.stopMotor();
        }
    }

    public boolean isDown() {
        return !downLimitSwitch.get();
    }

    public boolean isUp() {
        return !upLimitSwitch.get();
    }

    public void intakeSpoolStop() {
        spoolMotor.stopMotor();
    }

    // if speedControl is higher than rollerSpeed, set rollerSpeed to speedControl.
    // Roller stops when intakeStop() called.
    public void intakeSpeedSet(double speedControl) {
        if (speedControl > rollerSpeed) {
            rollerSpeed = speedControl;
        }
        rollerMotor.set(rollerSpeed);
    }

    public void intakeSpeedSetManual(double speedControl) {
        rollerMotor.set(rollerSpeed);
    }
}
