import java.io.FileOutputStream;
import java.util.ArrayList;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;

/**
 * Write a description of class PDF here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class PDF
{
    public static void crearPDFMalos(ArrayList<Usuario> usuarios, String fecha, String archivo){
        try {
            String ruta = "\\\\TUCAN\\varios\\1\\Cobian\\"+archivo;
            Document documento = new Document();
            PdfWriter.getInstance(documento, new FileOutputStream(ruta));
            documento.open();
            addTitle(documento, fecha, "Reporte semanal respaldo desactualizados al ");
            crearTablaMalos(documento,usuarios);
            documento.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void crearPDFBuenos(ArrayList<Usuario> usuarios, String fecha, String archivo){
        try {
            String ruta = "\\\\TUCAN\\varios\\1\\Cobian\\"+archivo;
            Document documento = new Document();
            PdfWriter.getInstance(documento, new FileOutputStream(ruta));
            documento.open();
            addTitle(documento, fecha, "Reporte semanal respaldos actualizados al ");
            crearTablaBuenos(documento,usuarios);
            documento.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void crearTablaMalos(Document documento, ArrayList<Usuario> usuarios) throws DocumentException{
        PdfPTable tabla = new PdfPTable(6);
        tabla.setWidths(new float [] {0.7f, 3.5f, 3.5f, 1f, 4f, 2.5f});
        
        PdfPCell celda = new PdfPCell(new Phrase("N°"));        
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        tabla.addCell(celda);

        celda = new PdfPCell(new Phrase("Usuario"));
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        tabla.addCell(celda);

        celda = new PdfPCell(new Phrase("Departamento"));
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        tabla.addCell(celda);

        celda = new PdfPCell(new Phrase("Piso"));
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        tabla.addCell(celda);

        celda = new PdfPCell(new Phrase("Observaciones"));
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        tabla.addCell(celda);
        
        celda = new PdfPCell(new Phrase("Último Respaldo"));
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        tabla.addCell(celda);
        
        tabla.setHeaderRows(1);
        
        for(int index = 0; index < usuarios.size(); index++){
            String a = Integer.toString(index + 1);
            tabla.addCell(a);
            tabla.addCell(usuarios.get(index).getNombre());
            tabla.addCell(usuarios.get(index).getDepartamento());
            tabla.addCell(usuarios.get(index).getPiso());
            tabla.addCell(usuarios.get(index).getStatus());
            tabla.addCell(usuarios.get(index).getFecha());
        }

        documento.add(tabla);
    }

    private static void crearTablaBuenos(Document documento, ArrayList<Usuario> usuarios) throws DocumentException{
        PdfPTable tabla = new PdfPTable(5);
        tabla.setWidths(new float [] {0.5f, 3, 3, 0.8f, 1.5f});
        
        PdfPCell celda = new PdfPCell(new Phrase("N°"));
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        tabla.addCell(celda);

        celda = new PdfPCell(new Phrase("Usuario"));
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        tabla.addCell(celda);

        celda = new PdfPCell(new Phrase("Departamento"));
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        tabla.addCell(celda);

        celda = new PdfPCell(new Phrase("Piso"));
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        tabla.addCell(celda);
        
        celda = new PdfPCell(new Phrase("Ejecución"));
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        tabla.addCell(celda);

        tabla.setHeaderRows(1);

        for(int index = 0; index < usuarios.size(); index++){
            String a = Integer.toString(index + 1);
            tabla.addCell(a);
            tabla.addCell(usuarios.get(index).getNombre());
            tabla.addCell(usuarios.get(index).getDepartamento());
            tabla.addCell(usuarios.get(index).getPiso());
            tabla.addCell(usuarios.get(index).getEjecucion());
        }

        documento.add(tabla);
    }

    public static void addTitle(Document documento, String fecha, String titulo) throws DocumentException{
        Font fontbold = FontFactory.getFont("Times-Roman", 22, Font.BOLD);
        Paragraph p = new Paragraph(titulo + fecha, fontbold);
        p.setSpacingAfter(20);
        p.setAlignment(1); // Center
        documento.add(p);
    }
}
