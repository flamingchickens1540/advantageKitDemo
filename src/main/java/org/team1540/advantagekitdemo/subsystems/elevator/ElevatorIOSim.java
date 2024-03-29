package org.team1540.advantagekitdemo.subsystems.elevator;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj.simulation.ElevatorSim;

import static org.team1540.advantagekitdemo.Constants.ElevatorConstants.*;

public class ElevatorIOSim implements ElevatorIO {
    private final ElevatorSim sim =
            new ElevatorSim(
                    DCMotor.getFalcon500(2),
                    GEAR_RATIO,
                    CARRIAGE_MASS_KG,
                    DRUM_RADIUS_METERS,
                    0,
                    MAX_HEIGHT_METERS,
                    true,
                    0,
                    null
            );

    private double appliedVolts = 0;

    @Override
    public void updateInputs(ElevatorIOInputs inputs) {
        sim.update(0.02);
        inputs.positionMeters = sim.getPositionMeters();
        inputs.appliedVolts = appliedVolts;
        inputs.currentAmps = sim.getCurrentDrawAmps();
        inputs.upperLimit = sim.hasHitUpperLimit();
        inputs.lowerLimit = sim.hasHitLowerLimit();
    }

    @Override
    public void setVoltage(double volts) {
        appliedVolts = MathUtil.clamp(volts, -12, 12);
        sim.setInputVoltage(volts);
    }
}
