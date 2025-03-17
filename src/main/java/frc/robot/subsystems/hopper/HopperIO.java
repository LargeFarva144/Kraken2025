package frc.robot.subsystems.hopper;

import org.littletonrobotics.junction.AutoLog;

public interface HopperIO {

    @AutoLog
    public static class HopperIOInputs {

        public boolean connected = false;
        public boolean objectDetectedLeft = false;
        public boolean objectDetectedRight = false;
        public double distanceLeftInches = 0.0;
        public double distanceRightInches = 0.0;
        public double signalStrengthLeft = 0.0;
        public double signalStrengthRight = 0.0;

    }

    public default boolean getObjectDetection() {
        return false;
    }

    public default void updateInputs(HopperIOInputs inputs) {}

}
