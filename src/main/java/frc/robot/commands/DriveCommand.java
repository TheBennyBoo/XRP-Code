package frc.robot.commands;

import frc.robot.subsystems.DriveSubsystem;

import edu.wpi.first.wpilibj2.command.Command;
import java.util.function.DoubleSupplier;

public class DriveCommand extends Command {
  private final DriveSubsystem drive;
  private final DoubleSupplier xSupplier;
  private final DoubleSupplier ySupplier;
  private final DoubleSupplier rotationSupplier;

  public DriveCommand(DriveSubsystem drive, DoubleSupplier x, DoubleSupplier y, DoubleSupplier rotation) {
    this.drive = drive;
    this.xSupplier = x;
    this.ySupplier = y;
    this.rotationSupplier = rotation;

    addRequirements(drive);
  }

  @Override
  public void execute() {
    drive.drive(xSupplier.getAsDouble(), ySupplier.getAsDouble(), rotationSupplier.getAsDouble());
  }
}