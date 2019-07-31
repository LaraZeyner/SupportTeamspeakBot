package de.spexmc.ts.supportteamspeakbot.events;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.api.event.ChannelCreateEvent;
import com.github.theholywaffle.teamspeak3.api.event.TS3EventAdapter;
import com.github.theholywaffle.teamspeak3.api.wrapper.ChannelInfo;
import de.spexmc.ts.supportteamspeakbot.storage.Data;

/**
 * Description
 *
 * @author Abgie on 31.10.2018 13:45
 * project SupportSystem
 * @version 1.0
 * @since JDK 8
 */
public class ChannelCreateHandler extends TS3EventAdapter {
  @Override
  public void onChannelCreate(ChannelCreateEvent createEvent) {
    final int channelId = createEvent.getChannelId();
    final TS3Api ts3Api = Data.getInstance().getTS3Api();
    final ChannelInfo channelInfo = ts3Api.getChannelInfo(channelId);
    final String channelName = channelInfo.getName().toLowerCase();
    evaluateIsChannelForbidden(channelId, channelName);
  }

  private void evaluateIsChannelForbidden(int channelId, String channelName) {
    for (String forbiddenName : Data.getInstance().getForbiddenChannelNames()) {
      if (channelName.contains(forbiddenName)) {
        Data.getInstance().getTS3Api().deleteChannel(channelId);
      }
    }
  }
}
