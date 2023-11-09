package BOOK4U;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.toedter.calendar.JDateChooser;
import java.sql.Date;

public class pNuevaReserva {
    private JDateChooser fechaInicioChooser;
    private JDateChooser fechaFinChooser;

    public pNuevaReserva() {
        JFrame frame = new JFrame("Sistema de Reservas");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4, 2));
        panel.setBackground(new Color(255, 210, 175));

        Font labelFont = new Font("Sans-serif", Font.PLAIN, 18);
        Font componentFont = new Font("Sans-serif", Font.PLAIN, 16);

        JLabel fechaInicioLabel = new JLabel("Fecha de inicio:");
        fechaInicioLabel.setFont(labelFont);

        fechaInicioChooser = new JDateChooser();
        fechaInicioChooser.setFont(componentFont);

        JLabel fechaFinLabel = new JLabel("Fecha de fin:");
        fechaFinLabel.setFont(labelFont);

        fechaFinChooser = new JDateChooser();
        fechaFinChooser.setFont(componentFont);

        JButton reservarButton = new JButton("Reservar");
        reservarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Date fechaInicio = new Date(fechaInicioChooser.getDate().getTime());
                Date fechaFin = new Date(fechaFinChooser.getDate().getTime());

                pFunciones.registrarFechasEstancia(fechaInicio, fechaFin);

                // Puedes mostrar un mensaje de confirmación o manejar cualquier otro resultado aquí
            }
        });

        panel.add(fechaInicioLabel);
        panel.add(fechaInicioChooser);
        panel.add(fechaFinLabel);
        panel.add(fechaFinChooser);
        panel.add(new JLabel());
        panel.add(reservarButton);

        frame.add(panel);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new pNuevaReserva();
        });
    }
}

