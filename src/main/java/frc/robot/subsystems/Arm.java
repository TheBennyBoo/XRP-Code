// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.xrp.XRPServo;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Arm extends SubsystemBase {
  // The lower servo controls the base joint of the arm (port 4 on the XRP)
  private final XRPServo m_armServoLower;
  // The upper servo controls the top joint of the arm (port 5 on the XRP)
  private final XRPServo m_armServoUpper;

  // Tracks the upper servo's current angle so we can increment/decrement it
  // WARNING: setAngleTwo() does NOT update this, so they can get out of sync
  private double currentAngle = 0.0;

  /** Creates a new Arm and initializes both servos */
  public Arm() {
    // Port 4 = physical Servo 1 port on the XRP board
    m_armServoLower = new XRPServo(4);
    // Port 5 = physical Servo 2 port on the XRP board
    m_armServoUpper = new XRPServo(5);

    // Start the upper arm at 45 degrees when the robot turns on
    m_armServoUpper.setAngle(120);
  }

  @Override
  public void periodic() {
    // Runs every 20ms - good place to add SmartDashboard updates if needed
  }

  /**
   * Directly sets the LOWER servo angle. No safety bounds - be careful!
   * Valid range is 0 to 180 degrees.
   *
   * @param angleDeg Desired arm angle in degrees
   */
  public void setAngle(double angleDeg) {
    // Sends the angle straight to the lower servo with no clamping
    m_armServoLower.setAngle(angleDeg);
  }

  
  /**
   * Safe - clamps the angle between 0 and 180
   * and keeps currentAngle in sync with the servo's actual position.
   */
  public void setUpperAngle(double angleDeg) {
    // Clamp the angle so it never goes below 0 or above 180
    currentAngle = Math.max(0, Math.min(180, angleDeg));
    m_armServoUpper.setAngle(currentAngle);
  }

  /**
   * Nudges the upper arm UP by the given number of degrees.
   * Used by MoveArmUp command, which calls this with 1.0 degree per cycle.
   * At 50 cycles/sec that means ~50 degrees per second of movement.
   */
  public void increaseAngle(double degrees) {
    currentAngle += degrees; // Add degrees to current position
    currentAngle = Math.max(0, Math.min(180, currentAngle)); // Clamp to valid range
    m_armServoUpper.setAngle(currentAngle); // Send new angle to servo
  }

  /**
   * Nudges the upper arm DOWN by the given number of degrees.
   * Mirror of increaseAngle - used by MoveArmDown command.
   */
  public void decreaseAngle(double degrees) {
    currentAngle -= degrees; // Subtract degrees from current position
    currentAngle = Math.max(0, Math.min(180, currentAngle)); // Clamp to valid range
    m_armServoUpper.setAngle(currentAngle); // Send new angle to servo
  }
}