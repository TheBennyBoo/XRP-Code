package frc.robot;

public class Constants {
  public static final class DriveConstants {
    public static final double DEADBAND = 0.05;
    public static final double TRACK_WIDTH_METERS = 62.0 / 100.0; // 62 cm
    public static final double WHEEL_BASE_METERS = 62.0 / 100.0; // 62 cm
    public static final double MAX_SPEED_METERS_PER_SECOND = 4.0;
    public static final double MAX_ANGULAR_SPEED_RADIANS_PER_SECOND = 2.0;

    public static final int FL_MOTOR_A = 0;
    public static final int FL_MOTOR_B = 1;

    public static final int FR_MOTOR_A = 2;
    public static final int FR_MOTOR_B = 3;

    public static final int BL_MOTOR_A = 4;
    public static final int BL_MOTOR_B = 5;

    public static final int BR_MOTOR_A = 6;
    public static final int BR_MOTOR_B = 7;
  }
}