package com.ljm.reactor.asyncbridge;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * @author 李佳明 https://github.com/pkpk1234
 * @date 2018-05-29
 */
public class BlockingHttpCaller {

    public String getWebResource(String webResource) throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {

            HttpGet httpGet = new HttpGet("webResource");
            CloseableHttpResponse response = httpclient.execute(httpGet);
            return EntityUtils.toString(response.getEntity());
        } finally {
            httpclient.close();
        }

    }
}
