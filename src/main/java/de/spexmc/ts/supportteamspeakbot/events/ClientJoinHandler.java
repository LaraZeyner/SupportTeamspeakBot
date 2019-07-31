package de.spexmc.ts.supportteamspeakbot.events;

import java.util.HashMap;
import java.util.Map;

import com.github.theholywaffle.teamspeak3.api.ChannelProperty;
import com.github.theholywaffle.teamspeak3.api.event.ClientJoinEvent;
import com.github.theholywaffle.teamspeak3.api.event.TS3EventAdapter;
import com.github.theholywaffle.teamspeak3.api.wrapper.Channel;
import com.github.theholywaffle.teamspeak3.api.wrapper.Client;
import de.spexmc.ts.supportteamspeakbot.storage.Const;
import de.spexmc.ts.supportteamspeakbot.storage.Data;
import de.spexmc.ts.supportteamspeakbot.util.ChannelUtils;

/**
 * Description
 *
 * @author Abgie on 03.11.2018 16:02
 * project SupportSystem
 * @version 1.0
 * @since JDK 8
 */
public class ClientJoinHandler extends TS3EventAdapter {
  @Override
  public void onClientJoin(ClientJoinEvent joinEvent) {
    Data.getInstance().getTS3Api().sendPrivateMessage(joinEvent.getClientId(), "Willkommen [b]" + joinEvent.getClientNickname() +
        " [/b]auf unserem Teamspeak³-Server.\nBei Fragen oder Problemen wende dich doch an unseren Support.");

    final Client client = new Client(joinEvent.getMap());
    if (client.isInServerGroup(Const.SUPPORT_SERVER_GROUP)) {
      Data.getInstance().getTS3Api().sendPrivateMessage(joinEvent.getClientId(), "Willkommen [b]" + joinEvent.getClientNickname() +
          " [/b]auf unserem Teamspeak³-Server.\nBei Fragen oder Problemen wende dich doch an unseren Support.");
      Data.getInstance().getTS3Api().sendPrivateMessage(joinEvent.getClientId(), "Es befinden sich Spieler in der " +
          "Supportwarteschlange. Mit !accept kannst du diese bearbeiten.");


      if (ClientQuitedHandler.isSupportActive() && ChannelUtils.getChannelFromName(Const.SUPPORT_CHANNEL_NAME_CLOSED) != null) {
        final Channel channel = Data.getInstance().getTS3Api().getChannelByNameExact(Const.SUPPORT_CHANNEL_NAME_CLOSED, true);
        final Map<ChannelProperty, String> map = new HashMap<>();
        map.put(ChannelProperty.CHANNEL_NAME, Const.SUPPORT_CHANNEL_NAME_OPENED);
        Data.getInstance().getTS3Api().editChannel(channel.getId(), map);
      }
    }
  }
}
