package BOOK4U;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.*;

public class pPerfil extends JFrame {

    private JLabel dniLabel, telefonoLabel, emailLabel, nombreLabel, creditosLabel, fotoPerfilLabel;
    private JTextField telefonoTextField, emailTextField, nombreTextField, creditosTextField;
    private JButton cambiarTelefonoButton, cambiarEmailButton, cambiarNombreButton, cambiarCreditosButton;
    private JLabel fotoPerfilImageLabel;
    private JButton perfilButton;
    private JButton fotoPerfilButton;
    
    private static final String USER = "23_24_DAM2_EHHMMM";
    private static final String PWD = "ehhmmm_123";
    private static final String URL = "jdbc:oracle:thin:@192.168.3.26:1521:xe";

    private String dniUsuario; // Variable para almacenar el DNI del usuario

    public pPerfil(String dniUsuario) {
        this.dniUsuario = dniUsuario;

        setTitle("Perfil de Usuario");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 500);
        setLocationRelativeTo(null);

        // Crear componentes
        dniLabel = new JLabel("DNI:");
        telefonoLabel = new JLabel("Teléfono:");
        emailLabel = new JLabel("Email:");
        nombreLabel = new JLabel("Nombre:");
        creditosLabel = new JLabel("Créditos:");
        fotoPerfilLabel = new JLabel("Foto de Perfil:");

        telefonoTextField = new JTextField(10);
        emailTextField = new JTextField(20);
        nombreTextField = new JTextField(20);
        creditosTextField = new JTextField(5);

        cambiarTelefonoButton = new JButton("Cambiar Teléfono");
        cambiarEmailButton = new JButton("Cambiar Email");
        cambiarNombreButton = new JButton("Cambiar Nombre");
        cambiarCreditosButton = new JButton("Cambiar Créditos");

        cambiarTelefonoButton.addActionListener(e -> cambiarCampo("TELEFONO", telefonoTextField.getText()));
        cambiarEmailButton.addActionListener(e -> cambiarCampo("EMAIL", emailTextField.getText()));
        cambiarNombreButton.addActionListener(e -> cambiarCampo("NOMBRE", nombreTextField.getText()));
        cambiarCreditosButton.addActionListener(e -> cambiarCampo("CREDITOS", creditosTextField.getText()));

        fotoPerfilImageLabel = new JLabel();

        perfilButton = new JButton("Abrir Perfil");
        perfilButton.addActionListener(e -> abrirPerfil());

        // Crear panel y establecer el diseño
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        fotoPerfilButton = new JButton("Ver Fotos de Perfil");
        fotoPerfilButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirVentanaFotoPerfil();
                dispose();
            }
        });

        
        JPanel panel1 = new JPanel();
        panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
        // Agregar componentes al panel
        panel1.add(dniLabel);
        panel1.add(new JLabel(pFunciones.dniUsuario)); // Mostrar el DNI directamente
        panel1.add(telefonoLabel);
        panel1.add(telefonoTextField);
        panel1.add(cambiarTelefonoButton);
        panel1.add(emailLabel);
        panel1.add(emailTextField);
        panel1.add(cambiarEmailButton);
        panel1.add(nombreLabel);
        panel1.add(nombreTextField);
        panel1.add(cambiarNombreButton);
        panel1.add(creditosLabel);
        panel1.add(creditosTextField);
        panel1.add(cambiarCreditosButton);
        panel1.add(fotoPerfilButton);

       
    

        // Agregar panel al JFrame
        add(panel1);

        // Cargar el perfil al inicio
        cargarPerfil();

        setVisible(true);
    }

    private void abrirPerfil() {
        // No se solicitará el DNI al usuario al abrir el perfil
        cargarPerfil();
    }
    private void abrirVentanaFotoPerfil() {
        // Crear una instancia de la ventana con las fotos de perfil
        SwingUtilities.invokeLater(fotosperfil::new);
    }
    private void cargarPerfil() {
        try {
            // Establecer conexión
            Connection connection = DriverManager.getConnection(URL, USER, PWD);

            // Consulta SQL para obtener el perfil del usuario por DNI
            String query = "SELECT TELEFONO, EMAIL, NOMBRE, CREDITOS, FOTOPERFIL FROM USUARIO WHERE DNI = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            // Utilizar setString para establecer el valor del DNI
            preparedStatement.setString(1, pFunciones.dniUsuario);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                // Obtener valores de la base de datos
                String telefono = resultSet.getString("TELEFONO");
                String email = resultSet.getString("EMAIL");
                String nombre = resultSet.getString("NOMBRE");
                int creditos = resultSet.getInt("CREDITOS");
                // En el caso de la imagen de perfil, puede ser más complicado y requerir un tratamiento especial.

                // Actualizar campos en la interfaz gráfica
                telefonoTextField.setText(telefono);
                emailTextField.setText(email);
                nombreTextField.setText(nombre);
                creditosTextField.setText(String.valueOf(creditos));

                // Puedes cargar la imagen de perfil aquí si está almacenada en la base de datos o en un archivo local.
                // Si está almacenada como BLOB en la base de datos, necesitarías convertirla y mostrarla en el JLabel correspondiente.

            } else {
                // Limpiar campos si no se encuentra el usuario
                limpiarCampos();
                JOptionPane.showMessageDialog(this, "Usuario no encontrado");
            }

            // Cerrar recursos
            resultSet.close();
            preparedStatement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar el perfil del usuario");
        }
    }

    private void cambiarCampo(String campo, String nuevoValor) {
        try {
            // Establecer conexión
            Connection connection = DriverManager.getConnection(URL, USER, PWD);

            // Consulta SQL para actualizar el campo del usuario por DNI
            String query = "UPDATE USUARIO SET " + campo + " = ? WHERE DNI = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, nuevoValor);
            preparedStatement.setString(2, pFunciones.dniUsuario);

            int filasActualizadas = preparedStatement.executeUpdate();

            if (filasActualizadas > 0) {
                JOptionPane.showMessageDialog(this, "Campo actualizado exitosamente");
                // Volver a cargar el perfil actualizado
                cargarPerfil();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo actualizar el campo");
            }

            // Cerrar recursos
            preparedStatement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al actualizar el campo del usuario");
        }
    }

    private void cargarImagenLocal(String ruta) {
        // Cargar la imagen desde un camino local
        ImageIcon imageIcon = new ImageIcon(ruta);

        // Mostrar la imagen en el JLabel
        fotoPerfilImageLabel.setIcon(imageIcon);
    }


    private void limpiarCampos() {
        telefonoTextField.setText("");
        emailTextField.setText("");
        nombreTextField.setText("");
        creditosTextField.setText("");
        fotoPerfilImageLabel.setIcon(null);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new pPerfil("DNI_DEL_USUARIO"));
        
    }
}



