public class ClientMain{

    public static String client_ip= "localhost";
    public static int port = 8080;

    public static void main(String[] args) {
        new ClientConnection(client_ip,port);
    }
}