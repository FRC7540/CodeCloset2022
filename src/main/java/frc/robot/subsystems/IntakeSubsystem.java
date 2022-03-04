// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

// Intake will use a single Redline.
// Controllers: Unsure. Will probably just be a relay.
// Goal: Be able to go forward at VARYING speed, backward at VARYING speed, and stop.

package frc.robot.subsystems;

import frc.robot.Constants;
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
    public void intakePosition(boolean isUp) {
        if(isUp && !upLimitSwitch.get()){
            spoolMotor.set(Constants.IntakeConstants.kIntakeSpoolMotorSpeed);
        } else if(!isUp && !downLimitSwitch.get()) {
            spoolMotor.set(-Constants.IntakeConstants.kIntakeSpoolMotorSpeed);
        } else {
            spoolMotor.set(0);
        }
    }

    public void intakeSpoolStop() {
        spoolMotor.stopMotor();
    }
}
