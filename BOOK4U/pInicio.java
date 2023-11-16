package BOOK4U;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;

public class pInicio extends JPanel {
    public pInicio() {
        setLayout(null);  // Usamos un diseño nulo para posicionar los componentes manualmente

        JButton boton1 = createButton("Login", 300, 550, Color.GREEN, Color.BLACK);
        JButton boton2 = createButton("Registro", 300, 600, Color.BLUE, Color.WHITE);

        // Agregar los botones al panel principal
        add(boton1);
        add(boton2);
        centrarEnPantalla();
    }

    private void centrarEnPantalla() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = getSize();

        int x = (screenSize.width - frameSize.width) / 2;
        int y = (screenSize.height - frameSize.height) / 2;

        setLocation(x, y);
    
        ImageIcon imageIcon = new ImageIcon("src/imagenes/LogoBOOK4U.png"); // Reemplaza con la ruta de tu imagen

        int frameWidth = 800;
        int frameHeight = 800;
        int imageX = (frameWidth - imageIcon.getIconWidth()) / 2;
        int imageY = ((frameHeight - imageIcon.getIconHeight()) / 2) - 100;

        JLabel imageLabel = new JLabel(imageIcon);
        imageLabel.setBounds(imageX, imageY, imageIcon.getIconWidth(), imageIcon.getIconHeight());
        add(imageLabel);
    }

    private JButton createButton(String text, int x, int y, Color backgroundColor, Color textColor) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                if (!isOpaque()) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(getBackground());
                    g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 30, 30));
                    g2.dispose();
                }
                super.paintComponent(g);
            }
        };
        button.setBounds(x, y, 200, 50);
        button.setBackground(backgroundColor);
        button.setForeground(textColor);
        button.setOpaque(false);

        if (text.equals("Login")) {
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    SwingUtilities.getWindowAncestor(pInicio.this).dispose();
                    JFrame loginFrame = new JFrame("BOOK4U");
                    pLogin loginPanel = new pLogin();
                    loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    loginFrame.add(loginPanel);
                    loginFrame.setSize(800, 800);
                    loginFrame.setVisible(true);
                }
            });
        } else if (text.equals("Registro")) {
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    SwingUtilities.getWindowAncestor(pInicio.this).dispose();
                    SwingUtilities.invokeLater(() -> {
                        pRegistro.createAndShowGUI();
                    });
                }
            });
        }

        return button;
    }
}