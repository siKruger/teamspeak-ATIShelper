package bot;

import com.github.manevolent.ts3j.api.TextMessageTargetMode;
import com.github.manevolent.ts3j.enums.CodecType;
import com.github.manevolent.ts3j.event.TS3Listener;
import com.github.manevolent.ts3j.event.TextMessageEvent;
import com.github.manevolent.ts3j.identity.LocalIdentity;
import com.github.manevolent.ts3j.protocol.socket.client.LocalTeamspeakClientSocket;
import commands.Commands;
import io.github.cdimascio.dotenv.Dotenv;
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
    public static TeamspeakFastMixerSink sink;

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

        client.setVoiceHandler((packet) -> onVoice(
                packet.getCodecType(),
                packet.getClientId(),
                packet.getPacketId(),
                packet.getCodecData())
        ));
    }

    public void onVoice(CodecType codecType, int clientId, int packetId, byte[] codecData) {

    }


    @Override
    public void onTextMessage(TextMessageEvent e) { // fetches message
        TS3Listener.super.onTextMessage(e);
        if (Pattern.matches("^!.+", e.getMessage())) { // checks if message is command
            int r = 0;
            if (e.getTargetMode().equals(TextMessageTargetMode.CLIENT)) {
                r = e.getInvokerId();
            }

            String[] request = e.getMessage().toUpperCase().substring(1).split("\s");
            String command = request[0];

            //Passenden ENUM finden
            try {
                Commands commandToExecute = Commands.valueOf(command);
                //Executen der Action-Methode
                this.response(commandToExecute.commandAction(request), r);

            } catch (Exception unused) {
                try {
                    this.response("Type !help to get a list of available commands.", r);
                } catch (Exception unused2) {
                }
            }
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
