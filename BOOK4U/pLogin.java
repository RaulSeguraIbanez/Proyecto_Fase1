package BOOK4U;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;

public class pLogin extends JPanel {
    private JButton boton;
    private JTextField textField1; // Variable para almacenar el contenido del primer TextField
    private JPasswordField textField2; // Variable para almacenar el contenido del segundo TextField

    public pLogin() {
        setPreferredSize(new Dimension(800, 700));
        setBackground(Color.WHITE);
        setLayout(null);
        centrarEnPantalla();
    }

    private void centrarEnPantalla() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = getSize();

        int x = (screenSize.width - frameSize.width) / 2;
        int y = (screenSize.height - frameSize.height) / 2;

        setLocation(x, y);
    
        JLabel label1 = new JLabel("Correo: ");
        JLabel label2 = new JLabel("Contraseña: ");
        label1.setBounds(200, 460, 100, 20);
        label2.setBounds(200, 500, 100, 20);

        textField1 = new JTextField(); // Inicializa la variable
        textField2 = new JPasswordField(); // Inicializa la variable
        textField1.setBounds(300, 460, 200, 20);
        textField2.setBounds(300, 500, 200, 20);

        JLabel imagenLabel = new JLabel();
        ImageIcon imagen = new ImageIcon("src/imagenes/LogoBOOK4U.png");
        imagenLabel.setIcon(imagen);
        imagenLabel.setBounds(140, 25, 800, 400);

        boton = new JButton("Login") {
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

        boton.setBounds(300, 550, 200, 50);
        boton.setBackground(Color.GREEN);
        boton.setForeground(Color.BLACK);
        boton.setOpaque(false);

        boton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Obtener el contenido de los TextField y almacenarlo en variables
                String email = textField1.getText();
                String password = new String(textField2.getPassword());
                
                if (pFunciones.comprobarLogin(email, password) == true) {
                	SwingUtilities.getWindowAncestor(pLogin.this).dispose();
                	SwingUtilities.invokeLater(() -> {
                        pPrincipal frame = new pPrincipal();
                        frame.setVisible(true);
                    });
                } else {
                    // Las credenciales son incorrectas. Puedes mostrar un mensaje de error aquí.
                    // Por ejemplo:
                    JOptionPane.showMessageDialog(pLogin.this, "Credenciales incorrectas", "Error de inicio de sesión", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        add(label1);
        add(label2);
        add(textField1);
        add(textField2);
        add(imagenLabel);
        add(boton);
    }
}
