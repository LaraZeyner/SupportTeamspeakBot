package de.spexmc.ts.supportteamspeakbot;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.TS3Config;
import com.github.theholywaffle.teamspeak3.TS3Query;
import com.github.theholywaffle.teamspeak3.api.wrapper.Client;
import com.github.theholywaffle.teamspeak3.api.wrapper.ServerGroupClient;
import de.spexmc.ts.supportteamspeakbot.storage.Const;
import de.spexmc.ts.supportteamspeakbot.storage.Data;
import de.spexmc.ts.supportteamspeakbot.util.Registerer;

/**
 * Created by Lara on 30.07.2019 for supportteamspeakbot
 */
public final class SupportTeamspeakBot {

  public static void main(String[] args) {
    final Data data = Data.getInstance();
    final TS3Config config = new TS3Config();
    config.setHost("ts.spexmc.de");

    final TS3Query query = new TS3Query(config);
    query.connect();

    final TS3Api ts3Api = query.getApi();
    ts3Api.login("serveradmin", Const.PASSWORD);
    ts3Api.selectVirtualServerById(1);
    ts3Api.setNickname("RAMHDs Bot");
    for (ServerGroupClient serverGroupClient : ts3Api.getServerGroupClients(Const.SUPPORT_SERVER_GROUP)) {
      final String uId = serverGroupClient.getUniqueIdentifier();
      final Client client = ts3Api.getClientByUId(uId);
      ts3Api.sendPrivateMessage(client.getId(), "Der Supportbot ist wieder online.");
    }
    data.setTS3Api(ts3Api);

    data.getTS3Api().registerAllEvents();

    Registerer.registerCommands();
    Registerer.registerListeners();
  }
}
