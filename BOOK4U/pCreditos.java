package BOOK4U;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class pCreditos extends JFrame {

    private static final String USER = "23_24_DAM2_EHHMMM";
    private static final String PWD = "ehhmmm_123";
    private static final String URL = "jdbc:oracle:thin:@192.168.3.26:1521:xe";

    private JTextField dineroTextField;
    private JTextField tarjetaTextField;
    private JTextField fechaTextField;
    private JTextField ccvTextField;
    private JLabel creditosLabel;
    private JLabel imagenUsuarioLabel;

    private int saldo = pFunciones.creditosUsuario;
    private String fechaCompra = "";

    public pCreditos() {

        setTitle("Compra de Créditos");
        setSize(800, 700);
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

        creditosLabel = new JLabel("Créditos disponibles: " + pFunciones.creditosUsuario + " - Fecha de compra: " + fechaCompra);
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

        ImageIcon imagenUsuario = new ImageIcon("src\\imagenes\\FotoPerfil.png");
        imagenUsuarioLabel = new JLabel(imagenUsuario);

        JPanel imagenPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        imagenPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        imagenPanel.add(imagenUsuarioLabel);

        add(formularioPanel, BorderLayout.CENTER);
        add(imagenPanel, BorderLayout.NORTH);

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
        try {
            int cantidadDinero = Integer.parseInt(dineroTextField.getText());
            System.out.println("Cantidad de dinero ingresada: " + cantidadDinero); // Agrega esta línea para depurar

            String numeroTarjeta = tarjetaTextField.getText();
            String fecha = fechaTextField.getText();
            String ccv = ccvTextField.getText();

            if (!isValidCreditCard(numeroTarjeta) && !isValidExpirationDate(fecha) && !isValidCCV(ccv)) {
                JOptionPane.showMessageDialog(this, "Error: Datos de tarjeta de crédito no válidos.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int nuevosCreditos = cantidadDinero;
            System.out.println("Nuevos créditos calculados: " + nuevosCreditos); // Agrega esta línea para depurar

            pFunciones.creditosUsuario += nuevosCreditos;
            saldo = pFunciones.creditosUsuario;
            fechaCompra = obtenerFechaActual();

            creditosLabel.setText("Créditos disponibles: " + saldo + " - Fecha de compra: " + fechaCompra);

            dineroTextField.setText("");
            tarjetaTextField.setText("");
            fechaTextField.setText("");
            ccvTextField.setText("");

            if (guardarEnBaseDeDatos(pFunciones.dniUsuario, saldo)) {
                JOptionPane.showMessageDialog(this, "Compra exitosa. Créditos actualizados.");
            } else {
                JOptionPane.showMessageDialog(this, "Error al actualizar la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Error: Ingresa una cantidad de dinero válida.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }



    private boolean guardarEnBaseDeDatos(String dni, double creditos) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PWD)) {
            String sql = "UPDATE usuario SET CREDITOS = ? WHERE dni = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setDouble(1, creditos);
                statement.setString(2, dni);

                int filasAfectadas = statement.executeUpdate();
                return filasAfectadas > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al actualizar la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    private boolean isValidExpirationDate(String expirationDate) {
        if (expirationDate.matches("\\d{2}/\\d{2}")) {
            int month = Integer.parseInt(expirationDate.substring(0, 2));
            int year = Integer.parseInt(expirationDate.substring(3));

            return (month >= 1 && month <= 12) && (year >= getCurrentYear());
        }

        return false;
    }

    private int getCurrentYear() {
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new pCreditos().setVisible(true);
            }
        });
    }
}

