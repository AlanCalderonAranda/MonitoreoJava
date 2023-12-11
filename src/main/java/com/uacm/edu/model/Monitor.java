package com.uacm.edu.model;

import java.io.File;
import java.lang.management.ManagementFactory;
import com.sun.management.OperatingSystemMXBean;
import java.text.DecimalFormat;

public class Monitor {

    public static void pruebamonitor() {
        DecimalFormat df = new DecimalFormat("#.##");
        // La tarea que se ejecutará cada 5 segundos
        OperatingSystemMXBean osBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        double cpuUsage = osBean.getSystemCpuLoad();

        // Verificar si el valor de la carga de la CPU es válido
        if (cpuUsage >= 0) {
            System.out.println("Uso de CPU: " + df.format(cpuUsage * 100) + "%");
        } else {
            System.out.println("Uso de CPU: 0%");
        }

        // Memoria RAM virtual
        long totalMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory(); //Memoria RAM UTILIZADA
        System.out.println("Memoria RAM utilizada: " + (totalMemory / (1024)) + " KB");

        // Tamaño del proyecto en NetBeans
        String projectPath = "C:\\Users\\alanc\\Documents\\NetBeansProjects\\PruebaExperimento";

        long projectSize = calculateDirectorySize(new File(projectPath));
        System.out.println("Tamaño del proyecto: " + df.format(projectSize / 1024.0) + " KB\n");
    }

    private static long calculateDirectorySize(File directory) {
        long size = 0;
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    size += file.length();
                } else if (file.isDirectory()) {
                    size += calculateDirectorySize(file);
                }
            }
        }
        return size;
    }
}
