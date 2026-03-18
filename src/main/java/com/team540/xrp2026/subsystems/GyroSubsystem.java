package com.team540.xrp2026.subsystems;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.xrp.XRPGyro;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class GyroSubsystem extends SubsystemBase {
  private final XRPGyro gyro = new XRPGyro();

  public GyroSubsystem() {}

  public void zeroYaw() {
    gyro.reset();
  }

  public Rotation2d getRotation2d() {
    return Rotation2d.fromDegrees(-gyro.getAngle());
  }
}
