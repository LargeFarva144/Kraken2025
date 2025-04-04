package frc.robot.subsystems.vacuum;

import org.littletonrobotics.junction.AutoLog;

public interface VacuumIO {

  @AutoLog
  public static class VacuumIOInputs {

    public boolean connected = false;
    public double supplyCurrentAmps = 0.0;
    public double sensorVolts = 0.0;
  }

  public default boolean getVacuumHasPressure(){
    return false;
  }

  public default void updateInputs(VacuumIOInputs inputs) {}

  public default void runVacuum(boolean runVacuum) {}

  public default void toggleVacuum() {}
}
