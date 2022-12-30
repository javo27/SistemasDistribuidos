package javdev.procesarorden.consumer;

import javdev.procesarorden.entities.Confirmacion;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
@Slf4j
@Component
public class ResultadoConsumer {
    @Autowired
    SimpMessagingTemplate websocketTemplate;
    @KafkaListener(
            topics = "${topic.name.resultado}",
            groupId = "groud_id",
            containerFactory = "confirmacionKafkaListenerContainerFactory"
    )
    public void consume(Confirmacion confirmacion) {
        log.info("Resultado recibido: {}",confirmacion.getMensaje());
        log.info("Enviando respuesta al cliente ...");
        websocketTemplate.convertAndSend("/topic/in-memory", confirmacion);
    }
}
