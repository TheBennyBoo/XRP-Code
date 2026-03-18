package frc.robot.subsystems;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj.xrp.XRPMotor;
import frc.robot.Constants;

public class SwerveModule {
  private final MotorController motorA;
  private final MotorController motorB;

  public SwerveModule(int motorPortA, int motorPortB) {
    this.motorA = new XRPMotor(motorPortA);
    this.motorB = new XRPMotor(motorPortB);
  }

  public void setDesiredState(SwerveModuleState state) {
    double drive = state.speedMetersPerSecond / Constants.DriveConstants.MAX_SPEED_METERS_PER_SECOND;
    drive = MathUtil.clamp(drive, -1.0, 1.0);
    double steer = state.angle.getRadians();
    steer = MathUtil.inputModulus(steer, -Math.PI, Math.PI) / Math.PI;
    double left = MathUtil.clamp(drive + steer, -1.0, 1.0);
    double right = MathUtil.clamp(drive - steer, -1.0, 1.0);
    motorA.set(left);
    motorB.set(right);
  }

  public void stop() {
    motorA.stopMotor();
    motorB.stopMotor();
  }
}
