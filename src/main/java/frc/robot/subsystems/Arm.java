// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.xrp.XRPServo;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Arm extends SubsystemBase {
  private final XRPServo m_armLower;
  private final XRPServo m_armUpper;

  /** Creates a new Arm. */
  public Arm() {
    // Device number 4 is Servo 1 port and device number 5 is Servo 2 on the XRP
    m_armLower = new XRPServo(5);
    m_armUpper = new XRPServo(4);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  /**
   * Set the current angle of the lower arm (0 - 180 degrees).
   *
   * @param angleDeg Desired lower arm angle in degrees
   */
  public void setAngleLower(double angleDeg) {
    m_armLower.setAngle(angleDeg);
  }

  /**
   * Set the current angle of the upper arm (0 - 180 degrees).
   *
   * @param angleDeg Desired upper arm angle in degrees
   */
  public void setAngleUpper(double angleDeg) {
    m_armUpper.setAngle(angleDeg);
  }

  /**
   * Turns the lower arm at a given speed (0 - 180 degrees).
   *
   * @param speed Speed of the turn
   * @param isUpward Whether the turn is upward (true) or downward (false)
   */
  public void turnSpeedLower(double speed, boolean isUpward) {
    m_armLower.setAngle(m_armLower.getAngle() + (isUpward ? speed : -speed));
  }

  /**
   * Turns the upper arm at a given speed (0 - 180 degrees).
   *
   * @param speed Speed of the turn
   * @param isUpward Whether the turn is upward (true) or downward (false)
   */
  public void turnSpeedUpper(double speed, boolean isUpward) {
    m_armUpper.setAngle(m_armUpper.getAngle() + (isUpward ? speed : -speed));
  }

  /**
   * Get the current angle of the lower arm (0 - 180 degrees).
   *
   * @return Current lower arm angle in degrees
   */
  public double getAngleLower() {
    return m_armLower.getAngle();
  }

  /**
   * Get the current angle of the upper arm (0 - 180 degrees).
   *
   * @return Current upper arm angle in degrees
   */
  public double getAngleUpper() {
    return m_armUpper.getAngle();
  }
}