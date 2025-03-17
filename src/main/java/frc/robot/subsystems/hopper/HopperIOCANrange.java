package frc.robot.subsystems.hopper;

import com.ctre.phoenix6.BaseStatusSignal;
import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.hardware.CANrange;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.units.measure.Distance;

public class HopperIOCANrange implements HopperIO {

    private final CANrange _CANrangeLeft;
    private final CANrange _CANrangeRight;

    private StatusSignal<Boolean> objectDetectedLeft;
    private StatusSignal<Boolean> objectDetectedRight;
    private StatusSignal<Distance> distanceLeft;
    private StatusSignal<Distance> distanceRight;
    private StatusSignal<Double> signalStrengthLeft;
    private StatusSignal<Double> signalStrengthRight;

    public HopperIOCANrange() {
        _CANrangeLeft = new CANrange(HopperConstants.leftCANrangeId);
        _CANrangeRight = new CANrange(HopperConstants.rightCANrangeId);

        objectDetectedLeft = _CANrangeLeft.getIsDetected();
        objectDetectedRight = _CANrangeRight.getIsDetected();
        distanceLeft = _CANrangeLeft.getDistance();
        distanceRight = _CANrangeRight.getDistance();
        signalStrengthLeft = _CANrangeLeft.getSignalStrength();
        signalStrengthRight = _CANrangeRight.getSignalStrength();

        BaseStatusSignal.setUpdateFrequencyForAll(
            50,
            objectDetectedLeft,
            objectDetectedRight,
            distanceLeft,
            distanceRight,
            signalStrengthLeft,
            signalStrengthRight);

        _CANrangeLeft.optimizeBusUtilization(0.0, 1.0);
        _CANrangeRight.optimizeBusUtilization(0.0, 1.0);
    }

    @Override
    public boolean getObjectDetection() {
        return objectDetectedLeft.getValue() && objectDetectedRight.getValue();
    }

    @Override
    public void updateInputs(HopperIOInputs inputs) {
        inputs.connected = 
        BaseStatusSignal.refreshAll(
            objectDetectedLeft,
            objectDetectedRight,
            distanceLeft,
            distanceRight,
            signalStrengthLeft,
            signalStrengthRight)
        .isOK();
        inputs.objectDetectedLeft = objectDetectedLeft.getValue();
        inputs.objectDetectedRight = objectDetectedRight.getValue();
        inputs.distanceLeftInches = Units.metersToInches(distanceLeft.getValueAsDouble());
        inputs.distanceRightInches = Units.metersToInches(distanceRight.getValueAsDouble());
        inputs.signalStrengthLeft = signalStrengthLeft.getValueAsDouble();
        inputs.signalStrengthRight = signalStrengthRight.getValueAsDouble();
    }

}
