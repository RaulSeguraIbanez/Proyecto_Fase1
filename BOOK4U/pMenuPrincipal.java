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
	
	  private JButton misreservas;
	  private JButton inicioButton;
	  private JButton irAPrincipalButton;
	  
	  public pMenuPrincipal() {
	        // Configuración de la ventana principal
	        setTitle("Menú Principal");
	        setSize(600, 400);
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setLocationRelativeTo(null); 
	        setLayout(new BorderLayout());
	        setBackground(new Color(255, 210, 175));

	        // Panel central con la imagen
	        JPanel panelCentral = new JPanel();
	        ImageIcon imagen = new ImageIcon("src/imagenes/LogoBOOK4U.png"); 
	        JLabel labelImagen = new JLabel(imagen);
	        panelCentral.add(labelImagen);

	        // Panel inferior con los botones
	        JPanel panelBotones = new JPanel();
	        JButton btnVerReservas = new JButton("Ver Reservas");
	        JButton btnIrAInicio = new JButton("Ir a Inicio");
	        JButton btnIrAPrincipal = new JButton("Ir a Principal");

	       
	        btnVerReservas.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	               
	            	 pMisReservas mp = new  pMisReservas();
	                    mp.setVisible(true);
	                    
	                
	                new pMisReservas().setVisible(true);
	            }
	        });

	        btnIrAInicio.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	               
	                dispose();
	                new pInicio().setVisible(true);
	            }
	        });

	        btnIrAPrincipal.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	              
	                dispose();
	                new pPrincipal().setVisible(true);
	            }
	        });

	        panelBotones.add(btnVerReservas);
	        panelBotones.add(btnIrAInicio);
	        panelBotones.add(btnIrAPrincipal);

	        
	        add(panelCentral, BorderLayout.CENTER);
	        add(panelBotones, BorderLayout.SOUTH);
	    }

	    public static void main(String[] args) {
	       
	        SwingUtilities.invokeLater(new Runnable() {
	            @Override
	            public void run() {
	                new pMenuPrincipal().setVisible(true);
	            }
	        });
	    }
	}
