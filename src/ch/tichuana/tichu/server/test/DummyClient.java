package ch.tichuana.tichu.server.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class DummyClient {

    public static void main(String[] args){
        try (Socket socket = new Socket("127.0.0.1", 8080);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream());) {

            Scanner scan = new Scanner(System.in);

            System.out.println("player number:");
            String input = scan.next();
            String join = "{\"msg\":\"JoinMsg\",\"password\":\"pw123\",\"playerName\":\"Player"+ input +"\"}";
            System.out.println(join);
            out.write(join+"\n");
            out.flush();
            System.out.println(in.readLine());

            while (true) {
                System.out.println("\ns: send, r: receive");
                input = scan.next();

                if(input.equals("r")){
                    System.out.println(in.readLine());
                }
                else {
                    System.out.println("message:");
                    input = scan.next();
                    out.write(input+"\n");
                    out.flush();
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}