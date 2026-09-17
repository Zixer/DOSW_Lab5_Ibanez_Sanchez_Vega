package edu.eci.dosw.tdd.skyrescue.center;

import edu.eci.dosw.tdd.skyrescue.exception.SkyRescueExceptions;
import edu.eci.dosw.tdd.skyrescue.drone.Drone;
import edu.eci.dosw.tdd.skyrescue.mission.Mission;
import edu.eci.dosw.tdd.skyrescue.mission.MissionStatus;
import edu.eci.dosw.tdd.skyrescue.operator.RescueOperator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RescueCenterTest {

    @Test
    public void shouldCompleteActiveMissionAndMakeDroneAvailable() {
        RescueCenter center = new RescueCenter();

        RescueOperator operator = new RescueOperator("OP1", "Carlos");

        Drone drone = new Drone("D1", "DJI Matrice", 100);

        center.addOperator(operator);
        center.addDrone(drone);

        Mission mission = center.assignMission("OP1","D1","Zona Norte",50);

        Mission completedMission = center.completeMission(mission.getId());

        assertEquals(MissionStatus.COMPLETED,completedMission.getStatus());

        assertNotNull(completedMission.getEndDate());

        assertTrue(completedMission.getDrone().isAvailable());
    }

    @Test
    public void shouldThrowMissionNotFoundExceptionWhenMissionDoesNotExist() {
        RescueCenter center = new RescueCenter();

        assertThrows(SkyRescueExceptions.MissionNotFoundException.class,() -> center.completeMission("M999"));
    }

    @Test
    public void shouldThrowMissionAlreadyCompletedExceptionWhenMissionIsCompletedTwice() {
        RescueCenter center = new RescueCenter();

        RescueOperator operator = new RescueOperator("OP1", "Carlos");

        Drone drone = new Drone("D1", "DJI Matrice", 100);

        center.addOperator(operator);
        center.addDrone(drone);

        Mission mission = center.assignMission("OP1","D1","Zona Norte",50);
    
        center.completeMission(mission.getId());

        assertThrows(SkyRescueExceptions.MissionAlreadyCompletedException.class,() -> center.completeMission(mission.getId()));
    }

    @Test
    public void shouldNotModifyAnotherActiveMissionWhenCompletingMission() {

        RescueCenter center = new RescueCenter();

        RescueOperator firstOperator = new RescueOperator("OP1", "Carlos");

        RescueOperator secondOperator = new RescueOperator("OP2", "Laura");

        Drone firstDrone = new Drone("D1", "DJI Matrice", 100);

        Drone secondDrone = new Drone("D2", "DJI Mavic", 100);

        center.addOperator(firstOperator);
        center.addOperator(secondOperator);

        center.addDrone(firstDrone);
        center.addDrone(secondDrone);

        Mission firstMission = center.assignMission("OP1","D1","Zona Norte",50);

        Mission secondMission =center.assignMission("OP2","D2","Zona Sur",40);

        center.completeMission(firstMission.getId());

        assertEquals(MissionStatus.ACTIVE,secondMission.getStatus());

        assertFalse(secondDrone.isAvailable());

        assertNull(secondMission.getEndDate());
    }
}