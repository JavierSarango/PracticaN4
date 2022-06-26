/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.tda.lista;

import controlador.Persona.PersonaController;
import controlador.tda.lista.exception.PosicionException;
import controlador.utiles.Utilidades;
import controlador.utiles.TipoOrdenacion;
import static controlador.utiles.Utilidades.getMethod;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import modelo.Persona;

/**
 *
 * @author sebastian
 */
//E   T    K   V
//E = T
@XmlRootElement

public class ListaEnlazada<E> {

    private NodoLista<E> cabecera;

    private Integer size;

    @XmlElement
    public NodoLista<E> getCabecera() {
        return cabecera;
    }

    public void setCabecera(NodoLista<E> cabecera) {
        this.cabecera = cabecera;
    }

    /**
     * Constructor de la clase se inicializa la lista en null y el tamanio en 0
     */
    public ListaEnlazada() {
        cabecera = null;
        size = 0;
    }

    /**
     * Permite ver si la lista esta vacia
     *
     * @return Boolean true si esta vacia, false si esta llena
     */
    public Boolean estaVacia() {
        return cabecera == null;
    }

    private void insertar(E dato) {
        NodoLista<E> nuevo = new NodoLista<>(dato, null);
        if (estaVacia()) {
            cabecera = nuevo;
        } else {
            NodoLista<E> aux = cabecera;
            while (aux.getSiguiente() != null) {
                aux = aux.getSiguiente();
            }
            aux.setSiguiente(nuevo);
        }
        size++;
    }

    public void insertarCabecera(E dato) {
        if (estaVacia()) {
            insertar(dato);
        } else {
            NodoLista<E> nuevo = new NodoLista<>(dato, null);

            nuevo.setSiguiente(cabecera);
            cabecera = nuevo;
            size++;
        }
    }

    public void insertar(E dato, Integer pos) throws PosicionException {
        //lista size = 1
        if (estaVacia()) {
            insertar(dato);
        } else if (pos >= 0 && pos < size) {
            NodoLista<E> nuevo = new NodoLista<>(dato, null);
            if (pos == (size - 1)) {
                insertar(dato);

            } else {

                NodoLista<E> aux = cabecera;
                for (int i = 0; i < pos - 1; i++) {
                    aux = aux.getSiguiente();
                }
                NodoLista<E> siguiente = aux.getSiguiente();
                aux.setSiguiente(nuevo);
                nuevo.setSiguiente(siguiente);
                size++;
            }

        } else {
            throw new PosicionException("Error en insertar: No existe la posicion dada");
        }
    }

    public void imprimir() {
        System.out.println("**************************");
        NodoLista<E> aux = cabecera;
        for (int i = 0; i < getSize(); i++) {
            System.out.print(aux.getDato().toString() + "\t");
            aux = aux.getSiguiente();
        }
        System.out.println("\n" + "**************************");
    }

    public Integer getSize() {
        return size;
    }

    /**
     * Metodo que permite obtener un dato segun la posicion
     *
     * @param pos posicion en la lista
     * @return Elemento
     */
    public E obtenerDato(Integer pos) throws PosicionException {
        if (!estaVacia()) {
            if (pos >= 0 && pos < size) {
                E dato = null;
                if (pos == 0) {
                    dato = cabecera.getDato();
                } else {
                    NodoLista<E> aux = cabecera;
                    for (int i = 0; i < pos; i++) {
                        aux = aux.getSiguiente();
                    }
                    dato = aux.getDato();
                }

                return dato;
            } else {
                throw new PosicionException("Error en obtener dato: No existe la posicion dada");
            }

        } else {
            throw new PosicionException("Error en obtener dato: La lista esta vacia, por endde no hay esa posicion");
        }
    }

    public E eliminarDato(Integer pos) throws PosicionException {
        E auxDato = null;
        if (!estaVacia()) {
            if (pos >= 0 && pos < size) {
                if (pos == 0) {
                    auxDato = cabecera.getDato();
                    cabecera = cabecera.getSiguiente();
                    size--;
                } else {
                    NodoLista<E> aux = cabecera;
                    for (int i = 0; i < pos - 1; i++) {
                        aux = aux.getSiguiente();
                    }
                    auxDato = aux.getDato();
                    NodoLista<E> proximo = aux.getSiguiente();
                    aux.setSiguiente(proximo.getSiguiente());
                    size--;
                }
                return auxDato;

            } else {
                throw new PosicionException("Error en eliminar dato: No existe la posicion dada");
            }

        } else {
            throw new PosicionException("Error en eliminar dato: La lista esta vacia, por endde no hay esa posicion");
        }
    }

    public void vaciar() {
        cabecera = null;
        size = 0;
        //en c utilizamos el free
        //malloc
    }

    public void modificarDato(Integer pos, E datoM) throws PosicionException {
        if (!estaVacia()) {
            if (pos >= 0 && pos < size) {
                // E dato = null;
                if (pos == 0) {
                    cabecera.setDato(datoM);
                } else {
                    NodoLista<E> aux = cabecera;
                    for (int i = 0; i < pos; i++) {
                        aux = aux.getSiguiente();
                    }
                    aux.setDato(datoM);
                }

            } else {
                throw new PosicionException("Error en obtener dato: No existe la posicion dada");
            }

        } else {
            throw new PosicionException("Error en obtener dato: La lista esta vacia, por endde no hay esa posicion");
        }
    }

    public E[] toArray() {
        // E[] matriz = (E[]) (new Object[this.size]);
        Class<E> clazz = null;
        E[] matriz = null;
        if (this.size > 0) {
            matriz = (E[]) java.lang.reflect.Array.newInstance(cabecera.getDato().getClass(), this.size);
            NodoLista<E> aux = cabecera;
            for (int i = 0; i < this.size; i++) {
                matriz[i] = aux.getDato();
                //System.out.print(aux.getDato().toString() + "\t");
                aux = aux.getSiguiente();
            }
        }

        return matriz;
    }

    public ListaEnlazada<E> toList(E[] matriz) {
        //E[] matriz = (E[]) (new Object[this.size]);
        this.vaciar();
        for (int i = 0; i < matriz.length; i++) {
            this.insertar(matriz[i]);
        }
        return this;
    }

