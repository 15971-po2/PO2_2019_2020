package pt.ipbeja.po2.contagious.gui;

public class SetupData {
    private int nHealthy;
    private int nSick;

    public SetupData(int nHealthy, int nSick) {
        this.nHealthy = nHealthy;
        this.nSick = nSick;
    }

    public int getHealthy() {
        return this.nHealthy;
    }

    public int getSick() {
        return this.nSick;
    }

}
