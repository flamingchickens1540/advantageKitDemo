package org.team1540.advantagekitdemo.commands;

import edu.wpi.first.math.controller.ArmFeedforward;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.Command;
import org.team1540.advantagekitdemo.subsystems.intake.Intake;

import static org.team1540.advantagekitdemo.Constants.IntakeConstants.*;

public class IntakeCommand extends Command {
    private final Intake intake;
    private final Rotation2d wristSetpoint;
    private final double intakePercent;

    private final PIDController pid = new PIDController(WRIST_KP, WRIST_KI, WRIST_KD);
    private final ArmFeedforward ff = new ArmFeedforward(WRIST_KS, WRIST_KG, WRIST_KV);

    public IntakeCommand(Intake intake, Rotation2d wristSetpoint, double intakePercent) {
        this.intake = intake;
        this.wristSetpoint = wristSetpoint;
        this.intakePercent = intakePercent;
        addRequirements(intake);
    }

    @Override
    public void initialize() {
        intake.setWristPosition(wristSetpoint);
        intake.setIntakePercent(intakePercent);
    }

    @Override
    public boolean isFinished() {
        return intake.getProfilePosition() == wristSetpoint.getRotations();
    }
}
