package tp;

import lombok.Data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Objects;
import java.util.Observable;


public class ServerReadSocket extends Observable implements Runnable {
    private Server server;
    private Socket socket;
    private String message;
    private BufferedReader in;

    public ServerReadSocket(Server server, Socket socket){
        this.socket = socket;
        this.server = server;
        addObserver(server);
    }

    @Override
    public void run(){
        try {
            while(!socket.isClosed() && !server.getServerSocket().isClosed()) {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                this.message = in.readLine();
                if(Objects.nonNull(this.message)) {
                    if (message.equals("X")) {
                        try {
                            socket.close();
                            this.showMessageClientDisconnect();
                        } catch (IOException ex) {
                            this.showMessageClientDisconnect();
                        }
                        in.close();
                    } else {
                        setChanged();
                        notifyObservers();
                    }
                }else{
                    this.socket.close();
                    this.in.close();
                    this.showMessageClientDisconnect();
                }
            }
        } catch (IOException e) {
            this.showMessageClientDisconnect();
        }
    }

    private void showMessageClientDisconnect(){
        System.out.println(socket.getInetAddress().getHostAddress() + " CERRO LA CONEXION ");
    }

    public String getMessage(){return this.message;}
    public Socket getSocket(){return this.socket;}
}
