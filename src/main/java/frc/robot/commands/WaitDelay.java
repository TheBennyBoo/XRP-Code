// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;

/*
 * Creates a new WaitDelay command. This command will pause command execution for a desired amount
 * of time (in seconds).
 */
public class WaitDelay extends Command {
  private final double m_duration;
  private long m_startTime;

  /**
   * Creates a new WaitDelay.
   *
   * @param time How much time to wait in seconds
   */
  public WaitDelay(double time) {
    m_duration = time * 1000;
    addRequirements();
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_startTime = System.currentTimeMillis();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return (System.currentTimeMillis() - m_startTime) >= m_duration;
  }
}
