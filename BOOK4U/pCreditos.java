package BOOK4U;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.regex.Pattern;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

    public void calcularResultado() {
        try {
            String input = textField.getText();
            double numero = Double.parseDouble(input);
            double resultado = numero / 10;
            label.setText("CRÉDITOS: " + resultado);
        } catch (NumberFormatException e) {
            label.setText("¡Ingresa un número válido!");
        }
    }

    private void comprarCreditos() {
        try {
            String numeroTarjeta = JOptionPane.showInputDialog("Ingresa tu número de tarjeta de crédito:");

            if (isValidCreditCardNumber(numeroTarjeta)) {
                double creditosComprados = Double.parseDouble(JOptionPane.showInputDialog("Ingresa la cantidad de créditos a comprar:"));
              
                 // Actualizar el área de texto
                JOptionPane.showMessageDialog(this, "Compra exitosa. Créditos añadidos a tu cuenta.");
            } else {
                JOptionPane.showMessageDialog(this, "Número de tarjeta de crédito no válido. Por favor, verifica el número.");
            }
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