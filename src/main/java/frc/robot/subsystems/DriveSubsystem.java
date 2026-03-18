package frc.robot.subsystems;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class DriveSubsystem extends SubsystemBase {
  private final SwerveModule flModule;
  private final SwerveModule frModule;
  private final SwerveModule blModule;
  private final SwerveModule brModule;

  private final SwerveDriveKinematics kinematics;
  private final GyroSubsystem gyro;

  private double lastXSpeed;
  private double lastYSpeed;
  private double lastRotationSpeed;

  public DriveSubsystem(GyroSubsystem gyro) {
    this.gyro = gyro;
    flModule = new SwerveModule(Constants.DriveConstants.FL_MOTOR_A, Constants.DriveConstants.FL_MOTOR_B);
    frModule = new SwerveModule(Constants.DriveConstants.FR_MOTOR_A, Constants.DriveConstants.FR_MOTOR_B);
    blModule = new SwerveModule(Constants.DriveConstants.BL_MOTOR_A, Constants.DriveConstants.BL_MOTOR_B);
    brModule = new SwerveModule(Constants.DriveConstants.BR_MOTOR_A, Constants.DriveConstants.BR_MOTOR_B);

    double halfWidth = Constants.DriveConstants.TRACK_WIDTH_METERS / 2.0;
    double halfLength = Constants.DriveConstants.WHEEL_BASE_METERS / 2.0;

    kinematics = new SwerveDriveKinematics(
      new Translation2d(halfLength, halfWidth),
      new Translation2d(halfLength, -halfWidth),
      new Translation2d(-halfLength, halfWidth),
      new Translation2d(-halfLength, -halfWidth)
    );
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Drive/xSpeed", lastXSpeed);
    SmartDashboard.putNumber("Drive/ySpeed", lastYSpeed);
    SmartDashboard.putNumber("Drive/rotationSpeed", lastRotationSpeed);
    SmartDashboard.putNumber("Drive/gyroAngle", gyro.getRotation2d().getDegrees());
  }

  public void drive(double xSpeed, double ySpeed, double rotationSpeed, boolean fieldRelative) {
    lastXSpeed = xSpeed;
    lastYSpeed = ySpeed;
    lastRotationSpeed = rotationSpeed;

    xSpeed = MathUtil.applyDeadband(xSpeed, Constants.DriveConstants.DEADBAND) * Constants.DriveConstants.MAX_SPEED_METERS_PER_SECOND;
    ySpeed = MathUtil.applyDeadband(ySpeed, Constants.DriveConstants.DEADBAND) * Constants.DriveConstants.MAX_SPEED_METERS_PER_SECOND;
    rotationSpeed = MathUtil.applyDeadband(rotationSpeed, Constants.DriveConstants.DEADBAND) * Constants.DriveConstants.MAX_ANGULAR_SPEED_RADIANS_PER_SECOND;

    ChassisSpeeds chassisSpeeds = fieldRelative
      ? ChassisSpeeds.fromFieldRelativeSpeeds(xSpeed, ySpeed, rotationSpeed, gyro.getRotation2d())
      : new ChassisSpeeds(xSpeed, ySpeed, rotationSpeed);

    SwerveModuleState[] states = kinematics.toSwerveModuleStates(chassisSpeeds);
    SwerveDriveKinematics.desaturateWheelSpeeds(states, Constants.DriveConstants.MAX_SPEED_METERS_PER_SECOND);

    flModule.setDesiredState(states[0]);
    frModule.setDesiredState(states[1]);
    blModule.setDesiredState(states[2]);
    brModule.setDesiredState(states[3]);
  }

  public void stop() {
    flModule.stop();
    frModule.stop();
    blModule.stop();
    brModule.stop();
  }
}

