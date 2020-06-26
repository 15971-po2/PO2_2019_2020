 /**
     *
	 Joana Ataíde Nº15971
	 jfrab@hotmail.com
	 https://github.com/PO2-2019-2020/tp2-15971-po2
     */
	 package pt.ipbeja.po2.contagious.model;

public interface View {
    void updatePosition(CellPosition position, CellPosition newPosition);

    void updateChart(int iteration, int healthy, int sick, int immune);
}
