/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador.Persona;

import controlador.DAO.AdaptadorDao;
import controlador.tda.lista.ListaEnlazadaServices;
import java.util.Random;
import modelo.Persona;

/**
 *
 * @author Gigabyte
 */
public class PersonaController extends AdaptadorDao<Persona>{

    private Persona persona;
    private ListaEnlazadaServices<Persona> listaPersona;

    public PersonaController() {
        super(Persona.class);
        listado();
    }
    
    public Persona getPersona() {
        if (persona == null) {
            persona = new Persona();
        }
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public ListaEnlazadaServices<Persona> getListaPersona() {
        return listaPersona;
    }

    public void setListaPersona(ListaEnlazadaServices<Persona> listaPersona) {
        this.listaPersona = listaPersona;
    }
    
    public Boolean guardar() {
        try {            
            getPersona().setId(listaPersona.getSize()+1);
            guardar(getPersona());
            return true;
        } catch (Exception e) {
            System.out.println("Error en guardar Persona"+e);
        }
        return false;
    }
    
    public Boolean modificar(Integer pos) {
        try {            
            
            modificar(getPersona(), pos);
            return true;
        } catch (Exception e) {
            System.out.println("Error en modificar Persona"+e);
        }
        return false;
    }
    
    public ListaEnlazadaServices<Persona> listado() {
        setListaPersona(listar());
        return listaPersona;
    }
    public String generarNombresAleatorios() {
        String nombresAleatorios;
        String[] nombres = {"Martín", "Lucas", "Mateo", "Leo", "Daniel", "Alejandro", "Pablo",
            "Manuel", "Alvaro", "Adrian", "David", "Mario", "Enzo", "Diego", "Marcos", "Izan", "Javier",
            "Marco", "Alex", "Bruno", "Oliver", "Miguel", "Thiago", "Antonio", "Marc", "Carlos", "Angel", "Juan",
            "Gonzalo", "Gael", "Sergio", "Nicolas", "Dylan", "Gabriel", "Jorge", "Jose", "Adam",
            "Liam", "Eric", "Samuel", "Dario", "Hector", "Lucas", "Iker", "Amir", "Rodrigo", "Saul", "Victor",
            "Francisco", "Ivan", "Jesus", "Jaime", "Aaron", "Ruben", "Ian", "Guillermo", "Mohamed", "Julen", "Luis",
            "Lucia", "Sofia", "Martina", "Maria", "Julia", "Paula", "Valeria", "Emma", "Daniela", "Carla", "Alba",
            "Noa", "Alma", "Sara", "Carmen", "Olivia", "Mia", "Jimena", "Claudia", "Chloe", "Aitana", "Abril",
            "Alejandra", "Adriana", "Cindy", "Wendy", "Lizett", "Domenica", "Nileida", "Nixon", "Byron", "Katherine",
            "Angie", "Barbara", "Nathalia", "Hilary", "Karen", "Thais", "Elena"};

        
            nombresAleatorios = nombres[(int) (Math.floor(Math.random() * ((nombres.length - 1) - 0 + 1) + 0))];
        

        return nombresAleatorios;
    }

    public String generarApellidosAleatorios() {
        String apellidosAleatorios;
        String[] apellidos = {"Gonzales", "Rodriguez", "Fernandez", "Diaz", "Lopez", "Gomez", "Flores",
            "Sanchez", "Martinez", "Romero", "Garcia", "Quiroga", "Ruiz", "Perez", "Silva", "Cruz", "Muñoz",
            "Rojas", "Soto", "Contreras", "Sepulveda", "Morales", "Hernandez", "Fuentes", "Torres", "Araya", "Espinoza", "Valenzuela",
            "Castillo", "Tapia", "Reyes", "Castro", "Gutierrez", "Pizarro", "Herrera", "Nuñez", "Jara",
            "Vergara", "Rivera", "Figueroa", "Requelme", "Miranda", "Vera", "Molina", "Vega", "Campos", "Sandoval", "Cardenas",
            "Olivares", "Alarcon", "Ortiz", "Salazar", "Guzman", "Saavedra", "Aguilera", "Parra", "Escobar", "Ruiz", "Vidal",
            "Salinas", "Zuñiga", "Peña", "Maldonado", "Palma", "Moreno", "Carvajal", "Donoso", "Bustamante", "Ortega", "Venegas",
            "Mendoza", "Farias", "Tuki", "Cueva", "Jaramillo", "Mora", "Calderon", "Vasquez", "Ramos", "Chuquihuanca", "Sarabia",
            "Chuquimarca", "Marquez", "Abrigo", "Nieto", "Pinta", "Encalada", "Guaman", "Cedeño", "Matamoros", "Zurita",
            "Heredia", "Andrade", "Zambrano", "Benitez", "Valencia", "Cartuche", "Bermudez"};

        
            apellidosAleatorios = apellidos[(int) (Math.floor(Math.random() * ((apellidos.length - 1) - 0 + 1) + 0))];
       

        return apellidosAleatorios;
    }

    public String generarNumeros() {
        String numerosTel;
        Integer datos;

        Random rnd = new Random();

       
            datos =  rnd.nextInt() * 999999 + 900000;
            if (datos < 0) {
                datos = datos * -1;
            }
            numerosTel = String.valueOf(datos);
      
        return numerosTel;
    }
}
