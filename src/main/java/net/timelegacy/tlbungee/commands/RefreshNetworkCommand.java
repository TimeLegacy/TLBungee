package net.timelegacy.tlbungee.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.timelegacy.tlbungee.TLBungee;
import net.timelegacy.tlbungee.datatype.Rank;
import net.timelegacy.tlbungee.handler.RankHandler;
import net.timelegacy.tlbungee.utils.MessageUtils;

public class RefreshNetworkCommand extends Command {

	private static TLBungee plugin = TLBungee.getPlugin();

	public RefreshNetworkCommand() {
		super("refreshnetwork", "");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {

		if (sender instanceof ProxiedPlayer) {

			ProxiedPlayer p = (ProxiedPlayer) sender;

			Rank r = RankHandler.getRank(p.getUniqueId());
            if (r.getPriority() >= 9) {

				ProxyServer.getInstance().getServers().clear();
							plugin.hubs.clear();
							plugin.getServersAndHubs();

							MessageUtils.sendMessage(p, MessageUtils.SUCCESS_COLOR
									+ "Successfuly refreshed servers & whitelisted players.", true);

			} else {
							MessageUtils.noPerm(p);
			}
		}

	}
}