    public ListaEnlazada<E> ordenar_seleccion(String atributo, TipoOrdenacion tipoOrdenacion) throws Exception {
        Class<E> clazz = null;
        E[] matriz = null;
        if (size > 0) {
            matriz = toArray();
            E t = null;
            clazz = (Class<E>) cabecera.getDato().getClass();//primitivo, Dato envolvente, Object
            Boolean isObject = Utilidades.isObject(clazz);//si es objeto
            Integer i, j, k = 0;
            Integer n = matriz.length;
            Integer cont = 0;
            for (i = 0; i < n - 1; i++) {
                k = i;
                t = matriz[i];  //toma el objeto de matriz en la posicion i
                for (j = i + 1; j < n; j++) {
                    if (isObject) {

                        Field field = Utilidades.getField(atributo, clazz); //Obtiene que es lo que se quiere obtener segun el atributo y la clase
                        Method method = getMethod("get" + Utilidades.capitalizar(atributo), t.getClass()); //Obtiene el metodo para la obtencion del dato
                        Method method1 = getMethod("get" + Utilidades.capitalizar(atributo), matriz[j].getClass());
                        Object[] aux = evaluaCambiarDato(field.getType(), t, matriz[j], method, method1, tipoOrdenacion, j, matriz);
                        if (aux[0] != null) {
                            t = (E) aux[0];
                            k = (Integer) aux[1];
                        }
                    } else {
                        Object[] aux = evaluaCambiarDatoNoObjeto(clazz, t, matriz[j], tipoOrdenacion, j, matriz);
                        if (aux[0] != null) {
                            t = (E) aux[0];
                            k = (Integer) aux[1];
                        }
                    }

                }
                matriz[k] = matriz[i];
                matriz[i] = t;
            }
        }
        if (matriz != null) {
            toList(matriz);
        }
        return this;
    }

    /*
     public ListaEnlazada<E> burbuja(String atributo, TipoOrdenacion tipoOrdenacion) throws PosicionException, Exception {
     Class<E> clazz = null;
     E[] matriz = null;
     if (size > 0) {
     clazz = (Class<E>) cabecera.getDato().getClass();//primitivo, Dato envolvente, Object
     Boolean isObject = Utilidades.isObject(clazz);//si es objeto
     System.out.println("TRANFORMANDO A MATRIZ");
     matriz = toArray();
     if (isObject) {
     for (int i = matriz.length; i > 1; i--) {
     for (int j = 0; j < i - 1; j++) {
     //E auxJ = this.obtenerDato(j);
     //E auxJ1 = this.obtenerDato(j + 1);//getApellido
     E auxJ = matriz[j];
     E auxJ1 = matriz[j + 1];//getApellido
     Field field = Utilidades.getField(atributo, clazz);
     Method method = getMethod("get" + Utilidades.capitalizar(atributo), auxJ.getClass());
     Method method1 = getMethod("get" + Utilidades.capitalizar(atributo), auxJ1.getClass());
     //llamar a utilidades
     //if (field.getType().getSuperclass().getSimpleName().equalsIgnoreCase("Number")) {
     evaluaCambiarDato(field.getType(), auxJ, auxJ1, method, method1, tipoOrdenacion, j, matriz);

     }
     }
     } else {
     System.out.println("METODO BURBUJA");
     for (int i = matriz.length; i > 1; i--) {
     for (int j = 0; j < i - 1; j++) {
     //E auxJ = this.obtenerDato(j);
     //E auxJ1 = this.obtenerDato(j + 1);//getApellido
     E auxJ = matriz[j];
     E auxJ1 = matriz[j + 1];
     evaluaCambiarDatoNoObjeto(clazz, auxJ, auxJ1, tipoOrdenacion, j, matriz);
     }
     }
     }

     }
     System.out.println("TRANFORMANDO A LISTA");
     if (matriz != null) {
     toList(matriz);
     }
     return this;
     }
     */
    /**
     * Sirve para realizar el intercambio del metodo burbuja
     *
     * @param j Posicion
     * @throws Exception Excepciones de tipo PosicionExcepcion
     */
    /* private void cambioBurbuja(Integer j, E[] matriz) throws Exception {
     E aux = matriz[j];
     matriz[j] = matriz[j + 1];
     matriz[j + 1] = aux;
     //E aux = this.obtenerDato(j);
     //this.modificarDato(j, this.obtenerDato(j + 1));
     //this.modificarDato(j + 1, aux);
     //  System.out.println("Cambio");
     }*/
    /**
     * Permite hacer el cambio con datos que no son objetos
     *
     * @param clazz El tipo de clase q permite identificar q tipo de dato es
     * @param auxJ Dato en la posicion J
     * @param auxJ1 Dato en la posicion J + 1
     * @param tipoOrdenacion El tipo de ordenacion si es Ascendente o
     * Descendente
     * @param j Posicion
     * @throws Exception
     */
    private Object[] evaluaCambiarDatoNoObjeto(Class clazz, E auxJ, E auxJ1, TipoOrdenacion tipoOrdenacion, Integer j, E[] matriz) throws Exception {
        Object aux[] = new Object[2];//aux[0];--->null
        if (clazz.getSuperclass().getSimpleName().equalsIgnoreCase("Number")) {
            // Number datoJ = (Number) auxJ;
            // Number datoJ1 = (Number) auxJ1;
            if (tipoOrdenacion.toString().equalsIgnoreCase(TipoOrdenacion.ASCENDENTE.toString())) {
                if ((((Number) auxJ).doubleValue() > ((Number) auxJ1).doubleValue())) {
                    aux[0] = auxJ1;
                    aux[1] = j;
                    //  cambioBurbuja(j, matriz);
                }
            } else {
                if ((((Number) auxJ).doubleValue() < ((Number) auxJ1).doubleValue())) {
                    // cambioBurbuja(j, matriz);
                    aux[0] = auxJ1;
                    aux[1] = j;
                }
            }
        } else if (Utilidades.isString(clazz)) {
            String datoJ = (String) auxJ;
            String datoJ1 = (String) auxJ1;
            if (tipoOrdenacion.toString().equalsIgnoreCase(TipoOrdenacion.ASCENDENTE.toString())) {
                if ((datoJ.toLowerCase().compareTo(datoJ1.toLowerCase()) > 0)) {
                    //cambioBurbuja(j, matriz);
                    aux[0] = auxJ1;
                    aux[1] = j;
                }
            } else {
                if ((datoJ.toLowerCase().compareTo(datoJ1.toLowerCase()) < 0)) {
                    //cambioBurbuja(j, matriz);
                    aux[0] = auxJ1;
                    aux[1] = j;
                }
            }

        } else if (Utilidades.isCharacter(clazz)) {
            Character datoJ = (Character) auxJ;
            Character datoJ1 = (Character) auxJ1;
            if (tipoOrdenacion.toString().equalsIgnoreCase(TipoOrdenacion.ASCENDENTE.toString())) {
                if (datoJ > datoJ1) {
                    //cambioBurbuja(j, matriz);
                    aux[0] = auxJ1;
                    aux[1] = j;
                }
            } else {
                if (datoJ < datoJ1) {
                    //cambioBurbuja(j, matriz);
                    aux[0] = auxJ1;
                    aux[1] = j;
                }
            }

        }
        return aux;
    }

