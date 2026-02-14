package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Arm;

public class MoveArmDown extends Command {

    // The arm subsystem this command will control
    private final Arm m_armUpper;

    // How many degrees the arm moves per scheduler cycle (every 20ms)
    // At 1.0 degree per cycle, the arm moves ~50 degrees per second
    // Increase this number to make the arm move faster
    private static final double speedDegreesPerCycle = 1.0;

    // Constructor - takes in the arm subsystem and registers it as a requirement
    // addRequirements ensures no other command can use the arm at the same time
    public MoveArmDown (Arm arm) {
        m_armUpper = arm;
        addRequirements(arm);
    }
    

    // Nothing needs to happen when the command starts
    @Override
    public void initialize () {}

    // Called every 20ms while the button is held - nudges the arm down 1 degree each tick
    @Override
    public void execute () {
        m_armUpper.decreaseAngle(speedDegreesPerCycle);
    }

    // Nothing needs to happen when the command ends or is interrupted
    // The arm will simply stop moving when the button is released
    @Override
    public void end (boolean interrupted) {}

    // Always returns false because this command should run until interrupted
    // It gets interrupted naturally when the button is released (whileTrue binding)
    @Override
    public boolean isFinished() {
        return false;
    }
}