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
        /*Scanner scanner = new Scanner(System.in);
        System.out.println("\nAGREGAR IP: ");
        String ip = scanner.nextLine();
        System.out.println("\nAGREGAR PUERTO: ");
        int port = scanner.nextInt();*/

        try {
            Client client = new Client("192.168.0.8" , 3000);
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
