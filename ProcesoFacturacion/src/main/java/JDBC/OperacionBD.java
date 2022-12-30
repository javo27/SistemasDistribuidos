package JDBC;

import Consumer.Consumidor;
import Entidades.Item;
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


        try {
            PreparedStatement preparedStatement = con.prepareStatement(" INSERT INTO facturacion (idFacturacion,NumeroFacturacion,CodigoCliente,NombreCliente,RUC_Cliente,Total_IGV) VALUES  (?,?,?,?,?,?)  ");
            preparedStatement.setString(1, "FA" + mensaje.getNumero());
            preparedStatement.setString(2, mensaje.getNumero());
            preparedStatement.setString(3, String.valueOf(mensaje.getCliente().getCodigo()));
            preparedStatement.setString(4, mensaje.getCliente().getNombre());
            preparedStatement.setString(5, mensaje.getCliente().getRuc());
            preparedStatement.setDouble(6, mensaje.getTotal());
            preparedStatement.executeUpdate();

            for (Item item : mensaje.getLista()) {
                PreparedStatement preparedStatement1 = con.prepareStatement(" INSERT INTO items (idITEMS,Cantidad,PrecioUnitario,SubTotal,Descripcion,Facturacion_idFacturacion) VALUES  (?,?,?,?,?,?)  ");
                preparedStatement1.setString(1, "FA" + item.getCodigo());
                preparedStatement1.setInt(2, item.getCantidad());
                preparedStatement1.setDouble(3, item.getPrecio());
                preparedStatement1.setDouble(4, item.subTotal());
                preparedStatement1.setString(5, item.getNombre());
                preparedStatement1.setString(6, "FA" + mensaje.getNumero());
                preparedStatement1.executeUpdate();

            }
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
