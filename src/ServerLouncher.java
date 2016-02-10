import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Вадим on 05.02.2016.
 */
public class ServerLouncher extends Thread {

    public static void main(String[] args) {
        Server server = new Server();
    }

}
