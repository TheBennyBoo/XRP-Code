package com.team540.xrp2026.subsystems;

import com.team540.xrp2026.Constants;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DriveSubsystem extends SubsystemBase {
  private final SwerveModule flModule;
  private final SwerveModule frModule;
  private final SwerveModule blModule;
  private final SwerveModule brModule;

  private final SwerveDriveKinematics kinematics;
  private final GyroSubsystem gyro;

  public DriveSubsystem(GyroSubsystem gyro) {
    this.gyro = gyro;
    flModule = new SwerveModule(Constants.Drive.FL_MOTOR_A, Constants.Drive.FL_MOTOR_B);
    frModule = new SwerveModule(Constants.Drive.FR_MOTOR_A, Constants.Drive.FR_MOTOR_B);
    blModule = new SwerveModule(Constants.Drive.BL_MOTOR_A, Constants.Drive.BL_MOTOR_B);
    brModule = new SwerveModule(Constants.Drive.BR_MOTOR_A, Constants.Drive.BR_MOTOR_B);

    double halfWidth = Constants.Drive.TRACK_WIDTH / 2.0;
    double halfLength = Constants.Drive.WHEEL_BASE / 2.0;

    kinematics = new SwerveDriveKinematics(
      new Translation2d(halfLength, halfWidth),
      new Translation2d(halfLength, -halfWidth),
      new Translation2d(-halfLength, halfWidth),
      new Translation2d(-halfLength, -halfWidth)
    );
  }

  @Override
  public void periodic() {}

  public void drive(double xSpeed, double ySpeed, double rotationSpeed, boolean fieldRelative) {
    xSpeed = MathUtil.applyDeadband(xSpeed, Constants.Drive.DEADBAND) * Constants.Drive.MAX_SPEED_METERS_PER_SECOND;
    ySpeed = MathUtil.applyDeadband(ySpeed, Constants.Drive.DEADBAND) * Constants.Drive.MAX_SPEED_METERS_PER_SECOND;
    rotationSpeed = MathUtil.applyDeadband(rotationSpeed, Constants.Drive.DEADBAND) * Constants.Drive.MAX_ANGULAR_SPEED_RADIANS_PER_SECOND;

    ChassisSpeeds chassisSpeeds = fieldRelative
      ? ChassisSpeeds.fromFieldRelativeSpeeds(xSpeed, ySpeed, rotationSpeed, gyro.getRotation2d())
      : new ChassisSpeeds(xSpeed, ySpeed, rotationSpeed);

    SwerveModuleState[] states = kinematics.toSwerveModuleStates(chassisSpeeds);
    SwerveDriveKinematics.desaturateWheelSpeeds(states, Constants.Drive.MAX_SPEED_METERS_PER_SECOND);

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

