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
    private JLabel fotoLabel;

    private static final String USER = "23_24_DAM2_EHHMMM";
    private static final String PWD = "ehhmmm_123";
    private static final String URL = "jdbc:oracle:thin:@192.168.3.26:1521:xe";

    public pCreditos() {
    	 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Compra de Créditos");
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(800, 600));
     // Crear el panelCentral con un nuevo color de fondo naranja
        JPanel panelCentral = new JPanel(new GridBagLayout());
        panelCentral.setBackground(new Color(255, 210, 175)); // RGB para naranja
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
       // gbc.gridwidth = 0;
        fotoLabel = new JLabel(new ImageIcon("src/imagenes/FotoPerfil.png")); // Reemplaza con la ruta correcta de tu imagen
        panelCentral.add(fotoLabel, gbc);
      
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelCentral.add(new JLabel("Cantidad de Dinero:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
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

        gbc.gridx = 3;
        irAPrincipalButton = new JButton("Ir a Principal");
        irAPrincipalButton.addActionListener(e -> abrirVentanaPrincipal());
        add(irAPrincipalButton, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public double convertirADolares(double cantidad) {
        double factorConversion = 10;
        return cantidad * factorConversion;
    }

    public boolean actualizarCreditosEnBD(double creditosComprados) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PWD)) {
            String updateQuery = "UPDATE usuario SET creditos = creditos + ? WHERE dni = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                preparedStatement.setDouble(1, creditosComprados);
                String dniUsuario = obtenerDniDeUsuario(); // Reemplaza con la lógica para obtener el DNI del usuario
                preparedStatement.setString(2, dniUsuario);
                int filasAfectadas = preparedStatement.executeUpdate();

                return filasAfectadas > 0; // Verifica si se afectó al menos una fila (actualización exitosa)
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error en la conexión a la base de datos.");
            return false; // Error en la conexión a la base de datos
        }
    }

    public String obtenerDniDeUsuario() {
       
        return dineroTextField.getText();
    }


    public boolean isValidCreditCardNumber(String numeroTarjeta) {
        if (!Pattern.matches("\\{16}", numeroTarjeta)) {
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
            double creditos = convertirADolares(cantidadDinero);
            JOptionPane.showMessageDialog(this, "Convertido a créditos: " + creditos);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, ingresa una cantidad válida de dinero.");
        }
    }

    public void comprarCreditos() {
        try {
            String cantidadDineroStr = dineroTextField.getText();
            double cantidadDinero = Double.parseDouble(cantidadDineroStr);
            String numeroTarjeta = JOptionPane.showInputDialog("Ingresa tu número de tarjeta de crédito:");

            if (isValidCreditCardNumber(numeroTarjeta)) {
                double creditosComprados = convertirADolares(cantidadDinero);

                // Aquí puedes agregar la lógica para actualizar la base de datos
                if (actualizarCreditosEnBD(creditosComprados)) {
                    JOptionPane.showMessageDialog(this, "Compra exitosa. Créditos comprados: " + creditosComprados);
                } else {
                    JOptionPane.showMessageDialog(this, "Error al actualizar la base de datos. Por favor, inténtalo de nuevo.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Número de tarjeta de crédito no válido. Por favor, verifica el número.");
            }
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
