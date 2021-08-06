package atis;

import api.AvwxRequests;
import converter.NatoAlphabet;
import exception.AtisCooldownException;
import org.joda.time.LocalTime;
import org.json.JSONObject;
import tts.TTSGenerator;
import tts.TeamspeakFastMixerSink;

public class Atis {
    private static LocalTime lastRequest = new LocalTime().minusMinutes(1);


    public static void generateAtis(String station, TeamspeakFastMixerSink sink) throws Exception {
        System.out.println(lastRequest);


        if (lastRequest.getHourOfDay() == new LocalTime().getHourOfDay() && lastRequest.getMinuteOfHour() + 1 > new LocalTime().getMinuteOfHour())
            throw new AtisCooldownException();
        lastRequest = new LocalTime();


        //First we want to request a METAR-Object
        JSONObject metar = AvwxRequests.getMetar(station);

        String[] stationIcao = String.valueOf(metar.get("station")).split("");
        String atisInformation = String.valueOf(metar.get("speech"));


        StringBuilder finalAtis = new StringBuilder("Automatic Terminal Information Service for ");


        for (String icaoLetter : stationIcao) {
            finalAtis.append(NatoAlphabet.valueOf(icaoLetter).getSpoken()).append(" ");
        }
        finalAtis.append(". ").append(atisInformation);


        finalAtis = new StringBuilder(finalAtis.toString().replaceAll("kt", " knots"));
        finalAtis = new StringBuilder(finalAtis.toString().replaceAll("[(]", ""));
        finalAtis = new StringBuilder(finalAtis.toString().replaceAll("[)]", ""));
        finalAtis = new StringBuilder(finalAtis.toString().replaceAll("ft", " feet"));
        finalAtis.append(". End of Automatic Terminal Information Service");


        TTSGenerator.speakText(String.valueOf(finalAtis), sink);
    }
}
