package org.team1540.advantagekitdemo.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import org.team1540.advantagekitdemo.subsystems.elevator.Elevator;

public class ElevatorManualCommand extends Command {
    private final Elevator elevator;
    private final CommandXboxController controller;

    public ElevatorManualCommand(Elevator elevator, CommandXboxController controller) {
        this.elevator = elevator;
        this.controller = controller;
        addRequirements(elevator);
    }

    @Override
    public void execute() {
        elevator.setPercent(controller.getRightTriggerAxis() - controller.getLeftTriggerAxis());
    }

    @Override
    public void end(boolean interrupted) {
        elevator.stop();
    }
}
