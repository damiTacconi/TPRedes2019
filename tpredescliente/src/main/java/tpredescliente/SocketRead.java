package tpredescliente;

import lombok.Data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class SocketRead extends Thread {

    protected Socket socket;

    SocketRead(Socket socket)
    {
        this.socket = socket;
    }

    @Override
    public void run()
    {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            while (!this.socket.isClosed()) {
                String message = in.readLine();
                if("X".equals(message)) {
                    this.socket.close();
                    this.showMessage("\nEL SERVIDOR SE DESCONECTO");
                }
                else this.showMessage(message);
            }
            in.close();

        }catch (IOException e){
           this.showMessage("\nSE CERRO LA CONEXION CON EL SERVIDOR");
        }
    }


    private void showMessage(String message)
    {
        System.out.println(message);
    }
}
