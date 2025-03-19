package frc.robot.subsystems.hopper;

import com.ctre.phoenix6.BaseStatusSignal;
import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.hardware.CANrange;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.units.measure.Distance;

public class HopperIOCANrange implements HopperIO {

  private final CANrange _CANrange;

  private StatusSignal<Boolean> objectDetected;
  private StatusSignal<Distance> distance;
  private StatusSignal<Double> signalStrength;

  public HopperIOCANrange() {
    _CANrange = new CANrange(26);

    objectDetected = _CANrange.getIsDetected();
    distance = _CANrange.getDistance();
    signalStrength = _CANrange.getSignalStrength();

    BaseStatusSignal.setUpdateFrequencyForAll(50, objectDetected, distance, signalStrength);

    _CANrange.optimizeBusUtilization(0.0, 1.0);
  }

  // @Override
  // public boolean hasCoral() {
  //   return distance.getValueAsDouble() < 0.040;
  // }

  @Override
  public void updateInputs(HopperIOInputs inputs) {
    inputs.connected = BaseStatusSignal.refreshAll(objectDetected, distance, signalStrength).isOK();
    inputs.objectDetected = objectDetected.getValue();
    inputs.distanceInches = Units.metersToInches(distance.getValueAsDouble());
    inputs.signalStrength = signalStrength.getValueAsDouble();
  }
}
