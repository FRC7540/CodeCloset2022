package frc.robot;

import com.revrobotics.CANSparkMaxLowLevel.MotorType;


public final class Constants {
    public static final class DriveConstants {
        public static final int kFrontLeftMotorCanID = 1;
        public static final int kFrontRightMotorCanID = 2;
        public static final int kRearLeftMotorCanID = 3;
        public static final int kRearRightMotorCanID = 4;

        public static final MotorType kMotorType = MotorType.kBrushed; 
    }

    public static final class ShooterConstants {
        public static final int kShooterMotor1CanID = 8;
        public static final int kShooterMotor2CanID = 9;

        public static final MotorType kMotorType = MotorType.kBrushed;
    }
}