    private Integer evaluarCambiarDatoNoObjetoQS_I(Class clazz, E auxI, E pivote, Integer i, TipoOrdenacion tipoOrdenacion, Integer j) throws Exception {

        if (clazz.getSuperclass().getSimpleName().equalsIgnoreCase("Number")) {
            if (tipoOrdenacion.toString().equalsIgnoreCase(TipoOrdenacion.ASCENDENTE.toString())) {
                while ((((Number) auxI).doubleValue() <= ((Number) pivote).doubleValue()) && i < j) {
                    i++;
                    break;
                }
                return i;
            } else {
                while ((((Number) auxI).doubleValue() >= ((Number) pivote).doubleValue()) && i > j) {
                    i++;
                    break;
                }
                return i;
            }
        } else if (Utilidades.isString(clazz)) {
            String datoJ = (String) auxI;
            String datoJ1 = (String) pivote;
            if (tipoOrdenacion.toString().equalsIgnoreCase(TipoOrdenacion.ASCENDENTE.toString())) {
                while ((datoJ.toLowerCase().compareTo(datoJ1.toLowerCase()) > 0)) {
                    i++;
                    break;
                }
                return i;
            } else {
                while ((datoJ.toLowerCase().compareTo(datoJ1.toLowerCase()) < 0)) {
                    i++;
                    break;
                }
                return i;
            }

        } else if (Utilidades.isCharacter(clazz)) {
            Character datoJ = (Character) auxI;
            Character datoJ1 = (Character) pivote;
            if (tipoOrdenacion.toString().equalsIgnoreCase(TipoOrdenacion.ASCENDENTE.toString())) {
                while (datoJ > datoJ1) {
                    i++;
                    break;
                }
                return i;
            } else {
                while (datoJ < datoJ1) {
                    i++;
                    break;
                }
                return i;
            }

        }
        return null;
    }

    private Integer evaluarCambiarDatoNoObjetoQS_J(Class clazz, E auxI, E pivote, TipoOrdenacion tipoOrdenacion, Integer j) throws Exception {

        if (clazz.getSuperclass().getSimpleName().equalsIgnoreCase("Number")) {
            if (tipoOrdenacion.toString().equalsIgnoreCase(TipoOrdenacion.ASCENDENTE.toString())) {
                while ((((Number) auxI).doubleValue() > ((Number) pivote).doubleValue())) {
                    j--;
                    break;
                }
                return j;
            } else {
                while ((((Number) auxI).doubleValue() < ((Number) pivote).doubleValue())) {
                    j--;
                    break;
                }
                return j;
            }
        } else if (Utilidades.isString(clazz)) {
            String datoJ = (String) auxI;
            String datoJ1 = (String) pivote;
            if (tipoOrdenacion.toString().equalsIgnoreCase(TipoOrdenacion.ASCENDENTE.toString())) {
                while ((datoJ.toLowerCase().compareTo(datoJ1.toLowerCase()) > 0)) {
                    j--;
                    break;
                }
                return j;
            } else {
                while ((datoJ.toLowerCase().compareTo(datoJ1.toLowerCase()) < 0)) {
                    j--;
                    break;
                }
                return j;
            }

        } else if (Utilidades.isCharacter(clazz)) {
            Character datoJ = (Character) auxI;
            Character datoJ1 = (Character) pivote;
            if (tipoOrdenacion.toString().equalsIgnoreCase(TipoOrdenacion.ASCENDENTE.toString())) {
                while (datoJ > datoJ1) {
                    j--;
                    break;
                }
                return j;
            } else {
                while (datoJ < datoJ1) {
                    //cambioBurbuja(j, matriz);
                    j--;
                    break;
                }
                return j;
            }

        }
        return null;
    }

