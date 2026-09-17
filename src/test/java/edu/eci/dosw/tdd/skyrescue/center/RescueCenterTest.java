package edu.eci.dosw.tdd.skyrescue.center;

import edu.eci.dosw.tdd.skyrescue.drone.Drone;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RescueCenterTest {

    @Test
    void shouldRegisterDroneWhenDataIsValid() {
        RescueCenter center = new RescueCenter();
        Drone drone = new Drone("D1", "DJI Matrice", 100);

        boolean result = center.addDrone(drone);

        assertTrue(result);
    }

    @Test
    void shouldReturnFalseWhenDroneIsNull() {
        RescueCenter center = new RescueCenter();

        boolean result = center.addDrone(null);

        assertFalse(result);
    }

    @Test
    void shouldReturnFalseWhenDroneIdIsEmpty() {
        RescueCenter center = new RescueCenter();
        Drone drone = new Drone("", "DJI Matrice", 100);

        boolean result = center.addDrone(drone);

        assertFalse(result);
    }

    @Test
    void shouldReturnFalseWhenDroneIdIsDuplicated() {
        RescueCenter center = new RescueCenter();

        Drone firstDrone =
                new Drone("D1", "DJI Matrice", 100);

        Drone secondDrone =
                new Drone("D1", "DJI Mavic", 80);

        boolean firstResult = center.addDrone(firstDrone);
        boolean secondResult = center.addDrone(secondDrone);

        assertTrue(firstResult);
        assertFalse(secondResult);
    }
}