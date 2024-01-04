package org.team1540.advantagekitdemo.util;

import com.pathplanner.lib.commands.PathPlannerAuto;
import com.pathplanner.lib.path.PathConstraints;
import com.pathplanner.lib.path.PathPlannerPath;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import org.team1540.advantagekitdemo.commands.auto.PathPlannerDriveCommand;
import org.team1540.advantagekitdemo.subsystems.drivetrain.Drivetrain;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public abstract class AutoCommand extends SequentialCommandGroup {

    public Command getPathPlannerDriveCommand(Drivetrain drivetrain, String pathName, boolean resetToPath) {
        PathPlannerPath path = PathPlannerPath.fromPathFile(pathName);
        return new PathPlannerDriveCommand(drivetrain, path, resetToPath);
    }

    public List<Command> getPathPlannerDriveCommandGroup(
            Drivetrain drivetrain,
            String autoName,
            boolean resetToPath) {
        List<PathPlannerPath> paths = PathPlannerAuto.getPathGroupFromAutoFile(autoName);
        LinkedList<Command> commands = new LinkedList<>();
        commands.addLast(new PathPlannerDriveCommand(drivetrain, paths.get(0), resetToPath));
        for (int i = 1; i < paths.size(); i++) {
            commands.addLast(new PathPlannerDriveCommand(drivetrain, paths.get(i), false));
        }
        return commands;
    }
}
