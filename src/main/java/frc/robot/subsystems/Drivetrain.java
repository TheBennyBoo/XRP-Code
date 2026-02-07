// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.xrp.XRPGyro;
import edu.wpi.first.wpilibj.xrp.XRPMotor;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Drivetrain extends SubsystemBase {
  private static final double kGearRatio = 48.75;
  private static final double kCountsPerMotorShaftRev = 12.0;
  private static final double kCountsPerRevolution = kCountsPerMotorShaftRev * kGearRatio; // 585.0
  private static final double kWheelDiameterInch = 2.3622; // 60 mm

  // PID values
  private double kP = 0.05;
  private double kD = 0.0;
  private double deadband = 0.05;
  private double targetAngle = 0.0;
  private double error = 0.0;
  private double previousError = 0.0;
  private double correction = 0.0;
  private double p = 0.0;
  private double d = 0.0;

  // The XRP has the left and right motors set to
  // channels 0 and 1 respectively
  private final XRPMotor m_leftMotor = new XRPMotor(0);
  private final XRPMotor m_rightMotor = new XRPMotor(1);

  // The XRP has onboard encoders that are hardcoded
  // to use DIO pins 4/5 and 6/7 for the left and right
  private final Encoder m_leftEncoder = new Encoder(4, 5);
  private final Encoder m_rightEncoder = new Encoder(6, 7);

  // Set up the differential drive controller
  private final DifferentialDrive m_diffDrive =
      new DifferentialDrive(m_leftMotor::set, m_rightMotor::set);

  // Set up the XRPGyro
  private final XRPGyro m_gyro = new XRPGyro();

  // Set up the BuiltInAccelerometer
  private final BuiltInAccelerometer m_accelerometer = new BuiltInAccelerometer();

  /** Creates a new Drivetrain. */
  public Drivetrain() {
    SendableRegistry.addChild(m_diffDrive, m_leftMotor);
    SendableRegistry.addChild(m_diffDrive, m_rightMotor);

    // We need to invert one side of the drivetrain so that positive voltages
    // result in both sides moving forward
    invertMotors(false, true);

    // Use inches as unit for encoder distances
    m_leftEncoder.setDistancePerPulse((Math.PI * kWheelDiameterInch) / kCountsPerRevolution);
    m_rightEncoder.setDistancePerPulse((Math.PI * kWheelDiameterInch) / kCountsPerRevolution);
    resetEncoders();

    // Reset the gyro and target angle
    resetGyro();
    setTargetAngle(0.0);
  }

  public void arcadeDrive(double xaxisSpeed, double zaxisRotate) {
    if (xaxisSpeed < 0) {
      invertMotors(false, true);
    } else if (xaxisSpeed > 0) {
      invertMotors(true, false);
    }
    if (xaxisSpeed == 0.0 && zaxisRotate == 0.0) {
      // Robot is not moving, reset target angle
      setTargetAngle(getGyroAngleZ());
    } else if (Math.abs(zaxisRotate) < deadband) {
      // Driver is not turning, drive straight with correction
      correction = getHeadingCorrection();
      m_diffDrive.arcadeDrive(xaxisSpeed, correction);
    } else {
      // Driver is turning, update target angle
      setTargetAngle(getGyroAngleZ());
      m_diffDrive.arcadeDrive(xaxisSpeed, zaxisRotate);
    }
  }

  public void invertMotors(boolean leftMotor, boolean rightMotor) {
    m_leftMotor.setInverted(leftMotor);
    m_rightMotor.setInverted(rightMotor);
  }

  public void resetEncoders() {
    m_leftEncoder.reset();
    m_rightEncoder.reset();
  }

  public int getLeftEncoderCount() {
    return m_leftEncoder.get();
  }

  public int getRightEncoderCount() {
    return m_rightEncoder.get();
  }

  public double getLeftDistanceInch() {
    return m_leftEncoder.getDistance();
  }

  public double getRightDistanceInch() {
    return m_rightEncoder.getDistance();
  }

  public double getAverageDistanceInch() {
    return (getLeftDistanceInch() + getRightDistanceInch()) / 2.0;
  }

  /**
   * The acceleration in the X-axis.
   *
   * @return The acceleration of the XRP along the X-axis in Gs
   */
  public double getAccelX() {
    return m_accelerometer.getX();
  }

  /**
   * The acceleration in the Y-axis.
   *
   * @return The acceleration of the XRP along the Y-axis in Gs
   */
  public double getAccelY() {
    return m_accelerometer.getY();
  }

  /**
   * The acceleration in the Z-axis.
   *
   * @return The acceleration of the XRP along the Z-axis in Gs
   */
  public double getAccelZ() {
    return m_accelerometer.getZ();
  }

  /** Sets the target angle in degrees. */
  public void setTargetAngle(double angleDeg) {
    targetAngle = angleDeg;
  }

  /**
   * Calculates heading correction using PID values
   * 
   * @return The heading correction of the XRP along the Z-axis
   */
  public double getHeadingCorrection() {
    error = targetAngle - getGyroAngleZ();
    p = kP * error;
    d = kD * (error - previousError);
    previousError = error;
    return p + d;
  }

  /**
   * Current angle of the XRP around the X-axis.
   *
   * @return The current angle of the XRP in degrees
   */
  public double getGyroAngleX() {
    return m_gyro.getAngleX();
  }

  /**
   * Current angle of the XRP around the Y-axis.
   *
   * @return The current angle of the XRP in degrees
   */
  public double getGyroAngleY() {
    return m_gyro.getAngleY();
  }

  /**
   * Current angle of the XRP around the Z-axis.
   *
   * @return The current angle of the XRP in degrees
   */
  public double getGyroAngleZ() {
    return m_gyro.getAngleZ();
  }

  /** Reset the gyro. */
  public void resetGyro() {
    m_gyro.reset();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    kP = SmartDashboard.getNumber("kP", 0.05);
    kD = SmartDashboard.getNumber("kD", 0.01);
    deadband = SmartDashboard.getNumber("Deadband", 0.05);
  }
}
