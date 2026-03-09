package frc.robot.subsystems;

import frc.robot.Constants;

import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.xrp.XRPMotor;
import edu.wpi.first.math.MathUtil;

public class DriveSubsystem extends SubsystemBase {
  // Motor controllers
  private final MotorController flMotorA;
  private final MotorController flMotorB;

  private final MotorController frMotorA;
  private final MotorController frMotorB;

  private final MotorController blMotorA;
  private final MotorController blMotorB;

  private final MotorController brMotorA;
  private final MotorController brMotorB;

  // Module geometry
  private static final double HALF_LENGTH = Constants.Drive.LENGTH / 2.0;
  private static final double HALF_WIDTH = Constants.Drive.WIDTH / 2.0;

  public DriveSubsystem() {
    // Initialize motors
    flMotorA = createMotor(Constants.Drive.FL_MOTOR_A);
    flMotorB = createMotor(Constants.Drive.FL_MOTOR_B);

    frMotorA = createMotor(Constants.Drive.FR_MOTOR_A);
    frMotorB = createMotor(Constants.Drive.FR_MOTOR_B);

    blMotorA = createMotor(Constants.Drive.BL_MOTOR_A);
    blMotorB = createMotor(Constants.Drive.BL_MOTOR_B);

    brMotorA = createMotor(Constants.Drive.BR_MOTOR_A);
    brMotorB = createMotor(Constants.Drive.BR_MOTOR_B);
  }

  @Override
  public void periodic() {

  }

  public void drive(double xSpeed, double ySpeed, double rotationSpeed) {
    // Apply deadband
    xSpeed = MathUtil.applyDeadband(xSpeed, Constants.Drive.DEADBAND);
    ySpeed = MathUtil.applyDeadband(ySpeed, Constants.Drive.DEADBAND);
    rotationSpeed = MathUtil.applyDeadband(rotationSpeed, Constants.Drive.DEADBAND);

    // Calculate module vectors
    ModuleState fl = calculateModule(xSpeed, ySpeed, rotationSpeed, -HALF_LENGTH, HALF_WIDTH);
    ModuleState fr = calculateModule(xSpeed, ySpeed, rotationSpeed, HALF_LENGTH, HALF_WIDTH);
    ModuleState bl = calculateModule(xSpeed, ySpeed, rotationSpeed, -HALF_LENGTH, -HALF_WIDTH);
    ModuleState br = calculateModule(xSpeed, ySpeed, rotationSpeed, HALF_LENGTH, -HALF_WIDTH);

    // Normalize speeds
    double maxSpeed = Math.max(
      Math.max(fl.speed, fr.speed),
      Math.max(bl.speed, br.speed)
    );

    if (maxSpeed > 1.0) {
      fl.speed /= maxSpeed;
      fr.speed /= maxSpeed;
      bl.speed /= maxSpeed;
      br.speed /= maxSpeed;
    }

    // Apply to motors
    setModule(flMotorA, flMotorB, fl);
    setModule(frMotorA, frMotorB, fr);
    setModule(blMotorA, blMotorB, bl);
    setModule(brMotorA, brMotorB, br);
  }

  private ModuleState calculateModule(double xSpeed, double ySpeed, double rotationSpeed, double rotationX, double rotationY) {
    double wheelXSpeed = xSpeed - rotationSpeed * rotationY;
    double wheelYSpeed = ySpeed + rotationSpeed * rotationX;

    double speed = Math.hypot(wheelXSpeed, wheelYSpeed);
    
    double angle = Math.atan2(wheelYSpeed, wheelXSpeed);

    return new ModuleState(speed, angle);

  }

  private MotorController createMotor(int port) {
    return new XRPMotor(port);
  }

  // Apply differential swerve motor math
  private void setModule(MotorController motorA, MotorController motorB, ModuleState state) {
    double drive = state.speed;

    // Convert steering angle into steering velocity
    double steer = state.angle / Math.PI;

    double motorASpeed = drive + steer;
    double motorBSpeed = drive - steer;

    motorA.set(MathUtil.clamp(motorASpeed, -1.0, 1.0));
    motorB.set(MathUtil.clamp(motorBSpeed, -1.0, 1.0));
  }

  public void stop() {
    flMotorA.stopMotor();
    flMotorB.stopMotor();

    frMotorA.stopMotor();
    frMotorB.stopMotor();

    blMotorA.stopMotor();
    blMotorB.stopMotor();

    brMotorA.stopMotor();
    brMotorB.stopMotor();
  }

  // Helper class
  private static class ModuleState {
    double speed;
    double angle;

    ModuleState(double speed, double angle) {
      this.speed = speed;
      this.angle = angle;
    }
  }
}