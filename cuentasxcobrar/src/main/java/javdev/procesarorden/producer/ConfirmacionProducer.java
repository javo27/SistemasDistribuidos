package javdev.procesarorden.producer;

import javdev.procesarorden.entities.Confirmacion;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
@Slf4j
@Service
@RequiredArgsConstructor
public class ConfirmacionProducer {
    @Value("${topic.name.resultado}")
    private String topicName;
    private final KafkaTemplate<String, Confirmacion> confirmacionkafkaTemplate;
    public void send(Confirmacion confirmacion){
        confirmacionkafkaTemplate.send(topicName,confirmacion);
    }
}
