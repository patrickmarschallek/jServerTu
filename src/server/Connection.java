/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Patrick
 */
public class Connection implements Runnable {

    private int port = 2106;
    private List<Byte> message;
    private final static Logger LOGGER = Logger.getLogger(Connection.class.getName());
    private int messageCounter;

    public Connection() throws IOException {
        this.messageCounter = 0;
        LOGGER.addHandler(new FileHandler("error.log"));
    }

    /**
     *
     * @param port
     * @throws IOException
     */
    public Connection(int port) throws IOException {
        this();
        this.port = port;
    }

    /**
     *
     */
    @Override
    public void run() {
        while (true) {
            try {
                ServerSocket serverSocket = new ServerSocket(this.port);
                Socket client = waitForMessage(serverSocket);

                initConnection();

                String m = readMessage(client);
                this.messageCounter++;
                switch (this.messageCounter) {
                    case 1:
                        MessageHandler.processFirstMessage(Integer.parseInt(m));
                        break;
                    case 2:
                        MessageHandler.processSecondMessage(m);
                        break;
                    default:
                    //exit connection

                }
                System.out.println(m);
                writeMessage(client, m);
            } catch (IOException ex) {
                System.out.println("ERROR : " + ex.getCause());
                LOGGER.log(Level.WARNING, null, ex);
            }
        }
    }

    /**
     *
     * @param serverSocket
     * @return
     * @throws IOException
     */
    private Socket waitForMessage(ServerSocket serverSocket) throws IOException {
        Socket socket = serverSocket.accept(); // blockiert, bis sich ein Client angemeldet hat
        return socket;
    }

    /**
     *
     * @param socket
     * @return
     * @throws IOException
     */
    private Byte[] readByteMessage(Socket socket) throws IOException {
        BufferedReader bufferedReader =
                new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
        int value;
        while ((value = bufferedReader.read()) != -1) {
            this.message.add((byte) value);
        }
        return this.message.toArray(new Byte[this.message.size()]);
    }

    /**
     *
     * @param socket
     * @return
     * @throws IOException
     */
    private String readMessage(Socket socket) throws IOException {
        BufferedReader bufferedReader =
                new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
        char[] buffer = new char[200];
        int anzahlZeichen = bufferedReader.read(buffer, 0, 200); // blockiert bis Nachricht empfangen
        String m = new String(buffer, 0, anzahlZeichen);
        return m;
    }

    /**
     *
     * @param socket
     * @param stringMessage
     * @throws IOException
     */
    private void writeMessage(Socket socket, String stringMessage) throws IOException {
        PrintWriter printWriter =
                new PrintWriter(
                new OutputStreamWriter(socket.getOutputStream()));
        printWriter.print(stringMessage);
        printWriter.flush();
    }

    /**
     *
     */
    private void initConnection() {
        try {
            Thread connection = new Thread(new Connection(this.port));
            connection.start();
        } catch (Exception ex) {
            System.out.println("ERROR : " + ex.getCause());
            LOGGER.log(Level.WARNING, null, ex);
        }
    }
}
