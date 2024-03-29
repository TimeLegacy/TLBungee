package net.timelegacy.tlbungee.commands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.timelegacy.tlbungee.TLBungee;
import net.timelegacy.tlbungee.datatype.PlayerProfile;
import net.timelegacy.tlbungee.datatype.PlayerProfile.Status;
import net.timelegacy.tlbungee.utils.MessageUtils;

public class PrivateMessageReplyCommand extends Command {

  private static TLBungee plugin = TLBungee.getPlugin();

  public PrivateMessageReplyCommand() {
    super("reply", null, "r", "respond");
  }

  @Override
  public void execute(CommandSender sender, String[] args) {

    if (args.length < 1) {
      MessageUtils.sendMessage(sender, MessageUtils.ERROR_COLOR + "Usage: /r <message>", true);
      return;
    }

    if (!plugin.messagesToReturn.containsKey(sender.getName())) {
      MessageUtils.sendMessage(
          sender, MessageUtils.ERROR_COLOR + "You have no message to respond to.", true);
      return;
    }

    String reciepent = plugin.messagesToReturn.get(sender.getName());
    ProxiedPlayer p = ProxyServer.getInstance().getPlayer(reciepent);

    if (p == null) {
      MessageUtils.sendMessage(
          sender,
          MessageUtils.ERROR_COLOR + "The player you are responding to disconnected.",
          true);
      plugin.messagesToReturn.remove(sender.getName());
      return;
    }

    StringBuilder msg = new StringBuilder();

    PlayerProfile profile = new PlayerProfile(p.getUniqueId());

    if (profile.getStatus() != Status.DND) {
      for (String arg : args) {
        msg.append(arg + " ");
      }

      MessageUtils.sendMessage(
          sender,
          "(&7To&8) &f" + reciepent + "&8:&f " + ChatColor.stripColor(msg.toString()),
          "&8[&7PM&8] &8");

      MessageUtils.sendMessage(
          p,
          "(&7From&8) &f" + sender.getName() + "&8:&f " + ChatColor.stripColor(msg.toString()),
          "&8[&7PM&8] &8");
    } else {
      MessageUtils.sendMessage(
          sender, MessageUtils.ERROR_COLOR + "This player is in do not disturb.", true);
    }

    plugin.messagesToReturn.put(sender.getName(), p.getName());
    plugin.messagesToReturn.put(p.getName(), sender.getName());
  }
}
