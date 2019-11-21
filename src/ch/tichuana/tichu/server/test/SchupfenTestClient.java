package ch.tichuana.tichu.server.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class SchupfenTestClient {

    public static void main(String[] args) {
        try (Socket socket = new Socket("127.0.0.1", 8080);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream());) {

            Scanner scan = new Scanner(System.in);

            System.out.println("player number:");
            String input = scan.next();

            String playerName = "player"+input;

            // join msg
            String join = "{\"msg\":\"JoinMsg\",\"password\":\"pw123\",\"playerName\":\""+ playerName +"\"}";
            out.write(join+"\n");
            out.flush();

            System.out.println("sent: " + join);

            System.out.println("received: " + in.readLine()); // connected
            System.out.println("received: " + in.readLine()); // Game start
            System.out.println("received: " + in.readLine()); // cards

            // grand tichu
            String tichu = "{\"msg\":\"TichuMsg\",\"tichuType\":\"none\",\"playerName\":\"" + playerName +"\"}";
            out.write(tichu+"\n");
            out.flush();

            System.out.println("sent: " + tichu);

            System.out.println("received: " + in.readLine()); // tichu
            System.out.println("received: " + in.readLine()); // tichu
            System.out.println("received: " + in.readLine()); // tichu
            System.out.println("received: " + in.readLine()); // tichu
            System.out.println("received: " + in.readLine()); // cards

            // small tichu
            out.write(tichu+"\n");
            out.flush();

            System.out.println("sent: " + tichu);

            System.out.println("received: " + in.readLine()); // tichu
            System.out.println("received: " + in.readLine()); // tichu
            System.out.println("received: " + in.readLine()); // tichu
            System.out.println("received: " + in.readLine()); // tichu

            while (true){
                System.out.print("s: send, r: receive:   ");
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

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
