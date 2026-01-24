// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Arm;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class Autonomous extends SequentialCommandGroup {
  /**
   * Creates a new Autonomous routine.
   *
   * @param drivetrain The drivetrain subsystem on which this command will run
   * @param arm The arm subsystem on which this command will run
   */
  public Autonomous(Drivetrain drivetrain, Arm arm) {
    // Test all autonomous commands
    addCommands(
        new ArmTurnDegrees(0.0, arm),
        new WaitDelay(1.0),
        new ArmTurnDegrees(90.0, arm),
        new WaitDelay(1.0),
        new ArmTurnDegrees(180.0, arm),
        new WaitDelay(3.0),
        new DriveDistance(1.0, 10.0, drivetrain),
        new WaitDelay(1.0),
        new TurnDegrees(1.0, 180.0, drivetrain),
        new WaitDelay(1.0),
        new DriveDistance(1.0, 10.0, drivetrain),
        new TurnDegrees(1.0, 180.0, drivetrain),
        new WaitDelay(1.0));
  }
}