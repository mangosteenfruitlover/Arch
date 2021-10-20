import java.io.*;
import java.util.LinkedList;

class Message_log{
    private LinkedList<String> message_log= new LinkedList<>();

    public LinkedList<String> getMessage_log() {
        return message_log;
    }

    public void addMessage_logItem(String item) {
        if (message_log.size() >= 100) {
            message_log.removeFirst();
            message_log.add(item);
        } else {
            message_log.add(item);
        }
    }

    public void printMessage_log(BufferedWriter writer) {
        if(message_log.size() > 0) {
            try {
                for (String vr : message_log) { //Лог сообщений Клиентов
                    writer.write(vr + "\n");
                }
                writer.flush();
            } catch (IOException ignored) {}

        }

    }
}
