/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network.responses;

/**
 *
 * @author HP
 */
import java.io.Serializable;

public class RegisterResponse extends Response {

    private static final long serialVersionUID = 1L;
    boolean status;

    public RegisterResponse() {

    }

    public boolean isSuccess() {
        return status;
    }

    public String getMessage() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
