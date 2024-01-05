package org.team1540.advantagekitdemo.subsystems.climber;

import org.littletonrobotics.junction.AutoLog;

public interface ClimberIO {
    @AutoLog
    class ClimberIOInputs{
        public boolean hangerStowed = true;
        public boolean forksStowed = true;
    }


    default void updateInputs(ClimberIOInputs inputs) {}

    default void setForks(boolean stowed) {}
    default void setHanger(boolean stowed) {}
}
