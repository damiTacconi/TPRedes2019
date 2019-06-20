package tpredescliente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        //final String IP = "10.0.2.133";
       // final String HOSTNAME = "damian-pc";
        //final int PORT = 2525;
        final Scanner scanner = new Scanner(System.in);

        try {

            System.out.print("ESCRIBA IP: ");
            String ip = scanner.nextLine();
            System.out.print("ESCRIBA PUERTO: ");
            int port = scanner.nextInt();


            Socket socket = new Socket("192.168.0.94", 3000);
            System.out.println("CONECTADO EXITOSAMENTE ! ");
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            Thread threadRead = new Thread(() -> {
                String mensaje = "";
                while(!"X".equals(mensaje) && !socket.isClosed()){
                    try {
                        mensaje = in.readLine();
                        if(mensaje.equals("X"))
                            socket.close();
                        System.out.println(mensaje);

                    }catch (IOException e){
                        System.out.println("FINALIZO LA CONEXION CON EL SERVIDOR");
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            });
            threadRead.start();
            String mensaje = "";
            while(!"X".equals(mensaje.trim()) && !socket.isClosed()){
                Scanner scanner2 = new Scanner(System.in);
                mensaje = scanner2.nextLine();
                out.println(mensaje);
                out.flush();
            }
            threadRead.interrupt();
            out.close();
            in.close();
        } catch (ConnectException e){
            System.out.println("LA CONEXION FUE RECHAZADA, NO SE PUDO CONECTAR");
        } catch (IOException e) {
            System.out.println("HUBO UN PROBLEMA CON LA CONEXION");
            e.printStackTrace();
        }catch (IllegalArgumentException e){
            System.out.println("PUERTO O IP INVALIDA");
        }catch (InputMismatchException e){
            System.out.println("DEBE INGRESAR DATOS VALIDOS");
        }
    }
}
