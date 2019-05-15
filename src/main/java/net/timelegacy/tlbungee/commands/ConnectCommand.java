package net.timelegacy.tlbungee.commands;

import net.md_5.bungee.api.ProxyServer;
import net.timelegacy.tlbungee.TLBungee;
import net.timelegacy.tlbungee.handler.Rank;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class ConnectCommand extends Command {

	private TLBungee bungee = TLBungee.getInstance();

	public ConnectCommand() {
		super("connect", "");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {

		if (sender instanceof ProxiedPlayer) {

			ProxiedPlayer p = (ProxiedPlayer) sender;

            Rank r = bungee.rankHandler.getRank(p.getName());
            if (r.getPriority() >= 9) {

				if (args.length == 0) {
					bungee.messageUtils.sendMessage(p, bungee.messageUtils.ERROR_COLOR + "Usage: /connect [server]", true);
				}

				if (args.length == 1) {
					
					if (ProxyServer.getInstance().getServerInfo(args[0]) == null) {

						bungee.messageUtils.sendMessage(p, bungee.messageUtils.ERROR_COLOR + "Server not found.", true);
					} else {


						bungee.messageUtils.sendMessage(p, bungee.messageUtils.MAIN_COLOR + "Connecting to " + bungee.messageUtils.SECOND_COLOR + args[0] + bungee.messageUtils.MAIN_COLOR + ".", true);
						p.connect(ProxyServer.getInstance().getServerInfo(args[0]));
					
					}
				}
			} else {
				bungee.messageUtils.noPerm(p);
			}
		}

	}
}
