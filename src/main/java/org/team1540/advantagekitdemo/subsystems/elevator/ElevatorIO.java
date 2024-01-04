package org.team1540.advantagekitdemo.subsystems.elevator;

import org.littletonrobotics.junction.AutoLog;

public interface ElevatorIO {


    @AutoLog
    class ElevatorIOInputs {
        public double positionMeters = 0;
        public double appliedVolts = 0;
        public double currentAmps = 0;
    }

    default void updateInputs(ElevatorIOInputs inputs) {}

    default void setVoltage(double volts) {}
}
