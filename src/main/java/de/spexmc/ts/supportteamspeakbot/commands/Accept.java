package de.spexmc.ts.supportteamspeakbot.commands;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;

import com.github.theholywaffle.teamspeak3.api.ChannelProperty;
import com.github.theholywaffle.teamspeak3.api.wrapper.ChannelInfo;
import com.github.theholywaffle.teamspeak3.api.wrapper.Client;
import de.spexmc.ts.supportteamspeakbot.model.Command;
import de.spexmc.ts.supportteamspeakbot.storage.Const;
import de.spexmc.ts.supportteamspeakbot.storage.Data;
import de.spexmc.ts.supportteamspeakbot.util.ChannelUtils;
import de.spexmc.ts.supportteamspeakbot.util.ClientUtils;
import de.spexmc.ts.supportteamspeakbot.util.QueueUtils;
import de.spexmc.ts.supportteamspeakbot.util.TimerUtils;

/**
 * Description
 *
 * @author Abgie on 31.10.2018 15:08
 * project SupportSystem
 * @version 1.0
 * @since JDK 8
 */
public class Accept extends Command {
  @Override
  public void onCommand(Client sender, String command, String[] args) {
    super.onCommand(sender, command, args);

    if (ClientUtils.isSupportable(sender) && !checkEmptyQueue()) {
      evaluateCommand(sender);
    }
  }

  private boolean checkEmptyQueue() {
    final List<Integer> queue = Data.getInstance().getQueue();
    if (queue.isEmpty()) {
      return true;
    } else {
      sendMessage("Es befindet sich niemand in der Support Warteschlange.");
      return false;
    }
  }

  @SuppressWarnings("OptionalGetWithoutIsPresent")
  private void evaluateCommand(Client sender) {

    final List<Integer> queue = Data.getInstance().getQueue();
    final int channelId = findChannel().getId();
    getApi().moveClient(sender.getId(), channelId);
    if (!QueueUtils.isEmpty()) {
      new Timer().schedule(TimerUtils.run(() ->
              getApi().sendPrivateMessage(sender.getId(), "Der wartende Spieler wird in 3 Sekunden gemovt.")),
          Const.DELAY_AFTER_PLAYER_MOVED);

      final int neededId = queue.stream().findFirst().get();
      getApi().moveClient(neededId, channelId);
      QueueUtils.removeElement(neededId);
    }
  }

  private ChannelInfo findChannel() {
    for (ChannelInfo info : ChannelUtils.getChannelsFromName(Const.SUPPORT_CHANNEL_NAME)) {
      if (ChannelUtils.getClientsInsideOfAChannel(info.getId()) == 0) {
        return info;
      }
    }

    final List<ChannelInfo> infos = ChannelUtils.getChannelsFromName(Const.SUPPORT_CHANNEL_NAME);
    final ChannelInfo channelInfoOfLast = ChannelUtils.getChannelFromName(Const.SUPPORT_CHANNEL_NAME + " " + infos.size() + 1);
    final int channelIdOfLast = channelInfoOfLast.getId();
    final Map<ChannelProperty, String> order = new HashMap<>();
    order.put(ChannelProperty.CHANNEL_ORDER, channelIdOfLast + "");
    order.put(ChannelProperty.CHANNEL_FLAG_PERMANENT, "0");
    order.put(ChannelProperty.CHANNEL_MAXCLIENTS, "2");

    final int channelId = createChannelAndModifyPermissions(infos.size() + 1, order);
    return getApi().getChannelInfo(channelId);
  }

  private int createChannelAndModifyPermissions(int numberOfSupports, Map<ChannelProperty, String> order) {
    final int channelId = getApi().createChannel(Const.SUPPORT_CHANNEL_NAME + " " + numberOfSupports, order);
    getApi().addChannelPermission(channelId, "i_channel_needed_join_power", Const.SUPPORT_CHANNEL_JOIN_POWER);
    getApi().addChannelPermission(channelId, "i_channel_needed_modify_power", Const.SUPPORT_CHANNEL_DELETE_AND_MODIFY_POWER);
    getApi().addChannelPermission(channelId, "i_channel_needed_subscribe_power", Const.SUPPORT_CHANNEL_SUBSCRIBE_POWER);
    getApi().addChannelPermission(channelId, "i_channel_needed_delete_power", Const.SUPPORT_CHANNEL_DELETE_AND_MODIFY_POWER);

    return channelId;
  }

}
