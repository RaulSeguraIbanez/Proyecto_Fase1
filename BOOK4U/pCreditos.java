package BOOK4U;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class pCreditos extends JFrame {

    private JButton convertirDineroButton;
    private JButton comprarCreditosButton;
    private JButton irAPrincipalButton;
    private JTextField dineroTextField;

    public pCreditos() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("CRÉDITOS");
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(100, 20, 100, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.setBackground(new Color(255, 210, 175));
      panel.setPreferredSize(new Dimension(800, 64));
        
        JPanel panelCentral = new JPanel(new GridBagLayout());
       
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelCentral.add(new JLabel("Cantidad de Dinero:"), gbc);

        gbc.gridx = 1;
        dineroTextField = new JTextField(10);
        panelCentral.add(dineroTextField, gbc);

        gbc.gridx = 2;
        convertirDineroButton = new JButton("Convertir a Créditos");
        panelCentral.add(convertirDineroButton, gbc);

        gbc.gridx = 3;
        comprarCreditosButton = new JButton("Comprar Créditos");
        panelCentral.add(comprarCreditosButton, gbc);

        convertirDineroButton.addActionListener(e -> convertirADolares());
        comprarCreditosButton.addActionListener(e -> comprarCreditos());

        add(panelCentral, BorderLayout.CENTER);

        // Botón para ir a la ventana principal
        irAPrincipalButton = new JButton("Ir a Principal");
        irAPrincipalButton.addActionListener(e -> abrirVentanaPrincipal());
        add(irAPrincipalButton, BorderLayout.SOUTH);
        add(panelCentral);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void comprarCreditos() {
        try {
            String cantidadDineroStr = dineroTextField.getText();
            double cantidadDinero = Double.parseDouble(cantidadDineroStr);
            String numeroTarjeta = JOptionPane.showInputDialog("Ingresa tu número de tarjeta de crédito:");
            
            if (isValidCreditCardNumber(numeroTarjeta)) {
                double creditosComprados = cantidadDinero;
                JOptionPane.showMessageDialog(this, "Compra exitosa. Créditos comprados: " + creditosComprados);
            } else {
                JOptionPane.showMessageDialog(this, "Número de tarjeta de crédito no válido. Por favor, verifica el número.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, ingresa una cantidad válida de dinero.");
        }
    }
    public void actualizarCreditosEnBD(double creditosComprados) throws SQLException {
        // Configuración de la conexión a la base de datos
    	 String USER = "23_24_DAM2_EHHMMM";
		 String PWD = "ehhmmm_123";
		 String URL = "jdbc:oracle:thin:@192.168.3.26:1521:xe"; 

         try (Connection connection = DriverManager.getConnection(URL, USER, PWD)) {
               // Query para actualizar los créditos en la tabla usuario
               String updateQuery = "UPDATE usuario SET creditos = creditos + ? WHERE dni = ?";
    
                    try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
        preparedStatement.setDouble(1, creditosComprados);
        String dniUsuario = obtenerNombreDeUsuario(); // Debes implementar esta función para obtener el DNI del usuario
        preparedStatement.setString(2, dniUsuario);
        preparedStatement.executeUpdate();
           } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error en la conexión a la base de datos.");
     }
}


        }



    private String obtenerNombreDeUsuario() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isValidCreditCardNumber(String numeroTarjeta) {
        if (!Pattern.matches("\\d{16}", numeroTarjeta)) {
            return false;
        }
        int sum = 0;
        boolean alternate = false;
        char[] digits = numeroTarjeta.toCharArray();
        for (int i = digits.length - 1; i >= 0; i--) {
            int digit = Character.getNumericValue(digits[i]);
            if (alternate) {
                digit *= 2;
                if (digit > 9) {
                    digit -= 9;
                }
            }
            sum += digit;
            alternate = !alternate;
        }
        return sum % 10 == 0;
    }

    public void convertirADolares() {
        try {
            String cantidadDineroStr = dineroTextField.getText();
            double cantidadDinero = Double.parseDouble(cantidadDineroStr);
            double factorConversion = 10;
            double creditos = cantidadDinero * factorConversion;
            JOptionPane.showMessageDialog(this, "Convertido a créditos: " + creditos);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, ingresa una cantidad válida de dinero.");
        }
    }

    public void abrirVentanaPrincipal() {
      
    	pMenuPrincipal ventanaPrincipal = new pMenuPrincipal();
        ventanaPrincipal.setVisible(true);

        dispose();
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(pCreditos::new);
    }
}
