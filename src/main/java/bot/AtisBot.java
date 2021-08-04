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
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import java.util.regex.*;

public class AtisBot implements TS3Listener {
    LocalTeamspeakClientSocket client;
    Dotenv dotenv = Dotenv.load();
    List<String> cmdList = new ArrayList<String>(); // create a list for all commands || maybe can be replaced by something better


    public AtisBot() throws CommandException, IOException, ExecutionException, InterruptedException, TimeoutException, GeneralSecurityException {
        client = new LocalTeamspeakClientSocket();

        LocalIdentity identity = LocalIdentity.read(new File("src\\main\\resources\\bot_identity.ini"));
        client.setIdentity(identity);


        client.setNickname("ATIS/METAR/TAF Bot");
        client.connect(dotenv.get("TEAMSPEAK_HOSTNAME"), dotenv.get("TEAMSPEAK_PASSWORD"), 20000);
        client.setDescription("Write !help to get a list of available commands.");

        client.addListener(this);

        // fill the list with availabe commands
        cmdList.add("METAR");
        cmdList.add("ATIS");
        cmdList.add("TAF");


    }

//    first implementation will only be onMessage
//    @Override
//    public void onClientPoke(ClientPokeEvent e) {
//        TS3Listener.super.onClientPoke(e);
//
//        //cmd(e.getMessage());
//        System.out.println("poke");
//    }

    @Override
    public void onTextMessage(TextMessageEvent e) {
        TS3Listener.super.onTextMessage(e);
        System.out.println(e.getMessage());                               // debug
        if (Pattern.matches("!.+",e.getMessage())) {                // ignores all messages witch aren't commands
            String msg = e.getMessage().toUpperCase().substring(1);       // variable for easier access || to uppercase for unity || removes the '!' || can be shortend
            if (Pattern.matches("^" + cmdList.get(0) + "\s*.*",msg) || Pattern.matches("^" + cmdList.get(1) + "\s*.*",msg) || Pattern.matches("^" + cmdList.get(2) + "\s*.*",msg)) { // fuck this || can be replaced by "([A-Z]+\s+[A-Z] * |^" in next line
                if (Pattern.matches("([A-Z]+\s+[A-Z]{4}|^" + cmdList.get(1) + "\s+([A-Z]{4}|1\\d\\d\\.\\d{1,3}))",msg)){ // checks if the command has a target (icao or freq) else sends help massage || makes the line above useless i guess
                   System.out.println(msg);             // can be send to the http request part
                } else {
                    System.out.println("Use !" + msg + " and the ICAO-Code of a target Airport to get the current " + msg + ". *placeholder*"); // replies with instructions for specific command || needs to be made command specific
                }
            }
            System.out.println("Type !help for a list of all commands"); // replies with a list of all commands for because entry is unknows || bug witch trigers
        }
    }

    public static void main(String[] args) throws GeneralSecurityException, CommandException, IOException, ExecutionException, InterruptedException, TimeoutException {
        new AtisBot();
    }

}