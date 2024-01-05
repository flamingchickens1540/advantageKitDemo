package org.team1540.advantagekitdemo.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import org.team1540.advantagekitdemo.subsystems.intake.Intake;

public class WristManualCommand extends Command {
    private final Intake intake;
    private final CommandXboxController controller;

    public WristManualCommand(Intake intake, CommandXboxController controller) {
        this.intake = intake;
        this.controller = controller;
        addRequirements(intake);
    }

    @Override
    public void execute() {
        intake.setWristPercent(controller.getRightX());
    }

    @Override
    public void end(boolean interrupted) {
        intake.stopWrist();
    }
}
