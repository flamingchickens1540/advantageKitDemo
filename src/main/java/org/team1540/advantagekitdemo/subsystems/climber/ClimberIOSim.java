package org.team1540.advantagekitdemo.subsystems.climber;

public class ClimberIOSim implements ClimberIO{
    private boolean hangerStowed = false;
    private boolean forksStowed = true;

    @Override
    public void updateInputs(ClimberIOInputs inputs) {
        inputs.forksStowed = forksStowed;
        inputs.hangerStowed = hangerStowed;
    }

    @Override
    public void setPosition(boolean hangerStowed, boolean forksStowed) {
        this.hangerStowed = hangerStowed;
        this.forksStowed = forksStowed;
    }
}
