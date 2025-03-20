package frc.robot.subsystems.vacuum;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Vacuum extends SubsystemBase {

  private VacuumIO io;

  public Vacuum(VacuumIO io) {
    this.io = io;
  }

  public void runVacuum(boolean runVacuum) {
    io.runVacuum(runVacuum);
  }

  public void toggleVacuum() {
    io.toggleVacuum();
  }
}
