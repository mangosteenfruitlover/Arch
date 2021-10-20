import java.io.*;
import java.net.*;
import java.util.LinkedList;

public class ServerMain{

    public static final int port= 8080;
    public static LinkedList<ServerConnection> server_list= new LinkedList<>(); // Сервера, слушащие своих Клиентов
    public static Message_log messagelog;

    public static void main(String[] args) throws IOException, InterruptedException {
        ServerSocket server = new ServerSocket(port);
        messagelog= new Message_log();
        System.out.println("Сервер начал работу\nОжидание сообщений");
        try {
            for (int i=0;i<2;i++){ //Тестирование 2-х подключений
                Socket socket = server.accept(); //Блокирование и ожидание новых подключений Клиентов
                try {
                    server_list.add(new ServerConnection(socket)); // Добавление нового подключение в server_list
                } catch (IOException e) {
                    socket.close();
                }

            }
            while(true){
                for (ServerConnection vr : ServerMain.server_list) {
                    BufferedWriter out=vr.getWriter();
                    messagelog.printMessage_log(out);
                }
                messagelog.getMessage_log().clear();
                Thread.sleep(5000);
            }
        } finally {
            server.close();
        }
    }
}