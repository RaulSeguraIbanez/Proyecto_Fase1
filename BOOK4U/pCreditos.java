package BOOK4U;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class pCreditos extends JFrame {
	
	private static final String USER = "23_24_DAM2_EHHMMM";
	private static final String PWD = "ehhmmm_123";
	private static final String URL = "jdbc:oracle:thin:@192.168.3.26:1521:xe"; 
	
	 public static int creditosUsuario = 0;

    private JTextField dineroTextField;
    private JTextField tarjetaTextField;
    private JTextField fechaTextField;
    private JTextField ccvTextField ;
    private JLabel creditosLabel;
    private JLabel imagenUsuarioLabel;

    private double saldo = 0;
    private String fechaCompra = "";

    public pCreditos() {
       
        setTitle("Compra de Créditos");
        setSize(800, 600);
        setBackground(new Color(255, 210, 175));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
       
        JPanel formularioPanel = new JPanel();
        formularioPanel.setLayout(new BoxLayout(formularioPanel, BoxLayout.Y_AXIS));

        JLabel dineroLabel = new JLabel("Introduce la cantidad de dinero:");
        dineroTextField = new JTextField(10);

        JLabel tarjetaLabel = new JLabel("Número de tarjeta:");
        tarjetaTextField = new JTextField(16);

        JLabel fechaLabel = new JLabel("Fecha (MM/YY):");
        fechaTextField = new JTextField(5);

        JLabel ccvLabel = new JLabel("CCV:");
        ccvTextField = new JTextField(3);

        JButton comprarButton = new JButton("Comprar Créditos");
        
        JButton menuPrincipalButton = new JButton("Menú Principal");
        formularioPanel.add(menuPrincipalButton);

        creditosLabel = new JLabel("Créditos disponibles: " + saldo + " - Fecha de compra: " + fechaCompra);
        creditosLabel.setForeground(Color.blue);

        formularioPanel.add(dineroLabel);
        formularioPanel.add(dineroTextField);
        formularioPanel.add(tarjetaLabel);
        formularioPanel.add(tarjetaTextField);
        formularioPanel.add(fechaLabel);
        formularioPanel.add(fechaTextField);
        formularioPanel.add(ccvLabel);
        formularioPanel.add(ccvTextField);
        formularioPanel.add(comprarButton);
        formularioPanel.add(creditosLabel);

        // Agregar imagen de usuario
        ImageIcon imagenUsuario = new ImageIcon(pFunciones.fotoUsuario); // Cambia "ruta_de_la_imagen.png" por la ruta real de la imagen
        imagenUsuarioLabel = new JLabel(imagenUsuario);

        // Alinear la imagen al centro y agregar borde
        JPanel imagenPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        imagenPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        imagenPanel.add(imagenUsuarioLabel);

        // Añadir componentes a la ventana
        add(formularioPanel, BorderLayout.CENTER);
        add(imagenPanel, BorderLayout.NORTH);

        // Configurar el ActionListener para el botón de comprar
        comprarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                comprarCreditos();
            }
        });
        
        menuPrincipalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirMenuPrincipal();
            }
        });
    }

    protected void abrirMenuPrincipal() {
    	 this.setVisible(false);
    	    pMenuPrincipal menuPrincipal = new pMenuPrincipal();
    	    menuPrincipal.setVisible(true);
		
	}

    private void comprarCreditos() {
        // Obtener la cantidad de dinero, número de tarjeta, fecha y CCV
        try {
            double cantidadDinero = Double.parseDouble(dineroTextField.getText());
            String numeroTarjeta = tarjetaTextField.getText();
            String fecha = fechaTextField.getText();
            String ccv = ccvTextField.getText();

          
            if (!isValidCreditCard(numeroTarjeta) && !isValidExpirationDate(fecha) && !isValidCCV(ccv)) {
                JOptionPane.showMessageDialog(this, "Error: Datos de tarjeta de crédito no válidos.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }


            double nuevosCreditos = cantidadDinero / 10;
            saldo += nuevosCreditos;
            fechaCompra = obtenerFechaActual();

            // Mostrar los créditos actualizados y la fecha de compra
            creditosLabel.setText("Créditos disponibles: " + saldo + " - Fecha de compra: " + fechaCompra);

            // Limpiar los campos de entrada
            dineroTextField.setText("");
            tarjetaTextField.setText("");
            fechaTextField.setText("");
            ccvTextField.setText("");

            JOptionPane.showMessageDialog(this, "Compra exitosa. Créditos actualizados.");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Error: Ingresa una cantidad de dinero válida.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean isValidExpirationDate(String expirationDate) {
        // La fecha de expiración debe tener el formato MM/YY
        // donde MM es el mes (01-12) y YY es el año (actual o futuro)
        if (expirationDate.matches("\\d{2}/\\d{2}")) {
            // Obtener el mes y el año de la fecha de expiración
            int month = Integer.parseInt(expirationDate.substring(0, 2));
            int year = Integer.parseInt(expirationDate.substring(3));

            // Validar que el mes esté en el rango válido (01-12) y el año sea actual o futuro
            return (month >= 1 && month <= 12) && (year >= getCurrentYear());
        }

        return false;
    }

    private int getCurrentYear() {
        // Obtiene el año actual del sistema utilizando la clase Calendar
        // Nota: Calendar.MONTH devuelve valores de 0 a 11, por lo que se suma 1 para obtener el mes actual
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        return calendar.get(java.util.Calendar.YEAR);
    }


	private boolean isValidCCV(String ccv) {
     
        return ccv.matches("\\d{3}");
    }
	
    private boolean isValidCreditCard(String creditCardNumber) {
        creditCardNumber = creditCardNumber.replaceAll("\\16", "");
        if (creditCardNumber.length() != 16) {
            
        	 
        	return false;
        	    }

        int sum = 0;
        boolean alternate = false;
        for (int i = creditCardNumber.length() - 1; i >= 0; i--) {
            int digit = Character.getNumericValue(creditCardNumber.charAt(i));

            if (alternate) {
                digit *= 2;
                if (digit > 9) {
                    digit -= 9;
                }
            }
            sum += digit;
            alternate = !alternate;
        }
        return (sum % 10 == 0);
    }

    private String obtenerFechaActual() {
    	
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date fechaActual = new Date();
        return formatoFecha.format(fechaActual);
    }
    
    private boolean guardarEnBaseDeDatos(double cantidadDinero, String fechaCompra) {
        try {
            // Establecer la conexión con la base de datos
            Connection connection = DriverManager.getConnection(URL, USER, PWD);

            // Consulta para insertar datos en la tabla de transacciones (ajusta según tu esquema)
            String sql = "INSERT INTO usuario (DNI, TELEFONO, EMAIL, NOMBRE, PASSWORD, CREDITOS, FOTOPERFIL) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, creditosUsuario);
               
                int filasAfectadas = statement.executeUpdate();
                return filasAfectadas<1 ;
            }

        
            // Cerrar la conexión
          
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al guardar en la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }
    
    public static boolean comprobarcreditos(int creditos) {
	    try (Connection connection = DriverManager.getConnection(URL, USER, PWD)) {
	        String query = "SELECT creditos FROM usuario WHERE dni = ? ";
	        PreparedStatement statement = connection.prepareStatement(query);
	        statement.setString(1, pFunciones.dniUsuario);
	       
	        ResultSet resultSet = statement.executeQuery();

	        if (resultSet.next()) {
	        	
	        	pFunciones.creditosUsuario = resultSet.getInt("CREDITOS");
	        	
	            
	            System.out.println("Inicio de sesión exitoso para el usuario: " + creditosUsuario);
	            
	            return true; // Usuario y contraseña coinciden
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false; // Usuario y contraseña no coinciden
	}
	
	


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new pCreditos().setVisible(true);
            }
        });
    }
}
