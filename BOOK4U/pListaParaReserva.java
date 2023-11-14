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
    private static final String USER = "23_24_DAM2_EHHMMM";
    private static final String PWD = "ehhmmm_123";
    private static final String URL = "jdbc:oracle:thin:@192.168.3.26:1521:xe";

    public pListaParaReserva() {
        setTitle("Ventana Principal");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 800);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(255, 210, 175));

        // Barra de herramientas
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);
        toolBar.setPreferredSize(new Dimension(800, 70));

        JButton paginaPrincipalButton = new JButton("Página principal");
        paginaPrincipalButton.addActionListener(e -> {
            // Creamos una instancia de la clase pMenuPrincipal
            pMenuPrincipal mp = new pMenuPrincipal();

            // Hacemos visible el JFrame de la clase pMenuPrincipal
            mp.setVisible(true);

            // Opcionalmente, podemos ocultar o cerrar el JFrame actual
            setVisible(false); // Para ocultar
            // dispose(); // Para cerrar
        });

        JButton misReservasButton = new JButton("Mis Reservas");
        JButton paginaCreditos = new JButton("Créditos");

        // Añadimos un ActionListener al botón
        paginaCreditos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Creamos una instancia de la clase pPrincipal
            	SwingUtilities.invokeLater(pCreditos::new);

                // Hacemos visible el JFrame de la clase pPrincipal


                // Opcionalmente, podemos ocultar o cerrar el JFrame actual
                dispose(); // Para cerrar
            }
        });

        FlowLayout buttonLayout = new FlowLayout();
        buttonLayout.setHgap(85);
        toolBar.setLayout(buttonLayout);

        toolBar.add(paginaPrincipalButton);
        toolBar.add(misReservasButton);
        toolBar.add(paginaCreditos);

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
        JPanel menuPanel = new JPanel(new GridLayout(0, 2, 10, 10)); // Cambia según tus necesidades

        // Botón de Refresh
        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> {
            // Lógica para volver a cargar la información de la base de datos
            refreshData(menuPanel);
        });

        // Agregar el botón Refresh en la parte inferior
        mainPanel.add(refreshButton, BorderLayout.SOUTH);

        // JScrollPane para permitir el desplazamiento
        JScrollPane scrollPane = new JScrollPane(menuPanel);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        add(mainPanel);
        setVisible(true);

        // Inicializar los datos al abrir la ventana
        refreshData(menuPanel);
    }

    private void refreshData(JPanel menuPanel) {
        // Limpiar el panel antes de volver a cargar los datos
        menuPanel.removeAll();

        try (Connection connection = DriverManager.getConnection(URL, USER, PWD)) {
            String query = "SELECT ID_ESTANCIA, NOMBRE, DIRECCION, CREDITOS_DIA, FOTO FROM ESTANCIA ORDER BY ID_ESTANCIA";
            try (PreparedStatement statement = connection.prepareStatement(query);
                 ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    int id = resultSet.getInt("ID_ESTANCIA");
                    String nombre = resultSet.getString("NOMBRE");
                    String direccion = resultSet.getString("DIRECCION");
                    String coste = resultSet.getString("CREDITOS_DIA");
                    String fotoUrl = resultSet.getString("FOTO");

                    // Crear un botón para cada estancia que contenga la foto y la información
                    JButton estanciaButton = new JButton();

                    // Configurar el botón para mostrar la foto
                    try {
                        ImageIcon fotoIcon = new ImageIcon(fotoUrl);
                        Image image1 = fotoIcon.getImage().getScaledInstance(140, 170, Image.SCALE_SMOOTH);
                        estanciaButton.setIcon(new ImageIcon(image1));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                    // Configurar el botón para mostrar la información de la estancia
                    estanciaButton.setToolTipText("<html><b>ID:</b> " + id +
                            "<br><b>Nombre:</b> " + nombre +
                            "<br><b>Dirección:</b> " + direccion +
                            "<br><b>Coste:</b> " + coste + " Creditos" + "</html>");

                    // Establecer el aspecto del botón para que no se vea como un botón
                    estanciaButton.setBorderPainted(false);
                    estanciaButton.setFocusPainted(false);
                    estanciaButton.setContentAreaFilled(false);

                    // Agregar un ActionListener para manejar el clic en el botón
                    estanciaButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            // Aquí puedes agregar la lógica para abrir la pantalla NuevaReserva con la información correspondiente
                            abrirNuevaReserva(id);
                        }
                    });

                    // Agregar el botón de la estancia al panel principal
                    menuPanel.add(estanciaButton);

                    System.out.println(id + " " + nombre + " " + direccion + " " + coste + " " + fotoUrl);
                }

                // Actualizar la vista
                menuPanel.revalidate();
                menuPanel.repaint();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void abrirNuevaReserva(int id) {
        // Crear una instancia de NuevaReserva y pasar la id al constructor
    	pNuevaReserva nuevaReserva = new pNuevaReserva();
        nuevaReserva.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new pListaParaReserva());
    }
}

