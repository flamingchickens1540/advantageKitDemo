package org.team1540.advantagekitdemo.subsystems.drivetrain;

import edu.wpi.first.math.geometry.Rotation2d;
import org.littletonrobotics.junction.AutoLog;

public interface DrivetrainIO {
  @AutoLog
  class DrivetrainIOInputs {
    public double leftPositionMeters = 0.0;
    public double leftVelocityMPS = 0.0;
    public double leftAppliedVolts = 0.0;
    public double[] leftCurrentAmps = new double[] {};

    public double rightPositionMeters = 0.0;
    public double rightVelocityMPS = 0.0;
    public double rightAppliedVolts = 0.0;
    public double[] rightCurrentAmps = new double[] {};

    public Rotation2d gyroYaw = new Rotation2d();
  }

  default void updateInputs(DrivetrainIOInputs inputs) {}

  default void setVoltage(double leftVolts, double rightVolts) {}
}
