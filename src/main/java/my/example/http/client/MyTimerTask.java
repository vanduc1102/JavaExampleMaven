package my.example.http.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentLinkedQueue;
import javax.net.ssl.SSLContext;
import static org.apache.http.HttpHeaders.USER_AGENT;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClients;

/**
 *
 * @author nvduc
 */
public class MyTimerTask extends TimerTask {

    private static int TIME_OUT = 1000;
    private ConcurrentLinkedQueue<String> eventQueue;

    @Override
    public void run() {
        System.out.println("Timer task started at:" + new Date());
        completeTask();
        System.out.println("Timer task finished at:" + new Date());
    }

    private void completeTask() {
        try {
            //assuming it takes 20 secs to complete the task
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) throws IOException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        TimerTask timerTask = new MyTimerTask();
        //running timer task as daemon thread
        System.out.println("my.example.http.client.MyTimerTask.main() : " + doGet());
        Timer timer = new Timer(true);
        //timer.scheduleAtFixedRate(timerTask, 0, 10 * 1000);
        System.out.println("TimerTask started");
        //cancel after sometime
        try {
            Thread.sleep(120000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        timer.cancel();
        System.out.println("TimerTask cancelled");
        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static String doGet() throws IOException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        String url = "https://vntils12:8443/xhrc-web/rest/user/profile";
        SSLContext sslcontext = SSLContexts.custom().loadTrustMaterial(null,
                new TrustSelfSignedStrategy()).build();

// Allow TLSv1 protocol only, use NoopHostnameVerifier to trust self-singed cert
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext,
                new String[]{"TLSv1"}, null, new NoopHostnameVerifier());
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials("hrcadmin", "123456"));
//do not set connection manager
        HttpClient httpclient = HttpClients
                .custom()
                .create()
                .setDefaultCredentialsProvider(credentialsProvider)
                .setSSLSocketFactory(sslsf).build();

        RequestConfig defaultRequestConfig = RequestConfig
                .custom()
                .setCookieSpec(CookieSpecs.DEFAULT)
                .setExpectContinueEnabled(true)
                .setTargetPreferredAuthSchemes(
                        Arrays.asList(AuthSchemes.NTLM, AuthSchemes.DIGEST))
                .setProxyPreferredAuthSchemes(Arrays.asList(AuthSchemes.BASIC)).build();
        RequestConfig requestConfig = RequestConfig.copy(defaultRequestConfig)
                .setSocketTimeout(TIME_OUT).setConnectTimeout(TIME_OUT)
                .setConnectionRequestTimeout(TIME_OUT).build();

        //HttpPost httpPost = new HttpPost(url);
//        httpPost.setConfig(requestConfig);
//        httpPost.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
//        StringEntity mEntity = new StringEntity(null, StandardCharsets.UTF_8);
//        mEntity.setContentType("application/json;charset=UTF-8");
//        mEntity.setContentEncoding(new BasicHeader(HttpHeaders.CONTENT_TYPE,
//                "application/json;charset=UTF-8"));
//        httpPost.setEntity(mEntity);
        //response = httpclient.execute(httpPost);
        //HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(url);
        request.setConfig(requestConfig);

// add request header
        request.addHeader("User-Agent", USER_AGENT);
        HttpResponse response = httpclient.execute(request);

        System.out.println("Response Code : "
                + response.getStatusLine().getStatusCode());

        BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));

        StringBuilder result = new StringBuilder();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        return result.toString();

    }

}
