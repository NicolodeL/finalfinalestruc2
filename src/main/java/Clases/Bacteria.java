package Clases;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Bacteria implements Serializable {
    private String nombre;
    private Date fechaInicio;
    private Date fechaFin;
    private int numBacteriasIniciales;
    private double temperatura;
    private String condicionesLuminosidad;
    private int comidaInicial;
    private int diaIncrementoComida;
    private int comidaDiaIncremento;
    private int comidaFinal;

    private Bacteria(Builder builder) {
        this.nombre = builder.nombre;
        this.fechaInicio = builder.fechaInicio;
        this.fechaFin = builder.fechaFin;
        this.numBacteriasIniciales = builder.numBacteriasIniciales;
        this.temperatura = builder.temperatura;
        this.condicionesLuminosidad = builder.condicionesLuminosidad;
        this.comidaInicial = builder.comidaInicial;
        this.diaIncrementoComida = builder.diaIncrementoComida;
        this.comidaDiaIncremento = builder.comidaDiaIncremento;
        this.comidaFinal = builder.comidaFinal;
    }

    public String getNombre() {
        return nombre;
    }

    public String getFechaInicio() {
        return fechaInicio.toString();
    }

    public String getFechaFin() {
        return fechaFin.toString();
    }

    public String getNumBacteriasIniciales() {
        return Integer.toString(numBacteriasIniciales);
    }

    public String getTemperatura() {
        return Double.toString(temperatura);
    }

    public String getCondicionesLuminosidad() {
        return condicionesLuminosidad;
    }

    public String getComidaInicial() {
        return Integer.toString(comidaInicial);
    }

    public String getDiaIncrementoComida() {
        return Integer.toString(diaIncrementoComida);
    }

    public String getComidaDiaIncremento() {
        return Integer.toString(comidaDiaIncremento);
    }

    public String getComidaFinal() {
        return Integer.toString(comidaFinal);
    }

    public static class Builder {
        private final String nombre;
        private final Date fechaInicio;
        private final Date fechaFin;
        private final int numBacteriasIniciales;
        private final double temperatura;
        private final String condicionesLuminosidad;

        private int comidaInicial;
        private int diaIncrementoComida;
        private int comidaDiaIncremento;
        private int comidaFinal;

        public Builder(String nombre, Date fechaInicio, Date fechaFin, int numBacteriasIniciales, double temperatura, String condicionesLuminosidad) {
            this.nombre = nombre;
            this.fechaInicio = fechaInicio;
            this.fechaFin = fechaFin;
            this.numBacteriasIniciales = numBacteriasIniciales;
            this.temperatura = temperatura;
            this.condicionesLuminosidad = condicionesLuminosidad;
        }

        public Builder comidaInicial(int val) {
            comidaInicial = val;
            return this;
        }

        public Builder diaIncrementoComida(int val) {
            diaIncrementoComida = val;
            return this;
        }

        public Builder comidaDiaIncremento(int val) {
            comidaDiaIncremento = val;
            return this;
        }

        public Builder comidaFinal(int val) {
            comidaFinal = val;
            return this;
        }

        public Bacteria build() {
            return new Bacteria(this);
        }
    }

    public int calcularComida(int dia) {
        // Implementar la lógica para calcular la cantidad de comida en el día dado
        return 0; // Cambiar esto por la implementación correcta
    }
    public String getInformacionDetallada() {
        StringBuilder informacion = new StringBuilder();
        informacion.append("Nombre de la bacteria: ").append(nombre).append("\n")
                .append("Fecha de inicio: ").append(fechaInicio).append("\n")
                .append("Fecha de fin: ").append(fechaFin).append("\n")
                .append("Número de bacterias iniciales: ").append(numBacteriasIniciales).append("\n")
                .append("Temperatura: ").append(temperatura).append("\n")
                .append("Condiciones de luminosidad: ").append(condicionesLuminosidad).append("\n")
                .append("Comida inicial: ").append(comidaInicial).append("\n")
                .append("Día de incremento de comida: ").append(diaIncrementoComida).append("\n")
                .append("Comida en el día de incremento: ").append(comidaDiaIncremento).append("\n")
                .append("Comida final: ").append(comidaFinal).append("\n");

        // Calcular la dosis de alimento para cada día
        double incrementoDiario = (comidaFinal - comidaInicial) / 29.0; // asumimos que el incremento es lineal
        for (int dia = 1; dia <= 30; dia++) {
            double dosisDia = comidaInicial + (dia - 1) * incrementoDiario;
            informacion.append("Dosis de alimento para el día ").append(dia).append(": ").append(dosisDia).append("\n");
        }





        return informacion.toString();
    }

    public static Bacteria fromInformacionDetallada(String informacion) throws ParseException {
        String[] lines = informacion.split("\n");
        String nombre = null;
        Date fechaInicio = null;
        Date fechaFin = null;
        int numBacteriasIniciales = 0;
        double temperatura = 0.0;
        String condicionesLuminosidad = null;
        int comidaInicial = 0;
        int diaIncrementoComida = 0;
        int comidaDiaIncremento = 0;
        int comidaFinal = 0;

        for (String line : lines) {
            String[] parts = line.split(": ");
            if (parts.length < 2) {
                continue;
            }
            switch (parts[0]) {
                case "Nombre de la bacteria":
                    nombre = parts[1];
                    break;
                case "Fecha de inicio":
                    fechaInicio = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US).parse(parts[1]);
                    break;
                case "Fecha de fin":
                    fechaFin = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US).parse(parts[1]);
                    break;
                case "Número de bacterias iniciales":
                    numBacteriasIniciales = Integer.parseInt(parts[1]);
                    break;
                case "Temperatura":
                    temperatura = Double.parseDouble(parts[1]);
                    break;
                case "Condiciones de luminosidad":
                    condicionesLuminosidad = parts[1];
                    break;
                case "Comida inicial":
                    comidaInicial = Integer.parseInt(parts[1]);
                    break;
                case "Día de incremento de comida":
                    diaIncrementoComida = Integer.parseInt(parts[1]);
                    break;
                case "Comida en el día de incremento":
                    comidaDiaIncremento = Integer.parseInt(parts[1]);
                    break;
                case "Comida final":
                    comidaFinal = Integer.parseInt(parts[1]);
                    break;
            }
        }

        return new Bacteria.Builder(nombre, fechaInicio, fechaFin, numBacteriasIniciales, temperatura, condicionesLuminosidad)
                .comidaInicial(comidaInicial)
                .diaIncrementoComida(diaIncrementoComida)
                .comidaDiaIncremento(comidaDiaIncremento)
                .comidaFinal(comidaFinal)
                .build();
    }


}





