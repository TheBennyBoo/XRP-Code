// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.Arm;
import edu.wpi.first.wpilibj2.command.Command;

public class ArmTurnDegrees extends Command {
  private final Arm m_arm;
  private final double m_angleDeg;

  /**
   * Creates a new ArmTurnDegrees. This command will turn the arm to the desired angle (in degrees).
   *
   * @param angleDeg The angle the arm will turn to in degrees
   * @param arm The arm subsystem on which this command will run
   */
  public ArmTurnDegrees(double angleDeg, Arm arm) {
    m_angleDeg = angleDeg;
    m_arm = arm;
    addRequirements(arm);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_arm.setAngle(m_angleDeg);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
  }
}