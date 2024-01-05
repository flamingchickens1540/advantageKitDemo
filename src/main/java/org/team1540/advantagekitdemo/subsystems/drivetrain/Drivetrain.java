package org.team1540.advantagekitdemo.subsystems.drivetrain;

import com.pathplanner.lib.commands.FollowPathRamsete;
import com.pathplanner.lib.path.PathPlannerPath;
import com.pathplanner.lib.util.PathPlannerLogging;
import com.pathplanner.lib.util.ReplanningConfig;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.estimator.DifferentialDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelPositions;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.littletonrobotics.junction.AutoLogOutput;
import org.littletonrobotics.junction.Logger;

import static org.team1540.advantagekitdemo.Constants.DrivetrainConstants.*;

public class Drivetrain extends SubsystemBase {
    private final DrivetrainIO io;
    private final DrivetrainIOInputsAutoLogged inputs = new DrivetrainIOInputsAutoLogged();
    private final DifferentialDrivePoseEstimator poseEstimator =
            new DifferentialDrivePoseEstimator(
                    driveKinematics,
                    getYaw(),
                    getWheelPositions().leftMeters,
                    getWheelPositions().rightMeters,
                    new Pose2d()
            );
    private final PIDController velocityPID = new PIDController(VELOCITY_KP, VELOCITY_KI, VELOCITY_KD);

    private boolean isClosedLoop = false;
    private DifferentialDriveWheelSpeeds closedLoopSetpoint;

    public Drivetrain(DrivetrainIO io) {
        this.io = io;
        PathPlannerLogging.setLogActivePathCallback(
                activePath -> {
                    Logger.recordOutput("odometry/activePath", activePath.toArray(new Pose2d[activePath.size()]));
                }
        );
    }

    @Override
    public void periodic() {
        poseEstimator.update(getYaw(), getWheelPositions());
        if (isClosedLoop && closedLoopSetpoint != null) {
            setVoltage(
                    velocityPID.calculate(getWheelSpeeds().leftMetersPerSecond, closedLoopSetpoint.leftMetersPerSecond)
                    + feedforward.calculate(closedLoopSetpoint.leftMetersPerSecond),
                    velocityPID.calculate(getWheelSpeeds().rightMetersPerSecond, closedLoopSetpoint.rightMetersPerSecond)
                    + feedforward.calculate(closedLoopSetpoint.rightMetersPerSecond)
            );
        }

        io.updateInputs(inputs);
        Logger.processInputs("Drivetrain", inputs);
    }

    public void drivePercent(double leftPercent, double rightPercent) {
        isClosedLoop = false;
        setVoltage(12 * leftPercent, 12 * rightPercent);
    }

    private void setVoltage(double leftVoltage, double rightVoltage) {
        io.setVoltage(leftVoltage, rightVoltage);
    }

    public void setChassisSpeeds(ChassisSpeeds chassisSpeeds) {
        setWheelSpeeds(driveKinematics.toWheelSpeeds(chassisSpeeds));
    }

    public void setWheelSpeeds(DifferentialDriveWheelSpeeds wheelSpeeds) {
        isClosedLoop = true;
        velocityPID.reset();
        closedLoopSetpoint = wheelSpeeds;
    }

    public void stop() {
        isClosedLoop = false;
        io.setVoltage(0.0, 0.0);
    }

    public Command getPathCommand(PathPlannerPath path, boolean resetToPath) {
        return Commands.sequence(
                new InstantCommand(() -> {
                    if (resetToPath) resetOdometry(path.getStartingDifferentialPose());
                }),
                new FollowPathRamsete(
                        path,
                        this::getPose,
                        this::getChassisSpeeds,
                        this::setChassisSpeeds,
                        new ReplanningConfig(),
                        this
                )
        );
    }

    public Rotation2d getYaw() {
        return inputs.gyroYaw;
    }

    @AutoLogOutput
    public Pose2d getPose() {
        return poseEstimator.getEstimatedPosition();
    }

    public void resetOdometry(Pose2d pose) {
        poseEstimator.resetPosition(inputs.gyroYaw, getWheelPositions(), pose);
    }

    public ChassisSpeeds getChassisSpeeds() {
        return driveKinematics.toChassisSpeeds(getWheelSpeeds());
    }

    public DifferentialDriveWheelPositions getWheelPositions() {
        return new DifferentialDriveWheelPositions(inputs.leftPositionMeters, inputs.rightPositionMeters);
    }

    public DifferentialDriveWheelSpeeds getWheelSpeeds() {
        return new DifferentialDriveWheelSpeeds(inputs.leftVelocityMPS, inputs.rightVelocityMPS);
    }
}