    /**
     * Permite hacer el cambio con datos que son objetos del modelo
     *
     * @param clazz El tipo de clase del atributo
     * @param auxJ dato en la posicion J
     * @param auxJ1 dato en la posicion J + 1
     * @param method El metodo get de J
     * @param method1 El metodo get de J + 1
     * @param tipoOrdenacion El tipo de ordenacion si es Ascendente o
     * Descendente
     * @param j posicion
     * @throws Exception
     */
    private Object[] evaluaCambiarDato(Class clazz, E auxJ, E auxJ1, Method method, Method method1, TipoOrdenacion tipoOrdenacion, Integer j, E[] matriz) throws Exception {
        Object aux[] = new Object[2];
        if (clazz.getSuperclass().getSimpleName().equalsIgnoreCase("Number")) {
            Number datoJ = (Number) method.invoke(auxJ);
//            System.out.println("Dato que hay en datoJ: "+datoJ);
            Number datoJ1 = (Number) method1.invoke(auxJ1);
//            System.out.println("Dato que hay en datoJ1: "+datoJ1);
            if (tipoOrdenacion.toString().equalsIgnoreCase(TipoOrdenacion.ASCENDENTE.toString())) {
                if ((datoJ.doubleValue() > datoJ1.doubleValue())) {
                    // cambioBurbuja(j, matriz);
                    aux[0] = auxJ1;
                    aux[1] = j;
//                    System.out.println("Intercambio si Es mayor");
                }
            } else {
                if ((datoJ.doubleValue() < datoJ1.doubleValue())) {
                    //    cambioBurbuja(j, matriz);
                    aux[0] = auxJ1;
                    aux[1] = j;
//                    System.out.println("Intercambio si Es menor");
                }
            }
        } else if (Utilidades.isString(clazz)) {
            String datoJ = (String) method.invoke(auxJ);
            String datoJ1 = (String) method1.invoke(auxJ1);
            if (tipoOrdenacion.toString().equalsIgnoreCase(TipoOrdenacion.ASCENDENTE.toString())) {
                if ((datoJ.toLowerCase().compareTo(datoJ1.toLowerCase()) > 0)) {
                    //   cambioBurbuja(j, matriz);
                    aux[0] = auxJ1;
                    aux[1] = j;
                }
            } else {
                if ((datoJ.toLowerCase().compareTo(datoJ1.toLowerCase()) < 0)) {
                    //  cambioBurbuja(j, matriz);
                    aux[0] = auxJ1;
                    aux[1] = j;
                }
            }

        } else if (Utilidades.isCharacter(clazz)) {
            Character datoJ = (Character) method.invoke(auxJ);
            Character datoJ1 = (Character) method1.invoke(auxJ1);
            if (tipoOrdenacion.toString().equalsIgnoreCase(TipoOrdenacion.ASCENDENTE.toString())) {
                if (datoJ > datoJ1) {
                    // cambioBurbuja(j, matriz);
                    aux[0] = auxJ1;
                    aux[1] = j;
                }
            } else {
                if (datoJ < datoJ1) {
                    //  cambioBurbuja(j, matriz);
                    aux[0] = auxJ1;
                    aux[1] = j;
                }
            }

        }
        return aux;
    }

    /*
        Evaluar cambiar dato para metodo QuickSort
     */
    private Integer evaluaCambiarDatoObjetoQS_I(Class clazz, E auxI, E pivote, Method method, Method method1, TipoOrdenacion tipoOrdenacion, Integer varI, Integer j) throws Exception {

        if (clazz.getSuperclass().getSimpleName().equalsIgnoreCase("Number")) {
            Number datoJ = (Number) method.invoke(auxI);
            Number datoJ1 = (Number) method1.invoke(pivote);
            if (tipoOrdenacion.toString().equalsIgnoreCase(TipoOrdenacion.ASCENDENTE.toString())) {
                while ((datoJ.doubleValue() <= datoJ1.doubleValue()) && varI < j) {
                    varI++;
                    System.out.println(varI);
                }

            } else {
                while ((datoJ.doubleValue() >= datoJ1.doubleValue()) && varI > j) {
                    varI++;
                    System.out.println(varI);
                }

            }

        } else if (Utilidades.isString(clazz)) {
            String datoJ = (String) method.invoke(auxI);
            String datoJ1 = (String) method1.invoke(pivote);
            if (tipoOrdenacion.toString().equalsIgnoreCase(TipoOrdenacion.ASCENDENTE.toString())) {
                while ((datoJ.toLowerCase().compareTo(datoJ1.toLowerCase()) > 0)) {
                    varI++;
                    System.out.println(varI);

                }

            } else {
                while ((datoJ.toLowerCase().compareTo(datoJ1.toLowerCase()) < 0)) {
                    varI++;
                    System.out.println(varI);
                }

            }

        } else if (Utilidades.isCharacter(clazz)) {
            Character datoJ = (Character) method.invoke(auxI);
            Character datoJ1 = (Character) method1.invoke(pivote);
            if (tipoOrdenacion.toString().equalsIgnoreCase(TipoOrdenacion.ASCENDENTE.toString())) {
                while (datoJ > datoJ1) {
                    varI++;
                    System.out.println(varI);
                }

            } else {
                while (datoJ < datoJ1) {
                    varI++;
                    System.out.println(varI);
                }

            }
        }
        return varI;
    }

    private Integer evaluaCambiarDatoObjetoQS_J(Class clazz, E auxJ, E pivote, Method method, Method method1, TipoOrdenacion tipoOrdenacion, Integer j) throws Exception {
        Integer datoPosJ = j;
        if (clazz.getSuperclass().getSimpleName().equalsIgnoreCase("Number")) {
            Number datoJ = (Number) method.invoke(auxJ);
            Number datoJ1 = (Number) method1.invoke(pivote);
            if (tipoOrdenacion.toString().equalsIgnoreCase(TipoOrdenacion.ASCENDENTE.toString())) {
                while ((datoJ.doubleValue() > datoJ1.doubleValue())) {
                    j--;
                }

            } else {
                while ((datoJ.doubleValue() < datoJ1.doubleValue())) {
                    j--;
                }

            }
            datoPosJ = j;
        } else if (Utilidades.isString(clazz)) {
            String datoJ = (String) method.invoke(auxJ);
            String datoJ1 = (String) method1.invoke(pivote);
            if (tipoOrdenacion.toString().equalsIgnoreCase(TipoOrdenacion.ASCENDENTE.toString())) {
                while ((datoJ.toLowerCase().compareTo(datoJ1.toLowerCase()) > 0)) {

                    j--;
                }
                datoPosJ = j;
            } else {
                while ((datoJ.toLowerCase().compareTo(datoJ1.toLowerCase()) < 0)) {
                    j--;
                }
                datoPosJ = j;
            }

        } else if (Utilidades.isCharacter(clazz)) {
            Character datoJ = (Character) method.invoke(auxJ);
            Character datoJ1 = (Character) method1.invoke(pivote);
            if (tipoOrdenacion.toString().equalsIgnoreCase(TipoOrdenacion.ASCENDENTE.toString())) {
                while (datoJ > datoJ1) {
                    j--;

                }
                datoPosJ = j;
            } else {
                while (datoJ < datoJ1) {
                    j--;

                }
                datoPosJ = j;
            }

        }
        System.out.println("Se retorna datoPosJ " + datoPosJ);
        return datoPosJ;
    }

