package frc.robot.subsystems.climb;

import com.ctre.phoenix6.controls.VoltageOut;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.ArmFeedforward;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.simulation.SingleJointedArmSim;

public class ClimbSim implements ClimbIO {

    private SingleJointedArmSim climb;
    private ArmFeedforward feedforward; 
    private final VoltageOut voltageOut = new VoltageOut(0).withEnableFOC(true).withUpdateFreqHz(0);
    boolean isClosedLoop = false;

 public ClimbSim() {
    climb = 
    new SingleJointedArmSim(
        DCMotor.getKrakenX60(1),
        225,
        14,
        Units.inchesToMeters(14),
        Units.degreesToRadians(150),
        Units.degreesToRadians(-10),
        true,
        0, 0);
        feedforward = new ArmFeedforward(0, 0, 0, 0);
    
 }

 public void setvolts(double volts) {
    double voltage = MathUtil.clamp(volts, -12, 12);
    climb.setInputVoltage(volts); 
 }

  @Override
  public void runVolts(double volts) {
    setvolts(volts);
  }

  @Override
  public double setAngle() {
    climb.setAngle(Units.rotationstodegrees());
  }
}
