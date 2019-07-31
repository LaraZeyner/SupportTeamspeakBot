package de.spexmc.ts.supportteamspeakbot.events;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.theholywaffle.teamspeak3.api.ChannelProperty;
import com.github.theholywaffle.teamspeak3.api.event.ClientLeaveEvent;
import com.github.theholywaffle.teamspeak3.api.event.TS3EventAdapter;
import com.github.theholywaffle.teamspeak3.api.wrapper.Channel;
import com.github.theholywaffle.teamspeak3.api.wrapper.Client;
import de.spexmc.ts.supportteamspeakbot.storage.Const;
import de.spexmc.ts.supportteamspeakbot.storage.Data;
import de.spexmc.ts.supportteamspeakbot.util.ChannelUtils;
import de.spexmc.ts.supportteamspeakbot.util.ClientUtils;

/**
 * Description
 *
 * @author Abgie on 31.10.2018 12:17
 * project SupportSystem
 * @version 1.0
 * @since JDK 8
 */
public class ClientQuitedHandler extends TS3EventAdapter {
  @Override
  public void onClientLeave(ClientLeaveEvent leaveEvent) {
    final List<Integer> supportQueue = Data.getInstance().getQueue();
    supportQueue.remove(leaveEvent.getClientId());

    if (!isSupportActive() && ChannelUtils.getChannelFromName(Const.SUPPORT_CHANNEL_NAME_OPENED) != null) {
      final Channel channel = Data.getInstance().getTS3Api().getChannelByNameExact(Const.SUPPORT_CHANNEL_NAME_OPENED, true);
      final Map<ChannelProperty, String> map = new HashMap<>();
      map.put(ChannelProperty.CHANNEL_NAME, Const.SUPPORT_CHANNEL_NAME_CLOSED);
      Data.getInstance().getTS3Api().editChannel(channel.getId(), map);
    }
  }

  static boolean isSupportActive() {
    for (Client client : Data.getInstance().getTS3Api().getClients()) {
      if (ClientUtils.isSupportable(client)) {
        return true;
      }
    }
    return false;
  }
}