// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

// Intake will use a single Redline.
// Controllers: Unsure. Will probably just be a relay.
// Goal: Be able to go forward at VARYING speed, backward at VARYING speed, and stop.

package frc.robot.subsystems;

import frc.robot.Constants;
import frc.robot.RobotContainer;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/** Add your docs here. */
public class IntakeSubsystem extends SubsystemBase {
    /** Creates a new IntakeSubsystem. */

    DigitalInput upLimitSwitch = new DigitalInput(Constants.IntakeConstants.kIntakeLimitSwitchUp);
    DigitalInput downLimitSwitch = new DigitalInput(Constants.IntakeConstants.kIntakeLimitSwitchDown);

    private static final WPI_VictorSPX spoolMotor = new WPI_VictorSPX(Constants.IntakeConstants.kIntakeSpoolMotorCanID);
    private static final WPI_VictorSPX rollerMotor = new WPI_VictorSPX(Constants.IntakeConstants.kIntakeRollerMotorCanID);  

    private boolean kIntakeCallibrated = false;
    private boolean isUp = true;
    private boolean moving = false;

    private static double rollerSpeed = 0;

    public IntakeSubsystem() {}

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
    }

    public void intakeStop() {
        rollerMotor.stopMotor();
    }

    // Note: isReverse should be FALSE in order to intake balls. TRUE makes it spit them out.
    public void intakeIn(boolean isReverse) {
        if(isReverse){
            rollerMotor.set(-Constants.IntakeConstants.kIntakeRollerMotorSpeed);
        } else {
            rollerMotor.set(Constants.IntakeConstants.kIntakeRollerMotorSpeed);
        }
    }

    // Note: isUp should be TRUE to spool paracord. FALSE unspools, setting the intake rollers down.
    public void intakePosition() {
        //NOTE: negative constant speed for down, positive for up
        if (RobotContainer.kOperateRobot) {
            moving = true;
            do {
                if(!kIntakeCallibrated){
                    spoolMotor.set(Constants.IntakeConstants.kIntakeSpoolMotorSpeed);
                } else if (!kIntakeCallibrated && upLimitSwitch.get()) {
                    spoolMotor.stopMotor();
                    kIntakeCallibrated = true;
                } else {
                    if (isUp && !downLimitSwitch.get()) {
                        spoolMotor.set(Constants.IntakeConstants.kIntakeSpoolMotorSpeed);
                    } else if (!isUp && !upLimitSwitch.get()) {
                        spoolMotor.set(Constants.IntakeConstants.kIntakeSpoolMotorSpeed);
                    } else {
                        spoolMotor.stopMotor();
                        isUp = !isUp;
                        moving = false;
                    }
                }
            } while (moving);
        }
    }

    public void intakeSpoolStop() {
        spoolMotor.stopMotor();
        kIntakeCallibrated = false;
    }

    public static void intakeSpeedSet(double speedControl) {
        if (speedControl > rollerSpeed) {
            rollerSpeed = speedControl;            
        }
        rollerMotor.set(rollerSpeed);
    }
}