    //Metodos para Shell
    private Integer evaluaCambiarDatoObjetoShell(Class clazz, E auxJ, E auxK, Method method, Method method1, TipoOrdenacion tipoOrdenacion, Integer j, E[] matriz, Integer intervalo) throws Exception {

        if (clazz.getSuperclass().getSimpleName().equalsIgnoreCase("Number")) {
            Number datoJ = (Number) method.invoke(auxJ);
            Number datoJ1 = (Number) method1.invoke(auxK);
            if (tipoOrdenacion.toString().equalsIgnoreCase(TipoOrdenacion.ASCENDENTE.toString())) {
                if ((datoJ.doubleValue() <= datoJ1.doubleValue())) {
                    j = -1;
                } else {
                    intercambiar(matriz, j, j + 1);
                    j -= intervalo;
                }
                return j;
            } else {
                if ((datoJ.doubleValue() >= datoJ1.doubleValue())) {
                    j = -1;
                } else {
                    intercambiar(matriz, j, j + 1);
                    j -= intervalo;
                }

                return j;
            }
        } else if (Utilidades.isString(clazz)) {
            String datoJ = (String) method.invoke(auxJ);
            String datoJ1 = (String) method1.invoke(auxK);
            if (tipoOrdenacion.toString().equalsIgnoreCase(TipoOrdenacion.ASCENDENTE.toString())) {
                if ((datoJ.toLowerCase().compareTo(datoJ1.toLowerCase()) > 0)) {
                    j = -1;
                } else {
                    intercambiar(matriz, j, j + 1);
                    j -= intervalo;
                }

                return j;
            } else {
                if ((datoJ.toLowerCase().compareTo(datoJ1.toLowerCase()) < 0)) {
                    j = -1;
                } else {
                    intercambiar(matriz, j, j + 1);
                    j -= intervalo;
                }
                return j;
            }

        } else if (Utilidades.isCharacter(clazz)) {
            Character datoJ = (Character) method.invoke(auxJ);
            Character datoJ1 = (Character) method1.invoke(auxK);
            if (tipoOrdenacion.toString().equalsIgnoreCase(TipoOrdenacion.ASCENDENTE.toString())) {
                if (datoJ > datoJ1) {
                    j = -1;
                } else {
                    intercambiar(matriz, j, j + 1);
                    j -= intervalo;
                }
                return j;
            } else {
                if (datoJ < datoJ1) {
                    j = -1;
                } else {
                    intercambiar(matriz, j, j + 1);
                    j -= intervalo;
                }
                return j;
            }

        }
        return null;
    }

    private Integer evaluarCambiarDatoNoObjetoShell(Class clazz, E auxJ, E auxK, TipoOrdenacion tipoOrdenacion, Integer j, E[] matriz, Integer intervalo) throws Exception {

        if (clazz.getSuperclass().getSimpleName().equalsIgnoreCase("Number")) {
            if (tipoOrdenacion.toString().equalsIgnoreCase(TipoOrdenacion.ASCENDENTE.toString())) {
                if ((((Number) auxJ).doubleValue() <= ((Number) auxK).doubleValue())) {
                    j = -1;
                } else {
                    intercambiar(matriz, j, j + 1);
                    j -= intervalo;
                }
                return j;
            } else {
                if ((((Number) auxJ).doubleValue() >= ((Number) auxK).doubleValue())) {
                    j = -1;
                } else {
                    intercambiar(matriz, j, j + 1);
                    j -= intervalo;
                }
                return j;
            }
        } else if (Utilidades.isString(clazz)) {
            String datoJ = (String) auxJ;
            String datoJ1 = (String) auxK;
            if (tipoOrdenacion.toString().equalsIgnoreCase(TipoOrdenacion.ASCENDENTE.toString())) {
                if ((datoJ.toLowerCase().compareTo(datoJ1.toLowerCase()) > 0)) {
                    j = -1;
                } else {
                    intercambiar(matriz, j, j + 1);
                    j -= intervalo;
                }
                return j;
            } else {
                if ((datoJ.toLowerCase().compareTo(datoJ1.toLowerCase()) < 0)) {
                    j = -1;
                } else {
                    intercambiar(matriz, j, j + 1);
                    j -= intervalo;
                }
                return j;
            }

        } else if (Utilidades.isCharacter(clazz)) {
            Character datoJ = (Character) auxJ;
            Character datoJ1 = (Character) auxK;
            if (tipoOrdenacion.toString().equalsIgnoreCase(TipoOrdenacion.ASCENDENTE.toString())) {
                if (datoJ > datoJ1) {
                    j = -1;
                } else {
                    intercambiar(matriz, j, j + 1);
                    j -= intervalo;
                }
                return j;
            } else {
                if (datoJ < datoJ1) {
                    j = -1;
                } else {
                    intercambiar(matriz, j, j + 1);
                    j -= intervalo;
                }
                return j;
            }

        }
        return null;
    }

    /**
     * Metodo para busqueda secuencial
     *
     * @param atributo el atributo donde deseo buscar
     * @param dato El dato a buscar
     * @return ListaEnlazada<E> El listado de datos a buscar
     */
    public ListaEnlazada<E> buscar(String atributo, Object dato) throws Exception {
        Class<E> clazz = null;
        E[] matriz = null;
        ListaEnlazada<E> resultado = new ListaEnlazada<>();
        if (size > 0) {
            matriz = toArray();

            clazz = (Class<E>) cabecera.getDato().getClass();//primitivo, Dato envolvente, Object
            Boolean isObject = Utilidades.isObject(clazz);//si es objeto
            if (isObject) {
                Field field = Utilidades.getField(atributo, clazz);
//                Method method = getMethod("get" + Utilidades.capitalizar(atributo), field.getClass());

                for (int i = 0; i < matriz.length; i++) {
                    Method method1 = getMethod("get" + Utilidades.capitalizar(atributo), matriz[i].getClass());
                    E aux = buscarDatoPosicionObjeto(i, matriz, field.getType(), dato, method1);
                    if (aux != null) {
                        resultado.insertar(aux);
                    }
                }
            } else {
                for (int i = 0; i < matriz.length; i++) {
                    E aux = buscarDatoPosicion(i, matriz, clazz, (E) dato);
                    if (aux != null) {
                        resultado.insertar(aux);
                    }
                }
            }

        }
        return resultado;
    }

