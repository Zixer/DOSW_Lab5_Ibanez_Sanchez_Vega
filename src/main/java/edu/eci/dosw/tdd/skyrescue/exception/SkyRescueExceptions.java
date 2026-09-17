package edu.eci.dosw.tdd.skyrescue.exception;

public final class SkyRescueExceptions {

    private SkyRescueExceptions() {
    }

    public static class DroneNotFoundException extends IllegalArgumentException {
        public DroneNotFoundException() {
            super("The drone does not exist.");
        }
    }

    public static class DroneUnavailableException extends IllegalStateException {
        public DroneUnavailableException() {
            super("The drone is not available.");
        }
    }

    public static class DistanceExceedsRangeException extends IllegalArgumentException {
        public DistanceExceedsRangeException() {
            super("The mission distance exceeds the drone maximum range.");
        }
    }

    public static class OperatorNotFoundException extends IllegalArgumentException {
        public OperatorNotFoundException() {
            super("The operator does not exist.");
        }
    }

    public static class OperatorHasActiveMissionException extends IllegalStateException {
        public OperatorHasActiveMissionException() {
            super("The operator already has an active mission.");
        }
    }

    public static class MissionNotFoundException extends IllegalArgumentException {
        public MissionNotFoundException() {
            super("The mission does not exist.");
        }
    }

    public static class MissionAlreadyCompletedException extends IllegalStateException {
        public MissionAlreadyCompletedException() {
            super("The mission has already been completed.");
        }
    }
}