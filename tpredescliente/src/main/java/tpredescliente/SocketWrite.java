package tpredescliente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class SocketWrite extends Thread{
    protected Socket socket;

    public SocketWrite(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run(){
        try {
            PrintWriter out = new PrintWriter(this.socket.getOutputStream());
            String message = "";
            while (!this.socket.isClosed() && !"X".equals(message)) {
                /*
                 * El Scanner bloquea la entrada de datos asi que por mas que el servidor se desconecte,
                 * el scanner tendra que terminar de leer la entrada de datos para luego volver
                 * al while y verificar que realmente termino la conexion.
                 */
                Scanner scanner = new Scanner(System.in);
                message = scanner.nextLine();
                out.println(message);
                out.flush();
            }
            out.close();
            this.socket.close();
        }catch (IOException e){
            System.out.println("\nSE CERRO LA CONEXION");
        }
    }

}
