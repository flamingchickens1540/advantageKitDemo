package org.team1540.advantagekitdemo.subsystems.drivetrain;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj.simulation.DifferentialDrivetrainSim;

import static org.team1540.advantagekitdemo.Constants.DrivetrainConstants.*;

public class DrivetrainIOSim implements DrivetrainIO {
    private final DifferentialDrivetrainSim sim =
            new DifferentialDrivetrainSim(
                    DCMotor.getFalcon500(2),
                    GEAR_RATIO,
                    ROBOT_MOI,
                    ROBOT_WEIGHT_KG,
                    WHEEL_DIAMETER_METERS / 2,
                    TRACK_WIDTH_METERS,
                    null
            );

    private double leftAppliedVolts = 0.0;
    private double rightAppliedVolts = 0.0;

    @Override
    public void updateInputs(DrivetrainIOInputs inputs) {
        sim.update(0.02);
        inputs.leftPositionMeters = sim.getLeftPositionMeters();
        inputs.leftVelocityMPS = sim.getLeftVelocityMetersPerSecond();
        inputs.leftAppliedVolts = leftAppliedVolts;
        inputs.leftCurrentAmps = new double[]{sim.getLeftCurrentDrawAmps()};

        inputs.rightPositionMeters = sim.getRightPositionMeters();
        inputs.rightVelocityMPS = sim.getRightVelocityMetersPerSecond();
        inputs.rightAppliedVolts = rightAppliedVolts;
        inputs.rightCurrentAmps = new double[]{sim.getRightCurrentDrawAmps()};

        inputs.gyroYaw = sim.getHeading();
    }

    @Override
    public void setVoltage(double leftVolts, double rightVolts) {
        leftAppliedVolts = MathUtil.clamp(leftVolts, -12.0, 12.0);
        rightAppliedVolts = MathUtil.clamp(rightVolts, -12.0, 12.0);
        sim.setInputs(leftAppliedVolts, rightAppliedVolts);
    }
}
