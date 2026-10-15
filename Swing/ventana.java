package Swing;
import java.awt.Color;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ventana {
    JFrame ventana;
    JLabel label, label1;
    JButton boton,boton1, boton3;
    JPanel panel1, panel2;

    void crearventana(){
        ventana = new JFrame();
        ventana.setTitle("Titulo");
        ventana.setSize(200, 200);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setLocationRelativeTo(null);
        
        ventana.setLayout(new BoxLayout(ventana.getContentPane(), BoxLayout.X_AXIS));
        
        ventana.add(label);
        ventana.add(label1);
        

        ventana.setVisible(true);
        ventana.add(panel1);
    }

    void crearpanel(){

        panel1 = new JPanel();
        panel1.setBackground(Color.cyan);
        boton = new JButton("Enviar");
        boton1=new JButton("cancelar");
        panel1.setLayout(null);
        boton.setBounds(0, 20, 100, 50);
        boton1.setBounds(0, 70, 100, 50);
        panel1.add(boton);
        panel1.add(boton1);
        

        panel2 = new JPanel();
        panel2.setBackground(Color.yellow);
        boton3=new JButton("Enviar");
        panel2.setBounds(120, 20,100,100);
        panel2.add(boton3);
        panel1.add(panel2);

        label = new JLabel("hola");
        label1 = new JLabel();
        label1.setText("Como");
    }

    ventana(){
        crearpanel(); 
        crearventana(); 
    }
}
