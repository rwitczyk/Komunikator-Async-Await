import org.apache.log4j.BasicConfigurator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static com.ea.async.Async.await;

@SuppressWarnings("unchecked")
class Server {
    static BufferedReader inFromClient;
    static PrintWriter outToClient[] = new PrintWriter[10];
    static Map<Socket, String> clients = new HashMap<Socket, String>();
    static ServerSocket welcomeSocket;
    static Socket s1;
    static int i=0;

    @SuppressWarnings("unchecked")
    public static void main(String argv[]) throws Exception {
        BasicConfigurator.configure();
        welcomeSocket = new ServerSocket(6789);


        while(true)
        {
            await(socketss());
            await(wyswietlaj());

        }

        //welcomeSocket.close();
    }

    //czy klient musi miec awaita? nie
    //musi byc while na kliencie ktory caigle chodzi? nie
    //jak identyfikowac klientow? //po socketach, slownik (id,socket)
    //czy moze byc na watkach ewentualnie? moze

// jak podlaczyc wielu klientow do jednego serwera == lista klientow zapisana na serwerze gdzies
// jak stworzyc wielu klientow, async await jest tylko do synchronizacji metod, zeby wiele rzeczy naraz moglo sie wykonywac
    //while, ktory czeka na klienta sobie
// klient po nicku ma byc rozpoznawany?


    public static CompletableFuture socketss() throws IOException {
        s1=welcomeSocket.accept();
        inFromClient = new BufferedReader(new InputStreamReader(s1.getInputStream())); //do odczytu od klienta
        outToClient[i] = new PrintWriter(s1.getOutputStream(),true); //do wyslania do klienta
        i++;
        return CompletableFuture.completedFuture(false);
    }

    public static CompletableFuture wyswietlaj() throws IOException {
        String message, id;
        final int[] j = {0};
        id=inFromClient.readLine();
        message=inFromClient.readLine();
        clients.put(s1,id);
        System.out.println(id + ": " + message);
        clients.forEach((k,v) -> {
            outToClient[j[0]].println(id + ": " + message);
            j[0]++;
            System.out.println("KEY: " + k +" ID " + id +" VALUE: " + v + " MESSAGE: " + message);
        });
        return CompletableFuture.completedFuture(false);
    }

}

// jak metody await maja wiedziec ze cos przyszlo, jakis parametr gdzies cos? + completable future ustawiamy parametr odpowiedni?
// jak klientow identyfikowac po tym id, w jakis sposob do konkretnych klientow cos wysylac?