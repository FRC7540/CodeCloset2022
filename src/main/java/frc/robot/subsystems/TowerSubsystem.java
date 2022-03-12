// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

// Tower will use two Mini CIM motors. 
// Controllers: Victors.
// Goal: Be able to move a motor in both directions at a VARYING speed 
//    Also needs to be able to reverse the movement to spit out balls.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;

/** Add your docs here. */
public class TowerSubsystem extends SubsystemBase {

    private static final WPI_VictorSPX frontMotor = new WPI_VictorSPX(Constants.TowerConstants.kFrontMotorCanID);
    private static final WPI_VictorSPX backMotor = new WPI_VictorSPX(Constants.TowerConstants.kBackMotorCanID);
    private final MotorControllerGroup motorGroup = new MotorControllerGroup(frontMotor, backMotor);

    private static double towerSpeed = Constants.TowerConstants.defaultSpeed;

    /** Creates a new DriveBaseSubsystem. */
    public TowerSubsystem() {
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
    }

    public void towerStop() {
        motorGroup.stopMotor();
    }

    // Assume hardware will just inverseley wire one motor, to make them opposite
    // directions.
    // Note: isUp should be TRUE for the tower to pull balls upward.
    // FALSE ejects them in the direction of the intake. (Which also needs to
    // reverse to spit them back out.)
    public void towerMove(boolean isUp) {
        if (isUp) {
            motorGroup.set(towerSpeed);
        } else {
            motorGroup.set(-towerSpeed);
        }
    }

    //increments kTowerSpeed by 10%. If up true, +10%. if false, -10%.
    public void setTowerSpeed (boolean up) {
        if (up && !(towerSpeed >= 1)) {
            towerSpeed = towerSpeed + 0.1;
        } else if (!up && !(towerSpeed <= 0)) {
            towerSpeed = towerSpeed - 0.1;
        }
    }
    public void setTowerSpeedManual(double set) {
        towerSpeed = set;
    }
}
