package de.spexmc.ts.supportteamspeakbot.commands;

import java.util.Calendar;

import com.github.theholywaffle.teamspeak3.api.wrapper.Client;
import de.spexmc.ts.supportteamspeakbot.model.Command;
import de.spexmc.ts.supportteamspeakbot.storage.Const;
import de.spexmc.ts.supportteamspeakbot.util.QueueUtils;

/**
 * Mit !support kann man die Warteschlange betreten :D
 *
 * @author Abgie on 31.10.2018 16:57
 * project SupportSystem
 * @version 1.0
 * @since JDK 8
 */
public class Support extends Command {
  @Override
  public void onCommand(Client sender, String command, String[] args) {
    super.onCommand(sender, command, args);

    if (checkSupportOpeningHours(sender)) {
      QueueUtils.addElement(sender.getId());
    }
  }

  public boolean checkSupportOpeningHours(Client sender) {
    final Calendar calendar = Calendar.getInstance();
    final int hour = calendar.get(Calendar.HOUR_OF_DAY);
    if (hour >= Const.SUPPORT_OPEN_HOUR && hour < Const.SUPPORT_CLOSE_HOUR) {
      return true;
    }
    if (sender != null) {
      getApi().sendPrivateMessage(sender.getId(), "Der Support ist nur von " + Const.SUPPORT_OPEN_HOUR + " bis " +
          Const.SUPPORT_CLOSE_HOUR + " geöffnet.");
    }
    return false;
  }
}