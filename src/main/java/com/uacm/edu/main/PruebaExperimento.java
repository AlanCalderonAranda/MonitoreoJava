package com.uacm.edu.main;

import com.uacm.edu.model.Empleado;
import com.uacm.edu.model.ExcelManager;
import com.uacm.edu.model.Monitor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class PruebaExperimento {
    
    //Monitor monitoreo = new Monitor();
    
    public static void main(String[] args) {
        //Revisamos las estadisticas del uso al inicio
        Monitor.pruebamonitor();
        String rutaCompleta = "C:/Users/alanc/Documents/NetBeansProjects/PruebaExperimento/src/main/java/com/uacm/edu/files/empleados.txt";

        File file = new File(ExcelManager.FILE_NAME);
        if (!file.exists()) {
            ExcelManager.crearNuevoLibroExcel();
        }
        //Revisamos al momento de revisar o crear el archivo excel (Empleados.xsl)
        Monitor.pruebamonitor();
        
        ArrayList<Empleado> listaEmpleados = leerEmpleadosDesdeArchivo(rutaCompleta);

        for (Empleado empleado : listaEmpleados) {
            if (!empleadoExistente(empleado)) {
                crearEmpleado(empleado);
            } else {
                System.out.println("El empleado con número " + empleado.getNumEmpleado() + " ya existe en el Excel.");
            }
            //Revisamos la carga en cada uno de los empledos
            Monitor.pruebamonitor();
        }
        
        //Revisamos la carga despues de cargar los datos en el excel
        Monitor.pruebamonitor();
        
        ExcelManager.imprimirEmpleados();
        
        //Revisamos la carga despues de imprimir los datos en el excel
        Monitor.pruebamonitor();
    }

    private static boolean empleadoExistente(Empleado empleado) {
        try {
            Workbook workbook = ExcelManager.abrirLibroExcel();

            if (workbook != null) {
                Sheet sheet = workbook.getSheetAt(0);

                for (Row row : sheet) {
                    Cell cellNumEmpleado = row.getCell(0);

                    if (cellNumEmpleado != null && cellNumEmpleado.getCellType() == CellType.NUMERIC) {
                        int numEmpleado = (int) cellNumEmpleado.getNumericCellValue();

                        if (numEmpleado == empleado.getNumEmpleado()) {
                            ExcelManager.cerrarLibroExcel(workbook);
                            return true; // El empleado ya existe
                        }
                    }
                }

                ExcelManager.cerrarLibroExcel(workbook);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false; // El empleado no existe
    }

    private static ArrayList<Empleado> leerEmpleadosDesdeArchivo(String nombreArchivo) {
        ArrayList<Empleado> listaEmpleados = new ArrayList<>();

        try ( BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(" ");
                int numEmpleado = Integer.parseInt(partes[0]);
                String nombre = partes[1];
                int edad = Integer.parseInt(partes[2]);
                char sexo = partes[3].charAt(0);
                double salario = Double.parseDouble(partes[4]);

                listaEmpleados.add(new Empleado(numEmpleado, nombre, edad, sexo, salario));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return listaEmpleados;
    }

    public static void crearEmpleado(Empleado empleadoNuevo) {
        try {
            Workbook workbook = ExcelManager.abrirLibroExcel();

            if (workbook != null) {
                Sheet sheet;
                if (workbook.getNumberOfSheets() > 0) {
                    sheet = workbook.getSheetAt(0);
                } else {
                    sheet = workbook.createSheet();
                }

                int rowNum = sheet.getPhysicalNumberOfRows();
                Row row = sheet.createRow(rowNum);

                Cell cellNumEmpleado = row.createCell(0);
                cellNumEmpleado.setCellValue(empleadoNuevo.getNumEmpleado());

                Cell cellNombre = row.createCell(1);
                cellNombre.setCellValue(empleadoNuevo.getNombre());

                Cell cellEdad = row.createCell(2);
                cellEdad.setCellValue(empleadoNuevo.getEdad());

                Cell cellSexo = row.createCell(3);
                cellSexo.setCellValue(empleadoNuevo.getSexo());

                Cell cellSalario = row.createCell(4);
                cellSalario.setCellValue(empleadoNuevo.getSalario());

                ExcelManager.cerrarLibroExcel(workbook);

                System.out.println("Empleado creado exitosamente.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
