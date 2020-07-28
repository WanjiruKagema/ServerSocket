/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.toysserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Kagema
 */
public class ToysServer {

    public static void main(String[] args) throws Exception {

        int portNumber = Integer.parseInt(args[0]);
        ServerSocket serverSocket = new ServerSocket(portNumber);
        Socket clientSocket = new Socket();
        int numConnections = 1;
        
        while (numConnections <= 4) {

            try {
                clientSocket = serverSocket.accept();
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                Thread t = new ClientThread(clientSocket, out, in);
                t.start();
                numConnections++;
                
            } catch (IOException e) {

                clientSocket.close();
                System.out.println("I/O error: " + e);
            }
            
        }

    }

    private static class ClientThread extends Thread {

        private String inputLine, outputLine;
        private Socket sock;
        PrintWriter o;
        BufferedReader i;

        public ClientThread(Socket sock, PrintWriter o, BufferedReader i) {
            this.sock = sock;
            this.o = o;
            this.i = i;
        }

        @Override
        public void run() {

            try {
                ToyServerProtocol toyProtocol = new ToyServerProtocol();

                outputLine = toyProtocol.processRequest(null);

                o.println(outputLine);

                while ((inputLine = i.readLine()) != null) {
                    outputLine = toyProtocol.processRequest(inputLine);
                    o.println(outputLine);

                    if (outputLine.equals("Thanks")) {
                        sock.close();
                        o.close();
                        i.close();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
