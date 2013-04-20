/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Patrick
 */
public class Server {

    private ServerSocket recieveSocket;
    private ServerSocket sendSocket;

    public static void main(String[] args) {
        new Server().start();
    }

    private void start() {

        try {
            sendSocket = new ServerSocket(4321);
            recieveSocket = new ServerSocket(1234);
            while (true) {

                Socket clientReceiveSocket = sendSocket.accept();
                Socket clientSendSocket = recieveSocket.accept();

                new Thread(new Handler(clientSendSocket, clientReceiveSocket)).start();
            }
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
        }
    }

    private class Handler implements Runnable {

        private Socket clientReceiveSocket;
        private Socket clientSendSocket;

        public Handler(Socket sender, Socket reciever) {
            this.clientReceiveSocket = reciever;
            this.clientSendSocket = sender;
        }

        @Override
        public void run() {
            BufferedReader in = null;
            PrintWriter out = null;
            try {
                System.out.println("Client has connected");
                in = new BufferedReader(new InputStreamReader(clientSendSocket.getInputStream()));
                out = new PrintWriter(clientReceiveSocket.getOutputStream(), true);

                while (true) {
//                if (in.ready()) {
                    String s = in.readLine();
                    if ((s != null) && (s.length() != 0)) {
                        if (s.equals("end")) {
                            System.out.println("\nClient has initiated server shutdown");
                            System.exit(0);
                        } else {
                            System.out.println("\nClient has send : " + s);
                            out.println(s + "\n");
                        }
                    }

//                }
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } catch (Exception ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    if (out != null) {
                        out.close();
                        in.close();
                        clientReceiveSocket.close();
                        clientSendSocket.close();
                    }
                } catch (IOException ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
