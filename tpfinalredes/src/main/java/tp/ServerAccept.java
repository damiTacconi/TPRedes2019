package tp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Observable;


class ServerAccept extends Observable implements Runnable {
    private ServerSocket serverSocket;
    private Socket socket = null;

    public ServerAccept(Server server){
        super();
        this.serverSocket = server.getServerSocket();
        this.addObserver(server);
    }

    @Override
    public void run(){
        try {
            while (!serverSocket.isClosed()) {
                this.socket = serverSocket.accept();
                System.out.println(socket.getInetAddress().getHostAddress() + " SE UNIO !");
                setChanged();
                notifyObservers();
            }
        }
        catch (IOException e)
        {
            System.out.println("SE CERRO LA CONEXION CON EL SERVIDOR");
        }
    }

    public Socket getSocket(){return this.socket; }


}
