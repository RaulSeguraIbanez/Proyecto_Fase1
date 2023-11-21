package BOOK4U;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

		public class pFunciones {
			
			 public static String nombreUsuario = "";
			    public static String emailUsuario = "";
			    public static String passwordUsuario = "";
			    public static String fotoUsuario = "";
			    public static int creditosUsuario = 0;
			    public static String dniUsuario = ""; // Agregar esta línea
			    public static String telefonoUsuario = ""; // Agregar esta línea
			protected static boolean comprobadoS;
			
			private static final String USER = "23_24_DAM2_EHHMMM";
			private static final String PWD = "ehhmmm_123";
			private static final String URL = "jdbc:oracle:thin:@192.168.3.26:1521:xe"; 
			// Si estáis desde casa, la url será oracle.ilerna.com y no 192.168.3.26 //
			
			public static boolean comprobarLogin(String email, String password) {
			    try (Connection connection = DriverManager.getConnection(URL, USER, PWD)) {
			        String query = "SELECT * FROM usuario WHERE EMAIL = ? AND PASSWORD = ?";
			        PreparedStatement statement = connection.prepareStatement(query);
			        statement.setString(1, email);
			        statement.setString(2, password);
			        ResultSet resultSet = statement.executeQuery();

			        if (resultSet.next()) {
			        	pFunciones.nombreUsuario = resultSet.getString("NOMBRE");
			        	pFunciones.emailUsuario = resultSet.getString("EMAIL");
			        	pFunciones.passwordUsuario = resultSet.getString("PASSWORD");
			        	pFunciones.fotoUsuario = resultSet.getString("FOTOPERFIL");
			        	pFunciones.creditosUsuario = resultSet.getInt("CREDITOS");
			        	pFunciones.dniUsuario = resultSet.getString("DNI"); 
			        	pFunciones.telefonoUsuario = resultSet.getString("TELEFONO"); 
			            
			            System.out.println("Inicio de sesión exitoso para el usuario: " + emailUsuario);
			            
			            return true; // Usuario y contraseña coinciden
			        }
			    } catch (SQLException e) {
			        e.printStackTrace();
			    }
			    return false; // Usuario y contraseña no coinciden
			}
			
			public static boolean comprobarRegistro() {
				
				return false;
			}
			

public static boolean registrarUsuario(String dni, String telefono, String correo, String usuario, String contrasena) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PWD);

            String sql = "INSERT INTO usuario (DNI, TELEFONO, EMAIL, NOMBRE, PASSWORD, CREDITOS, FOTOPERFIL) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, dni);
                statement.setString(2, telefono);
                statement.setString(3, correo);
                statement.setString(4, usuario);
                statement.setString(5, contrasena);
                statement.setInt(6, 0);
                statement.setString(7, "src/imagenes/FotoPerfil.png");

                int filasAfectadas = statement.executeUpdate();
                return filasAfectadas > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }
}
