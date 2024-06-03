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


    public Bacteria(String nombre, Date fechaInicio, Date fechaFin, int numBacteriasIniciales, double temperatura, String condicionesLuminosidad, int comidaInicial, int diaIncrementoComida, int comidaDiaIncremento, int comidaFinal) {
        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.numBacteriasIniciales = numBacteriasIniciales;
        this.temperatura = temperatura;
        this.condicionesLuminosidad = condicionesLuminosidad;
        this.comidaInicial = comidaInicial;
        this.diaIncrementoComida = diaIncrementoComida;
        this.comidaDiaIncremento = comidaDiaIncremento;
        this.comidaFinal = comidaFinal;
    }


    public String toString() {
        return nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public int getNumBacteriasIniciales() {
        return numBacteriasIniciales;
    }

    public void setNumBacteriasIniciales(int numBacteriasIniciales) {
        this.numBacteriasIniciales = numBacteriasIniciales;
    }

    public double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(double temperatura) {
        this.temperatura = temperatura;
    }

    public String getCondicionesLuminosidad() {
        return condicionesLuminosidad;
    }

    public void setCondicionesLuminosidad(String condicionesLuminosidad) {
        this.condicionesLuminosidad = condicionesLuminosidad;
    }

    public int getComidaInicial() {
        return comidaInicial;
    }

    public void setComidaInicial(int comidaInicial) {
        this.comidaInicial = comidaInicial;
    }

    public int getDiaIncrementoComida() {
        return diaIncrementoComida;
    }

    public void setDiaIncrementoComida(int diaIncrementoComida) {
        this.diaIncrementoComida = diaIncrementoComida;
    }

    public int getComidaDiaIncremento() {
        return comidaDiaIncremento;
    }

    public void setComidaDiaIncremento(int comidaDiaIncremento) {
        this.comidaDiaIncremento = comidaDiaIncremento;
    }

    public int getComidaFinal() {
        return comidaFinal;
    }

    public void setComidaFinal(int comidaFinal) {
        this.comidaFinal = comidaFinal;
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

        return new Bacteria(nombre, fechaInicio, fechaFin, numBacteriasIniciales, temperatura, condicionesLuminosidad, comidaInicial, diaIncrementoComida, comidaDiaIncremento, comidaFinal);
    }


}





