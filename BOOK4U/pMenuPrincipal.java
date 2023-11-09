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
        panel.setBackground(new Color(255, 210, 175)); // Color naranja claro
        panel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Imagen de usuario redonda
        JPanel usuarioPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                int width = Math.min(getWidth(), getHeight());
                BufferedImage image = new BufferedImage(width, width, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g2 = image.createGraphics();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE); // Color de fondo
                g2.fill(new Ellipse2D.Double(0, 0, width, width));

                Image usuarioImage = new ImageIcon("src/imagenes/imagen_usuario.png").getImage(); // Asegúrate de tener la imagen del usuario
                g2.setClip(new Ellipse2D.Double(0, 0, width, width));
                g2.drawImage(usuarioImage, 0, 0, width, width, null);

                g2.dispose();
                g.drawImage(image, 0, 0, this);
            }
        };

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth =4;
        gbc.anchor = GridBagConstraints.CENTER;
        usuarioPanel.setPreferredSize(new Dimension(200, 200)); // Tamaño del panel del usuario
        panel.add(usuarioPanel, gbc);
// Imagen de empresa redonda
        ImageIcon empresaImage = new ImageIcon("src/imagenes/LogoBOOK4U.png");
       
       
        JLabel empresaLabel = new JLabel(new ImageIcon(getRoundedImage(empresaImage.getImage(), 150, 150)));
        gbc.gridy = 1;
        panel.add(empresaLabel, gbc);

        // Botones
        gbc.gridwidth = 1;
        gbc.gridy =2;

        addButton(panel, "Nueva Reserva");
        addButton(panel,"Ver Reservas");

        addButton(panel,"Inicio");

        // Agregar el panel al JFrame
        add(panel);

        setVisible(true);
    }
private void addButton(JPanel panel, String text) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.CENTER;

   
JButton button = new JButton(text);
        button.setBackground(new Color(0, 102, 204)); // Color azul
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(150, 30));
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setBorder(new RoundButtonBorder(15));

        panel.add(button, gbc);
    }

private Image getRoundedImage(Image image, int width, int height) {
BufferedImage roundedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB); 
Graphics2D g2 = roundedImage.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setClip(new Ellipse2D.Double(0, 0, width, height));
        g2.drawImage(image, 0, 0, width, height, null);
        g2.dispose();    
return roundedImage;
    }

  /*  public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {      
new pMenuPrincipal();
        });
    } */
}  
class RoundButtonBorder extends AbstractBorder {
    private int radius;  
public RoundButtonBorder(int radius) {
        this.radius = radius;
    }  
@Override  
public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        g.setColor(Color.BLACK);
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);      
 new RoundRectangle2D.Double(x, y, width - 1, height - 1, radius, radius);
    }
}