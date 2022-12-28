package javdev.procesarorden.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "detalles")
public class Detalle implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ManyToOne
    @JoinColumn(name = "codigo")
    private Cuentas cuentas;
    @Id
    private String codItem;
    private int cantidad;
    private double precio;
    private double subtotal;

    @JsonBackReference(value = "detallepedido")
    public Cuentas getCuentas() {
        return cuentas;
    }

    public void setCuentas(Cuentas cuentas) {
        this.cuentas = cuentas;
    }

    public String getCodItem() {
        return codItem;
    }

    public void setCodItem(String codCuentas) {
        this.codItem = codCuentas;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }
}
