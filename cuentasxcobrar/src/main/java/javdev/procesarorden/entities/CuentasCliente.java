package javdev.procesarorden.entities;

import java.util.List;

public class CuentasCliente {
    private Cliente cliente;
    private List<Cobrar> cuentasxcobrar;

    private List<String> fechas;

    public CuentasCliente(){}
    public CuentasCliente(Cliente cliente, List<Cobrar> cuentasxcobrar, List<String> fecha) {
        this.cliente = cliente;
        this.cuentasxcobrar = cuentasxcobrar;
        this.fechas = fecha;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<Cobrar> getCuentasxcobrar() {
        return cuentasxcobrar;
    }

    public void setCuentasxcobrar(List<Cobrar> cuentasxcobrar) {
        this.cuentasxcobrar = cuentasxcobrar;
    }

    public List<String> getFecha() {
        return fechas;
    }

    public void setFecha(List<String> fecha) {
        this.fechas = fecha;
    }
}
