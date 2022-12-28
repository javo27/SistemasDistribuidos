package javdev.procesarorden.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "cuentasxcobrar")
public class Cuentas implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int codigo;
    private String codCuentas;
    private String codCliente;
    private String nombreCliente;
    private String rucCliente;

    private double totalIGV;
    private double totalCobrar;
    private String fecha;
    private String estado;
    @OneToMany(mappedBy = "cuentas", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Detalle> detalles;

    public Cuentas(){}

    public Cuentas(String codCuentas, String codCliente, String nombreCliente, String rucCliente, double totalIGV, double totalCobrar, String fecha, String estado) {
        this.codCuentas = codCuentas;
        this.codCliente = codCliente;
        this.nombreCliente = nombreCliente;
        this.rucCliente = rucCliente;
        this.totalIGV = totalIGV;
        this.totalCobrar = totalCobrar;
        this.fecha = fecha;
        this.estado = estado;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getCodCliente() {
        return codCliente;
    }

    public void setCodCliente(String codCliente) {
        this.codCliente = codCliente;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getRucCliente() {
        return rucCliente;
    }

    public void setRucCliente(String rucCliente) {
        this.rucCliente = rucCliente;
    }

    @JsonManagedReference(value = "detallepedido")
    public Set<Detalle> getDetalles() {
        return detalles;
    }

    public void setDetalles(Set<Detalle> detalles) {
        this.detalles = detalles;
        for (Detalle d: detalles){
            d.setCuentas(this);
        }
    }

    public String getCodCuentas() {
        return codCuentas;
    }

    public void setCodCuentas(String codCuentas) {
        this.codCuentas = codCuentas;
    }

    public double getTotalIGV() {
        return totalIGV;
    }

    public void setTotalIGV(double totalIGV) {
        this.totalIGV = totalIGV;
    }

    public double getTotalCobrar() {
        return totalCobrar;
    }

    public void setTotalCobrar(double totalCobrar) {
        this.totalCobrar = totalCobrar;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