    private E buscarDatoPosicion(Integer j, E[] matriz, Class<E> clazz, E dato) throws Exception {
        E aux = null;
        if (clazz.getSuperclass().getSimpleName().equalsIgnoreCase("Number")) {
            Number datoJ = (Number) dato;
            Number datoJ1 = (Number) matriz[j];
            if (datoJ.doubleValue() == datoJ1.doubleValue()) {
                aux = (E) datoJ1;
                System.out.println(aux);
            }
        } else if (Utilidades.isString(clazz)) {
            String datoJ = (String) dato;
            String datoJ1 = (String) matriz[j];

            if (datoJ1.toLowerCase().startsWith(datoJ.toLowerCase())
                    || datoJ1.toLowerCase().endsWith(datoJ.toLowerCase())
                    || datoJ1.toLowerCase().equalsIgnoreCase(datoJ.toLowerCase())) {
                //cambioBurbuja(j, matriz);
                aux = (E) matriz[j];
                System.out.println(aux);
            }

        } else if (Utilidades.isCharacter(clazz)) {
            Character datoJ = (Character) dato;
            Character datoJ1 = (Character) matriz[j];
            if (datoJ.charValue() == datoJ1.charValue()) {
                aux = (E) matriz[j];
            }

        }
        return aux;
    }

    //5  7  9 9  12   15   18   buscar 9
    //Dato = clave--- 
//    private E buscarDato(String atributo, Class<E> clazz, E[] matriz, Integer central, E dato, Integer superior, Integer inferior, Method method) throws Exception {
//        E aux = null;
//        if (clazz.getSuperclass().getSimpleName().equalsIgnoreCase("Number")) {
//            Number datoJ = (Number) dato;
//            Number datoJ1 = (Number) matriz[central];
//
//            if (datoJ.doubleValue() == datoJ1.doubleValue()) {
//                aux = (E) datoJ1;
//            } else if (datoJ1.doubleValue() < datoJ.doubleValue()) {
//                aux = (E) busquedaBR(atributo, matriz, datoJ, central + 1, superior);
//            } else {
//                aux = (E) busquedaBR(atributo, matriz, datoJ, inferior, central - 1);
//
//            }
//        } else if (Utilidades.isString(clazz)) {
//            String datoJ = (String) dato;
//            String datoJ1 = (String) matriz[central];
//
//            if (datoJ1.toLowerCase().startsWith(datoJ.toLowerCase())
//                    || datoJ1.toLowerCase().endsWith(datoJ.toLowerCase())
//                    || datoJ1.toLowerCase().equalsIgnoreCase(datoJ.toLowerCase())) {
//                //cambioBurbuja(j, matriz);
//                aux = (E) central;
//            } else if (datoJ.toLowerCase().compareTo(datoJ1.toLowerCase()) < 0) {
//                aux = (E) busquedaBR(atributo, matriz, datoJ, central + 1, superior);
//            } else {
//                aux = (E) busquedaBR(atributo, matriz, datoJ, inferior, central - 1);
//            }
//
//        } else if (Utilidades.isCharacter(clazz)) {
//            Character datoJ = (Character) dato;
//            Character datoJ1 = (Character) matriz[central];
//            if (datoJ.charValue() == datoJ1.charValue()) {
//                aux = (E) central;
//            } else if (datoJ < datoJ1) {
//                aux = (E) busquedaBR(atributo, matriz, datoJ, central + 1, superior);
//            } else {
//                aux = (E) busquedaBR(atributo, matriz, datoJ, inferior, central - 1);
//            }
//
//        }
//        return aux;
//    }
    private E buscarDatoObjetoBBinario(ListaEnlazada<E> resultado, String atributo, Class<E> clazz, E[] matriz, Integer central, E dato, Integer superior, Integer inferior, Method method) throws Exception {
        E aux = null;
        if (clazz.getSuperclass().getSimpleName().equalsIgnoreCase("Number")
                && dato.getClass().getSuperclass().getSimpleName().equalsIgnoreCase("Number")) {
            Number datoJ = (Number) dato;
            Number datoJ1 = (Number) method.invoke(matriz[central]);
            if (datoJ1.doubleValue() == datoJ.doubleValue()) {
                aux = (E) dato;

            } else if (datoJ1.doubleValue() < datoJ.doubleValue()) {
                busquedaBR(resultado, atributo, matriz, dato, central + 1, superior);
            } else {
                busquedaBR(resultado, atributo, matriz, dato, inferior, central - 1);

            }

        } else if (Utilidades.isString(clazz) && Utilidades.isString(dato.getClass())) {
            String datoJ = (String) dato;
            String datoJ1 = (String) method.invoke(matriz[central]);
            if (datoJ1.toLowerCase().startsWith(datoJ.toLowerCase())
                    || datoJ1.toLowerCase().endsWith(datoJ.toLowerCase())
                    || datoJ1.toLowerCase().equalsIgnoreCase(datoJ.toLowerCase())) {

                aux = (E) matriz[central];

            } else if (datoJ.toLowerCase().compareTo(datoJ1.toLowerCase()) < 0) {
                busquedaBR(resultado, atributo, matriz, dato, central + 1, superior);
            } else {
                busquedaBR(resultado, atributo, matriz, dato, inferior, central - 1);
            }

        } else if (Utilidades.isCharacter(clazz) && Utilidades.isCharacter(dato.getClass())) {
            Character datoJ = (Character) dato;
            Character datoJ1 = (Character) method.invoke(matriz[central]);
            if (datoJ.charValue() == datoJ1.charValue()) {
                aux = (E) matriz[central];
            } else if (datoJ < datoJ1) {
                busquedaBR(resultado, atributo, matriz, dato, central + 1, superior);
            } else {
                busquedaBR(resultado, atributo, matriz, dato, inferior, central - 1);
            }

        }
//        System.out.println("Busqueda objeto binario: " + aux);
        return aux;
    }

