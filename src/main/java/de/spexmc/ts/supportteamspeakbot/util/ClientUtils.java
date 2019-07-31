package de.spexmc.ts.supportteamspeakbot.util;

import java.util.List;

import com.github.theholywaffle.teamspeak3.api.wrapper.Client;
import de.spexmc.ts.supportteamspeakbot.storage.Const;
import de.spexmc.ts.supportteamspeakbot.storage.Data;

/**
 * Description
 *
 * @author Abgie on 31.10.2018 15:10
 * project SupportSystem
 * @version 1.0
 * @since JDK 8
 */
public final class ClientUtils {

  public static boolean isSupportable(Client client) {
    return client.isInServerGroup(Const.SUPPORT_SERVER_GROUP);
  }

  static int supportTeamSize() {
    final List<Client> clients = Data.getInstance().getTS3Api().getClients();
    return (int) clients.stream().filter(client -> client.isInServerGroup(Const.SUPPORT_SERVER_GROUP)).count();
  }

  static void informSupportTeam(String name) {
    for (Client client : Data.getInstance().getTS3Api().getClients()) {
      if (client.isInServerGroup(Const.SUPPORT_SERVER_GROUP)) {
        Data.getInstance().getTS3Api().sendPrivateMessage(client.getId(), "[b]Spieler [/b]" + name + " [b]benötigt Support!");
      }
    }
  }


}
