package org.team1540.advantagekitdemo.subsystems.drivetrain;

import com.ctre.phoenix6.BaseStatusSignal;
import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.Pigeon2;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.geometry.Rotation2d;
import org.team1540.advantagekitdemo.util.Conversions;

import static org.team1540.advantagekitdemo.Constants.DrivetrainConstants.*;

public class DrivetrainIOReal implements DrivetrainIO {

    private final TalonFX leftLeader = new TalonFX(FL_ID);
    private final TalonFX leftFollower = new TalonFX(BL_ID);
    private final TalonFX rightLeader = new TalonFX(FR_ID);
    private final TalonFX rightFollower = new TalonFX(BR_ID);

    private final StatusSignal<Double> leftPosition = leftLeader.getPosition();
    private final StatusSignal<Double> leftVelocity = leftLeader.getVelocity();
    private final StatusSignal<Double> leftAppliedVolts = leftLeader.getMotorVoltage();
    private final StatusSignal<Double> leftLeaderCurrent = leftLeader.getStatorCurrent();
    private final StatusSignal<Double> leftFollowerCurrent = leftFollower.getStatorCurrent();

    private final StatusSignal<Double> rightPosition = rightLeader.getPosition();
    private final StatusSignal<Double> rightVelocity = rightLeader.getVelocity();
    private final StatusSignal<Double> rightAppliedVolts = rightLeader.getMotorVoltage();
    private final StatusSignal<Double> rightLeaderCurrent = rightLeader.getStatorCurrent();
    private final StatusSignal<Double> rightFollowerCurrent = rightFollower.getStatorCurrent();

    private final Pigeon2 pigeon = new Pigeon2(20);
    private final StatusSignal<Double> yaw = pigeon.getYaw();

    public DrivetrainIOReal() {
        var config = new TalonFXConfiguration();
        config.CurrentLimits.StatorCurrentLimit = 30.0;
        config.CurrentLimits.StatorCurrentLimitEnable = true;
        config.MotorOutput.NeutralMode = NeutralModeValue.Brake;
        leftLeader.getConfigurator().apply(config);
        leftFollower.getConfigurator().apply(config);
        rightLeader.getConfigurator().apply(config);
        rightFollower.getConfigurator().apply(config);
        leftFollower.setControl(new Follower(leftLeader.getDeviceID(), false));
        rightFollower.setControl(new Follower(rightLeader.getDeviceID(), false));

        BaseStatusSignal.setUpdateFrequencyForAll(
                100.0, leftPosition, rightPosition, yaw); // Required for odometry, use faster rate
        BaseStatusSignal.setUpdateFrequencyForAll(
                50.0,
                leftVelocity,
                leftAppliedVolts,
                leftLeaderCurrent,
                leftFollowerCurrent,
                rightVelocity,
                rightAppliedVolts,
                rightLeaderCurrent,
                rightFollowerCurrent);
        leftLeader.optimizeBusUtilization();
        leftFollower.optimizeBusUtilization();
        rightLeader.optimizeBusUtilization();
        rightFollower.optimizeBusUtilization();
        pigeon.optimizeBusUtilization();
    }

    @Override
    public void updateInputs(DrivetrainIOInputs inputs) {
        BaseStatusSignal.refreshAll(
                leftPosition,
                leftVelocity,
                leftAppliedVolts,
                leftLeaderCurrent,
                leftFollowerCurrent,
                rightPosition,
                rightVelocity,
                rightAppliedVolts,
                rightLeaderCurrent,
                rightFollowerCurrent,
                yaw);

        inputs.leftPositionMeters = Conversions.motorRotsToMeters(
                leftPosition.getValue(), WHEEL_DIAMETER_METERS, GEAR_RATIO);
        inputs.leftVelocityMPS = Conversions.motorRotsToMeters(
                leftVelocity.getValue(), WHEEL_DIAMETER_METERS, GEAR_RATIO
        );
        inputs.leftAppliedVolts = leftAppliedVolts.getValueAsDouble();
        inputs.leftCurrentAmps =
                new double[]{leftLeaderCurrent.getValueAsDouble(), leftFollowerCurrent.getValueAsDouble()};

        inputs.rightPositionMeters = Conversions.motorRotsToMeters(
                rightPosition.getValue(), WHEEL_DIAMETER_METERS, GEAR_RATIO);
        inputs.rightVelocityMPS = Conversions.motorRotsToMeters(
                rightVelocity.getValue(), WHEEL_DIAMETER_METERS, GEAR_RATIO
        );
        inputs.rightAppliedVolts = rightAppliedVolts.getValueAsDouble();
        inputs.rightCurrentAmps =
                new double[]{rightLeaderCurrent.getValueAsDouble(), rightFollowerCurrent.getValueAsDouble()};

        inputs.gyroYaw = Rotation2d.fromDegrees(yaw.getValueAsDouble());
    }

    @Override
    public void setVoltage(double leftVolts, double rightVolts) {
        leftLeader.setControl(new VoltageOut(MathUtil.clamp(leftVolts, -12, 12)));
        rightLeader.setControl(new VoltageOut(MathUtil.clamp(rightVolts, -12, 12)));
    }
}
