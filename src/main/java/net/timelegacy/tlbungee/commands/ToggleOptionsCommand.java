package net.timelegacy.tlbungee.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;
import net.timelegacy.tlbungee.TLBungee;
import net.timelegacy.tlbungee.ToggleOptions;
import net.timelegacy.tlbungee.utils.MessageUtils;

public class ToggleOptionsCommand extends Command {

	private static TLBungee plugin = TLBungee.getPlugin();

	public ToggleOptionsCommand() {
		super("toggleoptions", "", "to", "options", "o");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if (!plugin.toggleOptions.containsKey(sender.getName())) {
			plugin.toggleOptions.put(sender.getName(), new ToggleOptions());
		}

		if (args.length < 1) {
			StringBuilder optionsList = new StringBuilder();

			if (plugin.toggleOptions.get(sender.getName())
					.isAllowPrivateMessages())
				optionsList.append("&aPrivateMessages&7, ");
			else
				optionsList.append("&cPrivateMessages&7, ");

			if (plugin.toggleOptions.get(sender.getName())
					.isAllowServerLookup())
				optionsList.append("&aServerLookup&7.");
			else
				optionsList.append("&cServerLookup&7.");

			MessageUtils.sendMessage(sender,
					MessageUtils.MAIN_COLOR + "Options: " + optionsList.toString(), true);
			MessageUtils.sendMessage(sender,
					MessageUtils.ERROR_COLOR + "Toggle these options with /o <name>", true);
			return;
		}

		String input = args[0];
		if (input.equalsIgnoreCase("PrivateMessages")) {
			plugin.toggleOptions.get(sender.getName())
					.setAllowPrivateMessages(!plugin.toggleOptions
							.get(sender.getName()).isAllowPrivateMessages());
		} else if (input.equalsIgnoreCase("ServerLookup")) {
			plugin.toggleOptions.get(sender.getName())
					.setAllowServerLookup(!plugin.toggleOptions
							.get(sender.getName()).isAllowServerLookup());
		} else {
			MessageUtils.sendMessage(sender, MessageUtils.ERROR_COLOR + "Invalid option specified.",
					true);
			return;
		}

		StringBuilder optionsList = new StringBuilder();

		if (plugin.toggleOptions.get(sender.getName()).isAllowPrivateMessages())
			optionsList.append("&aPrivateMessages&7, ");
		else
			optionsList.append("&cPrivateMessages&7, ");

		if (plugin.toggleOptions.get(sender.getName()).isAllowServerLookup())
			optionsList.append("&aServerLookup&7.");
		else
			optionsList.append("&cServerLookup&7.");

		MessageUtils.sendMessage(sender,
				MessageUtils.MAIN_COLOR + "New Options: " + optionsList.toString(), true);
		MessageUtils.sendMessage(sender,
				MessageUtils.ERROR_COLOR + "Toggle these options with /o <name>", true);
	}

}
