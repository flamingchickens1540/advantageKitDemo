package org.team1540.advantagekitdemo.subsystems.intake;

import edu.wpi.first.math.controller.ArmFeedforward;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj2.command.ProfiledPIDSubsystem;
import org.littletonrobotics.junction.Logger;
import org.team1540.advantagekitdemo.util.SuperstructureVisualizer;

import static org.team1540.advantagekitdemo.Constants.IntakeConstants.*;

public class Intake extends ProfiledPIDSubsystem {
    private final IntakeIO io;
    private final IntakeIOInputsAutoLogged inputs = new IntakeIOInputsAutoLogged();

    private final ArmFeedforward wristFeedforward = new ArmFeedforward(WRIST_KS, WRIST_KG, WRIST_KV);

    public Intake(IntakeIO io) {
        super(new ProfiledPIDController(WRIST_KP, WRIST_KI, WRIST_KD, MOTION_CONSTRAINTS));
        this.io = io;
    }

    @Override
    public void periodic() {
        super.periodic();
        io.updateInputs(inputs);
        Logger.processInputs("Intake", inputs);

        SuperstructureVisualizer.setWristPosition(getWristPosition());
    }

    public void setIntakePercent(double percentOutput) {
        io.setIntakeVoltage(12 * percentOutput);
    }

    public void setWristPercent(double percentOutput) {
        disable();
        setWristVoltage(12 * percentOutput);
    }

    public void setWristPosition(Rotation2d position) {
        enable();
        setGoal(position.getRotations());
    }

    private void setWristVoltage(double volts) {
        io.setWristVoltage(volts);
    }

    public void stopIntake() {
        setIntakePercent(0);
    }

    public void stopWrist() {
        setWristPercent(0);
    }

    public Rotation2d getWristPosition() {
        return inputs.wristPosition;
    }

    @Override
    protected void useOutput(double output, TrapezoidProfile.State setpoint) {
        setWristVoltage(output + wristFeedforward.calculate(getWristPosition().getRadians(), setpoint.velocity));
    }

    @Override
    protected double getMeasurement() {
        return inputs.wristPosition.getRotations();
    }
}
