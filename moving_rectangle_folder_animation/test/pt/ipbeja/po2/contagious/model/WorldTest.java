package pt.ipbeja.po2.contagious.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WorldTest {
    private World world;
    private View view;

    @BeforeEach
    public void setUp() {
        this.world = new World(view, 10, 10, 0, 0, 0, 0);
    }

    @AfterEach
    public void tearDown() {
        System.out.println("TEARDOWN");
    }

    @Test
    void testMovementRight() {
        this.world.createHealthyPerson(4, 9);
        boolean validMove = this.world.movePerson(4, 9, 4, 10);
        assertFalse(validMove);
        assertTrue(this.world.getCell(4, 9).isHealthy());
    }

    @Test
    void testMovementLeft() {
        this.world.createHealthyPerson(4, 0);
        boolean validMove = this.world.movePerson(4, 0, 4, -1);
        assertFalse(validMove);
        assertTrue(this.world.getCell(4, 0).isHealthy());
    }

    @Test
    void testMovementUp() {
        this.world.createHealthyPerson(0, 3);
        boolean validMove = this.world.movePerson(0, 3, -1, 3);
        assertFalse(validMove);
        assertTrue(this.world.getCell(0, 3).isHealthy());

    }

    @Test
    void testMovementDown() {
        this.world.createHealthyPerson(9, 3);
        boolean validMove = this.world.movePerson(9, 3, 10, 3);
        assertFalse(validMove);
        assertTrue(this.world.getCell(9, 3).isHealthy());
    }

    @Test
    void testHealthyInfection() {
        this.world.createSickPerson(5, 5);
        assertTrue(this.world.getCell(5, 5).isSick());

        this.world.createHealthyPerson(5, 3);
        assertTrue(this.world.getCell(5, 3).isHealthy());

        this.world.createHealthyPerson(4, 4);
        assertTrue(this.world.getCell(4, 4).isHealthy());

        this.world.movePerson(5, 5, 5, 4);

        assertTrue(this.world.getCell(5, 3).isSick());
        assertTrue(this.world.getCell(4, 4).isSick());
    }

    @Test
    void testSickInfection() {
        this.world.createSickPerson(5, 5);
        assertTrue(this.world.getCell(5, 5).isSick());

        this.world.createSickPerson(5, 3);
        assertTrue(this.world.getCell(5, 3).isSick());

        this.world.createSickPerson(4, 4);
        assertTrue(this.world.getCell(4, 4).isSick());

        this.world.movePerson(5, 5, 5, 4);

        assertTrue(this.world.getCell(5, 3).isSick());
        assertTrue(this.world.getCell(4, 4).isSick());
    }

    @Test
    void testImmunity() {
        this.world.createSickPerson(5, 5);
        assertTrue(this.world.getCell(5, 5).isSick());

        this.world.createImmunePerson(5, 3);
        assertTrue(this.world.getCell(5, 3).isImmune());

        this.world.createImmunePerson(4, 4);
        assertTrue(this.world.getCell(4, 4).isImmune());

        this.world.movePerson(5, 5, 5, 4);

        assertFalse(this.world.getCell(5, 3).isSick());
        assertFalse(this.world.getCell(4, 4).isSick());
    }

}