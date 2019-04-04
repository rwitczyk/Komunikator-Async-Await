import org.apache.log4j.BasicConfigurator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;

import static com.ea.async.Async.await;

class Client {
    static String modifiedSentence,message="",id="";
    static Scanner odczyt = new Scanner(System.in);
    static Socket clientSocket;

    static PrintWriter outToServer;
    static BufferedReader inFromServer;

    public static void main(String argv[]) throws Exception {
        BasicConfigurator.configure();
        clientSocket = new Socket("localhost", 6789);
        outToServer = new PrintWriter(clientSocket.getOutputStream(),true); //rzeczy do serwera wysylane
        inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); //rzeczy z serwera
        System.out.print("Podaj swoj nick: ");
        id=odczyt.nextLine();


           to_server();

            while(true) {
                modifiedSentence = inFromServer.readLine();
                if(!modifiedSentence.contains(id))
                System.out.println(modifiedSentence);
            }

     //   clientSocket.close();
    }

    public static void to_server() {
        if(!(message.equals("exit"))) {
            System.out.print(id + ": ");
            message=odczyt.nextLine();
            outToServer.println(id);
            outToServer.println(message);
        }
    }
}


/*
        public async process_user() // to na serwer, u klienta bedzie podobnie tylko connect bedzie
        {
            bool terminate = false
            while(!terminate)
            {
                int counter = 0
                await send("....");
                send(id); ??
                send(message); ??
                wynik = await recv();
                if (wynik ==fsdfsdsd)
                {
                    await send...
                    print("a");
                    wynik2 = await recv(...)
                    print("b");

                }
                if (wynik == "quit")
                {
                    await close()
                    terminate = true;
                }
            }
        }
        while(1)
{
    conn = accept(); // tu tez musi byc asynchroniczne
    process_user(conn); // ta metoda tez
}*/
//        sentence = inFromUser.readLine();
//        outToServer.writeBytes(sentence + 'n');
