/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network.requests;

/**
 *
 * @author HP
 */
public class LoginRequest extends Request {
    private String username;
    private String password;

    public LoginRequest(String username, String password) {
        super("LOGIN"); 
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
