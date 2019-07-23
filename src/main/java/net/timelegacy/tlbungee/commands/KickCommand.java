package net.timelegacy.tlbungee.commands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.timelegacy.tlbungee.datatype.Rank;
import net.timelegacy.tlbungee.handler.RankHandler;
import net.timelegacy.tlbungee.utils.MessageUtils;

public class KickCommand extends Command {

  public KickCommand() {
    super("kick", "");
  }

  @SuppressWarnings("deprecation")
  @Override
  public void execute(CommandSender sender, String[] args) {

    if (sender instanceof ProxiedPlayer) {

      ProxiedPlayer p = (ProxiedPlayer) sender;

      Rank r = RankHandler.getRank(p.getUniqueId());
      if (r.getPriority() >= 7) {

        if (args.length == 0) {
          MessageUtils.sendMessage(p, MessageUtils.ERROR_COLOR + "Usage: /kick [player] (message)", true);
        }

        if (args.length >= 1) {
          ProxiedPlayer t = ProxyServer.getInstance().getPlayer(args[0]);
          if (t == null) {
            MessageUtils.sendMessage(p, MessageUtils.ERROR_COLOR + "Player not found.", true);
          } else {

            if (args.length == 1) {

              MessageUtils.sendMessage(
                  p,
                  MessageUtils.MAIN_COLOR
                      + "Kicking "
                      + MessageUtils.SECOND_COLOR
                      + t.getName()
                      + MessageUtils.MAIN_COLOR
                      + ".",
                  true);
              t.disconnect(
                  ChatColor.translateAlternateColorCodes(
                      '&', MessageUtils.messagePrefix + "&4You have been kicked from the server!"));
            } else if (args.length > 1) {
              MessageUtils.sendMessage(
                  p,
                  MessageUtils.MAIN_COLOR
                      + "Kicking "
                      + MessageUtils.SECOND_COLOR
                      + t.getName()
                      + MessageUtils.MAIN_COLOR
                      + ".",
                  true);

              StringBuilder sb = new StringBuilder();
              for (int i = 1; i < args.length; i++) {
                sb.append(args[i]).append(" ");
              }

              String allArgs = sb.toString().trim();

              t.disconnect(
                  ChatColor.translateAlternateColorCodes(
                      '&', MessageUtils.messagePrefix + "&4" + allArgs));
            }
          }
        }
      } else {
        MessageUtils.noPerm(p);
      }
    }
  }
}
