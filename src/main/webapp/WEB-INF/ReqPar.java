import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.Socket;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;

@WebServlet({"/hello"})
public class ReqPar extends HttpServlet {
    PrintWriter writer;
    private static Socket clientSocket; //сокет для общения
    private static BufferedReader reader; // нам нужен ридер читающий с консоли, иначе как
    // мы узнаем что хочет сказать клиент?
    private static BufferedReader in; // поток чтения из сокета
    private static BufferedWriter out; // поток записи в сокет

    public ReqPar() {
    }
    public String SocketCommunication(String message){
        String serverWord="";
        try {
            try {
                // адрес - локальный хост, порт - 5001, такой же как у сервера
                clientSocket = new Socket("localhost", 5001); // этой строкой мы запрашиваем
                //                //  у сервера доступ на соединение
                reader = new BufferedReader(new InputStreamReader(System.in));
                // читать соообщения с сервера
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                // писать туда же
                out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                // не напишет в консоль
                out.write(message + "\n"); // отправляем сообщение на сервер
                out.flush();
                serverWord = in.readLine(); // ждём, что скажет сервер
            } finally { // в любом случае необходимо закрыть сокет и потоки
                //writer.println("Клиент был закрыт...");
                clientSocket.close();
                in.close();
                out.close();
            }
        } catch (IOException e) {
            System.err.println(e);
        }
        return serverWord;
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        writer = response.getWriter();
        response.setContentType("application/json");
        String direction = request.getParameter("direction");
        //MongoClient mongoClient = new MongoClient("192.168.99.100", 27017);
        //MongoClientURI uri = new MongoClientURI("mongodb://admin:admin@127.0.0.1:27017/test?readPreference=primary&authSource=admin&w=majority");
        //MongoClient mongoClient = new MongoClient(uri);
        //MongoDatabase database = mongoClient.getDatabase("Tanks");
        //MongoCollection<Document> collection = database.getCollection("Parametrs");
        writer.println("{\"coord\":"+SocketCommunication(direction)+"} 10.12.2019 23:07");
        //int takeFromDB;
        /*try {
            if (direction.equals("D")) {
                takeFromDB = Integer.parseInt(collection.find().first().get("y").toString()); // берём первую (.first) запись и достаём оттуда y
                int result = takeFromDB - 2;
                writer.println(result);
                collection.updateOne(eq("_id", new ObjectId("5dc15093b2c0392d9ccd97b5")), set("y", result)); // обновляем дб
            } else if (direction.equals("L")) {
                takeFromDB = Integer.parseInt(collection.find().first().get("x").toString()); // берём первую (.first) запись и достаём оттуда x
                int result = takeFromDB - 2;
                writer.println(SocketCommunication(direction));
                collection.updateOne(eq("_id", new ObjectId("5dc15093b2c0392d9ccd97b5")), set("x", result)); // обновляем дб
            } else if (direction.equals("R")) {
                takeFromDB = Integer.parseInt(collection.find().first().get("x").toString()); // берём первую (.first) запись и достаём оттуда x
                int result = takeFromDB + 2;
                writer.println(result);
                collection.updateOne(eq("_id", new ObjectId("5dc15093b2c0392d9ccd97b5")), set("x", result)); // обновляем дб
            } else if (direction.equals("U")) {
                takeFromDB = Integer.parseInt(collection.find().first().get("y").toString()); // берём первую (.first) запись и достаём оттуда y
                int result = takeFromDB + 2;
                writer.println(result);
                collection.updateOne(eq("_id", new ObjectId("5dc15093b2c0392d9ccd97b5")), set("y", result)); // обновляем дб
            }
        } finally {
            writer.close();
        }*/
        writer.close();
    }
}
