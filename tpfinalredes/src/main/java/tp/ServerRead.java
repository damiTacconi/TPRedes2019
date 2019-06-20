package tp;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ServerRead extends Thread {
    private Server server;
    ServerRead(Server server){
        this.server = server;
    }

    public void run(){


        server.getSockets().forEach(socket -> {

            Thread threadReadSocket = new Thread(new ServerReadSocket(server,socket));
            threadReadSocket.start();

        });
    }
}
