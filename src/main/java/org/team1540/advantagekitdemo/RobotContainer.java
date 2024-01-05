// Copyright 2021-2023 FRC 6328
// http://github.com/Mechanical-Advantage
//
// This program is free software; you can redistribute it and/or
// modify it under the terms of the GNU General Public License
// version 3 as published by the Free Software Foundation or
// available in the root directory of this project.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU General Public License for more details.

package org.team1540.advantagekitdemo;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import org.team1540.advantagekitdemo.commands.*;
import org.team1540.advantagekitdemo.commands.auto.TestAuto;
import org.team1540.advantagekitdemo.subsystems.drivetrain.Drivetrain;
import org.team1540.advantagekitdemo.subsystems.drivetrain.DrivetrainIOReal;
import org.team1540.advantagekitdemo.subsystems.drivetrain.DrivetrainIOSim;
import org.team1540.advantagekitdemo.subsystems.elevator.Elevator;
import org.team1540.advantagekitdemo.subsystems.elevator.ElevatorIOReal;
import org.team1540.advantagekitdemo.subsystems.elevator.ElevatorIOSim;
import org.team1540.advantagekitdemo.subsystems.intake.Intake;
import org.team1540.advantagekitdemo.subsystems.intake.IntakeIO;
import org.team1540.advantagekitdemo.subsystems.intake.IntakeIOSim;
import org.team1540.advantagekitdemo.subsystems.climber.*;;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
    // Controller
    private final CommandXboxController driver = new CommandXboxController(0);
    private final CommandXboxController copilot = new CommandXboxController(1);

    final Drivetrain drivetrain;
    final Elevator elevator;
    final Intake intake;
    final Climber climber;

    /**
     * The container for the robot. Contains subsystems, OI devices, and commands.
     */
    public RobotContainer() {
        if (Robot.isReal()) {
            drivetrain = new Drivetrain(new DrivetrainIOReal());
            elevator = new Elevator(new ElevatorIOReal());
            intake = new Intake(new IntakeIO() {});
            climber = new Climber(new ClimberIO() {});
        } else {
            drivetrain = new Drivetrain(new DrivetrainIOSim());
            elevator = new Elevator(new ElevatorIOSim());
            intake = new Intake(new IntakeIOSim());
            climber = new Climber(new ClimberIOSim());
        }
        configureButtonBindings();
    }

    /**
     * Use this method to define your button->command mappings. Buttons can be created by
     * instantiating a {@link GenericHID} or one of its subclasses ({@link
     * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
     * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */
    private void configureButtonBindings() {
        drivetrain.setDefaultCommand(new ArcadeDriveCommand(drivetrain, driver));
//        intake.setDefaultCommand(new WristManualCommand(intake, copilot));
//        elevator.setDefaultCommand(new ElevatorManualCommand(elevator, copilot));

        copilot.rightBumper().onTrue(new ElevatorSetpointCommand(elevator, 1.5));
        copilot.leftBumper().onTrue(new ElevatorSetpointCommand(elevator, 0));

        driver.leftBumper().onTrue(new InstantCommand(() -> climber.setForksStowed(!climber.getForksStowed())));
        driver.rightBumper().onTrue(new InstantCommand(() -> climber.setHangerStowed(!climber.getHangerStowed())));
        driver.a().onTrue(new InstantCommand(() -> climber.setForksStowed(!climber.getForksStowed())));
        driver.b().onTrue(new InstantCommand(() -> climber.setHangerStowed(!climber.getHangerStowed())));
    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        return Commands.parallel(
                new TestAuto(drivetrain),
                new IntakeCommand(intake, Rotation2d.fromDegrees(180), 0),
                new ElevatorSetpointCommand(elevator, 1.8)
        ).andThen(new InstantCommand(() -> {
            System.out.println("setting climbers");
            climber.setForksStowed(false);
            climber.setHangerStowed(false);
        }));
    }
}
