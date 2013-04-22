package server;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Patrick
 */
public class Connection implements Runnable {

    private final static String PATTERN = "[A-Za-z0-9]{10,20}";
    private final static String FAIL = "FAIL";
    private final static String OK = "OK";
    private int messageCount;
    private Socket clientReceiveSocket;
    private Socket clientSendSocket;
    private String randomSalt;

    /**
     * 
     * @param sender
     * @param reciever 
     */
    public Connection(Socket sender, Socket reciever) {
        this.clientReceiveSocket = reciever;
        this.clientSendSocket = sender;
        this.randomSalt = "";
    }

    /**
     * 
     */
    @Override
    public void run() {
        BufferedReader in = null;
        PrintWriter out = null;
        DataInputStream inData = null;
        DataOutputStream outData = null;
        try {
            System.out.println("Client has connected");
            in = new BufferedReader(new InputStreamReader(clientSendSocket.getInputStream()));
            out = new PrintWriter(clientReceiveSocket.getOutputStream(), true);
            inData = new DataInputStream(clientSendSocket.getInputStream());
            outData = new DataOutputStream(clientReceiveSocket.getOutputStream());

            while (true) {
            	
            	if(messageCount == 0){
            		int value = inData.readInt();
            		System.out.println("Received int: " + value);
            		value++;
            		this.randomSalt = generateRandomSalt();
            		System.out.println("Sending int: " + value);
            		outData.writeInt(value);
            		outData.writeInt(randomSalt.length());
            		System.out.println("Sending String: " + randomSalt);
            		outData.writeBytes(randomSalt);
            		
            	} else if (messageCount == 1){
            		String result = "";
            		 byte b = inData.readByte();
            		 while (b != 10){
            			 result += (char)b;
            			 b = inData.readByte();
            		 }
            		 System.out.println("result: " + result);
            		 
            		 if(result.equals(this.changeStringCases(this.randomSalt))){
            			 System.out.println("OK");
            			 outData.writeBytes("OK  ");
            		 } else{
            			 System.out.println("FAIL");
            			 outData.writeBytes("FAIL");
            		 }
            	}
            	messageCount++;
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
                    outData.close();
                    inData.close();
                    clientReceiveSocket.close();
                    clientSendSocket.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     *
     * @return
     */
    private String generateRandomSalt() {
        String salt = "";
        int length = radnomCharacter(10, 20);
//        do {
            for (int i = 0; i < length; i++) {
                char character;
                do {
                    character = (char) radnomCharacter(48, 122);
                } while (!validateLowerCharacter(character)
                        && !validateUpperCharacter(character)
                        && !validateNumberCharacter(character));
                salt += character;
            }
//        } while (salt.matches(PATTERN));
        return salt;
    }

    /**
     *
     * @param character
     * @return
     */
    private boolean validateNumberCharacter(char character) {
        return (character >= 48 && character <= 57) ? true : false;
    }

    /**
     *
     * @param character
     * @return
     */
    private boolean validateUpperCharacter(char character) {
        return (character >= 65 && character <= 90) ? true : false;
    }

    /**
     *
     * @param character
     * @return
     */
    private boolean validateLowerCharacter(char character) {
        return (character >= 97 && character <= 122) ? true : false;
    }

    /**
     *
     * @param value
     * @return
     */
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

    /**
     *
     * @param low
     * @param high
     * @return
     */
    public static int radnomCharacter(int low, int high) {
        return (int) (Math.random() * (high - low) + low);
    }

    /**
     *
     * @param value
     * @param pattern
     * @return
     */
    public boolean validatePattern(String value, String pattern) {
        if (value.matches(pattern)) {
            return true;
        } else {
            return false;
        }
    }
}
