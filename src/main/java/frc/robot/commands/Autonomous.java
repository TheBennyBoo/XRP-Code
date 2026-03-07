// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.Drivetrain;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class Autonomous extends SequentialCommandGroup {
  /**
   * Creates a new Autonomous routine.
   *
   * @param drivetrain The drivetrain subsystem on which this command will run
   * @param arm The arm subsystem on which this command will run
   * @param depthSensor The depth sensor subsystem on which this command will run
   */
  public Autonomous(Drivetrain drivetrain) {
    // Test all autonomous commands
    addCommands();
  }
}