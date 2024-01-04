package org.team1540.advantagekitdemo.util;

import edu.wpi.first.math.geometry.Rotation2d;

public class Conversions {
    /**
     * Converts a motor rotation count into mechanism rotations based on a gear ratio
     * @param rotationCounts motor rotation counts
     * @param gearRatio the gear ratio between motor and the mechanism
     * @return mechanism rotation in {@link Rotation2d}
     */
    public static Rotation2d motorRotsToRotation2d(double rotationCounts, double gearRatio) {
        return Rotation2d.fromRotations(rotationCounts / gearRatio);
    }

    /**
     * Converts a mechanism rotation (in {@link Rotation2d}) to motor rotation counts
     * @param rotations mechanism rotation
     * @param gearRatio gear ratio between motor and mechanism
     * @return motor rotation counts
     */
    public static double Rotation2dToMotorRots(Rotation2d rotations, double gearRatio) {
        return rotations.getRotations() * gearRatio;
    }

    /**
     * Converts a motor rotation count into meters
     * @param rotationCounts motor rotation counts
     * @param wheelDiameter wheel diameter in meters
     * @param gearRatio gear ratio between motor and wheel
     * @return meters
     */
    public static double motorRotsToMeters(double rotationCounts, double wheelDiameter, double gearRatio) {
        double wheelCircumference = Math.PI * wheelDiameter;
        return (rotationCounts / gearRatio) * wheelCircumference;
    }

    /**
     * Converts meters to motor rotation counts
     * @param meters meters
     * @param wheelDiameter wheel diameter in meters
     * @param gearRatio gear ratio between motor and wheel
     * @return motor rotation counts
     */
    public static double metersToMotorRots(double meters, double wheelDiameter, double gearRatio) {
        double wheelCircumference = Math.PI * wheelDiameter;
        return (meters / wheelCircumference) * gearRatio;
    }

    /**
     * Converts rotations per minute to meters per second
     * @param rpm rotations per minute
     * @param wheelDiameter wheel diameter in meters
     * @param gearRatio gear ratio between motor and wheel
     * @return meters per second
     */
    public static double RPMtoMPS(double rpm, double wheelDiameter, double gearRatio) {
        return motorRotsToMeters(rpm/60, wheelDiameter, gearRatio);
    }

    /**
     * Converts meters per second to rotations per minute
     * @param mps meters per second
     * @param wheelDiameter wheel diameter in meters
     * @param gearRatio gear ratio between motor and wheel
     * @return rotations per minute
     */
    public static double MPStoRPM(double mps, double wheelDiameter, double gearRatio) {
        return metersToMotorRots(mps, wheelDiameter, gearRatio) * 60;
    }
}
