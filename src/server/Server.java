/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.File;

/**
 *
 * @author Patrick
 */
public class Server {

    private final static String SHUTDOWN_MESSAGE = "shutdown";
    private final static String SEPERATOR = File.separator;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String message = "";
        
        //run the server if they don't recieve a shutdown message
        while (!message.equals(SHUTDOWN_MESSAGE)) {
            
        }
    }
}
