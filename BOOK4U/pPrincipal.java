package BOOK4U;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class pPrincipal extends JFrame {
    private BufferedImage profileImage;
    private int currentSize = 200; // Tamaño inicial
    Color color = new Color(255, 210, 175);

    public pPrincipal() {
        setTitle("Pantalla Principal");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 550);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        profileImage = loadImage();

        // Crea un panel para la imagen de perfil redonda
        JPanel profilePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                int x = (getWidth() - currentSize) / 2;
                int y = (getHeight() - currentSize) / 3; // Ajusta la posición de la imagen
                Ellipse2D.Double ellipse = new Ellipse2D.Double(x, y, currentSize, currentSize);
                ((Graphics2D) g).setClip(ellipse);
                g.drawImage(profileImage, x, y, currentSize, currentSize, this);
            }
        };
        profilePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (currentSize == 200) {
                    currentSize = 300; // Expande la imagen al hacer clic
                } else {
                    currentSize = 200; // Restablece el tamaño al hacer clic nuevamente
                }
                profilePanel.repaint();
            }
        });
        profilePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(profilePanel);

        // Crea un mensaje de bienvenida
        JLabel welcomeLabel = new JLabel("Bienvenido, " + pFunciones.nombreUsuario);
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(welcomeLabel);

        // Crea un espacio en blanco para ajustar la posición
        panel.add(Box.createVerticalStrut(30));

        // Crea un panel para los créditos
        JPanel creditsPanel = new JPanel();
        creditsPanel.setLayout(new BoxLayout(creditsPanel, BoxLayout.Y_AXIS));
        creditsPanel.add(new JLabel("Créditos: " + pFunciones.creditosUsuario));
        creditsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(creditsPanel);

        // Crea un espacio en blanco para ajustar la posición
        panel.add(Box.createVerticalStrut(30));

        // Crea un botón "Entrar"
        JButton enterButton = new JButton("Entrar");
        enterButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        enterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	dispose();
            	SwingUtilities.invokeLater(new Runnable() {
    	            @Override
    	            public void run() {
    	                new pMenuPrincipal().setVisible(true);
    	            }
    	        });            }
        });

        panel.add(enterButton);

        // Agrega el panel principal al marco
        add(panel);
    }

    private BufferedImage loadImage() {
        try {
            return ImageIO.read(new File(pFunciones.fotoUsuario));
        } catch (IOException e) {
            e.printStackTrace();
            getContentPane().setBackground(color);
            return null;
        }
    }
}