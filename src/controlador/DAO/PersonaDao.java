/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador.DAO;

import modelo.Persona;

/**
 *
 * @author Gigabyte
 */
public class PersonaDao extends AdaptadorDao<Persona>{
    private Persona persona;
    
    public PersonaDao() {
        super(Persona.class);
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
    public Boolean guardar() throws Exception{
    return guardar(persona);          
    }
//    public Boolean modificar() throws Exception{
//    return modificar(persona);          
//    }
}
