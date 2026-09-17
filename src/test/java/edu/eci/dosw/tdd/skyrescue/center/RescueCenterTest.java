package edu.eci.dosw.tdd.skyrescue.center;
import edu.eci.dosw.tdd.skyrescue.drone.Drone;
import edu.eci.dosw.tdd.skyrescue.exception.SkyRescueExceptions;
import edu.eci.dosw.tdd.skyrescue.mission.Mission;
import edu.eci.dosw.tdd.skyrescue.mission.MissionStatus;
import edu.eci.dosw.tdd.skyrescue.operator.RescueOperator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RescueCenterTest {

    @Test
    void shouldAssignMissionWhenOperatorDroneAndDistanceAreValid() {
        RescueCenter center = new RescueCenter();
        RescueOperator operator = new RescueOperator("OP1", "Carlos");
        Drone drone =
                new Drone("D1", "DJI Matrice", 100);
        center.addOperator(operator);
        center.addDrone(drone);
        Mission mission =
                center.assignMission("OP1","D1","Zona Norte",50);
        assertNotNull(mission);
        assertEquals(MissionStatus.ACTIVE, mission.getStatus());
        assertFalse(drone.isAvailable());
    }

    @Test
    void shouldThrowDroneNotFoundExceptionWhenDroneDoesNotExist() {
        RescueCenter center = new RescueCenter();
        RescueOperator operator = new RescueOperator("OP1", "Carlos"); center.addOperator(operator);
        assertThrows( SkyRescueExceptions.DroneNotFoundException.class, () -> center.assignMission("OP1", "D999", "Zona Norte", 50));
    }

    @Test
    void shouldThrowDroneUnavailableExceptionWhenDroneIsAlreadyOccupied() {
        RescueCenter center = new RescueCenter();
        RescueOperator firstOperator = new RescueOperator("OP1", "Carlos");
        RescueOperator secondOperator = new RescueOperator("OP2", "Laura");
        Drone drone = new Drone("D1", "DJI Matrice", 100);
        center.addOperator(firstOperator);
        center.addOperator(secondOperator);
        center.addDrone(drone);
        center.assignMission("OP1", "D1", "Zona Norte", 50);
        assertThrows(SkyRescueExceptions.DroneUnavailableException.class, () -> center.assignMission("OP2", "D1", "Zona Sur", 40));
    }

    @Test
    void shouldThrowDistanceExceedsRangeExceptionWhenDistanceExceedsDroneRange() {
        RescueCenter center = new RescueCenter();
        RescueOperator operator = new RescueOperator("OP1", "Carlos");
        Drone drone =new Drone("D1", "DJI Matrice", 100);
        center.addOperator(operator);
        center.addDrone(drone);
        assertThrows(SkyRescueExceptions.DistanceExceedsRangeException.class, () -> center.assignMission( "OP1", "D1", "Zona Norte", 101));
    }

    @Test
    void shouldThrowOperatorNotFoundExceptionWhenOperatorDoesNotExist() {
        RescueCenter center = new RescueCenter();
        Drone drone = new Drone("D1", "DJI Matrice", 100);
        center.addDrone(drone);
        assertThrows(SkyRescueExceptions.OperatorNotFoundException.class, () -> center.assignMission("OP999", "D1", "Zona Norte", 50));
    }

    @Test
    void shouldThrowOperatorHasActiveMissionExceptionWhenOperatorAlreadyHasActiveMission() {
        RescueCenter center = new RescueCenter();
        RescueOperator operator = new RescueOperator("OP1", "Carlos");
        Drone firstDrone = new Drone("D1", "DJI Matrice", 100);
        Drone secondDrone = new Drone("D2", "DJI Mavic", 100);
        center.addOperator(operator);
        center.addDrone(firstDrone);
        center.addDrone(secondDrone);
        center.assignMission("OP1", "D1", "Zona Norte", 50);
        assertThrows(SkyRescueExceptions.OperatorHasActiveMissionException.class, () -> center.assignMission( "OP1", "D2", "Zona Sur", 40));
    }
}