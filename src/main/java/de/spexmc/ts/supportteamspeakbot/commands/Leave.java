package de.spexmc.ts.supportteamspeakbot.commands;

import java.util.List;

import com.github.theholywaffle.teamspeak3.api.wrapper.Client;
import de.spexmc.ts.supportteamspeakbot.model.Command;
import de.spexmc.ts.supportteamspeakbot.storage.Data;
import de.spexmc.ts.supportteamspeakbot.storage.Messages;
import de.spexmc.ts.supportteamspeakbot.util.QueueUtils;

/**
 * Leave managt den Leave-Command
 * <p>
 * -> !leave = Der Spieler verlässt die Queue
 *
 * @author Abgie on 26.10.2018 16:11
 * project SupportSystem
 * @version 1.0
 * @since JDK 8
 */
public class Leave extends Command {
  @Override
  public void onCommand(Client sender, String cmd, String[] args) {
    super.onCommand(sender, cmd, args);

    final List<Integer> queue = Data.getInstance().getQueue();
    final int senderId = sender.getId();
    sendMessage(queue.contains(senderId) ? Messages.QUEUE_REMOVED : Messages.NOT_INTO_QUEUE);
    QueueUtils.removeElement(senderId);
  }
}
