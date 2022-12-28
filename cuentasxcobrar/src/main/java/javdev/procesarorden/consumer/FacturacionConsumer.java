package javdev.procesarorden.consumer;

import javdev.procesarorden.entities.*;
import javdev.procesarorden.producer.ConfirmacionProducer;
import javdev.procesarorden.repository.CuentasRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import com.google.gson.Gson;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Component
public class FacturacionConsumer {

    @Autowired
    SimpMessagingTemplate websocketTemplate;
    @Autowired
    private CuentasRepository cuentasRepository;

    @Autowired
    private ConfirmacionProducer confirmacionProducer;

    @KafkaListener(
            topics = "${topic.name.cuentas}",
            groupId = "group_id",
            containerFactory = "facturacionKafkaListenerContainerFactory"
    )
    public void consume(String facturacion) {
        log.info("Mensaje recibido del topic 'Facturacion': {}",facturacion);
        Facturacion factura = new Gson().fromJson(facturacion,Facturacion.class);
        Cuentas cuentas = new Cuentas();
        cuentas.setCodCuentas(factura.getNumero());
        cuentas.setCodCliente(factura.getCliente().getCodigo());
        cuentas.setNombreCliente(factura.getCliente().getNombre());
        cuentas.setRucCliente(factura.getCliente().getRuc());
        cuentas.setFecha(factura.getFecha());
        cuentas.setEstado("Pendiente");
        cuentas.setTotalCobrar(factura.getTotal());
        cuentas.setTotalIGV(0.0);
        Set<Detalle> lista = new HashSet<>();
        for(Item it: factura.getLista()){
            Detalle det = new Detalle();
            det.setCantidad(it.getCantidad());
            det.setPrecio(it.getPrecio());
            det.setSubtotal(it.getCantidad()*it.getPrecio());
            det.setCodItem(it.getCodigo());
            det.setCuentas(cuentas);
            lista.add(det);
        }
        cuentas.setDetalles(lista);
        try{
            cuentasRepository.save(cuentas);
            log.info("Enviando respuesta al cliente ...");
            Confirmacion confirmacion = new Confirmacion("Su pedido ha sido ejecutado Correctamente","2");
            confirmacionProducer.send(confirmacion);
            websocketTemplate.convertAndSend("/topic2/in-memory", "Se ha generado un nuevo registro, actualice la pagina para ver los cambios");
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
