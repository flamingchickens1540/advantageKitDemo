package org.team1540.advantagekitdemo.commands;

import edu.wpi.first.wpilibj2.command.Command;
import org.team1540.advantagekitdemo.subsystems.elevator.Elevator;

public class ElevatorSetpointCommand extends Command {
    private final Elevator elevator;
    private final double setpoint;

    public ElevatorSetpointCommand(Elevator elevator, double setpointMeters) {
        this.elevator = elevator;
        this.setpoint = setpointMeters;
        addRequirements(elevator);
    }

    @Override
    public void initialize() {
        elevator.setPosition(setpoint);
    }

    @Override
    public boolean isFinished() {
        return elevator.getProfilePosition() == setpoint;
    }
}
