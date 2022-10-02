package Model;

public abstract class AbstractDepartment {
    private String codigo;
    private String nombre;
    private boolean auditado;
    private double presupuesto;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isAuditado() {
        return auditado;
    }

    public void setAuditado(boolean auditado) {
        this.auditado = auditado;
    }

    public double getPresupuesto() {
        return presupuesto;
    }

    public void setPresupuesto(double presupuesto) {
        this.presupuesto = presupuesto;
    }

    public abstract void agregar(AbstractDepartment department);
    public abstract void remover(AbstractDepartment department);
    public abstract void obtenerHijo(int x);

    public abstract boolean crearXML(String fileName);


    public abstract boolean cargarXML(String filename);
}
