package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Arm;

public class MoveArmUp extends Command{

    private final Arm m_armUpper;
    private static final double speedDegreesPerCycle = 1.0;

    public MoveArmUp (Arm arm) {
        m_armUpper = arm;
        addRequirements(arm);
    }

    @Override 
    public void initialize () {}

    @Override 
    public void execute () {
        m_armUpper.increaseAngle(speedDegreesPerCycle);
    }

    @Override 
    public void end (boolean interrupted) {}

    @Override 
    public boolean isFinished() {
        return false;
    }
}
