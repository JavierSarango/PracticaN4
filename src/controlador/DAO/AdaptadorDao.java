/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.DAO;

import controlador.tda.lista.ListaEnlazada;
import controlador.tda.lista.ListaEnlazadaServices;
import controlador.utiles.Utilidades;
import java.io.File;
import java.io.FileOutputStream;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author sebastian
 */
public class AdaptadorDao<T> implements InterfazDao<T> {

    private Class<T> clazz;
    private ListaEnlazadaServices<T> listaFinal = new ListaEnlazadaServices<>();
//    private  String URL = "datos" + File.separatorChar;

    public AdaptadorDao(Class<T> clazz) {
        this.clazz = clazz;
//        URL += this.clazz.getSimpleName() + ".xml";
    }

    @Override
    public Boolean guardar(T dato) throws Exception {
        ListaEnlazada<T> lista = listar().getLista();
        if (lista.getSize() != 0) {
            lista.insertar(dato, lista.getSize() - 1);
        } else {
            lista.insertar(dato, 0);
        }
        this.listaFinal.setLista(lista);
        return true;
    }

    @Override
    public Boolean modificar(T dato, Integer pos) throws Exception {
        ListaEnlazada<T> lista = listar().getLista();
        lista.modificarDato(pos, dato);
        this.listaFinal.setLista(lista);
        return true;
    }

    @Override
    public ListaEnlazadaServices<T> listar() {

        return this.listaFinal;

    }

}
