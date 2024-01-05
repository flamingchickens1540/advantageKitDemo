package org.team1540.advantagekitdemo.subsystems.climber;

public class ClimberIOSim implements ClimberIO{
    private boolean hangerStowed = true;
    private boolean forksStowed = true;

    @Override
    public void updateInputs(ClimberIOInputs inputs) {
        inputs.forksStowed = forksStowed;
        inputs.hangerStowed = hangerStowed;
    }

    @Override
    public void setForks(boolean stowed) {
        forksStowed = stowed;
    }

    @Override
    public void setHanger(boolean stowed) {
        hangerStowed = stowed;
    }
}
