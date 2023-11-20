package BOOK4U;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class pHistorial extends JPanel {

    private JTextField dniField;
    private JTextField telefonoField;
    private JTextField correoField;
    private JTextField usuarioField;
    private JPasswordField contrasenaField;
    private JPasswordField repetirContrasenaField;

    public pHistorial() {
        setLayout(new GridBagLayout());
        setBackground(new Color(255, 210, 175)); // Cambiar el fondo a naranja clarito

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.fill = GridBagConstraints.HORIZONTAL;

        // Título similar al "código 2"
        JLabel lblTitulo = new JLabel("REGISTRO");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 70));
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        add(lblTitulo, constraints);

        // Configurar los JLabel con texto descriptivo
        JLabel dniLabel = new JLabel("DNI:");
        JLabel telefonoLabel = new JLabel("Teléfono:");
        JLabel correoLabel = new JLabel("Correo:");
        JLabel usuarioLabel = new JLabel("Usuario:");
        JLabel contrasenaLabel = new JLabel("Contraseña:");
        JLabel repetirContrasenaLabel = new JLabel("Repite Contraseña:");

        dniField = new JTextField(20);
        telefonoField = new JTextField(20);
        correoField = new JTextField(20);
        usuarioField = new JTextField(20);
        contrasenaField = new JPasswordField(20);
        repetirContrasenaField = new JPasswordField(20);

        // Botón similar al "código 2"
        constraints.gridx = 0;
        constraints.gridy = 1; // Asegura que los componentes de entrada estén debajo del título
        add(dniLabel, constraints);

        constraints.gridx = 1;
        add(dniField, constraints);

        constraints.gridx = 0;
        constraints.gridy++;
        add(telefonoLabel, constraints);

        constraints.gridx = 1;
        add(telefonoField, constraints);

        constraints.gridx = 0;
        constraints.gridy++;
        add(correoLabel, constraints);

        constraints.gridx = 1;
        add(correoField, constraints);

        constraints.gridx = 0;
        constraints.gridy++;
        add(usuarioLabel, constraints);

        constraints.gridx = 1;
        add(usuarioField, constraints);

        constraints.gridx = 0;
        constraints.gridy++;
        add(contrasenaLabel, constraints);

        constraints.gridx = 1;
        add(contrasenaField, constraints);
        
        constraints.gridx = 0;
        constraints.gridy++;
        add(repetirContrasenaLabel, constraints);

        constraints.gridx = 1;
        add(repetirContrasenaField, constraints);

        constraints.gridx = 0;
        constraints.gridy++;
        constraints.gridwidth = 2;
        JButton boton = new JButton("Registrarse");
        boton.setFont(new Font("Arial", Font.BOLD, 20));
        boton.setBackground(new Color(140, 150, 255));
        boton.setForeground(Color.BLACK);
        boton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lógica del botón similar al "código 2" aquí
                String dni = dniField.getText();
                String telefono = telefonoField.getText();
                String correo = correoField.getText();
                String usuario = usuarioField.getText();
                String contrasena = new String(contrasenaField.getPassword());
                String comprobarContrasena = new String(repetirContrasenaField.getPassword());

                // Realiza la lógica de registro o validación aquí
                // Por ejemplo, imprime los valores en la consola
                System.out.println("DNI: " + dni);
                System.out.println("Teléfono: " + telefono);
                System.out.println("Correo: " + correo);
                System.out.println("Usuario: " + usuario);
                System.out.println("Contraseña: " + contrasena);
                System.out.println("Repetir Contraseña: " + contrasena);
            }
        });//que coño pasa
        boton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        String dni = dniField.getText();
        String telefono = telefonoField.getText();
        String correo = correoField.getText();
        String usuario = usuarioField.getText();
        String contrasena = new String(contrasenaField.getPassword());
        String comprobarContrasena = new String(repetirContrasenaField.getPassword());

        // Llama al método de registro y verifica si la inserción fue exitosa
        if (pFunciones.registrarUsuario(dni, telefono, correo, usuario, contrasena) && comprobarContrasena.equals(contrasena)) {
            JOptionPane.showMessageDialog(null, "Usuario registrado con éxito");
            SwingUtilities.getWindowAncestor(pHistorial.this).dispose();
            SwingUtilities.invokeLater(() -> {
                pPrincipal frame = new pPrincipal();
                frame.setVisible(true);
            });
        } else {
            JOptionPane.showMessageDialog(null, "Error al registrar el usuario");
        }
    }
});
        add(boton, constraints);
    }

  /*  public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    } */

    public static void createAndShowGUI() {
        JFrame frame = new JFrame("BOOK4U");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pRegistro registroPanel = new pRegistro();

        // Configura el tamaño del JFrame y lo centra
        frame.setPreferredSize(new Dimension(800, 700));
        frame.getContentPane().add(registroPanel);
        frame.pack();
        frame.setLocationRelativeTo(null); // Centra la ventana en la pantalla
        frame.setVisible(true);
    }
}







