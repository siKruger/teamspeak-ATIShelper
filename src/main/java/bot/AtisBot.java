package bot;

import api.AvwxRequests;
import atis.Atis;
import com.github.manevolent.ts3j.api.TextMessageTargetMode;
import com.github.manevolent.ts3j.event.TS3Listener;
import com.github.manevolent.ts3j.event.TextMessageEvent;
import com.github.manevolent.ts3j.identity.LocalIdentity;
import com.github.manevolent.ts3j.protocol.socket.client.LocalTeamspeakClientSocket;
import exception.AtisCooldownException;
import io.github.cdimascio.dotenv.Dotenv;
import org.json.JSONObject;
import tts.OpusParameters;
import tts.TeamspeakFastMixerSink;

import java.io.File;
import java.util.regex.Pattern;

import static tts.TeamspeakFastMixerSink.AUDIO_FORMAT;

public class AtisBot implements TS3Listener {
    LocalTeamspeakClientSocket client;
    Dotenv dotenv = Dotenv.load();
    TeamspeakFastMixerSink sink;

    String liste[][] = new String[4][2];


    public AtisBot() throws Exception {
        client = new LocalTeamspeakClientSocket();

        LocalIdentity identity = LocalIdentity.read(new File("src\\main\\resources\\bot_identity.ini"));
        client.setIdentity(identity);


        client.setNickname("ATIS/METAR/TAF Bot");
        client.connect(dotenv.get("TEAMSPEAK_HOSTNAME"), dotenv.get("TEAMSPEAK_PASSWORD"), 20000);
        client.setDescription("Write !help to get a list of available commands.");

        // Create a sink
        sink = new TeamspeakFastMixerSink(
                AUDIO_FORMAT,
                (int) AUDIO_FORMAT.getSampleRate() * AUDIO_FORMAT.getChannels() * 2/*4=32bit float*/,
                new OpusParameters(
                        20,
                        96000, // 96kbps
                        10, // max complexity
                        0, // 0 expected packet loss
                        false, // no VBR
                        false, // no FEC
                        true // OPUS MUSIC - channel doesn't have to be Opus Music ;)
                )
        );
        client.setMicrophone(sink);
        client.addListener(this);
        sink.start();


        // fill the list with available commands
        liste[0][0] = "METAR";
        liste[0][1] = "Type !METAR and an Airport ICAO- or IATA-Code to get the current METAR report.";
        liste[1][0] = "ATIS";
        liste[1][1] = "Type !ATIS and an Airport ICAO- or IATA-Code to get the current ATIS report.";
        liste[2][0] = "TAF";
        liste[2][1] = "Type !TAF and an Airport ICAO- or IATA-Code to get the current TAF report.";
        liste[3][0] = "HELP";
        liste[3][1] = "The available commands are:\n!METAR\n!ATIS\n!TAF\n!HELP\nFor command specific help type the command without value.";
    }


    @Override
    public void onTextMessage(TextMessageEvent e) { // fetches message
        TS3Listener.super.onTextMessage(e);
        if (Pattern.matches("^!.+", e.getMessage())) { // checks if message is command
            int r = 0;
            if (e.getTargetMode().equals(TextMessageTargetMode.CLIENT)) {
                r = e.getInvokerId();
            }
            try {
                messageLength(e.getMessage(), r);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public void messageLength(String msg, int reciever) throws Exception { // checks if message contains values
        String[] part = msg.toUpperCase().substring(1).split("\s");
        String part1, part2;

        if (part.length == 2) {
            part1 = part[0];
            part2 = part[1];
            action(part1, part2, reciever);
        } else if (part.length == 1) {
            part1 = part[0];
            action(part1, "", reciever);
        }
    }

    public void action(String part1, String part2, int reciever) throws Exception {
        if (!part2.equals("") && Pattern.matches("[A-Z0-9]{3,4}", part2) && !part2.equals("HELP")) {
            if (liste[0][0].equals(part1)) {
                JSONObject getter = AvwxRequests.getMetar(part2);

                response(String.valueOf(getter.get("sanitized")), reciever);
            } else if (liste[1][0].equals(part1)) {

                try {
                    Atis.generateAtis(part2, sink);
                } catch (AtisCooldownException e) {
                    response("Bitte warte eine Minute...", reciever);
                }


            } else if (liste[2][0].equals(part1)) {
                JSONObject getter = AvwxRequests.getTAF(part2);

                response(String.valueOf(getter.get("sanitized")), reciever);
            }
        } else {
            for (int x = 0; x <= liste.length; x++) {
                if (part1.equals(liste[x][0])) {
                    response(liste[x][1], reciever);
                    return;
                }
            }
            response(liste[3][1], reciever);
        }
    }

    public void response(String msg, int reciever) throws Exception {
        if (reciever != 0) {
            client.sendPrivateMessage(reciever, msg);
        } else {
            client.sendChannelMessage(client.getClientId(), msg);
        }
    }

    public static void main(String[] args) throws Exception {
        new AtisBot();
    }

}
