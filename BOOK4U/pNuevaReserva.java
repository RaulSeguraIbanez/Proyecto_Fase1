package BOOK4U;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.toedter.calendar.JDateChooser;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class pNuevaReserva {
    private int estanciaId;
    private String fotoUrl;
    private int precioPorDia;
    private String nombre;
    private JDateChooser fechaInicioChooser;
    private JDateChooser fechaFinChooser;
    private static final String USER = "23_24_DAM2_EHHMMM";
    private static final String PWD = "ehhmmm_123";
    private static final String URL = "jdbc:oracle:thin:@192.168.3.26:1521:xe";

    public pNuevaReserva(int estanciaId) {
        this.estanciaId = estanciaId;
        JFrame frame = new JFrame("Sistema de Reservas");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        frame.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(255, 210, 175));

        JPanel northPanel = new JPanel(new BorderLayout());
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);
        toolBar.setPreferredSize(new Dimension(800, 64));

        JButton paginaPrincipalButton = new JButton("Página principal");
        paginaPrincipalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pMenuPrincipal mp = new pMenuPrincipal();
                mp.setVisible(true);
                frame.dispose();
            }
        });

        JButton misReservasButton = new JButton("Mis Reservas");
        misReservasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            	pMisReservas mp = new pMisReservas();
            	mp.setVisible(true);

                // Cierra el JFrame actual
                frame.dispose();
            }
        });
        JButton nuevasReservasButton = new JButton("Nuevas Reservas");
        nuevasReservasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });        

        FlowLayout buttonLayout = new FlowLayout();
        buttonLayout.setHgap(20);
        toolBar.setLayout(buttonLayout);

        toolBar.add(paginaPrincipalButton);
        toolBar.add(misReservasButton);
        toolBar.add(nuevasReservasButton);

        JButton perfilButton = new JButton();
        ImageIcon icon = new ImageIcon(pFunciones.fotoUsuario);
        Image image = icon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        perfilButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Abrir la ventana del perfil con un DNI específico
                SwingUtilities.invokeLater(() -> new pPerfil("DNI_DEL_USUARIO"));
            }
        });
        icon = new ImageIcon(image);
        perfilButton.setIcon(icon);

        JPanel perfilPanel = new JPanel(new BorderLayout());
        perfilPanel.add(perfilButton, BorderLayout.EAST);

        toolBar.add(perfilPanel);
        northPanel.add(toolBar, BorderLayout.NORTH);

        JLabel imageLabel = new JLabel();
        imageLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        mainPanel.add(imageLabel, BorderLayout.NORTH);
        northPanel.add(imageLabel, BorderLayout.CENTER);
        mainPanel.add(northPanel, BorderLayout.NORTH);

        try (Connection connection = DriverManager.getConnection(URL, USER, PWD)) {
            String query = "SELECT ID_ESTANCIA, NOMBRE, DESCRIPCION, FOTO, CREDITOS_DIA FROM ESTANCIA WHERE ID_ESTANCIA = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, estanciaId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        int id = resultSet.getInt("ID_ESTANCIA");
                        String nombreEstancia = resultSet.getString("NOMBRE");
                        System.out.println("Nombre obtenido de la base de datos: " + nombreEstancia);
                        this.nombre = nombreEstancia;
                        fotoUrl = resultSet.getString("FOTO");

                        if (resultSet.getObject("CREDITOS_DIA") != null) {
                            precioPorDia = resultSet.getInt("CREDITOS_DIA");
                        } else {
                            precioPorDia = 0;
                        }

                        String descripcion = resultSet.getString("DESCRIPCION");
                        System.out.println("ID: " + id + ", Nombre: " + nombreEstancia + ", Precio por día: " + precioPorDia);

                        JTextArea descripcionArea = new JTextArea();
                        descripcionArea.setBorder(BorderFactory.createEmptyBorder(15, 10, 10, 5));

                        if (descripcion != null) {
                            descripcionArea.setText(descripcion);
                        } else {
                            descripcionArea.setText("Descripción no disponible");
                        }

                        descripcionArea.setFont(new Font("Sans-serif", Font.PLAIN, 25));
                        descripcionArea.setLineWrap(true);
                        descripcionArea.setWrapStyleWord(true);
                        descripcionArea.setEditable(false);
                        descripcionArea.setBackground(new Color(255, 210, 175));

                        mainPanel.add(descripcionArea, BorderLayout.CENTER);

                        ImageIcon iconFoto = new ImageIcon(fotoUrl);
                        Image imageFoto = iconFoto.getImage().getScaledInstance(800, 300, Image.SCALE_SMOOTH);
                        imageLabel.setIcon(new ImageIcon(imageFoto));

                        JLabel precioLabel = new JLabel("Precio por día: " + precioPorDia + "€");
                        precioLabel.setFont(new Font("Sans-serif", Font.PLAIN, 25));

                        mainPanel.add(precioLabel, BorderLayout.SOUTH);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        JPanel subPanel = new JPanel(new GridBagLayout());
        subPanel.setBackground(new Color(255, 210, 175));
        subPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        Font labelFont = new Font("Sans-serif", Font.PLAIN, 18);
        Font componentFont = new Font("Sans-serif", Font.PLAIN, 16);

        JLabel fechaInicioLabel = new JLabel("Fecha de inicio:");
        fechaInicioLabel.setFont(labelFont);

        fechaInicioChooser = new JDateChooser();
        fechaInicioChooser.setFont(componentFont);
        fechaInicioChooser.setPreferredSize(new Dimension(200, 25));
        fechaInicioChooser.setMaximumSize(new Dimension(200, 25));

        JLabel fechaFinLabel = new JLabel("Fecha de fin:");
        fechaFinLabel.setFont(labelFont);

        fechaFinChooser = new JDateChooser();
        fechaFinChooser.setFont(componentFont);
        fechaFinChooser.setPreferredSize(new Dimension(200, 25));
        fechaFinChooser.setMaximumSize(new Dimension(200, 25));


        JButton reservarButton = new JButton("Reservar");
        reservarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Date fechaInicio = fechaInicioChooser.getDate();
                Date fechaFin = fechaFinChooser.getDate();

                // Realizar el cálculo de la reserva usando la nueva clase CalculadoraReserva
                String resultadoReserva = CalculoDias.calcularReserva(fechaInicio, fechaFin, precioPorDia, estanciaId);

                // Muestra los resultados en una nueva ventana de JOptionPane con botón de aceptar/cancelar
                int option = JOptionPane.showConfirmDialog(frame, resultadoReserva, "Reserva Calculada", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);

                // Verifica si se ha presionado el botón "Aceptar"
                if (option == JOptionPane.OK_OPTION) {
                    // Realiza el insert después de darle aceptar
                    CalculoDias.realizarReserva(fechaInicio, fechaFin, precioPorDia, estanciaId);

                    // Cierra la ventana pequeña
                    frame.dispose();
                }
            }
        });


        JLabel precioLabel = new JLabel("Precio por día: " + precioPorDia + " créditos");
        precioLabel.setFont(labelFont);
        precioLabel.setFont(new Font("Sans-serif", Font.PLAIN, 25));

        JLabel nombreLabel = new JLabel("Nombre: " + nombre);
        nombreLabel.setFont(labelFont);
        nombreLabel.setFont(new Font("Sans-serif", Font.PLAIN, 25));

        JPanel subPanel1 = new JPanel(new GridBagLayout());
        subPanel1.setBackground(new Color(255, 210, 175));
        subPanel1.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS));
        labelPanel.setBackground(new Color(255, 210, 175));
        labelPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        labelPanel.add(precioLabel);
        labelPanel.add(Box.createVerticalStrut(20));
        labelPanel.add(nombreLabel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        subPanel1.add(labelPanel, gbc);

        JPanel fechaPanel = new JPanel(new FlowLayout());
        fechaPanel.setBackground(new Color(255, 210, 175));

        fechaPanel.add(fechaInicioLabel);
        fechaPanel.add(fechaInicioChooser);
        fechaPanel.add(fechaFinLabel);
        fechaPanel.add(fechaFinChooser);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        subPanel1.add(fechaPanel, gbc);

        JPanel botonPanel = new JPanel(new FlowLayout());
        botonPanel.setBackground(new Color(255, 210, 175));

        botonPanel.add(reservarButton);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        subPanel1.add(botonPanel, gbc);
        Dimension botonSize = new Dimension(700, 45);

        reservarButton.setPreferredSize(botonSize);
        mainPanel.add(subPanel1, BorderLayout.SOUTH);

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    public void setVisible(boolean b) {
        // TODO Auto-generated method stub
    }
}
