/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.ModeloTablas;

import controlador.tda.lista.ListaEnlazadaServices;
import javax.swing.table.AbstractTableModel;
import modelo.Persona;

/**
 *
 * @author sebastian
 */
public class TablaPersonas extends AbstractTableModel {
    private ListaEnlazadaServices<Persona> lista;

    public ListaEnlazadaServices<Persona> getLista() {
        return lista;
    }

    public void setLista(ListaEnlazadaServices<Persona> lista) {
        this.lista = lista;
    }

    @Override
    public int getColumnCount() {
        return 6;
    }

    @Override
    public int getRowCount() {
        return lista.getSize();
    }

    @Override
    public String getColumnName(int column) {
        switch(column) {
            case 0: return "id";
            case 1: return "Nombres";
            case 2: return "Apellidos";
            case 3: return "email";
            case 4: return "Telefono";
            case 5: return "Cedula";
            default: return null;
        }
    }

    @Override
    public Object getValueAt(int arg0, int arg1) {
        Persona persona = lista.obtenerDato(arg0);
        switch(arg1) {
            case 0: return (arg0+1);
            case 1: return persona.getNombre();
            case 2: return persona.getApellido();
            case 3: return persona.getEmail();
            case 4: return persona.getTelefono();
            case 5: return persona.getCedula();
            default: return null;
        }
    }
    
    
    
    
}
