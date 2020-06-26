package pt.ipbeja.po2.contagious.model;

public interface View {
    void updatePosition(CellPosition position, CellPosition newPosition);

    void updateChart(int iteration, int healthy, int sick, int immune);
}
