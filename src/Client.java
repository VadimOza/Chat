import java.io.*;
import java.net.Socket;
import java.util.Locale;
import java.util.Scanner;

/**
 * Created by Вадим on 06.02.2016.
 */
public class Client {
    DataInputStream in;
    DataOutputStream out;
    Socket clientSocket;
    Scanner sc;
    String name;
    Client(){
        sc = new Scanner(new InputStreamReader(System.in));
        try {
            clientSocket = new Socket("localhost", 27015);
            System.out.println("Connected to server!");
            System.out.println("Enter your Nick: ");
            Scanner sc = new Scanner(new InputStreamReader(System.in));
            name = sc.nextLine();
        }catch(Exception ex){
            System.out.println("Can't connect to server "+ex);
        }
        try {
            in = new DataInputStream(clientSocket.getInputStream());
            out = new DataOutputStream(clientSocket.getOutputStream());

            ClientReader reader = new ClientReader();
            reader.start();
            String str = "";
            while (!str.equals("exit")) {
                str = sc.nextLine();
                out.writeUTF(name+": "+str);
            }
        }catch(Exception ex){
            System.out.println("Err: "+ex);
        }



    }
    public static void main(String[] args) {
        Client cl = new Client();

        Runtime.getRuntime().addShutdownHook(new Thread()
        {
            @Override
            public void run()
            {
                try {
                    cl.out.writeUTF("-LeftChat");
                }catch (Exception ex){
                    System.out.println("Err: Outing from server: "+ex);
                }
            }
        });

    }

    public class ClientReader extends Thread{
        Boolean stop = false;
        public void setStop(){
            stop = true;
        }


        public void run(){
            try {
                while (!stop) {
                    System.out.println(in.readUTF());
                }
            }catch (Exception ex){
                System.out.println("Err: "+ex);
            }
        }
    }


}
