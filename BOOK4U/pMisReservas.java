package BOOK4U;

import javax.swing.*;
import java.awt.*;

public class pMisReservas {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Mis reservas");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new BorderLayout());

            // Toolbar
            JToolBar toolbar = new JToolBar();
            frame.add(toolbar, BorderLayout.NORTH);

            // Panel de contenido
            JPanel contentPanel = new JPanel();
            contentPanel.setLayout(new GridLayout(0, 1)); // 2 elementos por fila

            // Añadir elementos al panel de contenido
            for (int i = 0; i < 6; i++) {
                contentPanel.add(createReservationPanel());
            }

            // ScrollPane
            JScrollPane scrollPane = new JScrollPane(contentPanel);
            frame.add(scrollPane, BorderLayout.CENTER);

            // Fondo
            frame.getContentPane().setBackground(new Color(255, 210, 175)); // RGB

            frame.setSize(800, 600);
            frame.setVisible(true);
        });
    }

    private static JPanel createReservationPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Imagen
     // Imagen
        JLabel imageLabel = new JLabel(new ImageIcon("ruta/a/la/imagen.jpg"));
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Añadir esta línea
        panel.add(imageLabel);

        // Dirección
        JLabel addressLabel = new JLabel("Dirección: lel");
        addressLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Añadir esta línea
        panel.add(addressLabel);

        // Fechas
        JLabel datesLabel = new JLabel("Fechas: lel");
        datesLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Añadir esta línea
        panel.add(datesLabel);

        // Coste
        JLabel costLabel = new JLabel("Coste: lel");
        costLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Añadir esta línea
        panel.add(costLabel);
		return panel;
    }
}
