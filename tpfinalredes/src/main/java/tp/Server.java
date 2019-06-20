package tp;

import lombok.Data;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
@Data
public class Server implements Observer {
    private ServerSocket serverSocket;
    private List<Socket> sockets;
    private Thread serverRead;

    public Server(int port) throws IOException
    {
        this.sockets = new ArrayList<>();
        this.serverSocket = new ServerSocket(port);
    }

    public void start() throws IOException
    {
        ServerAccept serverAccept = new ServerAccept(this);
        Thread threadAccept = new Thread(serverAccept);
        threadAccept.start();

        String msj = "";

        while(!"X".equals(msj))
        {
            Scanner scanner = new Scanner(System.in);
            msj = scanner.nextLine();
            for(Socket socket : sockets)
                write(msj,socket, true);
        }

        for(Socket socket : sockets)
            socket.close();

        serverSocket.close();
    }

    @Override
    public void update(Observable observable, Object o) {
        if(observable instanceof ServerAccept) {
            this.sockets.add(((ServerAccept) observable).getSocket());
            if(Objects.nonNull(this.serverRead)) {
                if (this.serverRead.isAlive()) {
                    this.serverRead.interrupt();
                }
            }
            this.serverRead = new Thread(new ServerRead(this));
            serverRead.start();
        }
        else if(observable instanceof ServerReadSocket) {
            String message = String.format("%s: %s",
                    ((ServerReadSocket) observable).getSocket().getInetAddress().getHostName(),
                    ((ServerReadSocket) observable).getMessage()
            );
            sockets.stream()
                    .filter(s ->
                    !s.getInetAddress()
                            .getHostAddress().equals(((ServerReadSocket) observable)
                            .getSocket()
                            .getInetAddress()
                            .getHostAddress())
                    )
                    .forEach( s -> {
                        try {
                            write(message,s, false);
                        } catch (IOException e) { }
                    });
            System.out.println(message);
        }
    }

    private void write(String message , Socket socket , boolean isServerMessage) throws IOException{
        String name = isServerMessage ? "SERVER: " : "";
        try {
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            out.println(name + message);
            out.flush();
        }catch (IOException e){
            socket.close();
        }
    }
}
