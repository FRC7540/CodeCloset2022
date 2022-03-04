// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

// Shooter will use two Redlines.
// Controllers: Victors
// Goal: Be able to run both motors at different, VARYING speeds, in 
//    opposite directions, and stop.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;

/** Add your docs here. */
public class ShooterSubsystem extends SubsystemBase {
    /** Creates a new ShooterSubsystem. */

    private static final WPI_VictorSPX shooterMotor1 = new WPI_VictorSPX(Constants.ShooterConstants.kShooterMotor1CanID);
    private static final WPI_VictorSPX shooterMotor2 = new WPI_VictorSPX(Constants.ShooterConstants.kShooterMotor2CanID);

    private static final MotorControllerGroup shooterMotors = new MotorControllerGroup(shooterMotor1, shooterMotor2);

    public ShooterSubsystem() {}

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
    }

    public void shooterStop() {
            shooterMotors.stopMotor();
    }

    public void shooterOut(double shooterSpeed) {
            shooterMotors.set(shooterSpeed);
    }
}
