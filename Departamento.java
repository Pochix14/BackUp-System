
import java.util.ArrayList;

/**
 * Write a description of class Departamento here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Departamento
{
    private String nombre;
    
    private String correo;
    
    private ArrayList<Usuario> listaBuenos;
    
    private ArrayList<Usuario> listaMalos;
    
    public Departamento(String nombre, String correo){
        this.nombre = nombre;
        this.correo = correo;
        listaBuenos = new ArrayList<>();
        listaMalos = new ArrayList<>();
    }
    
    public String getNombre(){
        return nombre;
    }
    
    public String getCorreo(){
        return correo;
    }
    
    public ArrayList<Usuario> getListaBuenos(){
        return listaBuenos;
    }
    
    public ArrayList<Usuario> getListaMalos(){
        return listaMalos;
    }
    
    public void agregarUsuario(Usuario usuario){
        if(usuario.getStatus()!=null){
            listaMalos.add(usuario);
        }
        else{
            listaBuenos.add(usuario);
        }
    }
    
}
