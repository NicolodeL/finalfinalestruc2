package VisualizacionGrafica;

import Clases.Bacteria;
import Clases.Experimento;
import Experimentos.Almacenamiento;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Interfaz extends JFrame {
    private JList<Experimento> experimentoList;
    private JList<Bacteria> bacteriaList;
    private JButton agregarPoblacionButton;
    private JButton addButton;
    private JButton saveButton;
    private JButton loadButton;
    private JButton borrarPoblacionButton;
    private JButton informacionButton;






    public Interfaz() {
        super("Gestión de Experimentos");
        setLayout(new BorderLayout());



        experimentoList = new JList<>();
        bacteriaList = new JList<>();
        experimentoList.setModel(new DefaultListModel<Experimento>());
        agregarPoblacionButton = new JButton("Agregar Población");
        addButton = new JButton("Agregar Experimento");
        saveButton = new JButton("Guardar Experimentos");
        loadButton = new JButton("Cargar Experimentos");
        borrarPoblacionButton = new JButton("Borrar Población");
        informacionButton = new JButton("Información");

        add(new JScrollPane(experimentoList), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(loadButton);
        buttonPanel.add(borrarPoblacionButton);
        buttonPanel.add(informacionButton);
        add(buttonPanel, BorderLayout.SOUTH);

        addButton.addActionListener(e -> {
            try {
                agregarExperimento();
            } catch (ParseException ex) {
                throw new RuntimeException(ex);
            }
        });
        JList<Bacteria> bacteriaList = new JList<>(new DefaultListModel<Bacteria>());
        add(new JScrollPane(bacteriaList), BorderLayout.EAST);

        experimentoList.addListSelectionListener(e -> {
            Experimento experimento = experimentoList.getSelectedValue();
            if (experimento != null) {
                DefaultListModel<Bacteria> model = (DefaultListModel<Bacteria>) bacteriaList.getModel();
                model.clear();
                for (Bacteria bacteria : experimento.obtenerBacterias()) {
                    model.addElement(bacteria);
                }
            }
        });

        JButton agregarPoblacionButton = new JButton("Agregar Población");
        buttonPanel.add(agregarPoblacionButton);
        agregarPoblacionButton.addActionListener(e -> {
            try {
                agregarBacteria();
            } catch (ParseException ex) {
                throw new RuntimeException(ex);
            }
        });

        borrarPoblacionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Bacteria bacteriaSeleccionada = bacteriaList.getSelectedValue();
                if (bacteriaSeleccionada != null) {
                    // Elimina la bacteria de la lista en la interfaz
                    ((DefaultListModel<Bacteria>) bacteriaList.getModel()).removeElement(bacteriaSeleccionada);

                    // Obtiene el experimento seleccionado
                    Experimento experimentoSeleccionado = experimentoList.getSelectedValue();

                    // Elimina la bacteria del experimento seleccionado
                    if (experimentoSeleccionado != null) {
                        experimentoSeleccionado.obtenerBacterias().remove(bacteriaSeleccionada);
                    }
                } else {
                    JOptionPane.showMessageDialog(Interfaz.this, "No se ha seleccionado ninguna población para borrar.");
                }
            }
        });
        informacionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Experimento experimentoSeleccionado = experimentoList.getSelectedValue();
                Bacteria bacteriaSeleccionada = bacteriaList.getSelectedValue();
                if (experimentoSeleccionado != null && bacteriaSeleccionada != null) {
                    String informacionBacteria = bacteriaSeleccionada.toString(); // Asume que tienes un método toString() en la clase Bacteria que devuelve toda la información de la bacteria
                    JOptionPane.showMessageDialog(null, informacionBacteria);

                    try (PrintWriter out = new PrintWriter(new FileWriter("experimento_" + experimentoSeleccionado.getNombre() + ".txt", true))) {
                        out.println(informacionBacteria);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        informacionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Bacteria bacteriaSeleccionada = bacteriaList.getSelectedValue();
                if (bacteriaSeleccionada != null) {
                    String informacionBacteria = bacteriaSeleccionada.getInformacionDetallada();
                    JOptionPane.showMessageDialog(null, informacionBacteria);

                    try (PrintWriter out = new PrintWriter(new FileWriter("bacteria_" + bacteriaSeleccionada.getNombre() + ".txt", true))) {
                        out.println(informacionBacteria);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });



        saveButton.addActionListener(e -> guardarExperimentos());
        loadButton.addActionListener(e -> cargarExperimentos());
    }

    private void agregarExperimento() throws ParseException {
        String nombre = JOptionPane.showInputDialog(this, "Por favor, introduce el nombre del experimento");
        Experimento experimento = new Experimento(nombre);
        DefaultListModel<Experimento> model = (DefaultListModel<Experimento>) experimentoList.getModel();
        model.addElement(experimento);


    }

    private void agregarBacteria() throws ParseException {
        Experimento experimento = experimentoList.getSelectedValue();
        if (experimento == null) {
            JOptionPane.showMessageDialog(this, "Por favor, selecciona un experimento");
            return;
        }
        String nombre = JOptionPane.showInputDialog(this, "Introduce el nombre de la población");
        String fechaInicioStr = JOptionPane.showInputDialog(this, "Introduce la fecha de inicio de la población (dd/MM/yyyy)");
        Date fechaInicio = null;
        try {
            fechaInicio = new SimpleDateFormat("dd/MM/yyyy").parse(fechaInicioStr);
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "La fecha de inicio debe estar en el formato correcto (dd/MM/yyyy)");
            return;
        }

        String duracionStr = JOptionPane.showInputDialog(this, "Introduce la duración de la población en días");
        int duracion = Integer.parseInt(duracionStr);
        if (duracion <= 0) {
            JOptionPane.showMessageDialog(this, "La duración debe ser un valor entero mayor que 0");
            return;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fechaInicio);
        calendar.add(Calendar.DATE, duracion);
        Date fechaFin = calendar.getTime();

        String numBacteriasInicialesStr = JOptionPane.showInputDialog(this, "Introduce el número de bacterias iniciales");
        int numBacteriasIniciales = Integer.parseInt(numBacteriasInicialesStr);
        if (numBacteriasIniciales < 0) {
            JOptionPane.showMessageDialog(this, "El número de bacterias iniciales no puede ser negativo");
            return;
        }

        String temperaturaStr = JOptionPane.showInputDialog(this, "Introduce la temperatura a la que se someterán las bacterias");
        int temperatura = Integer.parseInt(temperaturaStr);
        if (temperatura < 0) {
            JOptionPane.showMessageDialog(this, "La temperatura no puede ser negativa");
            return;
        }

        String[] condiciones = {"Alta", "Media", "Baja"};
        String condicionLuminosidad = (String) JOptionPane.showInputDialog(this, "Selecciona las condiciones de luminosidad", "Luminosidad", JOptionPane.QUESTION_MESSAGE, null, condiciones, condiciones[0]);

        String comidaInicialStr = JOptionPane.showInputDialog(this, "Introduce la cantidad inicial de comida que se le dará el primer día");
        String[] patrones = {"Incremento lineal-decremento lineal", "Constante", "Incremento", "Parcialmente constante"};
        String patronSimulacion = (String) JOptionPane.showInputDialog(this, "Selecciona el tipo de patrón de simulación", "Patrón de Simulación", JOptionPane.QUESTION_MESSAGE, null, patrones, patrones[0]);

        Bacteria bacteria = null;
        int comidaInicial = 0;
        int diaIncrementoComida = 0;
        int comidaDiaIncremento = 0;
        int comidaFinal = 0;
        switch (patronSimulacion) {
            case "Incremento lineal-decremento lineal":
                if (comidaInicial < 0 || comidaInicial >= 300000) {
                    JOptionPane.showMessageDialog(this, "La cantidad inicial de comida debe ser un valor entero menor que 300000");
                    return;
                }

                String diaIncrementoComidaStr = JOptionPane.showInputDialog(this, "Introduce el día hasta el cual se debe incrementar la cantidad de comida");
                diaIncrementoComida = Integer.parseInt(diaIncrementoComidaStr);
                if (diaIncrementoComida <= 0 || diaIncrementoComida >= 30) {
                    JOptionPane.showMessageDialog(this, "El día hasta el cual se debe incrementar la cantidad de comida debe ser un valor entero mayor que 0 y menor que 30");
                    return;
                }

                String comidaDiaIncrementoStr = JOptionPane.showInputDialog(this, "Introduce la comida de este día");
                comidaDiaIncremento = Integer.parseInt(comidaDiaIncrementoStr);
                if (comidaDiaIncremento < 0 || comidaDiaIncremento >= 300) {
                    JOptionPane.showMessageDialog(this, "La comida de este día debe ser un valor entero menor que 300");
                    return;
                }

                String comidaFinalStr = JOptionPane.showInputDialog(this, "Introduce la cantidad final de comida en el día 30");
                comidaFinal = Integer.parseInt(comidaFinalStr);
                if (comidaFinal < 0 || comidaFinal >= 300) {
                    JOptionPane.showMessageDialog(this, "La cantidad final de comida en el día 30 debe ser un valor entero menor que 300");
                    return;
                }
                break;
            case "Constante":
                String comidaConstanteStr = JOptionPane.showInputDialog(this, "Introduce la cantidad de comida que se dará todos los días");
                int comidaConstante = Integer.parseInt(comidaConstanteStr);
                if (comidaConstante < 0 || comidaConstante >= 300000) {
                    JOptionPane.showMessageDialog(this, "La cantidad de comida debe ser un valor entero menor que 300000");
                    return;
                }
                comidaInicial = comidaConstante;
                diaIncrementoComida = 0; // No hay incremento de comida
                comidaDiaIncremento = comidaConstante;
                comidaFinal = comidaConstante;
                break;

            case "Incremento":
                // Aquí es donde colocarías el código para manejar este tipo de patrón de simulación
                break;
            case "Parcialmente constante":
                // Aquí es donde colocarías el código para manejar este tipo de patrón de simulación
                break;
        }

        bacteria = new Bacteria.Builder(nombre, fechaInicio, fechaFin, numBacteriasIniciales, temperatura, condicionLuminosidad)
                .comidaInicial(comidaInicial)
                .diaIncrementoComida(diaIncrementoComida)
                .comidaDiaIncremento(comidaDiaIncremento)
                .comidaFinal(comidaFinal)
                .build();
        experimento.agregarBacteria(bacteria);

        DefaultListModel<Experimento> model = (DefaultListModel<Experimento>) experimentoList.getModel();

        int index = model.indexOf(experimento);
        if (index != -1) {
            model.set(index, experimento);
        }

        ListModel modelBacteria = bacteriaList.getModel();
        DefaultListModel<Bacteria> bacteriaModel;
        if (modelBacteria instanceof DefaultListModel) {
            bacteriaModel = (DefaultListModel<Bacteria>) modelBacteria;
        } else {
            bacteriaModel = new DefaultListModel<>();
            bacteriaList.setModel(bacteriaModel);
        }
        bacteriaModel.addElement(bacteria);

    }


    private void guardarExperimentos() {
        DefaultListModel<Experimento> model = (DefaultListModel<Experimento>) experimentoList.getModel();
        for (int i = 0; i < model.getSize(); i++) {
            Experimento experimento = model.getElementAt(i);
            try {
                try (PrintWriter out = new PrintWriter(new FileWriter("src/main/resources/ExperimentosGuardados/experimento_" + experimento.getNombre() + ".txt", false))) {
                    out.println("Nombre del experimento: " + experimento.getNombre());
                    for (Bacteria bacteria : experimento.obtenerBacterias()) {
                        out.println("Nombre de la bacteria: " + bacteria.getNombre());
                        out.println("Fecha de inicio: " + bacteria.getFechaInicio());
                        out.println("Fecha de fin: " + bacteria.getFechaFin());
                        out.println("Número de bacterias iniciales: " + bacteria.getNumBacteriasIniciales());
                        out.println("Temperatura: " + bacteria.getTemperatura());
                        out.println("Condiciones de luminosidad: " + bacteria.getCondicionesLuminosidad());
                        out.println("Comida inicial: " + bacteria.getComidaInicial());
                        out.println("Día de incremento de comida: " + bacteria.getDiaIncrementoComida());
                        out.println("Comida en el día de incremento: " + bacteria.getComidaDiaIncremento());
                        out.println("Comida final: " + bacteria.getComidaFinal());
                    }
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error al guardar el experimento " + experimento.getNombre());
            }
        }
    }

    private void cargarExperimentos() {
        File folder = new File("src/main/resources/ExperimentosGuardados");
        File[] listOfFiles = folder.listFiles();

        if (listOfFiles != null) {
            for (File file : listOfFiles) {
                if (file.isFile()) {
                    try {
                        BufferedReader reader = new BufferedReader(new FileReader(file));
                        String nombreExperimento = reader.readLine().split(": ")[1]; // asumimos que la primera línea del archivo es el nombre del experimento
                        Experimento experimento = new Experimento(nombreExperimento);

                        String line;
                        StringBuilder informacionBacteria = new StringBuilder();
                        while ((line = reader.readLine()) != null) {
                            // Aquí puedes procesar cada línea del archivo y agregar la información al objeto experimento
                            // Por ejemplo, si cada línea representa una bacteria, puedes crear un nuevo objeto Bacteria y agregarlo al experimento
                            if (line.startsWith("Nombre de la bacteria: ")) {
                                if (informacionBacteria.length() > 0) {
                                    Bacteria bacteria = Bacteria.fromInformacionDetallada(informacionBacteria.toString());
                                    experimento.agregarBacteria(bacteria);
                                    informacionBacteria = new StringBuilder();
                                }
                            }
                            informacionBacteria.append(line).append("\n");
                        }
                        // Procesar la última bacteria
                        if (informacionBacteria.length() > 0) {
                            Bacteria bacteria = Bacteria.fromInformacionDetallada(informacionBacteria.toString());
                            experimento.agregarBacteria(bacteria);
                        }

                        ((DefaultListModel<Experimento>) experimentoList.getModel()).addElement(experimento);
                    } catch (IOException e) {
                        JOptionPane.showMessageDialog(this, "Error al cargar el experimento " + file.getName());
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }
}