package org.example.simulation;
import java.io.*;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;



public class Excel {


    public static void crearExcel(String nombreArchivo, double[][] datos) {
        Workbook wb = new XSSFWorkbook();
        FileOutputStream fileOut = null;

        try {
            fileOut = new FileOutputStream(nombreArchivo);
            Sheet sheet = wb.createSheet("Tabla Montecarlo");

            // Títulos de columnas
            String[] columnTitles = {"Encuestado", "RND General", "RND No Recuerdan", "RND Recuerdan", "Ya Comprado", "Acum. Ya Comprado", "Probable Compra",
                    "Acum. Probable Compra","Porc. Ya Comprado", "Prob. Probable Compra"
            };

            // Crear la fila de títulos
            Row titleRow = sheet.createRow(0);
            CellStyle titleStyle = wb.createCellStyle();
            Font titleFont = wb.createFont();
            titleFont.setBold(true);
            titleFont.setColor(IndexedColors.WHITE.getIndex());
            titleStyle.setFont(titleFont);
            titleStyle.setFillForegroundColor(IndexedColors.BLUE.getIndex());
            titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            titleStyle.setAlignment(HorizontalAlignment.CENTER);
            titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            titleStyle.setBorderTop(BorderStyle.THIN);
            titleStyle.setBorderBottom(BorderStyle.THIN);
            titleStyle.setBorderLeft(BorderStyle.THIN);
            titleStyle.setBorderRight(BorderStyle.THIN);

            // Agregar los títulos al Excel
            for (int i = 0; i < columnTitles.length; i++) {
                Cell cell = titleRow.createCell(i);
                cell.setCellValue(columnTitles[i]);
                cell.setCellStyle(titleStyle);
            }

            // Estilo para los datos
            CellStyle dataStyle = wb.createCellStyle();
            dataStyle.setAlignment(HorizontalAlignment.CENTER);
            dataStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            dataStyle.setBorderTop(BorderStyle.THIN);
            dataStyle.setBorderBottom(BorderStyle.THIN);
            dataStyle.setBorderLeft(BorderStyle.THIN);
            dataStyle.setBorderRight(BorderStyle.THIN);

            // Bucle para escribir los datos en el Excel, incluyendo la fila de inicialización en la primera posición
            for (int fila = 0; fila < datos.length; fila++) {
                Row row = sheet.createRow(fila + 1); // Las filas comienzan en 1, ya que la fila de títulos es 0
                for (int columna = 0; columna < datos[fila].length; columna++) {
                    Cell cell = row.createCell(columna);
                    cell.setCellValue(datos[fila][columna]);
                    cell.setCellStyle(dataStyle);
                }
            }

            // Ajustar el ancho de las columnas automáticamente
            for (int i = 0; i < columnTitles.length; i++) {
                sheet.autoSizeColumn(i);
            }

            // Guardar el archivo Excel
            wb.write(fileOut);
            System.out.println("Tabla creada exitosamente");

        } catch (IOException e) {
            System.out.println("No se puede generar el Excel mientras el archivo está abierto. Cierrelo antes de volver a generar.");
        } finally {
            if (fileOut != null) {
                try {
                    fileOut.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }



}
