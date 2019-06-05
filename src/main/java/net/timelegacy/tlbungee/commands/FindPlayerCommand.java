package net.timelegacy.tlbungee.commands;

import java.util.UUID;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.timelegacy.tlbungee.TLBungee;
import net.timelegacy.tlbungee.ToggleOptions;
import net.timelegacy.tlbungee.handler.ServerHandler;
import net.timelegacy.tlbungee.utils.MessageUtils;

public class FindPlayerCommand extends Command {

  private TLBungee plugin = TLBungee.getPlugin();

  public FindPlayerCommand() {
    super("findplayer", "", "fp");
  }

  @Override
  public void execute(CommandSender sender, String[] args) {
    if (!plugin.toggleOptions.containsKey(sender.getName())) {
      plugin.toggleOptions.put(sender.getName(), new ToggleOptions());
    }

    if (args.length < 1) {
      MessageUtils.sendMessage(
          sender, MessageUtils.ERROR_COLOR + "Syntax: /findplayer <player>", true);
      return;
    }

    String reciepent = args[0];
    ProxiedPlayer p = ProxyServer.getInstance().getPlayer(reciepent);

    if (p == null) {
      MessageUtils.sendMessage(
          sender, MessageUtils.ERROR_COLOR + "The player you specified is not online.", true);
      return;
    }

    if (plugin.toggleOptions.get(p.getName()).isAllowServerLookup()) {
      MessageUtils.sendMessage(
          sender,
          MessageUtils.SECOND_COLOR
              + p.getName()
              + MessageUtils.MAIN_COLOR
              + " is on "
              + MessageUtils.SECOND_COLOR
              + MessageUtils.friendlyify(
                  ServerHandler.getType(UUID.fromString(p.getServer().getInfo().getName()))),
          true);
      MessageUtils.sendMessage(
          p,
          MessageUtils.SECOND_COLOR
              + sender.getName()
              + MessageUtils.MAIN_COLOR
              + " has been told what server you are on.",
          true);
    } else {
      MessageUtils.sendMessage(
          sender,
          MessageUtils.ERROR_COLOR + sender.getName() + " has disabled server lookups.",
          true);
    }
  }
}
