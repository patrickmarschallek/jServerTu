/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Patrick
 */
public class SocketClient implements Runnable {

    public static int count = 0;
    private String name;

    public static void main(String[] args) throws IOException {

        SocketClient client = new SocketClient("1");
        SocketClient client1 = new SocketClient("2");
        SocketClient client2 = new SocketClient("3");

    }

    public SocketClient(String name) {
        this.name = name;
        new Thread(this).start();
    }

    @Override
    public void run() {
        try {
            SocketClient.count++;
            Socket recievSocket = new Socket("localhost", 4321);
            Socket sendSocket = new Socket("localhost", 1234);

            System.out.println("Connect with server (" + sendSocket.getPort() + " / " + recievSocket.getPort() + ")");

            PrintWriter w = new PrintWriter(
                    new OutputStreamWriter(
                    sendSocket.getOutputStream()), true);
            w.print(name + "\n");
            w.flush();

            String m = "";

            BufferedReader in = new BufferedReader(new InputStreamReader(recievSocket.getInputStream()));
            m = in.readLine();
            System.out.println(m);
            System.out.println(this.changeStringCases(m));
            try {
                Thread.sleep(200);
            } catch (InterruptedException ex) {
                Logger.getLogger(SocketClient.class.getName()).log(Level.SEVERE, null, ex);
            }

            w.print(this.changeStringCases(m)+"\n");
            
            try {
                Thread.sleep(200);
            } catch (InterruptedException ex) {
                Logger.getLogger(SocketClient.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            System.out.println(in.readLine());
            
            w.close();
            in.close();
            recievSocket.close();
            sendSocket.close();
        } catch (UnknownHostException ex) {
            Logger.getLogger(SocketClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SocketClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private String changeStringCases(String value) {
        char[] chars = value.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if (Character.isUpperCase(c)) {
                chars[i] = Character.toLowerCase(c);
            } else if (Character.isLowerCase(c)) {
                chars[i] = Character.toUpperCase(c);
            }
        }
        return new String(chars);
    }
}
