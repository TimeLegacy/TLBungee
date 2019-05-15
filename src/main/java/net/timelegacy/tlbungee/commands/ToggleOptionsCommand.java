package net.timelegacy.tlbungee.commands;

import net.timelegacy.tlbungee.TLBungee;
import net.timelegacy.tlbungee.ToggleOptions;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class ToggleOptionsCommand extends Command {

	private static TLBungee bungee = TLBungee.getInstance();

	public ToggleOptionsCommand() {
		super("toggleoptions", "", "to", "options", "o");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if (!bungee.toggleOptions.containsKey(sender.getName()))
			bungee.toggleOptions.put(sender.getName(), new ToggleOptions());

		if (args.length < 1) {
			StringBuilder optionsList = new StringBuilder();

			if (bungee.toggleOptions.get(sender.getName())
					.isAllowPrivateMessages())
				optionsList.append("&aPrivateMessages&7, ");
			else
				optionsList.append("&cPrivateMessages&7, ");

			if (bungee.toggleOptions.get(sender.getName())
					.isAllowServerLookup())
				optionsList.append("&aServerLookup&7.");
			else
				optionsList.append("&cServerLookup&7.");

			bungee.messageUtils.sendMessage(sender,
					bungee.messageUtils.MAIN_COLOR + "Options: " + optionsList.toString(), true);
			bungee.messageUtils.sendMessage(sender,
					bungee.messageUtils.ERROR_COLOR + "Toggle these options with /o <name>", true);
			return;
		}

		String input = args[0];
		if (input.equalsIgnoreCase("PrivateMessages")) {
			bungee.toggleOptions.get(sender.getName())
					.setAllowPrivateMessages(!bungee.toggleOptions
							.get(sender.getName()).isAllowPrivateMessages());
		} else if (input.equalsIgnoreCase("ServerLookup")) {
			bungee.toggleOptions.get(sender.getName())
					.setAllowServerLookup(!bungee.toggleOptions
							.get(sender.getName()).isAllowServerLookup());
		} else {
			bungee.messageUtils.sendMessage(sender, bungee.messageUtils.ERROR_COLOR + "Invalid option specified.",
					true);
			return;
		}

		StringBuilder optionsList = new StringBuilder();

		if (bungee.toggleOptions.get(sender.getName()).isAllowPrivateMessages())
			optionsList.append("&aPrivateMessages&7, ");
		else
			optionsList.append("&cPrivateMessages&7, ");

		if (bungee.toggleOptions.get(sender.getName()).isAllowServerLookup())
			optionsList.append("&aServerLookup&7.");
		else
			optionsList.append("&cServerLookup&7.");

		bungee.messageUtils.sendMessage(sender,
				bungee.messageUtils.MAIN_COLOR + "New Options: " + optionsList.toString(), true);
		bungee.messageUtils.sendMessage(sender,
				bungee.messageUtils.ERROR_COLOR + "Toggle these options with /o <name>", true);
	}

}
