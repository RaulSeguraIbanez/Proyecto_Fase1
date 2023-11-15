package BOOK4U;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class pCreditos extends JFrame {

    private JTextField textField;
    private JButton obtenerButton;
    private JButton comprarButton;
    private JButton verCreditosButton;
    private JButton menuPrincipal;
    private JButton inicioButton;
    private JLabel label;
    private JButton irAPrincipalButton;
    private JTextArea creditosTextArea; // Área de texto para mostrar los créditos

	private static final String USER = "23_24_DAM2_EHHMMM";
	private static final String PWD = "ehhmmm_123";
	private static final String URL = "jdbc:oracle:thin:@192.168.3.26:1521:xe"; 

    public pCreditos() {
        

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("CRÉDITOS");
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(100, 20, 100, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.setBackground(new Color(255, 210, 175));
      //  panel.setPreferredSize(new Dimension(800, 64));

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("INTRODUCE UN NÚMERO:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0;
        textField = new JTextField(10);
        panel.add(textField, gbc);

        gbc.gridx = 2;
        gbc.weightx = 2;
        obtenerButton = new JButton("OBTENER");
        obtenerButton.addActionListener(e -> calcularResultado());
        panel.add(obtenerButton, gbc);

        gbc.gridx = 10;
        gbc.gridwidth = 0;
        gbc.gridheight = 1;
        label = new JLabel(" TUS CRÉDITOS:");
        panel.add(label, gbc);

        gbc.gridy = 2;
        gbc.gridx = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        comprarButton = new JButton("Comprar Créditos");
        comprarButton.addActionListener(e -> comprarCreditos());
        panel.add(comprarButton, gbc);

       

        inicioButton = new JButton("IR AL MENÚ PRINCIPAL");
        inicioButton.addActionListener(e -> JOptionPane.showMessageDialog(pCreditos.this, " pMisReservas"));
        panel.add(inicioButton, gbc);

        gbc.gridy = 4;
        gbc.gridwidth = 1;
        irAPrincipalButton = new JButton("IR A MENÚ PRINCIPAL");
        irAPrincipalButton.addActionListener(e -> {
        	 pMenuPrincipal menuPrincipal = new pMenuPrincipal();
        	    menuPrincipal.setVisible(true);
        	    dispose(); // Cierra la ventana actual (pCreditos)
        	});
        panel.add(irAPrincipalButton, gbc);

        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.gridheight = 2;

        creditosTextArea = new JTextArea(5, 20); // Área de texto para mostrar créditos
        creditosTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(creditosTextArea);
        panel.add(scrollPane, gbc);

        add(panel);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public double calcularResultado() {
        try {
            String input = textField.getText();
            double numero = Double.parseDouble(input);
            double resultado = numero / 10;
            label.setText("CRÉDITOS: " + resultado);
        } catch (NumberFormatException e) {
            label.setText("¡Ingresa un número válido!");
        }
		return 0;
    }

    private void comprarCreditos() {
        try {
            String numeroTarjeta = JOptionPane.showInputDialog("Ingresa tu número de tarjeta de crédito:");

            if (isValidCreditCardNumber(numeroTarjeta)) {
                // Database connection and update
                try (Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@192.168.3.26:1521:xe", "23_24_DAM2_EHHMMM", "ehhmmm_123")) {
                    String updateQuery = "UPDATE usuarios SET creditos = creditos + ? WHERE username = ?";
                    try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                        // Assuming 'username' is a column in your 'usuarios' table
                        preparedStatement.setDouble(1, calcularResultado());
                        preparedStatement.setString(2, "username_here"); // Replace with the actual username
                        preparedStatement.executeUpdate();
                    }
                }

                JOptionPane.showMessageDialog(this, "Compra exitosa. Créditos añadidos a tu cuenta.");
            } else {
                JOptionPane.showMessageDialog(this, "Número de tarjeta de crédito no válido. Por favor, verifica el número.");
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
            JOptionPane.showMessageDialog(this, "Error en la conexión a la base de datos.");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Número de tarjeta de crédito no válido. Por favor, verifica el número.");
        }
    }

   

  

    private boolean isValidCreditCardNumber(String cardNumber) {
        return Pattern.matches("\\d{16}", cardNumber);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(pCreditos::new);
    }
}
//