    private E buscarDatoPosicionObjeto(Integer j, E[] matriz, Class clazz, Object dato, Method method1) throws Exception {
        E aux = null;
        if (clazz.getSuperclass().getSimpleName().equalsIgnoreCase("Number")
                && dato.getClass().getSuperclass().getSimpleName().equalsIgnoreCase("Number")) {
            Number datoJ = (Number) dato;
            Number datoJ1 = (Number) method1.invoke(matriz[j]);
            if (datoJ.doubleValue() == datoJ1.doubleValue()) {
                aux = (E) matriz[j];
            }
        } else if (Utilidades.isString(clazz) && Utilidades.isString(dato.getClass())) {
            String datoJ = (String) dato;
            String datoJ1 = (String) method1.invoke(matriz[j]);

            if (datoJ1.toLowerCase().startsWith(datoJ.toLowerCase())
                    || datoJ1.toLowerCase().endsWith(datoJ.toLowerCase())
                    || datoJ1.toLowerCase().equalsIgnoreCase(datoJ.toLowerCase())) {
                //cambioBurbuja(j, matriz);
                aux = (E) matriz[j];
            }

        } else if (Utilidades.isCharacter(clazz) && Utilidades.isCharacter(dato.getClass())) {
            Character datoJ = (Character) dato;
            Character datoJ1 = (Character) method1.invoke(matriz[j]);
            if (datoJ.charValue() == datoJ1.charValue()) {
                aux = (E) matriz[j];
            }

        }
        return aux;
    }

    public ListaEnlazada<E> ordenar_QuickSort(String atributo, E[] matriz, Integer izq, Integer der, TipoOrdenacion orden) throws Exception {
        System.out.println("valor en der: " + der);
        Class<E> clazz = null;
        if (size > 0) {
            matriz = toArray();
            clazz = (Class<E>) cabecera.getDato().getClass();
            Boolean isObject = Utilidades.isObject(clazz);
            E pivote = matriz[izq];
            Integer i = izq; //Primer dato de la lista
            System.out.println(i);
            Integer j = der; //Ultimo dato de la lista
            System.out.println(j);
            E aux = null;
            System.out.println("Entrando al while i < j");
            while (i < j) {

                if (isObject) {
//                    System.out.println("Entrando al is object");
                    Field field = Utilidades.getField(atributo, clazz);
                    Method metodo_I = getMethod("get" + Utilidades.capitalizar(atributo), matriz[i].getClass());
                    Method metodo_J = getMethod("get" + Utilidades.capitalizar(atributo), matriz[j].getClass());
                    Method metodo_pivote = getMethod("get" + Utilidades.capitalizar(atributo), pivote.getClass());
                    i = evaluaCambiarDatoObjetoQS_I(field.getType(), matriz[i], pivote, metodo_I, metodo_pivote, orden, i, j);
//                    System.out.println("i objeto " + i);
                    j = evaluaCambiarDatoObjetoQS_J(field.getType(), matriz[j], pivote, metodo_J, metodo_pivote, orden, j);
//                    System.out.println("j objeto " + j);
                } else {
//                    System.out.println("Entrando al si no es object");
                    i = evaluarCambiarDatoNoObjetoQS_I(clazz, matriz[i], pivote, i, orden, j);

                    j = evaluarCambiarDatoNoObjetoQS_J(clazz, matriz[j], pivote, orden, j);

                }

                if (i < j) {
                    aux = matriz[i];
                    matriz[i] = matriz[j];
                    matriz[j] = aux;
                }
            }

            matriz[izq] = matriz[j];
            matriz[j] = pivote;

            if (izq < j - 1) {
                matriz = ordenar_QuickSort(atributo, matriz, izq, j - 1, orden).toArray();
            }
            if (j + 1 < der) {
                matriz = ordenar_QuickSort(atributo, matriz, j + 1, der, orden).toArray();
            }
        }
        if (matriz != null) {
            toList(matriz);
        }

        return this;
    }

    public ListaEnlazada<E> ordenar_Shell(String atributo, TipoOrdenacion orden) throws Exception {

        Class<E> clazz = null;
        E[] matriz = null;
        if (size > 0) {
            matriz = toArray();
            clazz = (Class<E>) cabecera.getDato().getClass();
            Boolean isObject = Utilidades.isObject(clazz);
            Integer intervalo, i, j, k = 0;
            Integer n = matriz.length;
            intervalo = n / 2;
            while (intervalo > 0) {
                for (i = intervalo; i < n; i++) {
                    j = i - intervalo;
                    while (j >= 0) {
                        k = j + intervalo;
                        if (isObject) {
                            Field field = Utilidades.getField(atributo, clazz);
                            Method metodo = getMethod("get" + Utilidades.capitalizar(atributo), matriz[j].getClass());
                            Method metodo1 = getMethod("get" + Utilidades.capitalizar(atributo), matriz[k].getClass());
                            j = evaluaCambiarDatoObjetoShell(field.getType(), matriz[j], matriz[k], metodo, metodo1, orden, j, matriz, intervalo);
                        } else {
                            j = evaluarCambiarDatoNoObjetoShell(clazz, matriz[j], matriz[k], orden, j, matriz, intervalo);
                        }
                    }
                }
                intervalo = intervalo / 2;
            }
        }
        if (matriz != null) {
            toList(matriz);
        }
        return this;
    }

    public void intercambiar(E[] matriz, Integer i, Integer j) {
        E aux = matriz[i];
        matriz[i] = matriz[j];
        matriz[j] = aux;
    }

