package de.spexmc.ts.supportteamspeakbot.util;

import com.github.theholywaffle.teamspeak3.api.event.TS3EventAdapter;
import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent;
import com.github.theholywaffle.teamspeak3.api.wrapper.Client;
import de.spexmc.ts.supportteamspeakbot.commands.Accept;
import de.spexmc.ts.supportteamspeakbot.commands.Leave;
import de.spexmc.ts.supportteamspeakbot.commands.Queue;
import de.spexmc.ts.supportteamspeakbot.commands.Support;
import de.spexmc.ts.supportteamspeakbot.events.ChannelCreateHandler;
import de.spexmc.ts.supportteamspeakbot.events.ClientJoinHandler;
import de.spexmc.ts.supportteamspeakbot.events.ClientMovedHandler;
import de.spexmc.ts.supportteamspeakbot.events.ClientQuitedHandler;
import de.spexmc.ts.supportteamspeakbot.model.Command;
import de.spexmc.ts.supportteamspeakbot.storage.Data;

/**
 * Created by Lara on 31.07.2019 for SupportTeamspeakBot
 */
public final class Registerer {

  public static void registerCommands() {
    registerCommands(new Accept(), new Leave(), new Queue(), new Support());
  }

  private static void registerCommands(Command... commands) {
    Data.getInstance().getTS3Api().addTS3Listeners(new TS3EventAdapter() {
      @Override
      public void onTextMessage(TextMessageEvent e) {
        for (Command command : commands) {
          final String name = commands.getClass().getSimpleName().toLowerCase();
          if (e.getMessage().toLowerCase().startsWith("!" + name)) {
            final String uid = e.getInvokerUniqueId();
            final Client client = Data.getInstance().getTS3Api().getClientByUId(uid);

            final String argString = e.getMessage().replace("!" + name + " ", "");
            command.onCommand(client, name, argString.split(" "));
          }
        }
      }
    });
  }

  public static void registerListeners() {
    registerListeners(new ChannelCreateHandler(), new ClientJoinHandler(), new ClientMovedHandler(),
        new ClientQuitedHandler());
  }

  private static void registerListeners(TS3EventAdapter... listeners) {
    Data.getInstance().getTS3Api().addTS3Listeners(listeners);
  }
}
