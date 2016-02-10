import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Вадим on 05.02.2016.
 */
public class Server {
    List<Connection> listOfConnections = new ArrayList<Connection>();

    public Server() {
        try {
            ServerSocket server = new ServerSocket(27015);
            System.out.println("Server created!");
            while(true){
                Connection con = new Connection(server.accept());
                listOfConnections.add(con);
                con.start();
                System.out.println("New User has connected!");

            }


        }catch (Exception ex){
            System.out.println("err: "+ex);
        }

    }


    public class Connection extends Thread {
        Socket s;
        DataInputStream in;
        DataOutputStream out;
        String message;
        String Nick;

        Connection(Socket s){
            this.s = s;

            try {
                in = new DataInputStream (s.getInputStream());
                out = new DataOutputStream(s.getOutputStream());
            }catch(Exception ex){
                System.out.println("err: "+ex);

            }
        }

        public void run(){
            try {
                while (true) {
                    message = in.readUTF();
                    System.out.println(message);
                    if(message.equals("-LeftChat") || message.equals("-exit")){
                        break;
                    }
                    for(Connection c : listOfConnections){
                        c.out.writeUTF(message);
                    }
                }
            }catch(Exception ex){
                System.out.println("err: "+ex);
            }
            listOfConnections.remove(this);
            System.out.println("User is left");
        }

    }
}
