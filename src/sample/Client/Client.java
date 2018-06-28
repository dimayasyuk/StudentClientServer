package sample.Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import org.apache.log4j.Logger;
import sample.Constants;
import sample.Student;

import java.net.Socket;
import java.util.List;

/**
 * Created by dmitriy on 10.6.18.
 */
public class Client {
    private static final Logger log = Logger.getLogger(Client.class);
    private Socket socket;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private String host;
    private int port;
    private MyView window;
    private Table table;
    private Table findTable;
    private boolean connect = false;
    public Client(MyView window,String host, int port){
        this.host = host;
        this.port = port;
        this.window = window;
        this.table = window.getTable();
        this.findTable = window.getFindTable();
        run();
    }
    public void setConnect(boolean connect){
        this.connect = connect;
    }

    public boolean isConnect() {
        return connect;
    }

    public ObjectInputStream getInput(){
        return input;
    }

    public ObjectOutputStream getOutput() {
        return output;
    }

    public void stop(){
        try {
            if(output!=null) {
                output.close();
            }
            if(input!=null) {
                input.close();
            }
            if(socket!=null) {
                socket.close();
            }
            table.getClient().sendData(Constants.Exit);
        } catch (IOException e) {
            log.error("Ошибка закрытия соединения");
        }
    }

    private void run(){
        try {
            this.socket = new Socket(host,port);
            this.socket.setSoTimeout(10000);
            input = new ObjectInputStream(socket.getInputStream());
            output = new ObjectOutputStream(socket.getOutputStream());
            connect = true;
        } catch (IOException e) {
           log.error("Не удалось соединиться с сервером");
           connect = false;
        }
    }
   public void sendData(Object data){
       try {
           output.writeObject(data);
           output.flush();
       } catch (IOException e) {
           log.error("Информация отсутствует");
       }
   }
   public void update(Table table) {
       try {
           table.setStudentsTable((List<Student>) input.readObject());
           table.setStudentsSize((int)input.readObject());
           table.setCurrentPage((int)input.readObject());
           table.setStudentOnPage((int)input.readObject());
       } catch (Exception e) {
           log.error("Не удалось прочитать информацию");
       }
   }
   public void updateTable(String currentTable) {
       if(currentTable.equals(Constants.MAIN_TABLE))
           update(table);
       else{
           update(findTable);
       }
   }
}
