package net.timelegacy.tlbungee.commands;

import net.timelegacy.tlbungee.TLBungee;
import net.timelegacy.tlbungee.handler.Rank;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class GlobalWhiteListCommand extends Command {

	private TLBungee bungee = TLBungee.getInstance();

	public GlobalWhiteListCommand() {
		super("globalwhitelist", "", "wlist");
	}

	@SuppressWarnings("deprecation")
	@Override
	public void execute(CommandSender sender, String[] args) {

		if (sender instanceof ProxiedPlayer) {

			ProxiedPlayer p = (ProxiedPlayer) sender;

            Rank r = bungee.rankHandler.getRank(p.getName());
            if (r.getPriority() >= 9) {

				if (args.length == 0) {
					bungee.messageUtils.sendMessage(p,
							bungee.messageUtils.MAIN_COLOR + "&lWhitelist Status: " + (bungee.whitelist
											? "&aEnabled" : "&cDisabled"),
							false);
					bungee.messageUtils.helpMenu(p, "/wlist <on/enable>",
							"Enable the Whitelist");
					bungee.messageUtils.helpMenu(p, "/wlist <off/disable>",
							"Disable the Whitelist");
					bungee.messageUtils.helpMenu(p, "/wlist kick",
							"Kick all non-whitelisted players");
					return;
				}

				if (args.length == 1) {
					String third = args[0];

					if (third.equalsIgnoreCase("on")
							|| third.equalsIgnoreCase("enable")) {
						bungee.whitelist = true;
						bungee.messageUtils.sendMessage(sender,
								bungee.messageUtils.SUCCESS_COLOR + "Whitelist enabled.", true);
						bungee.messageUtils.sendMessage(sender,
								bungee.messageUtils.MAIN_COLOR + "Whitelist Status: " + (bungee.whitelist
										? "&aEnabled" : "&cDisabled"),
								true);
						return;
					} else if (third.equalsIgnoreCase("off")
							|| third.equalsIgnoreCase("disable")) {
						bungee.whitelist = false;
						bungee.messageUtils.sendMessage(sender,
								bungee.messageUtils.ERROR_COLOR + "Whitelist disabled.", true);
						bungee.messageUtils.sendMessage(sender,
								bungee.messageUtils.MAIN_COLOR + "Whitelist Status: " + (bungee.whitelist
										? "&aEnabled" : "&cDisabled"),
								true);
						return;
					} else if (third.equalsIgnoreCase("kick")) {
						for (ProxiedPlayer pp : ProxyServer.getInstance()
								.getPlayers()) {
							if (bungee.whitelisted.contains(pp.getUniqueId().toString())) {
								bungee.messageUtils.sendMessage(sender,
										bungee.messageUtils.ERROR_COLOR + "You have not been kicked due to the whitelist.",
										true);
							} else {
								pp.disconnect(
										bungee.messageUtils.c(bungee.messageUtils.ERROR_COLOR + "Network under maintenance! Check back later..."));
							}
						}
						return;
					}
				}

				bungee.messageUtils.sendMessage(sender, "&cInvalid command!", true);
				bungee.messageUtils.sendMessage(sender, "&fWhitelist Status: "
						+ (bungee.whitelist ? "&aEnabled" : "&cDisabled"),
						true);
			} else {
				bungee.messageUtils.noPerm(p);
			}
		}
	}

}
