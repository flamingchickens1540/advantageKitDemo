package org.team1540.advantagekitdemo.subsystems.climber;

import org.littletonrobotics.junction.Logger;
import org.team1540.advantagekitdemo.util.SuperstructureVisualizer;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Climber extends SubsystemBase {
    private final ClimberIO io;
    private final ClimberIOInputsAutoLogged inputs = new ClimberIOInputsAutoLogged();
    public Climber(ClimberIO io){
        this.io = io;
    }

    @Override
    public void periodic() {
        io.updateInputs(inputs);
        Logger.processInputs("Climber", inputs);
        SuperstructureVisualizer.setClimberPosition(getHangerStowed(), getForksStowed());
    }

    public void setHangerStowed(boolean hangerStowed){
        io.setHanger(hangerStowed);
    }

    public void setForksStowed(boolean forksStowed){
        io.setForks(forksStowed);
    }

    public boolean getHangerStowed(){
        return inputs.hangerStowed;
    }

    public boolean getForksStowed(){
        return inputs.forksStowed;
    }
}
