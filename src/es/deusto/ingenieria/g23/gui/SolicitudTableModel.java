package es.deusto.ingenieria.g23.gui;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import es.deusto.ingenieria.g23.domain.SolicitudAdopcion;
import java.time.format.DateTimeFormatter;


public class SolicitudTableModel extends AbstractTableModel {
    private static final long serialVersionUID = 1L;
    private final String[] columnas = {"ID", "Animal", "Solicitante", "Fecha y Hora", "Estado","Boton1"};
    private final DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private List<SolicitudAdopcion> solicitudes;

    public SolicitudTableModel(List<SolicitudAdopcion> solicitudes) {
        this.solicitudes = solicitudes;
    }

    @Override
    public int getRowCount() {
        return solicitudes.size();
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
        SolicitudAdopcion s = solicitudes.get(rowIndex);
        switch (columnIndex) {
        case 0: 
            return s.getId();
        case 1: 
            if (s.getAnimal() != null) {
                return s.getAnimal().getNombre();
            } else {
                return "Sin asignar";
            }
        case 2: 
            if (s.getSolicitante() != null) {
                return s.getSolicitante().getNombre();
            } else {
                return "Sin asignar";
            }
        case 3: 
        	if (s.getFechaSolicitud() != null) {
				return s.getFechaSolicitud().format(formatoFecha);
			} else {
				return "Sin fecha";
			}
        case 4: 
            return s.getEstado();
        case 5: 
            return "";
        default: 
            return null;
    }
    }
}