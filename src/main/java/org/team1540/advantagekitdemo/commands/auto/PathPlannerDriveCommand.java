package org.team1540.advantagekitdemo.commands.auto;

import com.pathplanner.lib.path.PathPlannerPath;
import com.pathplanner.lib.util.PathPlannerLogging;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import org.team1540.advantagekitdemo.subsystems.drivetrain.Drivetrain;

public class PathPlannerDriveCommand extends SequentialCommandGroup {
    public PathPlannerDriveCommand(Drivetrain drivetrain, PathPlannerPath path, boolean resetToPath) {
        PathPlannerLogging.logActivePath(path);
        Command pathCommand = drivetrain.getPathCommand(path, resetToPath);
        addRequirements(drivetrain);
        addCommands(pathCommand);
    }
}
