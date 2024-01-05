package org.team1540.advantagekitdemo.util;

import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Translation3d;
import org.littletonrobotics.junction.Logger;
import org.team1540.advantagekitdemo.Constants.*;

public class SuperstructureVisualizer {
    private static Pose3d elevatorStage1 = new Pose3d();
    private static Pose3d elevatorCarriage = new Pose3d();
    private static Pose3d wrist = new Pose3d();
    private static Pose3d hanger = new Pose3d();
    private static Pose3d forks = new Pose3d();

    private static double elevatorPosition = 0;

    public static void periodic() {
        Logger.recordOutput("Mechanism3d", elevatorStage1, elevatorCarriage, wrist, hanger, forks);
    }

    public static void setElevatorPosition(double positionMeters) {
        if (positionMeters <= ElevatorConstants.STAGE_1_HEIGHT_METERS) {
            elevatorStage1 = new Pose3d();
        } else {
            elevatorStage1 =
                    new Pose3d(
                            0.0,
                            0.0,
                            positionMeters - ElevatorConstants.STAGE_1_HEIGHT_METERS,
                            new Rotation3d()
                    );
        }
        elevatorCarriage = new Pose3d(0.0, 0.0, positionMeters, new Rotation3d());
        elevatorPosition = positionMeters;
    }

    public static void setWristPosition(Rotation2d wristPosition) {
        wrist = new Pose3d(
                0.0 + 0.228600,
                0.0,
                elevatorPosition + 0.193675,
                new Rotation3d(0, wristPosition.getRadians(), 0)
        );
    }

    public static void setClimberPosition(boolean hangerStowed, boolean forksStowed){
        forks = new Pose3d(
                0 - 0.215900,
                0,
                0 + 0.155575,
                new Rotation3d(0, Rotation2d.fromDegrees(forksStowed?0:-90).getRadians(), 0));
        hanger = new Pose3d(
                0.0 - 0.292100,
                0.0,
                (elevatorPosition <= ElevatorConstants.STAGE_1_HEIGHT_METERS ? 0 : (elevatorPosition - ElevatorConstants.STAGE_1_HEIGHT_METERS)) + 1.374775,
                new Rotation3d(0, Rotation2d.fromDegrees(hangerStowed?0:90).getRadians(), 0));

    }
}
