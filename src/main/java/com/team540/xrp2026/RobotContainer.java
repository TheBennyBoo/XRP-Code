package com.team540.xrp2026;

import com.team540.xrp2026.commands.DriveCommand;
import com.team540.xrp2026.subsystems.DriveSubsystem;
import com.team540.xrp2026.subsystems.GyroSubsystem;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.Command;

public class RobotContainer {
  private final GyroSubsystem gyro = new GyroSubsystem();
  private final DriveSubsystem drive = new DriveSubsystem(gyro);
  private final CommandXboxController driverController = new CommandXboxController(0);

  public RobotContainer() {
    drive.setDefaultCommand(
      new DriveCommand(
        drive,
        () -> -driverController.getLeftY(),
        () -> -driverController.getLeftX(),
        () -> driverController.getRightX()
      )
    );

    configureButtonBindings();
  }

  private void configureButtonBindings() {}

  public Command getAutonomousCommand() {
    return null;
  }
}