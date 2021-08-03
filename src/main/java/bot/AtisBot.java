package bot;


import com.github.manevolent.ts3j.command.CommandException;
import com.github.manevolent.ts3j.event.ClientPokeEvent;
import com.github.manevolent.ts3j.event.TS3Listener;
import com.github.manevolent.ts3j.event.TextMessageEvent;
import com.github.manevolent.ts3j.identity.Identity;
import com.github.manevolent.ts3j.identity.LocalIdentity;
import com.github.manevolent.ts3j.protocol.socket.client.LocalTeamspeakClientSocket;
import io.github.cdimascio.dotenv.Dotenv;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.spec.ECPoint;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public class AtisBot {
    LocalTeamspeakClientSocket client;
    Dotenv dotenv = Dotenv.load();


    public AtisBot() throws CommandException, IOException, ExecutionException, InterruptedException, TimeoutException, GeneralSecurityException {
        client = new LocalTeamspeakClientSocket();

        LocalIdentity identity = LocalIdentity.read(new File("src\\main\\resources\\bot_identity.ini"));
        client.setIdentity(identity);


        client.setNickname("ATIS/METAR/TAF Bot");
        client.connect(dotenv.get("TEAMSPEAK_HOSTNAME"), dotenv.get("TEAMSPEAK_PASSWORD"), 20000);
        client.setDescription("Write !help to get a list of available commands.");

        client.addListener(this);


    }

    @Override
    public void onClientPoke(ClientPokeEvent e) {
        TS3Listener.super.onClientPoke(e);

        try {
            cmd(e.getMessage());
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (CommandException ex) {
            ex.printStackTrace();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        } catch (TimeoutException ex) {
            ex.printStackTrace();
        }
        System.out.println("poke");
    }

    @Override
    public void onTextMessage(TextMessageEvent e) {
        TS3Listener.super.onTextMessage(e);

        try {
            cmd(e.getMessage());
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (CommandException ex) {
            ex.printStackTrace();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        } catch (TimeoutException ex) {
            ex.printStackTrace();
        }
        System.out.println("nachricht");
    }

    public void cmd(String msg) throws IOException, CommandException, InterruptedException, TimeoutException {
        switch (msg) {
            case "!METAR": reply("METAR F"); break;
            case "!ATIS": reply("ATIS F"); break;
            case "!TAF": reply("TAF F"); break;
            default:break;
        }
    }

    public void reply(String msg) throws IOException, CommandException, InterruptedException, TimeoutException {
        client.sendChannelMessage(client.getClientId(), msg);
    }

    public static void main(String[] args) throws GeneralSecurityException, CommandException, IOException, ExecutionException, InterruptedException, TimeoutException {
        new AtisBot();
    }

}
