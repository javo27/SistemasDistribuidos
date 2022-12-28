package JDBC;

import Consumer.Consumidor;
import Entidades.Orden;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.lang.reflect.Type;
import java.sql.*;

public class OperacionBD {

    public OperacionBD() {
    }

    public void ActualizarBD(ConsumerRecord<String , String > record) throws InterruptedException {


        Connection con = Conexion.Conect();

        Gson gson= new Gson();
        Orden mensaje = gson.fromJson(record.value(), Orden.class);


        try{
            //Statement statement = con.createStatement();
            //ResultSet resultSet =
            PreparedStatement preparedStatement = con.prepareStatement(" CALL INSERTAR  (?,?,?,?)  ");
            //preparedStatement.setInt(1,2);
            preparedStatement.setString(1,"FA"+mensaje.getCliente().getRuc());
            preparedStatement.setString(2, String.valueOf(mensaje.getTotal()));
            preparedStatement.setString(3,mensaje.getCliente().getNombre());
            preparedStatement.setString(4,mensaje.getFecha());
            preparedStatement.executeUpdate();

            System.out.println("BD actualizado");

        }catch (SQLException e){
            System.out.println("no se pudo actualizar");
        }finally {
            try{
                if(con!=null){
                    con.close();
                }
            }catch (SQLException E){

            }
        }



    }

     






}
