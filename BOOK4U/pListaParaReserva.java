package BOOK4U;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class pListaParaReserva extends JFrame {
    private static final String URL = "jdbc:tu_base_de_datos"; // Reemplaza con la URL de tu base de datos
    private static final String USER = "tu_usuario"; // Reemplaza con tu usuario de base de datos
    private static final String PWD = "tu_contraseña"; // Reemplaza con tu contraseña de base de datos

    public pListaParaReserva() {
        setTitle("Ventana Principal");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);

        JPanel mainPanel = new JPanel(new BorderLayout());

        // Barra de herramientas
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);
        toolBar.setPreferredSize(new Dimension(800, 70));

        JButton paginaPrincipalButton = new JButton("Página principal");
        paginaPrincipalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Acción al hacer clic en "Página principal"
                System.out.println("Ir a la página principal");
            }
        });

        JButton misReservasButton = new JButton("Mis Reservas");
        JButton nuevasReservasButton = new JButton("Nuevas Reservas");

        FlowLayout buttonLayout = new FlowLayout();
        buttonLayout.setHgap(85);
        toolBar.setLayout(buttonLayout);

        toolBar.add(paginaPrincipalButton);
        toolBar.add(misReservasButton);
        toolBar.add(nuevasReservasButton);

        JButton perfilButton = new JButton();
        ImageIcon icon = new ImageIcon("src/imagenes/FotoPerfil.png");
        Image image = icon.getImage().getScaledInstance(45, 45, Image.SCALE_SMOOTH);
        icon = new ImageIcon(image);
        perfilButton.setIcon(icon);

        JPanel perfilPanel = new JPanel(new BorderLayout());
        perfilPanel.add(perfilButton, BorderLayout.EAST);
        toolBar.add(perfilPanel);

        mainPanel.add(toolBar, BorderLayout.NORTH);

        // Menú con información de estancias
        JPanel menuPanel = new JPanel(new GridLayout(0, 2));

        try (Connection connection = DriverManager.getConnection(URL, USER, PWD)) {
            String sql = "SELECT Nombre, Direccion, Coste, Foto_url FROM estancias";
            try (PreparedStatement statement = connection.prepareStatement(sql);
                 ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    String nombre = resultSet.getString("Nombre");
                    String direccion = resultSet.getString("Direccion");
                    String coste = resultSet.getString("Coste");
                    String fotoUrl = resultSet.getString("Foto_url");

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

                    // Cargar la imagen desde la URL (comentario, puedes adaptar esto según tus necesidades)
                    // Image foto = ImageIO.read(new URL(fotoUrl));
                    // ImageIcon fotoIcon = new ImageIcon(foto.getScaledInstance(100, 100, Image.SCALE_SMOOTH));
                    // estanciaButton.setIcon(fotoIcon);

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