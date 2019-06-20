package tpredescliente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
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
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("\nAGREGAR IP: ");
            String ip = scanner.nextLine();
            System.out.print("\nAGREGAR PUERTO: ");
            int port = scanner.nextInt();

            Client client = new Client(ip , port);
            client.start();
        }catch (UnknownHostException e) {
            System.out.println("\nNO SE ENCONTRO EL HOST");
        }catch (ConnectException e){
            System.out.println("LA CONEXION FUE RECHAZADA, NO SE PUDO CONECTAR");
        }catch (IOException e) {
            System.out.println("CONEXION FINALIZADA");
        }catch (IllegalArgumentException e){
            System.out.println("PUERTO O IP INVALIDA");
        }catch (InputMismatchException e){
            System.out.println("DEBE INGRESAR DATOS VALIDOS");
        }
    }
}
