/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.tda.lista.exception;

/**
 *
 * @author sebastian
 */
public class EstructuraDataVaciaExpetion extends Exception {

    /**
     * Creates a new instance of <code>PilaVaciaExpetion</code> without detail
     * message.
     */
    public EstructuraDataVaciaExpetion() {
    }

    /**
     * Constructs an instance of <code>PilaVaciaExpetion</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public EstructuraDataVaciaExpetion(String msg) {
        super(msg);
    }
}
