package frc.robot.subsystems;

import com.revrobotics.CANSparkMax; 
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants; 
import edu.wpi.first.wpilibj.drive.MecanumDrive;

public class DriveBaseSubsystem extends SubsystemBase {
    private final CANSparkMax rearLeft;  
    private final CANSparkMax rearRight;
    private final CANSparkMax frontLeft; 
    private final CANSparkMax frontRight;

    private final MecanumDrive mecanumDrive;
    
    /** Creates a new DriveBaseSubsystem. */
    public DriveBaseSubsystem() {
      this.rearLeft = new CANSparkMax(Constants.DriveConstants.kRearLeftMotorCanID, Constants.DriveConstants.kMotorType);
      this.rearRight = new CANSparkMax(Constants.DriveConstants.kRearRightMotorCanID, Constants.DriveConstants.kMotorType);
      this.frontLeft = new CANSparkMax(Constants.DriveConstants.kFrontLeftMotorCanID, Constants.DriveConstants.kMotorType);
      this.frontRight = new CANSparkMax(Constants.DriveConstants.kFrontRightMotorCanID, Constants.DriveConstants.kMotorType);

      this.mecanumDrive = new MecanumDrive(this.rearLeft, this.rearRight, this.frontLeft, this.frontRight);
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
    }
}
