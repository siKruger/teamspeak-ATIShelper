package api;


import io.github.cdimascio.dotenv.Dotenv;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class AvwxRequests {
    private static final String BASE_URL = "https://avwx.rest/api";
    private static final Dotenv dotenv = Dotenv.load();

    public static JSONObject getMetar(String station) throws IOException {
        return makeHttpGet("/metar/" + station);
    }


    /**
     * Make a http request to avwx
     *
     * @param urlAddition addition to URL
     * @return JSONObject with data
     */
    private static JSONObject makeHttpGet(String urlAddition) throws IOException {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet(BASE_URL + urlAddition);
            httpGet.setHeader("Authorization", dotenv.get("METAR_API_TOKEN"));

            try (CloseableHttpResponse httpRes = httpclient.execute(httpGet)) {
                HttpEntity httpEntity = httpRes.getEntity();
                return new JSONObject(IOUtils.toString(httpEntity.getContent(), StandardCharsets.UTF_8));
            }
        }
    }
}
