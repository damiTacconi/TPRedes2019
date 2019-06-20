package tp;

import lombok.Data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Observable;


public class ServerReadSocket extends Observable implements Runnable {
    private Socket socket;
    private String message;
    private BufferedReader in;

    public ServerReadSocket(Server server, Socket socket){
        this.socket = socket;
        addObserver(server);
    }

    @Override
    public void run(){
        try {
            while(!socket.isClosed()) {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                this.message = in.readLine();
                if(message.equals("X")){
                    try {
                        socket.close();
                    } catch (IOException ex) {
                        System.out.println(socket.getInetAddress().getHostAddress() + " CERRO LA CONEXION ");
                    }
                }else {
                    setChanged();
                    notifyObservers();
                }
            }
        } catch (IOException e) {
            System.out.println(socket.getInetAddress().getHostAddress() + " CERRO LA CONEXION ");
        }
    }

    public String getMessage(){return this.message;}
    public Socket getSocket(){return this.socket;}
}
