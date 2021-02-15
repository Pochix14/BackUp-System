import java.io.Serializable;
import java.util.ArrayList;
import java.io.*;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Write a description of class Datos here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Datos implements Serializable
{

    private ArrayList<Usuario> usuarios;

    /**
     * Constructor for objects of class Datos
     */
    public Datos() throws IOException
    {
        usuarios = new ArrayList<>();
        cargarUsuarios();
    }

    private void cargarUsuarios() throws IOException
    {
        File myFile = new File("\\\\Tucan\\varios\\1\\Cobian\\Cobian.xlsx"); 
        FileInputStream fis = new FileInputStream(myFile); 
        XSSFWorkbook myWorkBook = new XSSFWorkbook (fis);
        XSSFSheet mySheet = myWorkBook.getSheetAt(0); 
        Iterator<Row> rowIterator = mySheet.iterator();
        rowIterator.next();
        Usuario usuario;
        while (rowIterator.hasNext()) { 
            Row row = rowIterator.next();
            usuario = new Usuario();
            Iterator<Cell> cellIterator = row.cellIterator(); 
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                switch (cell.getCellType()) {
                    case Cell.CELL_TYPE_STRING:
                    switch (cell.getColumnIndex()){
                        case 1:
                        usuario.setDepartamento(cell.getStringCellValue());
                        break;
                        case 2:
                        usuario.setNombre(cell.getStringCellValue());
                        break;
                        case 3:
                        usuario.setUbicacion(cell.getStringCellValue());
                        break;
                    }
                    //System.out.print(cell.getStringCellValue()+"\t");
                    break;
                    case Cell.CELL_TYPE_NUMERIC:
                    usuario.setPiso(Integer.toString((int)cell.getNumericCellValue()));
                    //System.out.print((int)cell.getNumericCellValue()+"\t");
                    break;
                } 
            }
            usuarios.add(usuario);
        }
    }
    
    public void escribirEjecución(ArrayList<Usuario> usuario, int columna) throws IOException
    {
        File myFile = new File("\\\\Tucan\\varios\\1\\Cobian\\Historial.xlsx"); 
        FileInputStream fis = new FileInputStream(myFile);
        XSSFWorkbook myWorkBook = new XSSFWorkbook (fis);
        XSSFSheet mySheet = myWorkBook.getSheetAt(0);
        Cell celda = null;
        int numeroFilas = mySheet.getLastRowNum() + 1;
        
        for (int i = 1; i < numeroFilas; ++i)
        {
            celda = mySheet.getRow(i).getCell(columna+1);
            celda.setCellValue(usuario.get(i - 1).getEjecucion());
        }
        fis.close();
        
        FileOutputStream salida = new FileOutputStream(new File(("\\\\Tucan\\varios\\1\\Cobian\\Historial.xlsx")));
        myWorkBook.write(salida);
        salida.close();
        
    }

    public ArrayList<Usuario> getUsuarios()
    {
        return usuarios;
    }

}
