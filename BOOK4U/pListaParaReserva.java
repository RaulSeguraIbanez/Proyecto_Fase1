package BOOK4U;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class pListaParaReserva extends JFrame {
    private static final String URL = "jdbc:oracle:thin:@192.168.3.26:1521:xe"; // Reemplaza con la URL de tu base de datos
    private static final String USER = "23_24_DAM2_EHHMMM"; // Reemplaza con tu usuario de base de datos
    private static final String PWD = "ehhmmm_123"; // Reemplaza con tu contraseña de base de datos

    public pListaParaReserva() {
        setTitle("Ventana Principal");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);

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
              dispose(); // Para cerrar
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

        // Menú con información de estancias
        JPanel menuPanel = new JPanel(new GridLayout(0, 2));

        try (Connection connection = DriverManager.getConnection(URL, USER, PWD)) {
            String sql = "SELECT NOMBRE, DIRECCION, CREDITOS_DIA, FOTO FROM estancia";
            try (PreparedStatement statement = connection.prepareStatement(sql);
                 ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    String nombre = resultSet.getString("NOMBRE");
                    String direccion = resultSet.getString("DIRECCION");
                    String coste = resultSet.getString("CREDITOS_DIA");
                    String fotoUrl = resultSet.getString("FOTO");

                    JButton estanciaButton = new JButton();
                    estanciaButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            System.out.println("Ir a la página de la estancia: " + nombre);
                        }
                    });

                    estanciaButton.setText("<html><b>Nombre:</b> " + nombre +
                            "<br><b>Dirección:</b> " + direccion + "<br><b>Coste:</b> " + coste + "</html>");

                    estanciaButton.setBorderPainted(false);
                    estanciaButton.setContentAreaFilled(false);

                    estanciaButton.setBackground(new Color(255, 210, 175));

                    // Cargar la imagen desde un directorio local
                    ImageIcon fotoIcon = new ImageIcon(new ImageIcon(fotoUrl).getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
                    estanciaButton.setIcon(fotoIcon);

                    menuPanel.add(estanciaButton);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // JScrollPane para permitir el desplazamiento
        JScrollPane scrollPane = new JScrollPane(menuPanel);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        add(mainPanel);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new pListaParaReserva());
    }
}
