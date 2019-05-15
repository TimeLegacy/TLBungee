package net.timelegacy.tlbungee.commands;

import net.md_5.bungee.api.ProxyServer;
import net.timelegacy.tlbungee.TLBungee;
import net.timelegacy.tlbungee.handler.Rank;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class KickCommand extends Command {

	private TLBungee bungee = TLBungee.getInstance();

	public KickCommand() {
		super("kick", "");
	}

	@SuppressWarnings("deprecation")
	@Override
	public void execute(CommandSender sender, String[] args) {

		if (sender instanceof ProxiedPlayer) {

			ProxiedPlayer p = (ProxiedPlayer) sender;

            Rank r = bungee.rankHandler.getRank(p.getName());
            if (r.getPriority() >= 7) {

				if (args.length == 0) {
					bungee.messageUtils.sendMessage(p, bungee.messageUtils.ERROR_COLOR + "Usage: /kick [player]", true);
				}

				if (args.length == 1) {
					ProxiedPlayer t = ProxyServer.getInstance().getPlayer(args[0]);
					if (t == null) {
						bungee.messageUtils.sendMessage(p, bungee.messageUtils.ERROR_COLOR + "Player not found.", true);
					} else {


						bungee.messageUtils.sendMessage(p, bungee.messageUtils.MAIN_COLOR + "Kicking " + bungee.messageUtils.SECOND_COLOR + t.getName() + bungee.messageUtils.MAIN_COLOR + ".", true);
						t.disconnect(ChatColor.translateAlternateColorCodes('&', bungee.messageUtils.messagePrefix
								+ "&4You have been kicked from the server!"));
					
					}
				}
			} else {
				bungee.messageUtils.noPerm(p);
			}
		}

	}
}
