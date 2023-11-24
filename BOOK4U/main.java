	package BOOK4U;

import javax.swing.*;

public class main {

    public static void main(String[] args) {
        JFrame frame = new JFrame("BOOK4U");
        pInicio panel = new pInicio();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.setSize(800, 800);        
        frame.setVisible(true);

    }
}