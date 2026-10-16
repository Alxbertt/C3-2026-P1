package formularios;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class FrmInicio extends JFrame {

    private JButton btnClientes, btnProductos, btnSalir;

    // Se guardan las ventanas para abrirlas una sola vez y no perder los datos al cerrarlas
    private FrmClientes ventanaClientes;
    private FrmProductos ventanaProductos;

    public FrmInicio() {
        setTitle("Sistema de Tienda");
        setSize(500, 360);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        construirInterfaz();
    }

    private void construirInterfaz() {
        setLayout(new BorderLayout(0, 10));

        // ----- Parte superior: título y subtítulo -----
        JPanel panelTitulo = new JPanel();
        panelTitulo.setLayout(new BoxLayout(panelTitulo, BoxLayout.Y_AXIS));
        panelTitulo.setBorder(BorderFactory.createEmptyBorder(20, 10, 5, 10));

        JLabel titulo = new JLabel("SISTEMA DE TIENDA");
        titulo.setFont(new Font("Tahoma", Font.BOLD, 26));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitulo = new JLabel("Seleccione el módulo con el que desea trabajar");
        subtitulo.setFont(new Font("Tahoma", Font.PLAIN, 13));
        subtitulo.setForeground(Color.DARK_GRAY);
        subtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        panelTitulo.add(titulo);
        panelTitulo.add(Box.createVerticalStrut(6));
        panelTitulo.add(subtitulo);
        add(panelTitulo, BorderLayout.NORTH);

        // ----- Centro: los dos botones grandes -----
        JPanel panelModulos = new JPanel(new GridLayout(1, 2, 20, 0));
        panelModulos.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));

        btnClientes = crearBotonModulo("Clientes", "clientes.png");
        btnProductos = crearBotonModulo("Productos", "productos.png");

        btnClientes.addActionListener(e -> abrirClientes());
        btnProductos.addActionListener(e -> abrirProductos());

        panelModulos.add(btnClientes);
        panelModulos.add(btnProductos);
        add(panelModulos, BorderLayout.CENTER);

        // ----- Parte inferior: botón salir y créditos -----
        JPanel panelInferior = new JPanel(new BorderLayout());
        panelInferior.setBorder(BorderFactory.createEmptyBorder(0, 30, 12, 30));

        btnSalir = new JButton("Salir");
        btnSalir.setFocusPainted(false);
        btnSalir.addActionListener(e -> confirmarSalida());

        JLabel creditos = new JLabel("Programación 1 - Tarea Java Swing   |   Elaborado por: Albert Hichez");
        creditos.setFont(new Font("Tahoma", Font.PLAIN, 11));
        creditos.setForeground(Color.GRAY);

        panelInferior.add(creditos, BorderLayout.WEST);
        panelInferior.add(btnSalir, BorderLayout.EAST);
        add(panelInferior, BorderLayout.SOUTH);
    }

    private JButton crearBotonModulo(String texto, String archivoIcono) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Tahoma", Font.BOLD, 16));
        boton.setFocusPainted(false);

        ImageIcon icono = cargarIcono(archivoIcono, 72);
        if (icono != null) {
            boton.setIcon(icono);
            boton.setVerticalTextPosition(SwingConstants.BOTTOM);
            boton.setHorizontalTextPosition(SwingConstants.CENTER);
            boton.setIconTextGap(12);
        }
        return boton;
    }

    // =====================================================
    //                 ACCIONES
    // =====================================================

    private void abrirClientes() {
    if (ventanaClientes == null) {
        ventanaClientes = new FrmClientes(this);
    }
    ventanaClientes.setVisible(true);
    ventanaClientes.toFront();
    setVisible(false);   // esconde el menú mientras se trabaja en el formulario
}

    private void abrirProductos() {
    if (ventanaProductos == null) {
        ventanaProductos = new FrmProductos(this);
    }
    ventanaProductos.setVisible(true);
    ventanaProductos.toFront();
    setVisible(false);
}

    private void confirmarSalida() {
        int respuesta = JOptionPane.showConfirmDialog(this,
                "¿Desea cerrar el sistema?\nLos datos registrados se perderán.",
                "Salir", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (respuesta == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    private ImageIcon cargarIcono(String nombre, int tamano) {
        String[] carpetas = {
            "Recursos",
            "Tarea java Swing/Recursos",
            "Swing/Tarea java Swing/Recursos",
            "C3-2026 P1/Swing/Tarea java Swing/Recursos"
        };

        for (String carpeta : carpetas) {
            File archivo = new File(carpeta + File.separator + nombre);
            if (archivo.exists()) {
                Image imagen = new ImageIcon(archivo.getPath()).getImage()
                        .getScaledInstance(tamano, tamano, Image.SCALE_SMOOTH);
                return new ImageIcon(imagen);
            }
        }
        return null;
    }
}