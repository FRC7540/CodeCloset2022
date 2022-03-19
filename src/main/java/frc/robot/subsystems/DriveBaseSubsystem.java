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

      this.mecanumDrive = new MecanumDrive(this.frontLeft, this.rearLeft, this.frontRight, this.rearRight);
      this.rearLeft.setInverted(true);
      this.rearRight.setInverted(false);
      this.frontLeft.setInverted(true);
      this.frontRight.setInverted(false);
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
    }

    public void drive(double ySpeed, double xSpeed, double zRotation, boolean isSlowMode) {
      if (Math.abs(ySpeed) < Constants.DriveConstants.deadzone)
        ySpeed = 0;
      if (Math.abs(xSpeed) < Constants.DriveConstants.deadzone)
        xSpeed = 0;
      if (Math.abs(zRotation) < Constants.DriveConstants.deadzone)
        zRotation = 0;
      if (isSlowMode)
        mecanumDrive.driveCartesian(ySpeed*0.5, xSpeed*0.5, zRotation*0.5);
      else 
        mecanumDrive.driveCartesian(ySpeed, xSpeed, zRotation);
    }

    public void drive(double ySpeed, double xSpeed, double zRotation) {
      drive(ySpeed, xSpeed, zRotation, false);
    }

    public void driveStop() {
      mecanumDrive.stopMotor();
    }
}
