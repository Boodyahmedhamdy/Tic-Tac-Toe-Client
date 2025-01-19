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
public class LoginResponse extends Response {
    private String userId;

    public LoginResponse(boolean success, String message, String userId) {
        super(success, message);
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }
}