package BOOK4U;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class fotosperfil extends JFrame {

    private static final String USER = "23_24_DAM2_EHHMMM";
    private static final String PWD = "ehhmmm_123";
    private static final String URL = "jdbc:oracle:thin:@192.168.3.26:1521:xe";

    private JPanel buttonPanel;
    private String rutaImagenSeleccionada; // Nueva variable para almacenar la ruta de la imagen seleccionada

    public fotosperfil() {
        // Configurar el JFrame
        super("Botones con Imágenes");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 800);

        // Configurar el diseño del panel principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        add(mainPanel);

        // Añadir barra de herramientas
        mainPanel.add(createToolBar(), BorderLayout.NORTH);

        // Configurar el diseño del panel de botones
        buttonPanel = new JPanel(new GridLayout(3, 3, 10, 10));
        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        // Crear los botones con imágenes
        JButton button1 = createButton("src/imagenes/perfil1.png");
        JButton button2 = createButton("src/imagenes/perfil2.png");
        JButton button3 = createButton("src/imagenes/perfil3.png");
        JButton button4 = createButton("src/imagenes/perfil4.jpeg");
        JButton button5 = createButton("src/imagenes/perfil5.jpeg");
        JButton button6 = createButton("src/imagenes/pefil6.jpeg");
        JButton button7 = createButton("src/imagenes/perfil7.jpg");
        JButton button8 = createButton("src/imagenes/perfil8.jpg");
        JButton button9 = createButton("src/imagenes/perfil9.jpeg");

        // Hacer visible el JFrame
        setVisible(true);
    }

    private JButton createButton(String imagePath) {
        JButton button = new JButton();
        ImageIcon icon = new ImageIcon(imagePath);
        int width = 210;
        int height = 210;
        Image image = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        button.setIcon(new ImageIcon(image));

        // Almacena la ruta de la imagen en la descripción del botón
        button.setIconTextGap(-width); // Configura el espacio del texto para ocultar el texto
        button.setText(imagePath);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lógica cuando se presiona el botón
                JOptionPane.showMessageDialog(fotosperfil.this, "Botón presionado");

                // Obtener la ruta de la imagen asociada con el botón
                rutaImagenSeleccionada = obtenerRutaImagenPorBoton(button);

                // Actualizar la foto de perfil en la base de datos
                actualizarFotoPerfilEnBD(rutaImagenSeleccionada);
            }
        });

        buttonPanel.add(button);
        return button;
    }

    private String obtenerRutaImagenPorBoton(JButton button) {
        // Obtener la ruta de la imagen almacenada en la descripción del botón
        return button.getText();
    }

    private void actualizarFotoPerfilEnBD(String rutaImagen) {
        try {
            // Establecer conexión
            Connection connection = DriverManager.getConnection(URL, USER, PWD);

            // Consulta SQL para actualizar la foto de perfil en la base de datos
            String query = "UPDATE Usuario SET FOTOPERFIL = ? WHERE DNI = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            // Utilizar setString para establecer la ruta de la imagen
            preparedStatement.setString(1, rutaImagen);
            preparedStatement.setString(2, pFunciones.dniUsuario); // Reemplaza con el DNI del usuario

            int filasActualizadas = preparedStatement.executeUpdate();

            if (filasActualizadas > 0) {
                JOptionPane.showMessageDialog(this, "Foto de perfil actualizada exitosamente en la base de datos");
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo actualizar la foto de perfil en la base de datos");
            }

            // Cerrar recursos
            preparedStatement.close();
            connection.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al actualizar la foto de perfil en la base de datos");
        }
    }

    private JToolBar createToolBar() {
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);

        JButton paginaPrincipalButton = new JButton("Créditos");
        paginaPrincipalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(pCreditos::new);
                dispose();
            }
        });

        JButton misReservasButton = new JButton("Mis Reservas");
        misReservasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pMisReservas mp = new pMisReservas();
                mp.setVisible(true);
                dispose();
            }
        });

        JButton nuevasReservasButton = new JButton("Nuevas Reservas");
        nuevasReservasButton.addActionListener(e -> {
            pListaParaReserva mp = new pListaParaReserva();
            mp.setVisible(true);
            dispose();
        });

        toolBar.add(nuevasReservasButton);
        toolBar.add(misReservasButton);
        toolBar.add(paginaPrincipalButton);

        JButton perfilButton = new JButton();
        ImageIcon icon = new ImageIcon("src/imagenes/FotoPerfil.png");
        icon = new ImageIcon(redimensionarImagen(icon.getImage(), 60, 60));
        perfilButton.setIcon(icon);
        perfilButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(() -> new pPerfil("DNI_DEL_USUARIO"));
            }
        });

        toolBar.add(perfilButton);

        return toolBar;
    }

    private Image redimensionarImagen(Image imagen, int ancho, int alto) {
        return imagen.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(fotosperfil::new);
    }
}



