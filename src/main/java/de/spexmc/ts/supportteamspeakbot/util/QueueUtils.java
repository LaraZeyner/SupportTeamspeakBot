package de.spexmc.ts.supportteamspeakbot.util;

import java.util.List;

import com.github.theholywaffle.teamspeak3.api.wrapper.ClientInfo;
import de.spexmc.ts.supportteamspeakbot.storage.Data;
import de.spexmc.ts.supportteamspeakbot.storage.Messages;

/**
 * Description
 *
 * @author Abgie on 31.10.2018 14:33
 * project SupportSystem
 * @version 1.0
 * @since JDK 8
 */
public final class QueueUtils {
  private static final List<Integer> queue = Data.getInstance().getQueue();

  public static boolean isEmpty() {
    return queue.isEmpty();
  }

  public static void addElement(int element) {
    queue.add(element);
    Data.getInstance().getTS3Api().sendPrivateMessage(element, Messages.QUEUE_JOINED);
    if (ClientUtils.supportTeamSize() != 0) {
      Data.getInstance().getTS3Api().sendPrivateMessage(element, "[b]Willkommen in unserem Support! Bitte gedulde dich, " +
          "in kürze wird sich ein Teammitglied um dich kümmern.");
      final ClientInfo clientInfo = Data.getInstance().getTS3Api().getClientInfo(element);
      ClientUtils.informSupportTeam(clientInfo.getNickname());
    } else {
      Data.getInstance().getTS3Api().sendPrivateMessage(element, "[b]Willkommen in unserem Support! In Moment ist jedoch kein Teammitglied online.");
    }
  }

  public static void removeElement(int element) {
    queue.remove((Integer) element);
  }
}
