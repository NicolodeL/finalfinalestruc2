package Clases;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Experimento implements Serializable {
    private List<Bacteria> bacterias;
    private String nombre;

    public Experimento(String nombre) {
        this.bacterias = new ArrayList<>();
        this.nombre = nombre;
    }
    @Override
    public String toString() {
        return nombre;
    }

    public void agregarBacteria(Bacteria bacteria) {
        this.bacterias.add(bacteria);
    }

    public List<Bacteria> obtenerBacterias() {
        return this.bacterias;
    }
    public String getNombre() {
        return this.nombre;
    }

    public String getInformacionDetallada() {
        // Aquí debes devolver una cadena con toda la información del experimento
        // Por ejemplo:
        return "Nombre del experimento: " + nombre ;
        // ... y así sucesivamente para todos los campos del experimento
    }


}

