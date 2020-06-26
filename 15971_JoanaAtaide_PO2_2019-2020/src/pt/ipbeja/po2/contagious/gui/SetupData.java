package pt.ipbeja.po2.contagious.gui;

public class SetupData {
    private int nHealthy;
    private int nSick;
    private int speed;
    private int directions;
    private int nImmune;

    public SetupData(int nHealthy, int nSick,int nImmune, int speed, int directions) {
        this.nHealthy = nHealthy;
        this.nSick = nSick;
        this.nImmune = nImmune;
        this.speed = speed;
        this.directions = directions;
    }

    /**
     * Get number of Healthy people
     * @return - number of Healthy people
     */
    public int getHealthy() {
        return this.nHealthy;
    }

    /**
     * Get number of Sick people
     * @return - number of Sick people
     */
    public int getSick() {
        return this.nSick;
    }

    /**
     * Get number Immune people
     * @return - number Immune people
     */
    public int getImmune() {
        return this.nImmune;
    }

    /**
     * Get squares per move
     * @return - speed
     */
    public int getSpeed() {
        return this.speed;
    }

    /**
     * Get directions variable
     * @return - directions
     */
    public int getDirections() {
        return this.directions;
    }
}
