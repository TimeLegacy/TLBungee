package net.timelegacy.tlbungee.event;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import net.md_5.bungee.api.Favicon;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.ServerPing.Players;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.timelegacy.tlbungee.TLBungee;
import net.timelegacy.tlbungee.handler.MultiplierHandler;
import net.timelegacy.tlbungee.utils.MessageUtils;

public class ServerPingEvent implements Listener {

  private static TLBungee plugin = TLBungee.getPlugin();

  @SuppressWarnings("deprecation")
  @EventHandler
  public void onPing(ProxyPingEvent event) {
    ServerPing sp = event.getResponse();
    Players p = sp.getPlayers();

    p.setMax(9999);
    sp.setFavicon(Favicon.create(getImage("server-icon.png")));
    String ver = "1.13";

    if (MultiplierHandler.isMultiplierEnabled()) {
      sp.setDescription(
          MessageUtils.colorize(
              "                 &8<&3< &b{ &6&lTIME LEGACY &b} &3>&8>\n       &a&lCOMING SOON &7/ &b&lMultiplier Enabled!"));

    } else {

      if (!plugin.whitelist) {
        sp.setDescription(
            (MessageUtils.colorize(
                "                 &8<&3< &b{ &6&lTIME LEGACY &b} &3>&8>\n                &3&lCREA&b&lTIVE &7:: &a&lRELEASED")));

      } else if (plugin.whitelist) {

        ServerPing.Protocol version = sp.getVersion();
        version.setProtocol(999);

        sp.setDescription(
            (MessageUtils.colorize(
                "                 &8<&3< &b{ &6&lTIME LEGACY &b} &3>&8>\n       &7&c&oNetwork under maintenance! Check back later...")));

        // version.setName("&5&nMaintenance");
        version.setName(MessageUtils.colorize("&7?/?"));
      }
    }

    sp.setPlayers(p);

    event.setResponse(sp);
  }

  public BufferedImage getImage(String s) {
    BufferedImage img = null;

    try {
      img = ImageIO.read(new File(s));
    } catch (IOException e) {
      e.printStackTrace();
    }

    return img;
  }
}
