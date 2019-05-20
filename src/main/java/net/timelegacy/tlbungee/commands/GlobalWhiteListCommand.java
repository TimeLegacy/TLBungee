package net.timelegacy.tlbungee.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.timelegacy.tlbungee.TLBungee;
import net.timelegacy.tlbungee.handler.Rank;
import net.timelegacy.tlbungee.handler.RankHandler;
import net.timelegacy.tlbungee.utils.MessageUtils;

public class GlobalWhiteListCommand extends Command {

	private static TLBungee plugin = TLBungee.getPlugin();

	public GlobalWhiteListCommand() {
		super("globalwhitelist", "", "wlist");
	}

	@SuppressWarnings("deprecation")
	@Override
	public void execute(CommandSender sender, String[] args) {

		if (sender instanceof ProxiedPlayer) {

			ProxiedPlayer p = (ProxiedPlayer) sender;

			Rank r = RankHandler.getRank(p.getName());
            if (r.getPriority() >= 9) {

				if (args.length == 0) {
					MessageUtils.sendMessage(p,
							MessageUtils.MAIN_COLOR + "&lWhitelist Status: " + (plugin.whitelist
											? "&aEnabled" : "&cDisabled"),
							false);
					MessageUtils.helpMenu(p, "/wlist <on/enable>",
							"Enable the Whitelist");
					MessageUtils.helpMenu(p, "/wlist <off/disable>",
							"Disable the Whitelist");
					MessageUtils.helpMenu(p, "/wlist kick",
							"Kick all non-whitelisted players");
					return;
				}

				if (args.length == 1) {
					String third = args[0];

					if (third.equalsIgnoreCase("on")
							|| third.equalsIgnoreCase("enable")) {
						plugin.whitelist = true;
						MessageUtils.sendMessage(sender,
								MessageUtils.SUCCESS_COLOR + "Whitelist enabled.", true);
						MessageUtils.sendMessage(sender,
								MessageUtils.MAIN_COLOR + "Whitelist Status: " + (plugin.whitelist
										? "&aEnabled" : "&cDisabled"),
								true);
						return;
					} else if (third.equalsIgnoreCase("off")
							|| third.equalsIgnoreCase("disable")) {
						plugin.whitelist = false;
						MessageUtils.sendMessage(sender,
								MessageUtils.ERROR_COLOR + "Whitelist disabled.", true);
						MessageUtils.sendMessage(sender,
								MessageUtils.MAIN_COLOR + "Whitelist Status: " + (plugin.whitelist
										? "&aEnabled" : "&cDisabled"),
								true);
						return;
					} else if (third.equalsIgnoreCase("kick")) {
						for (ProxiedPlayer pp : ProxyServer.getInstance()
								.getPlayers()) {
							if (RankHandler.getRank(pp.getName()).getPriority() >= 7) {
								MessageUtils.sendMessage(pp,
										MessageUtils.ERROR_COLOR + "You have not been kicked due to the whitelist.",
										true);
							} else {
								pp.disconnect(
										MessageUtils.colorize(MessageUtils.ERROR_COLOR
												+ "Network under maintenance! Check back later..."));
							}
						}
						return;
					}
				}

							MessageUtils.sendMessage(sender, "&cInvalid command!", true);
							MessageUtils.sendMessage(sender, "&fWhitelist Status: "
											+ (plugin.whitelist ? "&aEnabled" : "&cDisabled"),
						true);
			} else {
							MessageUtils.noPerm(p);
			}
		}
	}

}
