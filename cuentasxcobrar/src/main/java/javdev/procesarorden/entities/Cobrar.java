package javdev.procesarorden.entities;

public class Cobrar {
    private int numero;
    private String codigo;
    private double importe;

    private String fecha;

    public Cobrar() {
    }

    public Cobrar(int numero, String codigo, double importe, String fecha) {
        this.numero = numero;
        this.codigo = codigo;
        this.importe = importe;
        this.fecha = fecha;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
