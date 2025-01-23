/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network.responses;
import network.requests.Request;
import java.io.Serializable;

/**
 *
 * @author Abdelrahman_Elshreif
 */

public class StartGameResponse extends Response implements Serializable {
    private String username;

    public StartGameResponse(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}