package org.team1540.advantagekitdemo.commands.auto;

import org.team1540.advantagekitdemo.subsystems.drivetrain.Drivetrain;
import org.team1540.advantagekitdemo.util.AutoCommand;

public class TestAuto extends AutoCommand {
    public TestAuto(Drivetrain drivetrain) {
        addCommands(
                getPathPlannerDriveCommand(drivetrain, "TestPath", true)
        );
    }
}
