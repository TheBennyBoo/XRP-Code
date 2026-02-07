// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.xrp.XRPRangefinder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DepthSensor extends SubsystemBase {
  private final XRPRangefinder m_depthSensor;

  /** Creates a new DepthSensor. */
  public DepthSensor() {
    m_depthSensor = new XRPRangefinder();
  }

  @Override
  public void periodic() {
    // Display the current distance on SmartDashboard
    SmartDashboard.putNumber("Distance (in)", getDistanceInches());
  }

  /**
   * Get the current distance in inches.
   *
   * @return distance in inches
   */
  public double getDistanceInches() {
    return m_depthSensor.getDistanceInches();
  }
}
