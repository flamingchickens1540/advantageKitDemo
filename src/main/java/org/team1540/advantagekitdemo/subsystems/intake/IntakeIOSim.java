package org.team1540.advantagekitdemo.subsystems.intake;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj.simulation.SingleJointedArmSim;

import static org.team1540.advantagekitdemo.Constants.IntakeConstants.*;

public class IntakeIOSim implements IntakeIO {
    private final SingleJointedArmSim wristSim =
            new SingleJointedArmSim(
                    DCMotor.getFalcon500(1),
                    WRIST_GEARING,
                    INTAKE_MOI,
                    INTAKE_LENGTH_METERS,
                    Math.toRadians(0),
                    Math.toRadians(180),
                    true,
                    Math.toRadians(0)
            );

    private double intakeVoltage;
    private double wristVoltage;

    @Override
    public void updateInputs(IntakeIOInputs inputs) {
        wristSim.update(0.02);
        inputs.wristPosition = Rotation2d.fromRadians(wristSim.getAngleRads());
        inputs.wristAppliedVolts = wristVoltage;
        inputs.wristCurrentAmps = wristSim.getCurrentDrawAmps();
        inputs.intakeAppliedVolts = intakeVoltage;
    }

    @Override
    public void setWristVoltage(double volts) {
        this.wristVoltage = volts;
        wristSim.setInputVoltage(volts);
    }

    @Override
    public void setIntakeVoltage(double volts) {
        intakeVoltage = volts;
    }
}
