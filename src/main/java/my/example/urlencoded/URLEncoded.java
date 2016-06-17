package my.example.urlencoded;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

/**
 *
 * @author nvduc
 */
public class URLEncoded {
    public static void main(String[] args) throws UnsupportedEncodingException {
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("key", "Đức nguyễn here"));
        String encodedData = URLEncoder.encode("Đức nguyễn here", "UTF-8");
        System.out.println("encoded data : "+ encodedData);
        String decodedData = java.net.URLDecoder.decode(encodedData, "UTF-8");
        System.out.println("decoded data : "+decodedData);
    }
}
