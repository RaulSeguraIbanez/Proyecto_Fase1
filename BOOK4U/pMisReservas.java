package BOOK4U;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class pMisReservas {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Mis reservas");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new BorderLayout());

            // Menú
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

            JButton nuevasReservasButton = new JButton("Nuevas Reservas");
            nuevasReservasButton.addActionListener(e -> {
                // Creamos una instancia de la clase pMenuPrincipal
                pListaParaReserva mp = new pListaParaReserva();

                // Hacemos visible el JFrame de la clase pMenuPrincipal
                mp.setVisible(true);

                // Opcionalmente, podemos ocultar o cerrar el JFrame actual
                frame.dispose(); // Para ocultar
                // dispose(); // Para cerrar
            });
            
            JButton paginaCreditos = new JButton("Créditos");

            // Añadimos un ActionListener al botón
            paginaCreditos.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Creamos una instancia de la clase pPrincipal
                	SwingUtilities.invokeLater(pCreditos::new);

                    // Hacemos visible el JFrame de la clase pPrincipal


                    // Opcionalmente, podemos ocultar o cerrar el JFrame actual
                    frame.dispose(); // Para cerrar
                }
            });
            
            FlowLayout buttonLayout = new FlowLayout();
            buttonLayout.setHgap(95);
            toolBar.setLayout(buttonLayout);
            toolBar.add(paginaPrincipalButton);
            toolBar.add(nuevasReservasButton);
            toolBar.add(paginaCreditos);

            JButton perfilButton = new JButton();
            ImageIcon icon = new ImageIcon("src/imagenes/FotoPerfil.png");
            Image image = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            icon = new ImageIcon(image);
            perfilButton.setIcon(icon);

            JPanel perfilPanel = new JPanel(new BorderLayout());
            perfilPanel.add(perfilButton, BorderLayout.EAST);
            toolBar.add(perfilPanel);

            northPanel.add(toolBar, BorderLayout.NORTH);

            mainPanel.add(northPanel, BorderLayout.NORTH);

            // Panel de contenido
            JPanel contentPanel = new JPanel();
            contentPanel.setLayout(new GridLayout(0, 1)); // 2 elementos por fila

            // Añadir elementos al panel de contenido
            for (int i = 0; i < 7; i++) {
                contentPanel.add(createReservationPanel());
            }

            // ScrollPane
            JScrollPane scrollPane = new JScrollPane(contentPanel);
            mainPanel.add(scrollPane, BorderLayout.CENTER);

            mainPanel.setBackground(new Color(255, 210, 175));

            frame.add(mainPanel);
            frame.setSize(800, 600);
            frame.setVisible(true);
        });
    }

    protected static void dispose() {
		// TODO Auto-generated method stub
		
	}

	private static JPanel createReservationPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Imagen
        JLabel imageLabel = new JLabel(new ImageIcon("src/imagenes/Casa1.jpg"));
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(imageLabel);

        // Dirección
        JLabel addressLabel = new JLabel("Dirección: lel");
        addressLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(addressLabel);

        // Fechas
        JLabel datesLabel = new JLabel("Fechas: lel");
        datesLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(datesLabel);

        // Coste
        JLabel costLabel = new JLabel("Coste: lel");
        costLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(costLabel);

        return panel;
    }

	public static void setVisible(boolean b) {
		// TODO Auto-generated method stub
		
	}
}
