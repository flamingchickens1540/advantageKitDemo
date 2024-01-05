package org.team1540.advantagekitdemo.commands;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import org.team1540.advantagekitdemo.Constants;
import org.team1540.advantagekitdemo.subsystems.drivetrain.Drivetrain;

public class ArcadeDriveCommand extends Command {
    private final Drivetrain drivetrain;

    private final CommandXboxController xBoxController;

    private final SlewRateLimiter leftRateLimiter = new SlewRateLimiter(5);
    private final SlewRateLimiter rightRateLimiter = new SlewRateLimiter(5);

    public ArcadeDriveCommand(Drivetrain drivetrain, CommandXboxController xBoxController) {
        this.drivetrain = drivetrain;
        this.xBoxController = xBoxController;
        addRequirements(drivetrain);
    }

    public void execute() {
        double throttle = MathUtil.applyDeadband(-xBoxController.getLeftY(), Constants.DEADZONE_RADIUS);
        double turn = MathUtil.applyDeadband(xBoxController.getRightX(), Constants.DEADZONE_RADIUS);
        double left = leftRateLimiter.calculate(
                MathUtil.clamp(throttle + turn,-1, 1)
        );
        double right  = rightRateLimiter.calculate(
                MathUtil.clamp(throttle - turn, -1, 1)
        );

        drivetrain.drivePercent(left, right);
    }

    @Override
    public void end(boolean interrupted) {
        drivetrain.stop();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}