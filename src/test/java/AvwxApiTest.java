import api.AvwxRequests;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class AvwxApiTest {

    @Test
    public void getMetarTest() throws IOException {
        JSONObject res = AvwxRequests.getMetar("EDDF");


        JSONObject meta = (JSONObject) res.get("meta");
        meta.get("timestamp");
    }
}
