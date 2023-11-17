package BOOK4U;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class CalculoDias {

    private static final String USER = "23_24_DAM2_EHHMMM";
    private static final String PWD = "ehhmmm_123";
    private static final String URL = "jdbc:oracle:thin:@192.168.3.26:1521:xe";

    public static String calcularReserva(Date fechaInicio, Date fechaFin, int precioPorDia, int estanciaId) {
        // Calcula la cantidad de días entre las dos fechas
        long diferenciaEnMillis = fechaFin.getTime() - fechaInicio.getTime();
        long diasDiferencia = TimeUnit.DAYS.convert(diferenciaEnMillis, TimeUnit.MILLISECONDS);

        // Calcula el precio total
        int precioTotal = precioPorDia * (int) diasDiferencia;

        // Muestra los resultados en la consola (puedes ajustar esto según tus necesidades)
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String mensaje = "Reserva calculada:\n" +
                "Fecha de inicio: " + sdf.format(fechaInicio) + "\n" +
                "Fecha de fin: " + sdf.format(fechaFin) + "\n" +
                "Días de diferencia: " + diasDiferencia + "\n" +
                "Precio por día: " + precioPorDia + "€\n" +
                "Precio total: " + precioTotal + "€";

        // Imprime los datos antes de insertar en la base de datos
        System.out.println("Datos para INSERT:\n" +
                "DNI del usuario: " + pFunciones.dniUsuario + "\n" +
                "ID de estancia: " + estanciaId + "\n" +
                "Precio total: " + precioTotal);

        // Inserta los datos en la tabla Reservas
        insertarDatosReserva(estanciaId, precioTotal, fechaInicio, fechaFin);

        return mensaje;
    }

    public static String realizarReserva(Date fechaInicio, Date fechaFin, int precioPorDia, int estanciaId) {
        long diferenciaEnMillis = fechaFin.getTime() - fechaInicio.getTime();
        long diasDiferencia = TimeUnit.DAYS.convert(diferenciaEnMillis, TimeUnit.MILLISECONDS);
        int precioTotal = precioPorDia * (int) diasDiferencia;

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        // Verifica si el usuario tiene suficientes créditos
        if (verificarCreditosSuficientes(pFunciones.dniUsuario, precioTotal)) {
            System.out.println("Datos para INSERT:\n" +
                    "DNI del usuario: " + pFunciones.dniUsuario + "\n" +
                    "ID de estancia: " + estanciaId + "\n" +
                    "Precio total: " + precioTotal);

            // Inserta los datos en la tabla Reservas
            insertarDatosReserva(estanciaId, precioTotal, fechaInicio, fechaFin);

            // Resta los créditos al usuario
            restarCreditosUsuario(pFunciones.dniUsuario, precioTotal);

            return "Reserva realizada:\n" +
                    "Fecha de inicio: " + sdf.format(fechaInicio) + "\n" +
                    "Fecha de fin: " + sdf.format(fechaFin) + "\n" +
                    "Días de diferencia: " + diasDiferencia + "\n" +
                    "Precio por día: " + precioPorDia + "€\n" +
                    "Precio total: " + precioTotal + "€";
        } else {
            return "No tienes suficientes créditos para realizar la reserva.";
        }
    }

    private static boolean verificarCreditosSuficientes(String dniUsuario, int precioTotal) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PWD)) {
            String query = "SELECT CREDITOS FROM USUARIOS WHERE DNI = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, dniUsuario);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    int creditosUsuario = resultSet.getInt("CREDITOS");
                    return creditosUsuario >= precioTotal;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    private static void restarCreditosUsuario(String dniUsuario, int precioTotal) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PWD)) {
            String query = "UPDATE USUARIOS SET CREDITOS = CREDITOS - ? WHERE DNI = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, precioTotal);
                statement.setString(2, dniUsuario);
                statement.executeUpdate();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private static void insertarDatosReserva(int estanciaId, int precioTotal, Date fechaInicio, Date fechaFin) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PWD)) {
            String query = "INSERT INTO Reservas (CREDITOS, ID_ESTANCIA, DNI, FECHAINICIO, FECHAFIN) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, precioTotal);
                statement.setInt(2, estanciaId);
                statement.setString(3, pFunciones.dniUsuario);
                statement.setDate(4, new java.sql.Date(fechaInicio.getTime()));
                statement.setDate(5, new java.sql.Date(fechaFin.getTime()));
                statement.executeUpdate();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
