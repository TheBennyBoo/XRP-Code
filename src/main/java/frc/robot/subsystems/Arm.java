// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.xrp.XRPServo;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Arm extends SubsystemBase {
  private XRPServo m_armLowerServo;
  private XRPServo m_armUpperServo;

  private double m_upperTarget = 0;
  private double m_lowerTarget = 0;

  private double m_upperSpeed = 10.0;
  private double m_lowerSpeed = 4.0;

    /** Creates a new Arm. */
    public Arm() {
      // Device number 4 maps to the physical Servo 1 port on the XRP
      m_armLowerServo = new XRPServo(5);
      m_armUpperServo = new XRPServo(4);
    }
  
    @Override
    public void periodic() {
      // This method will be called once per scheduler run
      double currentUpper = m_armUpperServo.getAngle();
      double currentLower = m_armLowerServo.getAngle();

      if (Math.abs(currentUpper - m_upperTarget) > m_upperSpeed){
        currentUpper += Math.copySign(m_upperSpeed, m_upperTarget - currentUpper);
      }
      else {
        currentUpper = m_upperTarget;
      }
      if (Math.abs(currentLower - m_lowerTarget) > m_lowerSpeed){
        currentLower += Math.copySign(m_lowerSpeed, m_lowerTarget - currentLower);
      } else {
        currentLower = m_lowerTarget;
      }
      m_armUpperServo.setAngle(currentUpper);
      m_armLowerServo.setAngle(currentLower);
    }
  
    /**
     * Set the current angle of the arm (0 - 180 degrees).
     *
     * @param angleDeg Desired arm angle in degrees
     */
    public void setUpperAngle(double angleDeg) {
      m_upperTarget = angleDeg;
    }
    public void setLowerAngle(double angleDeg) {
      m_lowerTarget = angleDeg;
    }
}
