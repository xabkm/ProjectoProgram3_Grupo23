package es.deusto.ingenieria.g23.gui;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import es.deusto.ingenieria.g23.domain.Animal;

public class AnimalTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private static final String[] COLUMNAS = {"ID", "Nombre", "Especie", "Raza", "Estado"};
	private List<Animal> animales;
	
	public AnimalTableModel(List<Animal> animales) {
		this.animales = animales;
	}

	@Override
	public int getRowCount() {
		return (animales != null) ? animales.size() : 0;
	}

	@Override
	public int getColumnCount() {
		return COLUMNAS.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Animal animal = animales.get(rowIndex);
		
		return switch (columnIndex) {
			case 0 -> animal.getId();
			case 1 -> animal.getNombre();
			case 2 -> animal.getEspecie();
			case 3 -> animal.getRaza();
			case 4 -> animal.getEstado();
			default -> null;
		};
	}
	
	@Override
	public String getColumnName(int column) {
		return COLUMNAS[column];
	}
}