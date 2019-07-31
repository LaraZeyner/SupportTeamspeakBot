package de.spexmc.ts.supportteamspeakbot.storage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.github.theholywaffle.teamspeak3.TS3Api;

/**
 * Created by Lara on 30.07.2019 for SupportTeamspeakBot
 */
public final class Data {
  private static Data instance;

  public static Data getInstance() {
    if (instance == null) {
      instance = new Data();
    }
    return instance;
  }

  private final List<Integer> queue;
  private final List<String> forbiddenChannelNames;
  private TS3Api ts3Api;

  private Data() {
    this.queue = new ArrayList<>();
    this.forbiddenChannelNames = Arrays.asList("support", "gespräch");
  }

  //<editor-fold desc="getter and setter">
  public List<String> getForbiddenChannelNames() {
    return forbiddenChannelNames;
  }

  public List<Integer> getQueue() {
    return queue;
  }

  public TS3Api getTS3Api() {
    return ts3Api;
  }

  public void setTS3Api(TS3Api ts3Api) {
    this.ts3Api = ts3Api;
  }
  //</editor-fold>
}
