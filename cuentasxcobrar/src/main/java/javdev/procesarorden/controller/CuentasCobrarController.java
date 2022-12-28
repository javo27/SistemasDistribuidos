package javdev.procesarorden.controller;

import javdev.procesarorden.entities.Cliente;
import javdev.procesarorden.entities.Cobrar;
import javdev.procesarorden.entities.Cuentas;
import javdev.procesarorden.entities.CuentasCliente;
import javdev.procesarorden.repository.CuentasRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/cuentascobrar")
public class CuentasCobrarController {
    @Autowired
    private CuentasRepository cuentasRepository;
    @GetMapping("")
    public String home(){
        return "home";
    }
    @GetMapping("/lista")
    public String lista(Model model){
        List<Cuentas> cuentas = cuentasRepository.findAll();
        List<CuentasCliente> cuentasxCliente = new ArrayList<>();
        List<Cobrar> cobrar = new ArrayList<>();
        List<String> fechas = new ArrayList<>();
        Cliente cliente = new Cliente();
        CuentasCliente agregar = new CuentasCliente();
        List<String> codigosVisitados = new ArrayList<>();
        String codigo = "";
        int cantidad = 1;
        for(int i=0;i<cuentas.size();i++){
            if(!codigo.equals(cuentas.get(i).getCodCliente())){
                cliente = new Cliente(cuentas.get(i).getCodCliente(),cuentas.get(i).getNombreCliente(),cuentas.get(i).getRucCliente());
                if(!codigosVisitados.contains(cliente.getCodigo())){
                    for (int j=0;j<cuentas.size();j++){
                        if(cliente.getCodigo().equals(cuentas.get(j).getCodCliente())){
                            fechas.add(cuentas.get(j).getFecha());
                            cobrar.add(new Cobrar(cantidad, cuentas.get(j).getCodCuentas(),cuentas.get(j).getTotalCobrar(),cuentas.get(j).getFecha()));
                            cantidad++;
                        }
                    }
                    cantidad = 1;
                    codigo = cliente.getCodigo();
                    codigosVisitados.add(codigo);
                    agregar.setCliente(cliente);
                    agregar.setFecha(fechas);
                    agregar.setCuentasxcobrar(cobrar);
                    cuentasxCliente.add(agregar);
                    cliente = new Cliente();
                    fechas = new ArrayList<>();
                    cobrar = new ArrayList<>();
                    agregar = new CuentasCliente();
                }
            }
        }
        model.addAttribute("cuentas",cuentasxCliente);
        return "cuentas";
    }
}
