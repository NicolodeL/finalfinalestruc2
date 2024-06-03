package Experimentos;

import Clases.Experimento;

import java.io.*;

public class Almacenamiento {
    public static void guardarExperimento(Experimento experimento, String nombreArchivo) throws IOException {
        File directory = new File("src/main/resources/ExperimentosGuardados");
        if (!directory.exists()) {
            directory.mkdirs();
        }
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(directory.getPath() + "/" + nombreArchivo))) {
            oos.writeObject(experimento);
        }
    }

    public static Experimento cargarExperimento(String rutaArchivo) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(rutaArchivo))) {
            return (Experimento) ois.readObject();
        }
    }
}