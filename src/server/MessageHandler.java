/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

/**
 *
 * @author Patrick
 */
public class MessageHandler {
    
    /**
     * 
     * @param message
     * @return 
     */
    public static String processFirstMessage(int message){
        String m = "";
        //check MAX and MIN int values to handle integer overflow.
        message++;
        
        //generate a Random value of the expression => [A-Za-z0-9]{10,20}
        // it can be contains numbers, characters upper and lower case
        // the value consists of 10 characters - 20 characters
        m +=message+" ";
        return String.valueOf(message);
    }
    
    /**
     * 
     * @return 
     */
    public static String processSecondMessage(String message){
        //check if the client have send the correct answer
        
        // OK / FAIL
        return "OK";
    }
}
