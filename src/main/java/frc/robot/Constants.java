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
        public static final int kShooterMotor1CanID = 31;
        public static final int kShooterMotor2CanID = 32;

        // Both of these constants need to be changed at the same time.
        public static final double lowestSpeed = 0.7;
        public static final double increment = 0.1;
    }

    public static final class IntakeConstants {
        public static final int kIntakeSpoolMotorCanID = 11;
        public static final int kIntakeRollerMotorCanID = 12;

        public static final double kIntakeRollerMotorSpeed = 0.5;
        public static final double kIntakeSpoolMotorSpeed = 0.7;

        public static final int kIntakeLimitSwitchUp = 0;
        public static final int kIntakeLimitSwitchDown = 1;
        public static final int kCyclesUntilDown = 10;
    }

    public static final class TowerConstants {
        public static final int kFrontMotorCanID = 21;
        public static final int kBackMotorCanID = 22;
        public static final int kTowerLimitSwitchTop = 3;
        public static final int kTowerLimitSwitchBottom = 4;

        public static final double defaultSpeed = 0.5;
    }

    public static final class ClimberConstants {
        public static final int kClimberMotorCanID = 41;

        public static final double kClimberSpeed = 0.5;
        public static final double kClimberLetDownModifier = -0.1;
    }

    public static final class IO {
        public static final int kXboxChannel = 0; // BTW, this might be wrong. Fix it if it is.
        public static final int kDriverControllerPort = 0;
        public static final int kOperatorControllerPort = 1;
    }
}
