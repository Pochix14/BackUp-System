import java.io.File;
import java.io.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 * Write a description of class Archivo here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Archivo
{
    private String patch;
    private LocalDate fecha,domingo,lunes,martes,miercoles,jueves,sabado;

    /**
     * Constructor for objects of class Archivo
     */
    public Archivo(LocalDate fechaInicio)
    {
        patch  = "\\\\172.31.254.22\\";
        domingo = fechaInicio;
        lunes = domingo.plusDays(1);
        martes = lunes.plusDays(1);
        miercoles = martes.plusDays(1);
        jueves = miercoles.plusDays(1);
        sabado = jueves.plusDays(2);
    }

    public int verificar (Usuario usuario)
    {
        String ubicacion = usuario.getUbicacion();
        int piso = Integer.parseInt(usuario.getPiso());
        String ruta = patch + ubicacion;
        File directorio = new File(ruta);
        File[] list = directorio.listFiles(new FilenameFilter() {
                    @Override
                    public boolean accept(File directorio, String name) {
                        return name.toLowerCase().endsWith(".pst");
                    }
                });
        if (list == null || list.length == 0)
        {
            usuario.setEjecucion("No se ejecutó");
            return 0;
        }
        File file;
        fecha = null;
        for(int index = 0; index < list.length; index++){
            file = list[index];            
            long l = file.lastModified();
            LocalDate fechaArchivo = Instant.ofEpochMilli(l).atZone(ZoneId.systemDefault()).toLocalDate();
            boolean bandera = false;
            if (fechaArchivo.isBefore(sabado) && fechaArchivo.isAfter(domingo))
            {
                switch(piso)
                {
                case 1:
                if(fechaArchivo.isEqual(lunes))
                {
                    bandera = true;
                }
                break;
                
                case 2:
                if(fechaArchivo.isEqual(martes))
                {
                    bandera = true;
                }
                break;
                
                case 3:
                if(fechaArchivo.isEqual(miercoles))
                {
                    bandera = true;
                }
                break;
                
                case 4:
                if(fechaArchivo.isEqual(jueves))
                {
                    bandera = true;
                }
                break;
                }
                
                if(bandera)
                {
                    usuario.setEjecucion("Automático");
                }
                
                else
                {
                    usuario.setEjecucion("Manual");
                }
                return 2;
            }
            
            
            if (fecha == null)
            {
                fecha = fechaArchivo;
            }
            else 
            {
                compararFechas(fechaArchivo);
            }
            //System.out.println(file.getName() + " "+ fechaArchivo.toString());
        }
        usuario.setEjecucion("No se ejecutó");
        return 1;
    }    

    public void compararFechas(LocalDate fechaArchivo)
    {
        if (fechaArchivo.isAfter(fecha))
        {
            fecha = fechaArchivo;
        }
    }

    public String getFecha()
    {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");         
        String fecha1 = fecha.format(formato).toString();
        return fecha1;
    }
}
