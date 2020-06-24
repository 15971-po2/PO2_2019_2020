package pt.ipbeja.po2.contagious.model;

public interface View {
    void populateWorld(CellPosition position);

    void addPerson(CellPosition position);

    void updatePosition(int dx, int dy, int i);
}
