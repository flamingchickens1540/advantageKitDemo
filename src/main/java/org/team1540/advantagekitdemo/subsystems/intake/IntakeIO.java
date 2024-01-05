package org.team1540.advantagekitdemo.subsystems.intake;

import edu.wpi.first.math.geometry.Rotation2d;
import org.littletonrobotics.junction.AutoLog;

public interface IntakeIO {
    @AutoLog
    class IntakeIOInputs {
        public Rotation2d wristPosition = new Rotation2d();
        public double wristAppliedVolts = 0;
        public double wristCurrentAmps = 0;

        public double intakeAppliedVolts = 0;
        public double intakeCurrentAmps = 0;
    }

    default void updateInputs(IntakeIOInputs inputs) {}

    default void setWristVoltage(double volts) {}

    default void setIntakeVoltage(double volts) {}
}
