package org.team1540.advantagekitdemo.util;

import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import org.littletonrobotics.junction.Logger;
import org.team1540.advantagekitdemo.Constants.*;

public class SuperstructureVisualizer {
    private static Pose3d elevatorStage1 = new Pose3d();
    private static Pose3d elevatorCarriage = new Pose3d();
    private static Pose3d wrist = new Pose3d();

    private static double elevatorPosition = 0;

    public static void periodic() {
        Logger.recordOutput("Mechanism3d", elevatorStage1, elevatorCarriage, wrist);
    }

    public static void setElevatorPosition(double positionMeters) {
        if (positionMeters <= ElevatorConstants.STAGE_1_HEIGHT_METERS) {
            elevatorStage1 = new Pose3d();
            elevatorCarriage = new Pose3d(0.0, positionMeters, 0.0, new Rotation3d());
        } else {
            elevatorCarriage = new Pose3d(0.0, ElevatorConstants.STAGE_1_HEIGHT_METERS, 0.0, new Rotation3d());
            elevatorStage1 =
                    new Pose3d(
                            0.0,
                            positionMeters - ElevatorConstants.STAGE_1_HEIGHT_METERS,
                            0.0,
                            new Rotation3d()
                    );
        }
        elevatorPosition = positionMeters;
    }

    public static void setWristPosition(Rotation2d wristPosition) {
        wrist = new Pose3d(
                0.0,
                elevatorPosition,
                0.0,
                new Rotation3d(0, wristPosition.getDegrees(), 0)
        );
    }
}
