package de.spexmc.ts.supportteamspeakbot.util;

import java.util.TimerTask;

/**
 * Description
 *
 * @author Abgie on 26.10.2018 19:21
 * project SupportSystem
 * @version 1.0
 * @since JDK 8
 */
public final class TimerUtils {

  public static TimerTask run(Runnable r) {
    return new TimerTask() {

      @Override
      public void run() {
        r.run();
      }
    };
  }
}
