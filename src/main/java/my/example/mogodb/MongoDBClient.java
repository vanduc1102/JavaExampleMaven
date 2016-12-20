package my.example.mogodb;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

/**
 *
 * @author nvduc
 */
public class MongoDBClient {
    static String host = "vntils11";
    static int port = 27017;
    public static void main(String[] args) throws UnknownHostException {
        MongoClient mongoClient = new MongoClient(new ServerAddress(host,port),Arrays.asList(MongoCredential.createCredential("admin", "demo", "123456".toCharArray())));
        mongoClient.getDB("demo");
        System.out.println("my.example.mogodb.MongoDBClient.main()" + mongoClient.getDatabaseNames());
    }
}
