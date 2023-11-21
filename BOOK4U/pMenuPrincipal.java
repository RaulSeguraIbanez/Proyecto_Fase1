package BOOK4U;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

public class pMenuPrincipal extends JFrame {

    private JButton misreservas;
    private JButton inicioButton;
    private JButton irAPrincipalButton;

    public pMenuPrincipal() {
        // Configuración de la ventana principal
        setTitle("Menú Principal");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setBackground(new Color(255, 210, 175));
        centrarEnPantalla();
    }

    private void centrarEnPantalla() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = getSize();

        int x = (screenSize.width - frameSize.width) / 2;
        int y = (screenSize.height - frameSize.height) / 2;

        setLocation(x, y);
    
        // Añadir la JToolBar
        add(createToolBar(), BorderLayout.NORTH);

        // Panel central con la imagen
	        JPanel panelCentral = new JPanel();
	        ImageIcon imagen = new ImageIcon("src/imagenes/LogoBOOK4U.png");
	        JLabel labelImagen = new JLabel(imagen);
	        panelCentral.add(labelImagen);

        add(panelCentral, BorderLayout.CENTER);
    }

    private JToolBar createToolBar() {
        // Creamos un JToolBar para colocar los cuatro botones en la zona norte del panel secundario
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);

        // Añadimos un Dimension al JToolBar para hacerlo más alto
        toolBar.setPreferredSize(new Dimension(800, 64));

        JButton historial = new JButton("Historial");

        // Añadimos un ActionListener al botón
        historial.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Creamos una instancia de la clase pPrincipal
            	SwingUtilities.invokeLater(pHistorial::new);

                // Hacemos visible el JFrame de la clase pPrincipal

                // Opcionalmente, podemos ocultar o cerrar el JFrame actual
                dispose(); // Para cerrar
            }
        });

        JButton misReservasButton = new JButton("Mis Reservas");
        misReservasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Abre la página MisReservas
            	pMisReservas mp = new pMisReservas();
            	mp.setVisible(true);
                dispose();

                // Cierra el JFrame actual
                            }
        });
        JButton nuevasReservasButton = new JButton("Nuevas Reservas");
        nuevasReservasButton.addActionListener(e -> {
            // Creamos una instancia de la clase pMenuPrincipal
            pListaParaReserva mp = new pListaParaReserva();

            // Hacemos visible el JFrame de la clase pMenuPrincipal
            mp.setVisible(true);

            // Opcionalmente, podemos ocultar o cerrar el JFrame actual
            dispose();
           
        });
        // Creamos un FlowLayout para los botones y le asignamos un espacio entre ellos
        FlowLayout buttonLayout = new FlowLayout();
        buttonLayout.setHgap(85); // Puedes cambiar el valor según tu preferencia
        toolBar.setLayout(buttonLayout);

        toolBar.add(nuevasReservasButton);
        toolBar.add(misReservasButton);
        toolBar.add(historial);

        // Creamos un JButton con un ImageIcon para el botón de perfil
        JButton perfilButton = new JButton();
        ImageIcon icon = new ImageIcon(pFunciones.fotoUsuario); // Puedes cambiar la ruta de la imagen según tu preferencia
        perfilButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Abrir la ventana del perfil con un DNI específico
                SwingUtilities.invokeLater(() -> new pPerfil("DNI_DEL_USUARIO"));
            }
        });
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

        return toolBar;
    }
}
