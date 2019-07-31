package de.spexmc.ts.supportteamspeakbot.commands;

import java.util.List;

import com.github.theholywaffle.teamspeak3.api.wrapper.Client;
import com.github.theholywaffle.teamspeak3.api.wrapper.ClientInfo;
import de.spexmc.ts.supportteamspeakbot.model.Command;
import de.spexmc.ts.supportteamspeakbot.storage.Data;
import de.spexmc.ts.supportteamspeakbot.util.ClientUtils;
import de.spexmc.ts.supportteamspeakbot.util.QueueUtils;

/**
 * Description
 *
 * @author Abgie on 31.10.2018 17:18
 * project SupportSystem
 * @version 1.0
 * @since JDK 8
 */
public class Queue extends Command {
  @Override
  public void onCommand(Client sender, String command, String[] args) {
    super.onCommand(sender, command, args);

    if (ClientUtils.isSupportable(sender)) {
      evaluateCommand();
    }
  }

  private void evaluateCommand() {
    if (!QueueUtils.isEmpty()) {
      final List<Integer> list = Data.getInstance().getQueue();
      final StringBuilder message = new StringBuilder("Im Support befinden sich folgende " + list.size() + " wartende Spieler:\n");

      for (int clientId : list) {
        final ClientInfo info = getApi().getClientInfo(clientId);
        final String clientName = info.getLoginName();
        message.append(clientName).append("\n");
      }

      sendMessage(message.toString());
    } else {
      sendMessage("Die Supportwarteschlange ist leer.");
    }
  }

}
