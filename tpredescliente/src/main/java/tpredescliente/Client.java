package tpredescliente;

import lombok.Data;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import java.util.Scanner;

@Data
public class Client{
    protected Socket socket;
    protected SocketRead socketRead;
    protected SocketWrite socketWrite;

    public Client(final String IP , final int PORT) throws IOException{
        this.socket = new Socket(IP , PORT);
    }

    public void start()
    {
        System.out.println("\n\t\t CONECTADO EXITOSAMENTE \n");
        this.socketRead = new SocketRead(this.socket);
        this.socketWrite = new SocketWrite(this.socket);
        this.socketRead.start();
        this.socketWrite.start();
    }

}
