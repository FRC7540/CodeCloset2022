// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

// Climber one redline
// Controller: victor
// CLIMBER: Driver controller button Y to go up, and button X to go down.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

/** Add your docs here. */
public class ClimberSubsystem extends SubsystemBase {
    /** Creates a new ClimberSubsystem. */

    private static final WPI_VictorSPX climberMotor = new WPI_VictorSPX(Constants.ClimberConstants.kClimberMotorCanID);
    private static final DigitalInput limitSwitch = new DigitalInput(Constants.ClimberConstants.kclimberLowerSwtich);
    private static final Encoder encoder = 
        new Encoder(Constants.ClimberConstants.kClimberEncoderA, Constants.ClimberConstants.kClimberEncoderB, false, Encoder.EncodingType.k2X);
    private double DistancePerPulse = Constants.ClimberConstants.kEncoderDistancePerPulse;
    private static final NetworkTableEntry encoderEntry= Shuffleboard
    .getTab(Constants.ShuffleboardConstants.kConfigTabName)
    .add("Encoder Distance Travled", 0.0)
    .withPosition(0, 1)
    .withSize(1, 1)
    .getEntry();

    private static final NetworkTableEntry encoderDistancePerPulseEntry = Shuffleboard
    .getTab(Constants.ShuffleboardConstants.kConfigTabName)
    .add("Encoder distance per pulse", 0.0)
    .withPosition(0, 3)
    .getEntry();

    private static final NetworkTableEntry encoderPulsecountEntry = Shuffleboard
    .getTab(Constants.ShuffleboardConstants.kConfigTabName)
    .add("Pulse count", 0.0)
    .withPosition(0, 4)
    .getEntry();



    public ClimberSubsystem() {
        encoder.setDistancePerPulse(Constants.ClimberConstants.kEncoderDistancePerPulse);
        encoderDistancePerPulseEntry.setDouble(DistancePerPulse);
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
        encoderPulsecountEntry.setDouble(encoder.get());
        encoderEntry.setDouble(encoder.getDistance());
        DistancePerPulse = encoderDistancePerPulseEntry.getDouble(Constants.ClimberConstants.kEncoderDistancePerPulse);
    }
    
    public void climbStop() {
        climberMotor.stopMotor();
    }
    
    
    public boolean lowerlimitswitch() {
        return limitSwitch.get();
    }

    public double encoderDistanceTravled() {
        return encoder.getDistance();
    }

    public void Climb() {
        climberMotor.set(Constants.ClimberConstants.kClimberSpeed);
    }

}
