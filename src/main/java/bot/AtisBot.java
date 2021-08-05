package bot;

import com.github.manevolent.ts3j.command.CommandException;

import com.github.manevolent.ts3j.event.ClientPokeEvent;
import com.github.manevolent.ts3j.event.TS3Listener;
import com.github.manevolent.ts3j.event.TextMessageEvent;
import com.github.manevolent.ts3j.identity.Identity;

import com.github.manevolent.ts3j.identity.LocalIdentity;
import com.github.manevolent.ts3j.protocol.socket.client.LocalTeamspeakClientSocket;
import io.github.cdimascio.dotenv.Dotenv;
import tts.OpusParameters;
import tts.TeamspeakFastMixerSink;

import java.io.File;

import static tts.TeamspeakFastMixerSink.AUDIO_FORMAT;
import java.io.IOException;
import java.security.GeneralSecurityException;

import java.security.spec.ECPoint;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import java.util.regex.*;

public class AtisBot implements TS3Listener {
    LocalTeamspeakClientSocket client;
    Dotenv dotenv = Dotenv.load();

    String liste[][] = new String[4][2];



    public AtisBot() throws Exception {
        client = new LocalTeamspeakClientSocket();

        LocalIdentity identity = LocalIdentity.read(new File("src\\main\\resources\\bot_identity.ini"));
        client.setIdentity(identity);


        client.setNickname("ATIS/METAR/TAF Bot");
        client.connect(dotenv.get("TEAMSPEAK_HOSTNAME"), dotenv.get("TEAMSPEAK_PASSWORD"), 20000);
        client.setDescription("Write !help to get a list of available commands.");
        
        // Create a sink
        TeamspeakFastMixerSink sink = new TeamspeakFastMixerSink(
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
        sink.start();

    }

    public static void main(String[] args) throws Exception {
        new AtisBot();
        client.addListener(this);

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
        System.out.println(e.getMessage());
        if (Pattern.matches("^!.+",e.getMessage())) { // checks if message is command
            try {
                messageLength(e.getMessage());
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (CommandException ex) {
                ex.printStackTrace();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            } catch (TimeoutException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public void messageLength(String msg) throws IOException, CommandException, InterruptedException, TimeoutException { // checks if message contains values
        String[] part = msg.toUpperCase().substring(1).split("\s");
        String part1, part2;

        if (part.length == 2) {
            part1 = part[0];
            part2 = part[1];
            System.out.println(part1+"\n"+part2);
            action(part1,part2);
        } else if (part.length == 1)  {
            part1 = part[0];
            System.out.println(part1);
            action(part1,"");
        }
    }
    
    public void action(String part1, String part2) throws IOException, CommandException, InterruptedException, TimeoutException {
        System.out.println("action!"+part1+part2);
        if (!part2.equals("") && Pattern.matches("[A-Z]{3,4}",part2) && !part2.equals("HELP")) {
            if (liste[0][0].equals(part1)) {
                // getMetar(part2);
                System.out.println("getMetar(part2);");
                response("getMetar(part2);");
            } else if (liste[1][0].equals(part1)) {
                // getAtis(part2);
                System.out.println("getAtis(part2);");
                response("getAtis(part2);");
            } else if (liste[2][0].equals(part1)) {
                // getTaf(part2);
                System.out.println("getTaf(part2);");
                response("getTaf(part2);");
            }
        } else {
            if (liste[0][0].equals(part1)) {
                System.out.println(liste[0][1]);
                response(liste[0][1]);
            } else if (liste[1][0].equals(part1)) {
                System.out.println(liste[1][1]);
                response(liste[1][1]);
            } else if (liste[2][0].equals(part1)) {
                System.out.println(liste[2][1]);
                response(liste[2][1]);
            } else if (liste[3][0].equals(part1)) { // useless because it gets called regardless of being !help or something he doesn't know
                System.out.println(liste[3][1]);
                response(liste[3][1]);
            } else {
                System.out.println(liste[3][1]);
                response(liste[3][1]);
            }
        }
    }

    public void response(String msg) throws IOException, CommandException, InterruptedException, TimeoutException {
        client.sendChannelMessage(client.getClientId(),msg);
    }

    public static void main(String[] args) throws GeneralSecurityException, CommandException, IOException, ExecutionException, InterruptedException, TimeoutException {
        new AtisBot();
    }

}
