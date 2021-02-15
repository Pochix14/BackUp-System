import java.io.Serializable;

/**
 * Write a description of class Usuario here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Usuario
{
    private String nombre, departamento, piso, respaldo, status, ultimoRespaldo,ejecucion;
    
    public String getUbicacion()
    {
     return respaldo;   
    }
    
    public String getStatus()
    {
        return status;
    }
    
    public String getNombre()
    {
        return nombre;
    }
    
    public String getPiso()
    {
        return piso;
    }
    
    public String getDepartamento()
    {
        return departamento;
    }
    
    public String getFecha()
    {
        return ultimoRespaldo;
    }
    
    public String getEjecucion()
    {
        return ejecucion;
    }
    
    public String getInformacion()
    {
        String info = nombre + " " + departamento + " " + piso;
        return info;
    }
    
    public void setNombre(String name)
    {
        nombre = name;
    }
    
    public void setFecha(String fecha)
    {
        ultimoRespaldo = fecha;
    }
    
    public void setStatus(String estado)
    {
        status = estado;
    }
    
    public void setPiso(String pPiso)
    {
        piso = pPiso;
    }
    
    public void setDepartamento(String depa)
    {
        departamento = depa;
    }
    
    public void setUbicacion(String pRespaldo)
    {
        respaldo = pRespaldo;
    }
    
    public void setEjecucion(String pEjecucion)
    {
        ejecucion = pEjecucion;
    }

}
