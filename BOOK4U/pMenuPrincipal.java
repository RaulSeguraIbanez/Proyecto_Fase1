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
    public pMenuPrincipal() {
        setTitle("MENÚ PRINCIPAL");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(255, 210, 175));
        panel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JPanel usuarioPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                // ... (unchanged)
            }
        };

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 4;
        gbc.anchor = GridBagConstraints.CENTER;
        usuarioPanel.setPreferredSize(new Dimension(200, 200));
        panel.add(usuarioPanel, gbc);

        ImageIcon empresaImage = new ImageIcon("src/imagenes/LogoBOOK4U.png");
        JLabel empresaLabel = new JLabel(new ImageIcon(getRoundedImage(empresaImage.getImage(), 150, 150)));
        gbc.gridy = 1;
        panel.add(empresaLabel, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 2;


  
        addButton(panel, "PPrincipal", e -> {
            pPrincipal Principal = new pPrincipal();
            Principal.setVisible(true);
            dispose();
        });

        addButton(panel, "pMisReservas", e -> {
            pMisReservas MisReservas = new pMisReservas();
            MisReservas.setVisible(true);
            dispose();
        });

        addButton(panel, "pInicio", e -> {
            pInicio Inicio = new pInicio();
            Inicio.setVisible(true);
            dispose();
        });


        add(panel);

        setVisible(true);
    }

    private void addButton(JPanel panel, String text, ActionListener actionListener) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        JButton button = new JButton(text);
        button.setBackground(new Color(0, 102, 204));
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(150, 30));
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setBorder(new RoundButtonBorder(15));

        // Add ActionListener to the button
        button.addActionListener(actionListener);

        panel.add(button, gbc);
    }

    private Image getRoundedImage(Image image, int width, int height) {
		return image;
        // ... (unchanged)
    }

    private void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new pMenuPrincipal());
    }
}

class RoundButtonBorder extends AbstractBorder {
    private int radius;

    public RoundButtonBorder(int radius) {
        this.radius = radius;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.BLACK);
        g2.draw(new RoundRectangle2D.Double(x, y, width - 1, height - 1, radius, radius));
        g2.dispose();
    }
}
