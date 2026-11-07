package es.deusto.ingenieria.g23.gui;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import es.deusto.ingenieria.g23.domain.Acogida;
import es.deusto.ingenieria.g23.domain.Usuario;

public class UsuarioTableModel extends AbstractTableModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String [] COLUMNAS = {"Animal_ID","Especie", "Fecha Acogida", "Estado"};
	private Usuario usuario;
	private List<Acogida> acogidas;
	
	
	public UsuarioTableModel(List<Acogida> acogidas) {
		this.acogidas =acogidas;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return (acogidas != null) ? acogidas.size() :0;
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return COLUMNAS.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Acogida acogida = acogidas.get(rowIndex);
		return switch (columnIndex) {
		case 0 -> acogida.getId_a();
		case 1 -> acogida.getEspecie();
		case 2 -> acogida.getFecha_ac();
		case 3 -> acogida.getEstado();
		default -> null;
		};
	}
	
	@Override
	public String getColumnName(int column) {
		
		return COLUMNAS[column];
		
	}
	
	

}
