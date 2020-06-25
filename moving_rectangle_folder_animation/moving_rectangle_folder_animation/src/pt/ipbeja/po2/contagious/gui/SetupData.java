package pt.ipbeja.po2.contagious.gui;

public class SetupData {
    private int nHealthy;
    private int nSick;
    private int speed;
    private int directions;

    public SetupData(int nHealthy, int nSick, int speed, int directions) {
        this.nHealthy = nHealthy;
        this.nSick = nSick;
        this.speed = speed;
        this.directions = directions;
    }

    public int getHealthy() {
        return this.nHealthy;
    }

    public int getSick() {
        return this.nSick;
    }

    public int getSpeed() {
        return this.speed;
    }

    public int getDirections() {
        return this.directions;
    }

}
