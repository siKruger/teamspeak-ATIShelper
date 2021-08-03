import bot.AtisBot;
import com.github.manevolent.ts3j.api.Client;
import com.github.manevolent.ts3j.command.CommandException;
import com.github.manevolent.ts3j.identity.LocalIdentity;
import com.github.manevolent.ts3j.protocol.socket.client.LocalTeamspeakClientSocket;
import com.github.manevolent.ts3j.protocol.socket.server.TeamspeakServerSocket;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.security.GeneralSecurityException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public class basicBotTest {

    @Test
    public void createAndDisconnectBot() throws GeneralSecurityException, CommandException, IOException, ExecutionException, InterruptedException, TimeoutException {
        AtisBot t = new AtisBot();
    }
}
