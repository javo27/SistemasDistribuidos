package javdev.procesarorden.controller;

import javdev.procesarorden.consumer.ResultadoConsumer;
import javdev.procesarorden.entities.Cliente;
import javdev.procesarorden.entities.Item;
import javdev.procesarorden.entities.Orden;
import javdev.procesarorden.producer.OrdenProducer;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
@Controller
@RequestMapping("/orden")
public class ProcesarOrdenController {
    private final OrdenProducer ordenProducer;
    private Orden ordenEnviar;

    ArrayList<Cliente> clientes = new ArrayList<>();
    List<Item> items = new ArrayList<>();
    //Cliente cliente = new Cliente("C001","Pepito","1234567891011");

    @GetMapping("")
    public String home(){
        return "home";
    }
    @GetMapping("/registrar")
    public String vistaRegistrar(Model model){
        List<Item> itemsEnviar = items();
        double total = 0.0;
        for(Item i :itemsEnviar){
            total += i.getCantidad()*i.getPrecio();
        }
        total = Math.round(total*100.0)/100.0;
        Cliente cliente = clientes();
        String fecha = "";
        int rand = (int)(Math.random()*((9-1)+1))+1;
        int rand2 = (int)(Math.random()*((9-1)+1))+1;
        int rand3 = (int)(Math.random()*((9-1)+1))+1;
        String numOrden = "0"+String.valueOf(rand)+String.valueOf(rand2)+String.valueOf(rand3);
        ordenEnviar = new Orden(numOrden,itemsEnviar,cliente,fecha,total);
        model.addAttribute("orden", ordenEnviar);
        return "orden";
    }
    @PostMapping("/enviar")
    public String enviarOrden(@ModelAttribute Orden orden){
        ordenEnviar.setFecha(orden.getFecha());
        ordenProducer.send(ordenEnviar);
        return "redirect:estado";
    }
    @GetMapping("/estado")
    public String mostrarEstado(){
        return "show";
    }

    public Cliente clientes(){
        clientes.add(new Cliente("C001","Javier Valle","1234567891011"));
        clientes.add(new Cliente("C002","Saul Yalle","4321567891011"));
        clientes.add(new Cliente("C003","Alan Garcia","3421567891011"));
        clientes.add(new Cliente("C004","Pamela Gonza","2143567891011"));
        int rand = (int)(Math.random()*((3-0)+1))+0;
        return clientes.get(rand);
    }
    public List<Item> items(){
        items.add(new Item("AR001","Papel Bond XEROX",5,18.9));
        items.add(new Item("AR002","Lapices VINIFAN",3,6.4));
        items.add(new Item("AR003","Goma de barra",4,3.8));
        items.add(new Item("AR004","Cuaderno MINERVA",2,11.9));
        items.add(new Item("AR005","Cinta Embalaje",1,5));
        items.add(new Item("AR006","Libreta de Apuntes",6,19));
        items.add(new Item("AR007","Cuaderno Espiralado",2,14.9));
        items.add(new Item("AR008","Libreta de Apuntes A4",3,11.0));
        items.add(new Item("AR009","Papel Bond A3 XEROX",3,8.5));
        items.add(new Item("AR010","Lapiceros FABER",2,6.5));
        items.add(new Item("AR011","Cuaderno Rayado MINERVA",4,4.9));
        items.add(new Item("AR012","Colores FABER",1,10.0));
        items.add(new Item("AR013","Marcadores FABER",1,1.9));
        items.add(new Item("AR014","Borrador FABER",2,3.4));
        items.add(new Item("AR015","Goma Extra Grande",5,5.9));
        List<Item> itemsElegidos = new ArrayList<>();
        int cantidad = (int)(Math.random()*((5-1)+1))+1;
        int rand = (int)(Math.random()*((14-0)+1))+0;
        for(int i=0;i<cantidad;i++){
            itemsElegidos.add(items.get(rand));
            rand = (int)(Math.random()*((14-0)+1))+0;
            while(itemsElegidos.contains(items.get(rand))){
                rand = (int)(Math.random()*((14-0)+1))+0;
            }
        }
        return itemsElegidos;
    }

}
