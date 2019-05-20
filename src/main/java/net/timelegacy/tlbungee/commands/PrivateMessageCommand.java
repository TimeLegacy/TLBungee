package net.timelegacy.tlbungee.commands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.timelegacy.tlbungee.TLBungee;
import net.timelegacy.tlbungee.ToggleOptions;
import net.timelegacy.tlbungee.utils.MessageUtils;

public class PrivateMessageCommand extends Command {

	private static TLBungee plugin = TLBungee.getPlugin();

	public PrivateMessageCommand() {
		super("pm", null, "msg");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if (!plugin.toggleOptions.containsKey(sender.getName())) {
			plugin.toggleOptions.put(sender.getName(), new ToggleOptions());
		}

		if (args.length < 2) {
			MessageUtils
					.sendMessage(sender, MessageUtils.ERROR_COLOR + "Usage: /pm <player> <message>", true);
			return;
		}

		String reciepent = args[0];
		ProxiedPlayer p = ProxyServer.getInstance().getPlayer(reciepent);

		if (p == null) {
			MessageUtils
					.sendMessage(sender, MessageUtils.ERROR_COLOR + "Specified player is not online.", true);
			return;
		}

		if (p.getName().equals(sender.getName())) {
			MessageUtils
					.sendMessage(sender, MessageUtils.MAIN_COLOR + "You cannot send yourself a message.",
							true);
			return;
		}

		StringBuilder msg = new StringBuilder();

		for (String arg : args)
			if (!arg.equals(reciepent))
				msg.append(" " + arg);

		if (plugin.toggleOptions.get(p.getName()).isAllowPrivateMessages()) {
			MessageUtils.sendMessage(sender, "(&7To&8) &f"
					+ reciepent + "&8:&f"
					+ ChatColor.stripColor(msg.toString()), "&8[&7PM&8] &8");

			MessageUtils.sendMessage(p, "(&7From&8) &f"
					+ sender.getName() + "&8:&f"
					+ ChatColor.stripColor(msg.toString()), "&8[&7PM&8] &8");
		} else {
			MessageUtils.sendMessage(sender,
					MessageUtils.ERROR_COLOR + p.getName() + " has disabled private messaging.", true);
		}

		plugin.messagesToReturn.put(sender.getName(), p.getName());
		plugin.messagesToReturn.put(p.getName(), sender.getName());
	}

}
