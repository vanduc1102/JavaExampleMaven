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
    static String host = "127.0.0.1";
    static int port = 27017;
    static char[] password = "changeMe".toCharArray();
    public static void main(String[] args) throws UnknownHostException {
        MongoClient mongoClient = new MongoClient(new ServerAddress(host,port),Arrays.asList(MongoCredential.createScramSha1Credential("root", "ductest", password)));
        mongoClient.getDB("ductest");
        System.out.println("my.example.mogodb.MongoDBClient.main()" + mongoClient.getDatabaseNames());
    }
}
