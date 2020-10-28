package Week_02.第2次课;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import java.io.IOException;

public class HttpClient {
    public static void main(String[] args) {
        // 创建http get请求
        HttpGet httpGet = new HttpGet("http://localhost:8809/test");

        try (
                // 创建http对象
                CloseableHttpClient httpClient = HttpClients.createDefault();
                // 创建http response
                CloseableHttpResponse response = httpClient.execute(httpGet);
                ) {
                // 获取返回内容并输出
                String content = EntityUtils.toString(response.getEntity(), "UTF-8");
                System.out.println(content);

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
