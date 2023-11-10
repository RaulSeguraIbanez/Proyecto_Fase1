package BOOK4U;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.toedter.calendar.JDateChooser;
import java.sql.Date;

public class pNuevaReserva { 
	private JDateChooser fechaInicioChooser; 
	private JDateChooser fechaFinChooser;
    
	public pNuevaReserva() {
        JFrame frame = new JFrame("Sistema de Reservas");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        frame.setLocationRelativeTo(null);

        // Creamos un panel principal con BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(255, 210, 175));

        // Creamos un JPanel con un BorderLayout para colocar el JToolBar y la foto en la zona norte del panel principal
        JPanel northPanel = new JPanel(new BorderLayout());

        // Creamos un JToolBar para colocar los cuatro botones en la zona norte del panel secundario
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);

        // Añadimos un Dimension al JToolBar para hacerlo más alto
        toolBar.setPreferredSize(new Dimension(800, 64));

        JButton paginaPrincipalButton = new JButton("Página principal");

     // Añadimos un ActionListener al botón
     paginaPrincipalButton.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
             // Creamos una instancia de la clase menuprincipal
        	 pMenuPrincipal mp = new pMenuPrincipal();

             // Hacemos visible el JFrame de la clase menuprincipal
             mp.setVisible(true);

             // Opcionalmente, podemos ocultar o cerrar el JFrame actual
             // frame.setVisible(false); // Para ocultar
              frame.dispose(); // Para cerrar
         }
     });
        
        JButton misReservasButton = new JButton("Mis Reservas");
        JButton nuevasReservasButton = new JButton("Nuevas Reservas");

        // Creamos un FlowLayout para los botones y le asignamos un espacio entre ellos
        FlowLayout buttonLayout = new FlowLayout();
        buttonLayout.setHgap(20); // Puedes cambiar el valor según tu preferencia
        toolBar.setLayout(buttonLayout);

        toolBar.add(paginaPrincipalButton);
        toolBar.add(misReservasButton);
        toolBar.add(nuevasReservasButton);

        // Creamos un JButton con un ImageIcon para el botón de perfil
        JButton perfilButton = new JButton();
        ImageIcon icon = new ImageIcon("src/imagenes/FotoPerfil.png"); // Puedes cambiar la ruta de la imagen según tu preferencia

        // Creamos un Image con el tamaño que queramos para el botón de perfil
        Image image = icon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH); // Puedes cambiar los valores según tu preferencia
        icon = new ImageIcon(image);

        // Añadimos el ImageIcon al JButton
        perfilButton.setIcon(icon);

        // Creamos un JPanel con un BorderLayout para colocar el botón de perfil en la zona este
        JPanel perfilPanel = new JPanel(new BorderLayout());
        perfilPanel.add(perfilButton, BorderLayout.EAST);

        // Añadimos el JPanel al JToolBar
        toolBar.add(perfilPanel);

        // Añadimos el JToolBar a la zona norte del panel secundario
        northPanel.add(toolBar, BorderLayout.NORTH);

        // Creamos un JLabel para mostrar la foto en la zona centro del panel secundario
     // Creamos un JLabel para mostrar la imagen
     // Creamos un JLabel para mostrar la imagen
        JLabel imageLabel = new JLabel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Obtenemos el objeto Graphics2D del parámetro
                Graphics2D g2d = (Graphics2D) g;
                // Creamos un ImageIcon con la ruta de la imagen que queremos mostrar
                ImageIcon icon = new ImageIcon("src/imagenes/Casa.jpg"); // Puedes cambiar la ruta según tu preferencia
                // Obtenemos el Image del ImageIcon
                Image image = icon.getImage();
                // Pintamos la imagen escalada al tamaño del JLabel
                g2d.drawImage(image, 0, 0, getWidth(), getHeight(), null);
            }
        };

        // Añadimos un EmptyBorder al JLabel para crear un margen alrededor de la imagen
        imageLabel.setBorder(BorderFactory.createEmptyBorder(110, 180, 200, 180));

        // Añadimos el JLabel a la zona norte del panel principal, debajo del JToolBar
        mainPanel.add(imageLabel, BorderLayout.NORTH);


        // Añadimos el JLabel a la zona centro del panel secundario
        northPanel.add(imageLabel, BorderLayout.CENTER);

        // Añadimos el panel secundario a la zona norte del panel principal
        mainPanel.add(northPanel, BorderLayout.NORTH);

        // Creamos un JTextArea para mostrar la descripción de la estancia en la zona centro del panel principal
        JTextArea descripcionArea = new JTextArea();
        descripcionArea.setBorder(BorderFactory.createEmptyBorder(15, 10, 10, 5));
        descripcionArea.setText("Esta es una estancia acogedora y confortable, ideal para pasar unos días de relax y disfrutar de la naturaleza. Cuenta con todas las comodidades necesarias para que te sientas como en casa."); // Puedes cambiar el texto según tu preferencia
        descripcionArea.setFont(new Font("Sans-serif", Font.PLAIN, 25));
        descripcionArea.setLineWrap(true);
        descripcionArea.setWrapStyleWord(true);
        descripcionArea.setEditable(false);

        // Añadimos el mismo color de fondo que el panel principal al JTextArea
        descripcionArea.setBackground(new Color(255, 210, 175));

        // Añadimos el JTextArea a la zona centro del panel principal
        mainPanel.add(descripcionArea, BorderLayout.CENTER);

        // Creamos un JPanel con un GridBagLayout para colocar la parte de las fechas, el botón de reservas, el precio por día y el nombre en la zona sur del panel principal
        JPanel subPanel = new JPanel(new GridBagLayout());
        subPanel.setBackground(new Color(255, 210, 175));

        // Añadimos un EmptyBorder al panel secundario para separarlo del borde
        subPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        Font labelFont = new Font("Sans-serif", Font.PLAIN, 18);
        Font componentFont = new Font("Sans-serif", Font.PLAIN, 16);

        JLabel fechaInicioLabel = new JLabel("Fecha de inicio:");
        fechaInicioLabel.setFont(labelFont);

        fechaInicioChooser = new JDateChooser();
        fechaInicioChooser.setFont(componentFont);

        // Añadimos un Dimension al JDateChooser para hacerlo más largo y menos alto
        fechaInicioChooser.setPreferredSize(new Dimension(200, 25));
        fechaInicioChooser.setMaximumSize(new Dimension(200, 25));

        JLabel fechaFinLabel = new JLabel("Fecha de fin:");
        fechaFinLabel.setFont(labelFont);

        fechaFinChooser = new JDateChooser();
        fechaFinChooser.setFont(componentFont);

        // Añadimos un Dimension al JDateChooser para hacerlo más largo y menos alto
        fechaFinChooser.setPreferredSize(new Dimension(200, 25));
        fechaFinChooser.setMaximumSize(new Dimension(200, 25));

        JButton reservarButton = new JButton("Reservar");
        reservarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Date fechaInicio = new Date(fechaInicioChooser.getDate().getTime());
                Date fechaFin = new Date(fechaFinChooser.getDate().getTime());

                pFunciones.registrarFechasEstancia(fechaInicio, fechaFin);

                // Puedes mostrar un mensaje de confirmación o manejar cualquier otro resultado aquí

                // Cerramos el JFrame actual con el método dispose()
                frame.dispose();
            }
        });

        // Creamos dos JLabels para mostrar el precio por día y el nombre de la estancia
        JLabel precioLabel = new JLabel("Precio por día: 50€"); // Puedes cambiar el texto según tu preferencia
        precioLabel.setFont(labelFont);
        precioLabel.setFont(new Font("Sans-serif", Font.PLAIN, 25));
        
        JLabel nombreLabel = new JLabel("Estancia acogedora"); // Puedes cambiar el texto según tu preferencia
        nombreLabel.setFont(labelFont);
        nombreLabel.setFont(new Font("Sans-serif", Font.PLAIN, 25));
        // Creamos un GridBagConstraints para asignar los componentes a las celdas que queramos
     // Creamos un JPanel con un GridBagLayout para colocar los componentes en cuatro filas y dos columnas
        JPanel subPanel1 = new JPanel(new GridBagLayout());
        subPanel1.setBackground(new Color(255, 210, 175));

        // Añadimos un EmptyBorder al panel secundario para separarlo del borde
        subPanel1.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Creamos un JPanel con un BoxLayout para los labels de precio y nombre
        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS));
        labelPanel.setBackground(new Color(255, 210, 175));

        // Añadimos un EmptyBorder al JPanel para separarlo de la fecha
        labelPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Puedes cambiar los valores según tu preferencia

        // Añadimos los labels al JPanel con espacios entre ellos
        labelPanel.add(precioLabel);
        labelPanel.add(Box.createVerticalStrut(20)); // Puedes cambiar el valor según tu preferencia
        labelPanel.add(nombreLabel);

        // Creamos un GridBagConstraints para asignar los componentes a las celdas que queramos
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10); // Puedes cambiar los valores según tu preferencia

        // Añadimos el JPanel con los labels a la celda (0, 0) del panel secundario
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // Ocupa dos columnas
        subPanel1.add(labelPanel, gbc);

        // Creamos un JPanel con un FlowLayout para colocar los labels y los JDateChoosers de las fechas
        JPanel fechaPanel = new JPanel(new FlowLayout());
        fechaPanel.setBackground(new Color(255, 210, 175));

        // Añadimos los componentes al JPanel
        fechaPanel.add(fechaInicioLabel);
        fechaPanel.add(fechaInicioChooser);
        fechaPanel.add(fechaFinLabel);
        fechaPanel.add(fechaFinChooser);

        // Añadimos el JPanel con los componentes de las fechas a la celda (0, 1) del panel secundario
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2; // Ocupa dos columnas
        subPanel1.add(fechaPanel, gbc);

        // Creamos un JPanel con un FlowLayout para colocar el botón de reservas
        JPanel botonPanel = new JPanel(new FlowLayout());
        botonPanel.setBackground(new Color(255, 210, 175));

        // Añadimos el botón al JPanel
        botonPanel.add(reservarButton);

        // Añadimos el JPanel con el botón a la celda (1, 2) del panel secundario
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 1; // Ocupa una columna
        subPanel1.add(botonPanel, gbc);
        Dimension botonSize = new Dimension(700, 45); // Puedes cambiar los valores según tu preferencia

     // Llamamos al método setPreferredSize del botón y le pasamos el objeto Dimension
        reservarButton.setPreferredSize(botonSize);
        // Añadimos el panel secundario a la zona sur del panel principal
        mainPanel.add(subPanel1, BorderLayout.SOUTH);


        frame.add(mainPanel);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new pNuevaReserva();
        });
    }
}


