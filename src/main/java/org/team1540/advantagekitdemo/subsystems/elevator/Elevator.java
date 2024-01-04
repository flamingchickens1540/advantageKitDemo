package org.team1540.advantagekitdemo.subsystems.elevator;

import edu.wpi.first.math.controller.ElevatorFeedforward;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj2.command.ProfiledPIDSubsystem;
import org.littletonrobotics.junction.Logger;

import static org.team1540.advantagekitdemo.Constants.ElevatorConstants.*;

public class Elevator extends ProfiledPIDSubsystem {
    private final ElevatorIO io;
    private final ElevatorIOInputsAutoLogged inputs = new ElevatorIOInputsAutoLogged();

    private final ElevatorFeedforward feedforward = new ElevatorFeedforward(KS, KG, KV);

    public Elevator(ElevatorIO io) {
        super(new ProfiledPIDController(KP, KI, KD, MOTION_CONSTRAINTS));
        this.io = io;
    }

    @Override
    public void periodic() {
        super.periodic();
        io.updateInputs(inputs);
        Logger.processInputs("Elevator", inputs);
    }

    public void setPercent(double percentOutput) {
        disable();
        setVoltage(12 * percentOutput);
    }

    private void setVoltage(double volts) {
        io.setVoltage(volts);
    }

    public void setPosition(double positionMeters) {
        enable();
        setGoal(positionMeters);
    }

    @Override
    protected void useOutput(double output, TrapezoidProfile.State setpoint) {
        setVoltage(output + feedforward.calculate(setpoint.velocity));
    }

    @Override
    protected double getMeasurement() {
        return inputs.positionMeters;
    }
}
