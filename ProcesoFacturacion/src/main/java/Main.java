import Consumer.Consumidor;
import JDBC.Conexion;
import JDBC.OperacionBD;
import org.apache.kafka.clients.consumer.ConsumerRecord;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        //OperacionBD operacionBD= new OperacionBD();
       // operacionBD.ActualizarBD();
        Consumidor  consumidor = new Consumidor();
        consumidor.consumer();

    }
}
