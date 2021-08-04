import api.AvwxRequests;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class AvwxApiTest {

    @Test
    public void getMetarIcaoTest() throws IOException {
        JSONObject res = AvwxRequests.getMetar("EDDF");


        JSONObject meta = (JSONObject) res.get("meta");
        meta.get("timestamp");
    }

    @Test
    public void getMetarIataTest() throws IOException {
        JSONObject res = AvwxRequests.getMetar("FRA");


        JSONObject meta = (JSONObject) res.get("meta");
        meta.get("timestamp");
    }

    @Test
    public void getTafIcaoTest() throws IOException {
        JSONObject res = AvwxRequests.getTAF("EDDF");


        JSONObject meta = (JSONObject) res.get("meta");
        meta.get("timestamp");
    }

    @Test
    public void getTafIataTest() throws IOException {
        JSONObject res = AvwxRequests.getTAF("FRA");


        JSONObject meta = (JSONObject) res.get("meta");
        meta.get("timestamp");
    }

    @Test
    public void getInvalidTafTest() throws IOException {
        JSONObject res = AvwxRequests.getTAF("FRAS");


        Assertions.assertThrows(JSONException.class, () -> res.get("meta"));
    }

}