    public ListaEnlazada<E> busquedaBR(ListaEnlazada<E> resultado, String atributo, E[] matriz, Object dato, Integer inferior, Integer superior) throws Exception {
        Class<E> clazz = null;

        if (size > 0) {
            E aux = null;
            Integer central;
            matriz = toArray();
            clazz = (Class<E>) cabecera.getDato().getClass();//primitivo, Dato envolvente, Object
            Boolean isObject = Utilidades.isObject(clazz);//si es objeto      
            if (inferior > superior) {
                aux = null;
            } else {
                central = (inferior + superior) / 2;

                if (isObject) {
                    Field field = Utilidades.getField(atributo, clazz);
//                Method method = getMethod("get" + Utilidades.capitalizar(atributo), field.getClass());

//                for (int i = 0; i < matriz.length; i++) {
                    Method method1 = getMethod("get" + Utilidades.capitalizar(atributo), matriz[central].getClass());
//                    E aux = buscarDatoPosicionObjeto(i, matriz, field.getType(), dato, method1);
                    aux = buscarDatoObjetoBBinario(resultado, atributo, (Class<E>) field.getType(), matriz, central, (E) dato, superior, inferior, method1);
//                System.out.println(aux.getClass().getSimpleName());
//                System.out.println("Lo que hay en aux " + aux);
                    if (aux != null) {

                        resultado.insertar(aux);
//                    resultado.imprimir();
                    }
//                }
                } else {
//                for (int i = 0; i < matriz.length; i++) {
//                    E aux = buscarDatoPosicion(i, matriz, clazz, (E) dato);
//                    if (aux != null) {
//                        resultado.insertar(aux);
//                    }
//                }
                }

            }

        }
//        System.out.println("Lo que hay en la lista");
//        resultado.imprimir();

        return resultado;
    }

    public static void main(String[] args) throws Exception {
//
        try {
//
////            ListaEnlazada<String> lista = new ListaEnlazada<>();
////            FileReader fr = new FileReader("datos" + File.separatorChar + "numero.txt");
////            BufferedReader entrada = new BufferedReader(fr);
////            String aux = entrada.readLine();
////            Integer cont = 0;
////            while (aux != null) {
////                //matriz[cont] = Integer.parseInt(aux);
////                //lista.insertar(aux);
////                aux = entrada.readLine();
////                cont++;
////            }
////            fr.close();
////            entrada.close();
            PersonaController pc = new PersonaController();
            ListaEnlazada<Persona> lista = new ListaEnlazada<>();
            Persona persona = new Persona();
            Persona persona1 = new Persona();
            Persona persona2 = new Persona();
            Persona persona3 = new Persona();
            Persona persona4 = new Persona();
            Persona persona5 = new Persona();
            Persona persona6 = new Persona();

            persona.setNombre(pc.generarNombresAleatorios());
            persona.setApellido(pc.generarApellidosAleatorios());
            persona.setEmail(persona.getNombre() + persona.getApellido() + "@hotmail.com");
            persona.setTelefono(pc.generarNumeros());
            persona.setCedula(pc.generarNumeros());

            persona1.setNombre(pc.generarNombresAleatorios());
            persona1.setApellido(pc.generarApellidosAleatorios());
            persona1.setEmail(persona1.getNombre() + persona1.getApellido() + "@hotmail.com");
            persona1.setTelefono(pc.generarNumeros());
            persona1.setCedula(pc.generarNumeros());

            persona2.setNombre(pc.generarNombresAleatorios());
            persona2.setApellido(pc.generarApellidosAleatorios());
            persona2.setEmail(persona2.getNombre() + persona2.getApellido() + "@hotmail.com");
            persona2.setTelefono(pc.generarNumeros());
            persona2.setCedula(pc.generarNumeros());

            persona3.setNombre(pc.generarNombresAleatorios());
            persona3.setApellido(pc.generarApellidosAleatorios());
            persona3.setEmail(persona3.getNombre() + persona3.getApellido() + "@hotmail.com");
            persona3.setTelefono(pc.generarNumeros());
            persona3.setCedula(pc.generarNumeros());

            persona4.setNombre(pc.generarNombresAleatorios());
            persona4.setApellido(pc.generarApellidosAleatorios());
            persona4.setEmail(persona4.getNombre() + persona4.getApellido() + "@hotmail.com");
            persona4.setTelefono(pc.generarNumeros());
            persona4.setCedula(pc.generarNumeros());

            persona5.setNombre("Carlos");
            persona5.setApellido("Andrade");
            persona5.setEmail(persona.getNombre() + persona.getApellido() + "@hotmail.com");
            persona5.setTelefono(pc.generarNumeros());
            persona5.setCedula(pc.generarNumeros());

            persona6.setNombre("Carlos");
            persona6.setApellido("Guzman");
            persona6.setEmail(persona.getNombre() + persona.getApellido() + "@hotmail.com");
            persona6.setTelefono(pc.generarNumeros());
            persona6.setCedula(pc.generarNumeros());
            lista.insertar(persona);
            lista.insertar(persona1);
            lista.insertar(persona2);
            lista.insertar(persona3);
            lista.insertar(persona4);
            lista.insertar(persona5);
            lista.insertar(persona6);

//               
            lista.imprimir();
            System.out.println("SELECCION");
            ListaEnlazada<Persona> ordenadaSeleccion = lista.ordenar_seleccion("nombre", TipoOrdenacion.DESCENDENTE);
            ordenadaSeleccion.imprimir();
//            System.out.println(lista.getSize());
//            System.out.println("Quicksort");
//            Integer der = lista.getSize() - 1;
//            System.out.println(der);
            System.out.println("Tamaño de la lista: " + (lista.getSize() - 1));
            ListaEnlazada<Persona> ordenadaQuicksort = lista.ordenar_QuickSort("nombre", lista.toArray(), 0, (lista.getSize() - 1), TipoOrdenacion.ASCENDENTE);

            ordenadaQuicksort.imprimir();
//            System.out.println("Busqueda Binaria");
//            ListaEnlazada<Persona> bus = new ListaEnlazada();
//            bus = lista.busquedaBR(bus,"nombre", ordenada.toArray(), "Carlos", 0, ordenada.getSize() - 1);
////            System.out.println("--------------------------------------------------");
//            bus.imprimir();
//            System.out.println("--------------------------------------------------");
////            ListaEnlazada<Double> lista1 = new ListaEnlazada<>();
////            lista1.insertar(9.3);
////            lista1.insertar(4.1);
////            lista1.insertar(1.0);
////            lista1.insertar(9.3);
////            lista1.insertar(4.0);
////            lista1.insertar(4.1);
////            lista1.ordenar_seleccion("", TipoOrdenacion.ASCENDENTE);
////            lista1.imprimir();
//            System.out.println("Busqueda Ingeniero");
//            ListaEnlazada<Persona> busqueda = lista.buscar("nombre", "Carlos");
//            busqueda.imprimir();
        } catch (Exception e) {
            System.out.println("error " + e);
            e.printStackTrace();
        }

    }
}
