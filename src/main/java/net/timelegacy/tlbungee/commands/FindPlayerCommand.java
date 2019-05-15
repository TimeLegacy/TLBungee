package net.timelegacy.tlbungee.commands;

import net.timelegacy.tlbungee.TLBungee;
import net.timelegacy.tlbungee.ToggleOptions;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class FindPlayerCommand extends Command {

	private TLBungee bungee = TLBungee.getInstance();

	public FindPlayerCommand() {
		super("findplayer", "", "fp");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if (!bungee.toggleOptions.containsKey(sender.getName()))
			bungee.toggleOptions.put(sender.getName(), new ToggleOptions());

		if (args.length < 1) {
			bungee.messageUtils.sendMessage(sender, bungee.messageUtils.ERROR_COLOR + "Syntax: /findplayer <player>",
					true);
			return;
		}

		String reciepent = args[0];
		ProxiedPlayer p = ProxyServer.getInstance().getPlayer(reciepent);

		if (p == null) {
			bungee.messageUtils.sendMessage(sender,
					bungee.messageUtils.ERROR_COLOR + "The player you specified is not online.", true);
			return;
		}

		if (bungee.toggleOptions.get(p.getName()).isAllowServerLookup()) {
			bungee.messageUtils.sendMessage(sender,
					bungee.messageUtils.SECOND_COLOR + p.getName() + bungee.messageUtils.MAIN_COLOR + " is on " + bungee.messageUtils.SECOND_COLOR + bungee.messageUtils.friendlyify(p.getServer().getInfo().getName()),
					true);
			bungee.messageUtils.sendMessage(p,
					bungee.messageUtils.SECOND_COLOR + sender.getName()
							+ bungee.messageUtils.MAIN_COLOR + " has been told what server you are on.",
							true);
		} else {
			bungee.messageUtils.sendMessage(sender,
					bungee.messageUtils.ERROR_COLOR + sender.getName() + " has disabled server lookups.",
					true);
		}
	}

}
