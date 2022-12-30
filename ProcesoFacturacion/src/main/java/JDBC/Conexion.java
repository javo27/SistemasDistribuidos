package JDBC;

import org.apache.kafka.common.protocol.types.Field;

import java.sql.*;

public class Conexion {

    static Connection connection;
    static String username = "root";
    static String password = "toor";
    static String bd = "db_facturacion";
    static String url = "jdbc:mysql://localhost:3306/"+bd+"?serverTimezone=UTC";


    public Conexion() {

    }

    public static Connection Conect() {

        try {
            connection = DriverManager.getConnection(url, username, password);
            //Statement  statement = connection.createStatement();

            //ResultSet resultSet = statement.executeQuery("");
            System.out.println("conexion exitosa");
            return connection;
        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
    }


}
