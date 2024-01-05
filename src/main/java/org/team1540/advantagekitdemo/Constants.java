package org.team1540.advantagekitdemo;

import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.math.util.Units;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
    public static final SimulationMode simulationMode = SimulationMode.SIM;

    public enum SimulationMode {
        /**
         * Running a physics simulator.
         */
        SIM,

        /**
         * Replaying from a log file.
         */
        REPLAY
    }

    public static final double DEADZONE_RADIUS = 0.1;

    public static final class DrivetrainConstants {
        public static final int FL_ID = 1;
        public static final int BL_ID = 2;
        public static final int FR_ID = 3;
        public static final int BR_ID = 4;

        public static final double CURRENT_LIMIT = 40;

        public static final double GEAR_RATIO = 6.0;
        public static final double WHEEL_DIAMETER_METERS = Units.inchesToMeters(4.0);
        public static final double TRACK_WIDTH_METERS = 26.5;

        public static final double ROBOT_MASS_KG = Units.lbsToKilograms(118); // omg its tem 118 teh robnots
        public static final double ROBOT_MOI = 2.54; // omg its tem 254 teh chezy pofs

        public static final double VELOCITY_KP = 3.2925;
        public static final double VELOCITY_KI = 0;
        public static final double VELOCITY_KD = 0;

        public static final double KS = 0.650;
        public static final double KV = 2.81;
        public static final double KA = 0.224;

        public static final SimpleMotorFeedforward feedforward = new SimpleMotorFeedforward(KS, KV, KA);
        public static final DifferentialDriveKinematics driveKinematics =
                new DifferentialDriveKinematics(TRACK_WIDTH_METERS);
    }

    public static final class ElevatorConstants {
        public static final double GEAR_RATIO = 4.21;
        public static final double DRUM_RADIUS_METERS = Units.inchesToMeters(0.75);
        public static final double MAX_HEIGHT_METERS = Units.inchesToMeters(70);
        public static final double STAGE_1_HEIGHT_METERS = Units.inchesToMeters(38);
        public static final double CARRIAGE_MASS_KG = 20;

        public static final double KP = 1;
        public static final double KI = 0;
        public static final double KD = 0;
        public static final TrapezoidProfile.Constraints MOTION_CONSTRAINTS =
                new TrapezoidProfile.Constraints(2.5, 12.5);

        public static final double KS = 0;
        public static final double KG = 0.38;
        public static final double KV = 0.19;
    }

    public static final class IntakeConstants {
        public static final double WRIST_GEARING = 50;
        public static final double INTAKE_LENGTH_METERS = Units.inchesToMeters(17);
        public static final double INTAKE_MASS_KG = Units.lbsToKilograms(8);
        public static final double INTAKE_MOI = INTAKE_MASS_KG * INTAKE_LENGTH_METERS * INTAKE_LENGTH_METERS / 3;

        public static final double WRIST_KP = 1;
        public static final double WRIST_KI = 0;
        public static final double WRIST_KD = 0;
        public static final TrapezoidProfile.Constraints MOTION_CONSTRAINTS =
                new TrapezoidProfile.Constraints(1.8, 7.2);

        public static final double WRIST_KS = 0;
        public static final double WRIST_KG = 0.87;
        public static final double WRIST_KV = 5.64;
    }
}
