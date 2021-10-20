import java.io.*;
import java.net.*;
class ServerConnection extends Thread {
    private Socket socket;
    private BufferedReader reader; // Поток чтения из сокета
    public BufferedWriter getWriter() {
        return writer; // Поток чтения в сокет
    }
    private BufferedWriter writer;
    public ServerConnection(Socket socket) throws IOException{
        this.socket = socket;
        reader= new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer= new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        ServerMain.messagelog.printMessage_log(writer);
        start();
    }
    @Override
    public void run() {
        String word_message;
        try {
            word_message = reader.readLine();
            try {
                writer.write(word_message + "\n");
                writer.flush();
            } catch (IOException ignored) {}
            try {
                while (true) {
                    word_message = reader.readLine();
                    if(word_message.equals("exit")) {
                        this.downService();
                        break;
                    }
                    System.out.println("Сообщение: " + word_message);
                    ServerMain.messagelog.addMessage_logItem(word_message);
                }
            } catch (NullPointerException ignored) {}
        } catch (IOException e) {
            this.downService();
        }
    }
    private void downService() {
        try {
            if(!socket.isClosed()) {
                socket.close();
                reader.close();
                writer.close();
                for (ServerConnection vr : ServerMain.server_list) {
                    if(vr.equals(this)) vr.interrupt();
                    ServerMain.server_list.remove(this);
                }
            }
        } catch (IOException ignored) {}
    }
}
