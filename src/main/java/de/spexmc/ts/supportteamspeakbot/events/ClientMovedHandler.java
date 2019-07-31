package de.spexmc.ts.supportteamspeakbot.events;

import java.util.List;
import java.util.Timer;

import com.github.theholywaffle.teamspeak3.api.event.ClientMovedEvent;
import com.github.theholywaffle.teamspeak3.api.event.TS3EventAdapter;
import com.github.theholywaffle.teamspeak3.api.wrapper.ChannelInfo;
import com.github.theholywaffle.teamspeak3.api.wrapper.Client;
import de.spexmc.ts.supportteamspeakbot.commands.Support;
import de.spexmc.ts.supportteamspeakbot.storage.Const;
import de.spexmc.ts.supportteamspeakbot.storage.Data;
import de.spexmc.ts.supportteamspeakbot.util.ClientUtils;
import de.spexmc.ts.supportteamspeakbot.util.QueueUtils;
import de.spexmc.ts.supportteamspeakbot.util.TimerUtils;

/**
 * Hadlet, wenn ein Spieler den CHannel wechselt.
 * -> durch Wechsel
 * -> durch Moven
 *
 * @author Abgie on 31.10.2018 12:57
 * project SupportSystem
 * @version 1.0
 * @since JDK 8
 */
public class ClientMovedHandler extends TS3EventAdapter {

  @Override
  public void onClientMoved(ClientMovedEvent moveEvent) {
    if (checkWaitingForSupport(moveEvent) && new Support().checkSupportOpeningHours(new Client(moveEvent.getMap()))) {
      QueueUtils.addElement(moveEvent.getClientId());
    }

    final int channelId = moveEvent.getTargetChannelId();
    final String channelName = Data.getInstance().getTS3Api().getChannelInfo(channelId).getName();
    if (channelName.toLowerCase().contains(Const.SUPPORT_CHANNEL_NAME) && ClientUtils.isSupportable(new Client(moveEvent.getMap()))) {
      if (!QueueUtils.isEmpty()) {
        final int clientid = moveEvent.getClientId();
        new Timer().schedule(TimerUtils.run(() ->
            Data.getInstance().getTS3Api().sendPrivateMessage(clientid, "Der wartende Spieler wird in 3 Sekunden gemovt.")), Const.DELAY_AFTER_PLAYER_MOVED);

        final List<Integer> queue = Data.getInstance().getQueue();
        final int neededId = queue.stream().findFirst().orElse(null);
        Data.getInstance().getTS3Api().moveClient(neededId, moveEvent.getTargetChannelId());
        QueueUtils.removeElement(neededId);
        if (QueueUtils.isEmpty()) {
          Data.getInstance().getTS3Api().addChannelPermission(channelId, "channel_join_power", Const.DISALLOW_JOIN_POWER);
        }
      }
    }
  }

  private boolean checkWaitingForSupport(ClientMovedEvent moveEvent) {
    final int channelId = moveEvent.getTargetChannelId();
    final ChannelInfo channelInfo = Data.getInstance().getTS3Api().getChannelInfo(channelId);
    final String channelName = channelInfo.getName();
    final Client client = new Client(moveEvent.getMap());
    return channelName.contains(Const.SUPPORT_CHANNEL_NAME_OPENED) && !client.isInServerGroup(Const.SUPPORT_SERVER_GROUP);
  }
}
