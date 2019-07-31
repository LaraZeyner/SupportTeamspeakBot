package de.spexmc.ts.supportteamspeakbot.model;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.api.wrapper.Client;
import de.spexmc.ts.supportteamspeakbot.storage.Data;

/**
 * Created by Lara on 31.07.2019 for SupportTeamspeakBot
 */
public abstract class Command {
  private Client sender;

  public void onCommand(Client sender, String cmd, String[] args) {
    this.sender = sender;
  }

  protected TS3Api getApi() {
    return Data.getInstance().getTS3Api();
  }

  protected void sendMessage(String msg) {
    getApi().sendPrivateMessage(sender.getId(), msg);
  }
}
