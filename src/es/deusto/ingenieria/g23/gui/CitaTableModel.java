package es.deusto.ingenieria.g23.gui;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import es.deusto.ingenieria.g23.domain.CitaAgenda;
//IAG  GitHub Copilot
//ADAPTADO: Generación de la estructura repetitiva del AbstractTableModel tomando como referencia SolicitudTableModel y ajustando los getters de CitaAgenda
public class CitaTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private final String[] columnas = { "ID", "Fecha y Hora", "Tipo", "Animal", "Persona Asignada" };
	private List<CitaAgenda> citas;

	public CitaTableModel(List<CitaAgenda> citas) {
		this.citas = citas;
	}

	@Override
	public int getRowCount() {
		return citas.size();
	}

	@Override
	public int getColumnCount() {
		return columnas.length;
	}

	@Override
	public String getColumnName(int column) {
		return columnas[column];
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		CitaAgenda c = citas.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return c.getId();
		case 1:
			return c.getFechaHora();
		case 2:
			return c.getTipoCita();
		case 3:
			if (c.getAnimal() != null) {
				return c.getAnimal().getNombre();
			} else {
				return "Sin asignar";
			}
		case 4:
			if (c.getPersonaAsignada() != null) {
				return c.getPersonaAsignada().getNombre();
			} else {
				return "Sin asignar";
			}
		default:
			return null;
		}
	}
}