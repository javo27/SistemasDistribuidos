package Consumer;

import JDBC.Conexion;
import JDBC.OperacionBD;
import Producer.Productor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class Consumidor {

    private static final Logger log = LoggerFactory.getLogger(Productor.class.getSimpleName());


    //static ConsumerRecords<String , String > records;

    public Consumidor() {
    }

    //static ConsumerRecords<String , String > records;

    /*public ConsumerRecords<String, String> getRecords() {
        return records;
    }*/



    public static void consumer()  throws InterruptedException {

        String groupId = "group_id";
        String topic = "facturacion";

        OperacionBD operacionBD= new OperacionBD();

        Productor productor = new Productor();

        //log.info("alo");
        //Crear consumidor propiedades
        Properties properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"127.0.0.1:9092");
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG,groupId);
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"latest");

        //Crear consumidor
        KafkaConsumer<String , String > consumer = new KafkaConsumer<>(properties);



        try{
            //subscribirse a un topic

            consumer.subscribe(Collections.singletonList(topic));

            //sondear la data

            while(true){
                System.out.println("Esperando");
                //log.info("Esperando");
                ConsumerRecords<String , String > records = consumer.poll(Duration.ofMillis(900));

                for (ConsumerRecord<String, String> record : records) {
                    System.out.println("Key: " + record.key() + ", Value: " + record.value());
                    System.out.println("Partition: " + record.partition() + ", Offset:" + record.offset());
                    operacionBD.ActualizarBD(record);
                    productor.Producir(record.value());
                }

            }
        }catch (WakeupException e){
            System.out.println(e.getMessage());
            log.info("error de activacion");

        }catch (Exception e){
            System.out.println(e.getMessage());
            log.error("Excepcion inesperada");
        }finally {
            //consumer.close(); // this will also commit the offsets if need be.
            //log.info("CERRANDO CONSUMIDOR");
        }



    }

}
