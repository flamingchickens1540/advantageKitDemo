package org.team1540.advantagekitdemo.subsystems.elevator;

import com.ctre.phoenix6.BaseStatusSignal;
import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.ForwardLimitValue;
import com.ctre.phoenix6.signals.ReverseLimitValue;
import org.team1540.advantagekitdemo.util.Conversions;

import static org.team1540.advantagekitdemo.Constants.ElevatorConstants.*;

public class ElevatorIOReal implements ElevatorIO {
    private final TalonFX leader = new TalonFX(LEADER_ID);
    private final TalonFX follower = new TalonFX(FOLLOWER_ID);

    private final StatusSignal<Double> position = leader.getPosition();
    private final StatusSignal<Double> voltage = leader.getMotorVoltage();
    private final StatusSignal<Double> current = leader.getSupplyCurrent();
    private final StatusSignal<ReverseLimitValue> lowerLimit = leader.getReverseLimit();
    private final StatusSignal<ForwardLimitValue> upperLimit = leader.getForwardLimit();

    public ElevatorIOReal() {
        TalonFXConfiguration config = new TalonFXConfiguration();
        config.CurrentLimits.SupplyCurrentLimit = 40;
        config.CurrentLimits.SupplyCurrentThreshold = 60;
        config.CurrentLimits.SupplyTimeThreshold = 0.1;
        config.CurrentLimits.SupplyCurrentLimitEnable = true;

        config.HardwareLimitSwitch.ForwardLimitEnable = true;
        config.HardwareLimitSwitch.ForwardLimitAutosetPositionValue = 0;
        config.HardwareLimitSwitch.ForwardLimitAutosetPositionEnable = true;
        config.HardwareLimitSwitch.ReverseLimitEnable = true;

        follower.setControl(new Follower(LEADER_ID, true));
    }

    @Override
    public void updateInputs(ElevatorIOInputs inputs) {
        BaseStatusSignal.refreshAll(position, voltage, current);

        inputs.positionMeters = Conversions.motorRotsToMeters(position.getValue(), DRUM_RADIUS_METERS * 2, GEAR_RATIO);
        inputs.appliedVolts = voltage.getValue();
        inputs.currentAmps = voltage.getValue();
        inputs.lowerLimit = lowerLimit.getValue() == ReverseLimitValue.ClosedToGround;
        inputs.upperLimit = upperLimit.getValue() == ForwardLimitValue.ClosedToGround;
    }

    @Override
    public void setVoltage(double volts) {
        leader.setControl(new VoltageOut(volts));
    }
}
