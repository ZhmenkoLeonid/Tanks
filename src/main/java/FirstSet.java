import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;
import java.io.PrintWriter;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;

@WebServlet({"/hello"})
public class FirstSet extends HttpServlet {
    public FirstSet() {
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        String x = request.getParameter("x");
        String y = request.getParameter("y");
        //MongoClientURI uri = new MongoClientURI("mongodb://admin:admin@tanksdb-shard-00-00-aruig.mongodb.net:27017,tanksdb-shard-00-01-aruig.mongodb.net:27017,tanksdb-shard-00-02-aruig.mongodb.net:27017/test?ssl=true&replicaSet=TanksDB-shard-0&authSource=admin&retryWrites=true&w=majority");
        //MongoClient mongoClient = new MongoClient(uri);
         MongoClient mongoClient = new MongoClient("192.168.99.100", 27017);

        MongoDatabase database = mongoClient.getDatabase("Tanks");
        MongoCollection<Document> collection = database.getCollection("Parametrs");
        String takeFromDB = collection.find().first().get("y").toString();
        if (x != null) {
            collection.updateOne(eq("_id", new ObjectId("5dc15093b2c0392d9ccd97b5")), set("x", Integer.parseInt(x))); // обновляем дб
        }
        if (y != null){
            collection.updateOne(eq("_id", new ObjectId("5dc15093b2c0392d9ccd97b5")), set("y", Integer.parseInt(y))); // обновляем дб
        }

    }
}
