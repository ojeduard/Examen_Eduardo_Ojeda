package View;

import Model.AbstractDepartment;
import Model.Department;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.util.ArrayList;
import java.util.List;

public class ModeloTabla implements TableModel {
    private List<AbstractDepartment> departmentos;

    ModeloTabla(List<AbstractDepartment> lista){
        departmentos = lista;
    }

    @Override
    public int getRowCount() {
        return departmentos.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public String getColumnName(int columnIndex) {
        String titulo = null;
        switch (columnIndex){
            case 0:
                titulo = "Codigo";
                break;

            case 1:
                titulo = "Nombre";
                break;

            case 2:
                titulo = "Auditado";
                break;

            case 3:
                titulo = "Presupuesto";
                break;
        }
        return titulo;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex){
            case 0:
                return String.class;


            case 1:
                return String.class;


            case 2:
                return Boolean.class;


            default:
                return Double.class;

        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex != 0;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        AbstractDepartment d = departmentos.get(rowIndex);


        switch (columnIndex){
            case 0:
                return d.getCodigo();

            case 1:
                return d.getNombre();

            case 2:
                return d.isAuditado();

            default:
                return d.getPresupuesto();
        }

    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

    }

    @Override
    public void addTableModelListener(TableModelListener l) {

    }

    @Override
    public void removeTableModelListener(TableModelListener l) {

    }
}
