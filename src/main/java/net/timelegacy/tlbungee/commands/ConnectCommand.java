package net.timelegacy.tlbungee.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.timelegacy.tlbungee.handler.Rank;
import net.timelegacy.tlbungee.handler.RankHandler;
import net.timelegacy.tlbungee.utils.MessageUtils;

public class ConnectCommand extends Command {

	public ConnectCommand() {
		super("connect", "");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {

		if (sender instanceof ProxiedPlayer) {

			ProxiedPlayer p = (ProxiedPlayer) sender;

      Rank r = RankHandler.getRank(p.getName());
            if (r.getPriority() >= 9) {

				if (args.length == 0) {
          MessageUtils.sendMessage(p, MessageUtils.ERROR_COLOR + "Usage: /connect [server]", true);
				}

				if (args.length == 1) {
					
					if (ProxyServer.getInstance().getServerInfo(args[0]) == null) {

            MessageUtils.sendMessage(p, MessageUtils.ERROR_COLOR + "Server not found.", true);
					} else {

            MessageUtils.sendMessage(p,
                MessageUtils.MAIN_COLOR + "Connecting to " + MessageUtils.SECOND_COLOR + args[0]
                    + MessageUtils.MAIN_COLOR + ".", true);
						p.connect(ProxyServer.getInstance().getServerInfo(args[0]));
					
					}
				}
			} else {
              MessageUtils.noPerm(p);
			}
		}

	}
}
