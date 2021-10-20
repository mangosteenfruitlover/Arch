import java.net.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

class ClientConnection{
    private Socket socket;
    private BufferedReader reader; // Поток чтения из сокета
    private BufferedWriter writer; // Поток чтения в сокет
    private BufferedReader inputClient; // Поток чтения с консоли
    private String client_ip; // IP Клиента
    private int port; // Порт соединения
    private String login; // Логин клиента
    private Date time_message;
    private String str_datetime_message; //Сообщение о времени для вывода в консоль
    private SimpleDateFormat datetime_format; //Формат времени HH:mm:ss

    public ClientConnection(String client_ip,int port) {
        this.client_ip=client_ip;
        this.port = port;
        try {
            this.socket = new Socket(client_ip,port);
        } catch (IOException e) {
            System.err.println("Ошибка покдлючения");
        }
        try {
            inputClient= new BufferedReader(new InputStreamReader(System.in));
            reader= new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer= new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.writeLogin(); // Консоль: имя Клиента
            new ReadMessage().start(); // Консоль: чтение из сокета сообщений в бесконечном цикле
            new WriteMessage().start(); // Консоль: ввод из сокета сообщений в бесконечном цикле
        } catch (IOException e) {
            ClientConnection.this.downService();
        }
    }

    private void writeLogin() {
        System.out.print("имя Клиента: ");
        try {
            login= inputClient.readLine();
            writer.write("Ваше имя: " +login+ "\n");
            writer.flush();
        } catch (IOException ignored) {
        }
    }

    private void downService() {
        try {
            if (!socket.isClosed()) {
                socket.close();
                reader.close();
                writer.close();
            }
        } catch (IOException ignored) {}
    }

    private class ReadMessage extends Thread {
        @Override
        public void run() {
            String message;
            try {
                while (true) {
                    message = reader.readLine();
                    if (message.equals("exit")) {
                        ClientConnection.this.downService();
                        break;
                    }
                    System.out.println(message);
                }
            } catch (IOException e) {
                ClientConnection.this.downService();
            }
        }
    }

    public class WriteMessage extends Thread {
        @Override
        public void run() {
            while (true) {
                String client_word;
                try {
                    time_message= new Date(); // Текущая дата
                    datetime_format= new SimpleDateFormat("HH:mm:ss");
                    str_datetime_message= datetime_format.format(time_message);
                    client_word = inputClient.readLine();
                    if (client_word.equals("exit")) {
                        writer.write("exit" + "\n");
                        ClientConnection.this.downService();
                        break;
                    } else {
                        writer.write("|" +str_datetime_message+ "| " +login+ ": " + client_word + "\n");
                    }
                    writer.flush();
                } catch (IOException e) {
                    ClientConnection.this.downService();
                }
            }
        }
    }
}
