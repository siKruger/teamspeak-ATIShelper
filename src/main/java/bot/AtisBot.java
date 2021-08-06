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
    /**
     * ATIS/METAR/TAF helper bot for TS3
     * Authors: Simon and Ben
     *
     * ToDo
     *  - Refactor methode action - ASAP
    */


    LocalTeamspeakClientSocket client;
    Dotenv dotenv = Dotenv.load();
    TeamspeakFastMixerSink sink;

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

        Database.DBcreate(); // create the database for the commands - necessary
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
        String part1, part2, part3, part4;

        if (part.length == 1) {
            part1 = part[0];
            action(part1, "", "", "", reciever);
        } else if (part.length == 2) {
            part1 = part[0];
            part2 = part[1];
            action(part1, part2, "", "", reciever);
        } else if (part.length == 3) {
            part1 = part[0];
            part2 = part[1];
            part3 = part[2];
            action(part1, part2, part3, "",reciever);
        } else if (part.length == 4) {
            part1 = part[0];
            part2 = part[1];
            part3 = part[2];
            part4 = part[3];
            System.out.println(part1 +"\n"+  part2+ "\n"+part3+ "\n"+part4);
            action(part1, part2, part3, part4, reciever);
        }
    }

    // this is going to be terrifying
    public void action(String part1, String part2, String part3, String part4, int reciever) throws Exception {
        if (!part2.equals("") && Pattern.matches("[A-Z0-9]{3,4}", part2) && !part2.equals("HELP") && part3.equals("")) {
            if (Database.command[0][0].equals(part1)) {
                JSONObject getter = AvwxRequests.getMetar(part2);

                response(String.valueOf(getter.get("sanitized")), reciever);
            } else if (Database.command[1][0].equals(part1)) {

                try {
                    Atis.generateAtis(part2, sink);
                } catch (AtisCooldownException e) {
                    response("Bitte warte eine Minute...", reciever);
                }


            } else if (Database.command[2][0].equals(part1)) {
                JSONObject getter = AvwxRequests.getTAF(part2);

                response(String.valueOf(getter.get("sanitized")), reciever);
            }
        } else if (!part2.equals("") && Pattern.matches("\\d+", part2) && !part2.equals("HELP")) {
            if (Database.command[4][0].equals(part1)) { // skip 3, because 3 is !HELP
                response("" + FLC.kg2lb(Double.parseDouble(part2)), reciever);
            } else if (Database.command[5][0].equals(part1)) {
                response("" + FLC.lb2kg(Double.parseDouble(part2)), reciever);
            } else if (Database.command[6][0].equals(part1)) {
                response("" + FLC.kmh2kn(Double.parseDouble(part2)), reciever);
            } else if (Database.command[7][0].equals(part1)) {
                response("" + FLC.kn2kmh(Double.parseDouble(part2)), reciever);
            } else if (Database.command[8][0].equals(part1)) {
                response("" + FLC.km2nm(Double.parseDouble(part2)), reciever);
            } else if (Database.command[9][0].equals(part1)) {
                response("" + FLC.nm2km(Double.parseDouble(part2)), reciever);
            } else if (Database.command[10][0].equals(part1)) {
                response("" + FLC.m2ft(Double.parseDouble(part2)), reciever);
            } else if (Database.command[11][0].equals(part1)) {
                response("" + FLC.ft2m(Double.parseDouble(part2)), reciever);
            } else if (Database.command[12][0].equals(part1)) {
                response("" + FLC.c2f(Double.parseDouble(part2)), reciever);
            } else if (Database.command[13][0].equals(part1)) {
                response("" + FLC.f2c(Double.parseDouble(part2)), reciever);
            } else if (Database.command[14][0].equals(part1)) {
                response("" + FLC.hPa2inHg(Double.parseDouble(part2)), reciever);
            } else if (Database.command[15][0].equals(part1)) {
                response("" + FLC.inHg2hPa(Double.parseDouble(part2)), reciever);
            } else if (Database.command[16][0].equals(part1)) {
                response("" + FLC.h2min(Double.parseDouble(part2)), reciever);
            } else if (Database.command[17][0].equals(part1)) {
                response("" + FLC.min2h(Double.parseDouble(part2)), reciever);
            } else if (Database.command[18][0].equals(part1)) {
                response("" + FLC.ISA(Double.parseDouble(part2)), reciever);
            } else if (Database.command[19][0].equals(part1)) { // can cause problems
                response("" + FLC.endurance(Double.parseDouble(part2), Double.parseDouble(part3)), reciever);
            } else if (Database.command[20][0].equals(part1)) {
                response("" + FLC.reqFuel(Double.parseDouble(part2), Double.parseDouble(part3)), reciever);
            } else if (Database.command[21][0].equals(part1)) {
                response("" + FLC.ffl(Double.parseDouble(part2), Double.parseDouble(part3)), reciever);
            } else if (Database.command[22][0].equals(part1)) {
                response("" + FLC.timeForDist(Double.parseDouble(part2), Double.parseDouble(part3)), reciever);
            } else if (Database.command[23][0].equals(part1)) {
                response("" + FLC.distInTime(Double.parseDouble(part2), Double.parseDouble(part3)), reciever);
            } else if (Database.command[24][0].equals(part1)) {
                response("" + FLC.speedByTimeForDist(Double.parseDouble(part2), Double.parseDouble(part3)), reciever);
            } else if (Database.command[25][0].equals(part1)) {
                response("" + FLC.pressureAlt(Double.parseDouble(part2), Double.parseDouble(part3)), reciever);
            } else if (Database.command[26][0].equals(part1)) {
                response("" + FLC.deltaTemp(Double.parseDouble(part2), Double.parseDouble(part3)), reciever);
            } else if (Database.command[27][0].equals(part1)) {
                response("" + FLC.densityAlt((Double.parseDouble(part2)), Double.parseDouble(part3)), reciever);
            } else if (Database.command[28][0].equals(part1)) {
                response("" + FLC.trueAlt(Double.parseDouble(part2), Double.parseDouble(part3), Double.parseDouble(part4)), reciever);
            } else if (Database.command[29][0].equals(part1)) {
                response("" + FLC.TAS(Double.parseDouble(part2), Double.parseDouble(part3)), reciever);
            } // this is horrifying and needs to be refactored ASAP
        } else {
            for (int x = 0; x < Database.command.length; x++) {
                if (part1.equals(Database.command[x][0])) {
                    response(Database.command[x][1], reciever);
                    return;
                }
            }
            response(Database.command[3][1], reciever);
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
