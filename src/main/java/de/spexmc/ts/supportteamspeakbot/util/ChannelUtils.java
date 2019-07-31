package de.spexmc.ts.supportteamspeakbot.util;

import java.util.List;
import java.util.stream.Collectors;

import com.github.theholywaffle.teamspeak3.api.wrapper.Channel;
import com.github.theholywaffle.teamspeak3.api.wrapper.ChannelInfo;
import de.spexmc.ts.supportteamspeakbot.storage.Data;

/**
 * Description
 *
 * @author Abgie on 31.10.2018 14:56
 * project SupportSystem
 * @version 1.0
 * @since JDK 8
 */
public final class ChannelUtils {
  public static ChannelInfo getChannelFromName(String name) {
    return Data.getInstance().getTS3Api().getChannels().stream().mapToInt(Channel::getId)
        .mapToObj(channelId -> Data.getInstance().getTS3Api().getChannelInfo(channelId))
        .filter(channelInfo -> channelInfo.getName().contains(name)).findFirst().orElse(null);
  }

  public static List<ChannelInfo> getChannelsFromName(String name) {
    return Data.getInstance().getTS3Api().getChannels().stream().mapToInt(Channel::getId)
        .mapToObj(channelId -> Data.getInstance().getTS3Api().getChannelInfo(channelId))
        .filter(channelInfo -> channelInfo.getName().contains(name))
        .collect(Collectors.toList());
  }

  public static int getClientsInsideOfAChannel(int channelId) {
    return (int) Data.getInstance().getTS3Api().getClients().stream().
        filter(client -> client.getChannelId() == channelId).count();
  }
}
