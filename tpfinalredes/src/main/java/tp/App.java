package tp;
import java.io.*;

/**
 * Hello world!
 *
 */
public class App 
{
    private static final int PORT = 3000;

    public static void main( String[] args )
    {
        try{
            new Server(PORT)
                    .start();

        }catch (IOException e){
            System.out.println("SE CERRO LA CONEXION DEL SERVIDOR");
        }
    }
}
