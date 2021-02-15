import java.util.ArrayList;
import java.util.Locale;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.io.*;

/**
 * Write a description of class Controlador here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Controlador
{
    private Archivo archivo;
    private Datos datos;
    private ArrayList<Usuario> usuarios;
    private ArrayList<Usuario> listaMalos;
    private ArrayList<Usuario> listaBuenos;
    private ArrayList<Departamento> departamentos;
    private LocalDate fin;
    

    /**
     * Constructor for objects of class Controlador
     */
    public Controlador(LocalDate inicio, LocalDate pFin) throws IOException
    {
        fin = pFin;
        archivo = new Archivo(inicio);
        datos = new Datos();
        usuarios = datos.getUsuarios();
        departamentos = new ArrayList<>();
        String[] correo ={"rosny.barquero@sinac.go.cr","lesbia.sevilla@sinac.go.cr","henry.ramirez@sinac.go.cr","hazel.calderon@sinac.go.cr","direccion.ejecutiva@sinac.go.cr",
                          "analucia.ovares@sinac.go.cr","hazel.calderon@sinac.go.cr","karen.quesada@sinac.go.cr","cindy.sanchez@sinac.go.cr","gina.vargas@sinac.go.cr",
                          "juan.villegas@sinac.go.cr","marietta.tencio@sinac.go.cr","sandra.rodriguez@sinac.go.cr","mauricio.castillo@sinac.go.cr","emilio.arguedas@sinac.go.cr"};
        String[] departamento = {"Control Interno","Coop. Técnica","CUSBSE","Dirección Administrativa","Dirección Ejecutiva", "Financiero","Infraestructura","Legal",
                                 "Participación Ciudadana","Planificación","Prevención","Proveeduría","Recursos Humanos","Regularización", "Servicios Generales"};
        for(int index = 0; index < 15; index++){
            Departamento depto = new Departamento(departamento[index],correo[index]);
            departamentos.add(depto);
        }
        //int tamaño = usuarios.size();
        // Se elimina el restar uno al tamaño de los usuarios
        //usuarios.remove(tamaño-1);
    }

    public void verificarRespaldos (int fechaSemana) throws IOException 
    {
        int estado;
        String fecha;
        listaMalos = new ArrayList<>();
        listaBuenos = new ArrayList<>();
        for (int i = 0; i < usuarios.size(); i++)
        {
            estado = archivo.verificar(usuarios.get(i));
            
            switch (estado)
            {
                case 0:
                listaMalos.add(usuarios.get(i));
                usuarios.get(i).setStatus("Respaldo no existe");
                break;
                case 1:
                listaMalos.add(usuarios.get(i));
                usuarios.get(i).setStatus("Respaldo desactualizado");
                fecha = archivo.getFecha();
                usuarios.get(i).setFecha(fecha);
                break;      
                case 2:
                listaBuenos.add(usuarios.get(i));
                break;
            }
            asignarUsuario(usuarios.get(i));
        }
        
        datos.escribirEjecución(usuarios, fechaSemana);
        verificarListaMalos();
        
    }
    
    private void asignarUsuario(Usuario usuario){
        switch(usuario.getDepartamento()){
            case "Control Interno":
            departamentos.get(0).agregarUsuario(usuario);
            break;
            case "Coop. Técnica":
            departamentos.get(1).agregarUsuario(usuario);
            break;
            case "CUSBSE":
            departamentos.get(2).agregarUsuario(usuario);
            break;
            case "Dirección Administrativa":
            departamentos.get(3).agregarUsuario(usuario);
            break;
            case "Dirección Ejecutiva":
            departamentos.get(4).agregarUsuario(usuario);
            break;
            case "Financiero":
            departamentos.get(5).agregarUsuario(usuario);
            break;
            case "Infraestructura":
            departamentos.get(6).agregarUsuario(usuario);
            break;
            case "Legal":
            departamentos.get(7).agregarUsuario(usuario);
            break;
            case "Participación Ciudadana":
            departamentos.get(8).agregarUsuario(usuario);
            break;
            case "Planificación":
            departamentos.get(9).agregarUsuario(usuario);
            break;
            case "Prevención":
            departamentos.get(10).agregarUsuario(usuario);
            break;
            case "Proveeduría":
            departamentos.get(11).agregarUsuario(usuario);
            break;
            case "Recursos Humanos":
            departamentos.get(12).agregarUsuario(usuario);
            break;
            case "Regularización":
            departamentos.get(13).agregarUsuario(usuario);
            break;
            case "Servicios Generales":
            departamentos.get(14).agregarUsuario(usuario);
            break;
        }
    }
    
    private void verEjecucion()
    {
        for (int i = 0; i < usuarios.size(); i++)
        {
             System.out.println(usuarios.get(i).getNombre()+" "+usuarios.get(i).getEjecucion());
        }
    }

    private void verificarListaMalos(){
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");       
        String fecha = fin.format(formato).toString();
        String pdfBuenos = null;
        String pdfMalos = null;
        if(listaMalos.size()!=0){
            pdfMalos = "Revisión Cobian Malos.pdf";
            PDF.crearPDFMalos(listaMalos, fecha, pdfMalos);
        }
        if (listaBuenos.size()!=0)
        {
            pdfBuenos = "Revisión Cobian Buenos.pdf";
            PDF.crearPDFBuenos(listaBuenos, fecha, pdfBuenos);
        }
        String historial = "Historial.xlsx";
        String mensajeTIC = "Se adjuntan 2 archivos con el informe final de la revisión semanal de Cobian y el historial semanal de la ejecución del programa";
        String mensaje = "Se adjunta informe final semanal de revisión de respaldos de los usuarios del departamento.";
        
        Correo correo = new Correo();
        correo.enviar("soporte@sinac.go.cr", pdfMalos, pdfBuenos, historial, mensajeTIC);
        correo.enviar("ti@sinac.go.cr", pdfMalos, pdfBuenos, historial, mensajeTIC);
        
        for(int index = 0; index<departamentos.size(); index++){
            pdfBuenos = null;
            pdfMalos = null;
            if(departamentos.get(index).getListaMalos().size()!=0){
                pdfMalos = "Respaldos Desactualizados "+departamentos.get(index).getNombre()+".pdf";
                PDF.crearPDFMalos(departamentos.get(index).getListaMalos(), fecha, pdfMalos);
            }
            if (departamentos.get(index).getListaBuenos().size()!=0){
                pdfBuenos = "Respaldos Actualizados "+departamentos.get(index).getNombre()+".pdf";
                PDF.crearPDFBuenos(departamentos.get(index).getListaBuenos(), fecha, pdfBuenos);
            }
            System.out.println(departamentos.get(index).getNombre());
            correo.enviar(departamentos.get(index).getCorreo(), pdfMalos, pdfBuenos, null, mensaje);
            correo.enviar("soporte@sinac.go.cr", pdfMalos, pdfBuenos, null, mensaje);
        }
    }

    public void imprimirUsuarios(){
        for(int index = 0 ; index < usuarios.size(); index++){
            System.out.println(usuarios.get(index).getInformacion()+ " "+usuarios.get(index).getUbicacion());
        }
        System.out.println( usuarios.size());
    }

    public static void main (String args[]) throws IOException 
    {
        LocalDate a,b;
        b = LocalDate.now().plusDays(0);
        a = b.minusDays(5);
        WeekFields semana = WeekFields.of(Locale.getDefault());
        int numeroSemana = b.get(semana.weekOfWeekBasedYear());
        //System.out.println(numeroSemana);
        Controlador controlador = new Controlador(a,b);
        controlador.verificarRespaldos(numeroSemana);
    }
}
