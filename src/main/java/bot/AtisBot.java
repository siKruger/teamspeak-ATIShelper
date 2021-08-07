package bot;

import api.AvwxRequests;
import atis.Atis;
import com.github.manevolent.ts3j.api.TextMessageTargetMode;
import com.github.manevolent.ts3j.event.TS3Listener;
import com.github.manevolent.ts3j.event.TextMessageEvent;
import com.github.manevolent.ts3j.identity.LocalIdentity;
import com.github.manevolent.ts3j.protocol.socket.client.LocalTeamspeakClientSocket;
import converter.Commands;
import converter.FLC;
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

        CommandList.DBcreate(); // create the database for the commands - necessary
    }


    @Override
    public void onTextMessage(TextMessageEvent e) { // fetches message
        TS3Listener.super.onTextMessage(e);
        if (Pattern.matches("^!.+", e.getMessage())) { // checks if message is command
            int r = 0;
            if (e.getTargetMode().equals(TextMessageTargetMode.CLIENT)) {
                r = e.getInvokerId();
            }

            //todo exception handling
            try {
                commandAnalyzer(e.getMessage(), r);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private void commandAnalyzer(String msg, int reciever) throws Exception { // checks if message contains values
        String[] request = msg.toUpperCase().substring(1).split("\s");
        String command = request[0], value1 = "", value2 = "", value3 = "";

        switch (request.length) {
            //command + 1 value (e.g. conversion, metar -> !atis eddh; !metar fra)
            case 2 -> {
                value1 = request[1];
            }

            //command + 2 values (e.g conversion; !tas 32 42)
            case 3 -> {
                value1 = request[1];
                value2 = request[2];
            }

            //command + 3 values (e.g conversion; !truealtitude 23 2323 3)
            case 4 -> {
                value1 = request[1];
                value2 = request[2];
                value3 = request[3];
            }
        }

        action(command, value1, value2, value3, reciever);
    }


    // this is going to be terrifying. YES IT IS
    private void action(String command, String value1, String value2, String value3, int reciever) throws Exception {
        Commands commandToExecute = Commands.valueOf(command);

        //METAR/ATIS/TAF
        if (!value1.equals("") && Pattern.matches("[A-Z0-9]{3,4}", value1) && !command.equals("HELP") && value2.equals("")) {

            if (commandToExecute.name().equals("METAR")) {
                JSONObject getter = AvwxRequests.getMetar(value1);
                response(String.valueOf(getter.get("sanitized")), reciever);
                return;
            }

            if (commandToExecute.name().equals("ATIS")) {
                try {
                    response("Playing ATIS for " + value1, reciever);
                    Atis.generateAtis(value1, sink);
                } catch (AtisCooldownException e) {
                    response("Bitte warte eine Minute...", reciever);
                }
                return;
            }

            if (commandToExecute.name().equals("TAF")) {
                JSONObject getter = AvwxRequests.getTAF(value1);
                response(String.valueOf(getter.get("sanitized")), reciever);
            }

            // for every calculation
        } else if (!value1.equals("") && Pattern.matches("\\d+", value1) && !command.equals("HELP")) {
            response(FLC.calculations(command, value1, value2, value3), reciever);
        } else {
            // help
            response(commandToExecute.getHelpText(commandToExecute.name()), reciever);
        }
    }

    private void response(String msg, int reciever) throws Exception {
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
