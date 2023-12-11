package com.uacm.edu.model;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

public class ExcelManager {

    public static final String FILE_NAME = "Empleados.xlsx";

    public static Workbook abrirLibroExcel() {
        try {
            FileInputStream excelFile = new FileInputStream(FILE_NAME);
            return new XSSFWorkbook(excelFile);
        } catch (IOException e) {
            return crearNuevoLibroExcel();
        }
    }

    public static Workbook crearNuevoLibroExcel() {
        try {
            Workbook workbook = new XSSFWorkbook();
            try ( FileOutputStream fileOut = new FileOutputStream(FILE_NAME)) {
                workbook.write(fileOut);
            }
            System.out.println("Se ha creado un nuevo archivo Excel: " + FILE_NAME);
            return workbook;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void cerrarLibroExcel(Workbook workbook) {
        try {
            FileOutputStream excelFileOut = new FileOutputStream(FILE_NAME);
            workbook.write(excelFileOut);
            excelFileOut.close();
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void imprimirEmpleados() {
        try {
            Workbook workbook = abrirLibroExcel();
            if (workbook != null) {
                Sheet sheet = workbook.getSheetAt(0);
                Iterator<Row> iterator = sheet.iterator();

                System.out.println("\nLista de Empleados:");
                System.out.println("Empleado\tNombre\tEdad\tSexo\tSalario");

                while (iterator.hasNext()) {
                    Row currentRow = iterator.next();
                    Iterator<Cell> cellIterator = currentRow.iterator();

                    while (cellIterator.hasNext()) {
                        Cell currentCell = cellIterator.next();

                        switch (currentCell.getCellType()) {
                            case STRING:
                                System.out.print(currentCell.getStringCellValue() + "\t");
                                break;
                            case NUMERIC:
                                // Comprobar si es una celda de fecha
                                if (DateUtil.isCellDateFormatted(currentCell)) {
                                    System.out.print(currentCell.getDateCellValue() + "\t");
                                } else {
                                    // Identificar si es el número de Empleado o Salario
                                    if (currentCell.getColumnIndex() == 0 || currentCell.getColumnIndex() == 4) {
                                        System.out.print((int) currentCell.getNumericCellValue() + "\t");
                                    } else {
                                        System.out.print(currentCell.getNumericCellValue() + "\t");
                                    }
                                }
                                break;
                            case BLANK:
                                // Tratar las celdas en blanco según sea necesario
                                System.out.print("\t");
                                break;
                            default:
                                // Manejar otros tipos de celda según sea necesario
                                System.out.print("\t");
                        }
                    }
                    System.out.println();
                }

                cerrarLibroExcel(workbook);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
