package net.timelegacy.tlbungee.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.timelegacy.tlbungee.datatype.Rank;
import net.timelegacy.tlbungee.handler.RankHandler;
import net.timelegacy.tlbungee.utils.MessageUtils;

public class BuildCommand extends Command {

  public BuildCommand() {
    super("build", "");
  }

  @Override
  public void execute(CommandSender sender, String[] args) {

    if (sender instanceof ProxiedPlayer) {

      ProxiedPlayer p = (ProxiedPlayer) sender;

      Rank r = RankHandler.getRank(p.getUniqueId());
      if (r.equals(
          RankHandler.stringToRank("BUILDER"))
          || r.equals(RankHandler.stringToRank("FOUNDER"))
          || r.equals(RankHandler.stringToRank("ADMIN"))
          || r.equals(RankHandler.stringToRank("DEVELOPER"))) {

        if (args.length == 0) {
          MessageUtils.sendMessage(
              p, MessageUtils.SUCCESS_COLOR + "Connecting to the Build server!", true);
          p.connect(
              ProxyServer.getInstance().getServerInfo("fc89acb0-3490-4905-9f20-464cc28ce497"));
        } else {
          MessageUtils.sendMessage(p, MessageUtils.ERROR_COLOR + "Its just `/build`", true);
        }
      } else {
        MessageUtils.noPerm(p);
      }
    }
  }
}
