package Producer;

import Entidades.Orden;
import com.google.gson.Gson;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class Productor {

    private static final Logger log = LoggerFactory.getLogger(Productor.class.getSimpleName());



    public  void Producir(String ordenEnviar) {

        //Crear productor propiedades
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"127.0.0.1:9092");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        //crear el productor
        KafkaProducer<String,String> producer =
                new KafkaProducer<>(properties);

        //Crear record de productor




        Gson gson = new Gson();
        Orden orden = gson.fromJson(ordenEnviar, Orden.class);
        orden.setNumero("FA"+orden.getNumero());
        String json = gson.toJson(orden);

        ProducerRecord<String,String  > producerRecord =
                new ProducerRecord<>("cuentas",json);

        //enviar data - asincronico
        producer.send(producerRecord);

        //vaciar y cerrar el productor - sincronico
        //producer.flush();
        producer.close();
        //

    }

}
