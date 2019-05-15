package net.timelegacy.tlbungee.commands;

import net.timelegacy.tlbungee.TLBungee;
import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Filters;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import org.bson.Document;

import java.security.SecureRandom;

public class HubCommand extends Command {

	private TLBungee bungee = TLBungee.getInstance();

	public HubCommand() {
		super("hub", "", "exit", "lobby", "spawn", "mineaqua");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {

		if (sender instanceof ProxiedPlayer) {

			ProxiedPlayer p = (ProxiedPlayer) sender;


			FindIterable<Document> doc = bungee.mongoDB.servers.find(Filters.eq("uid", p.getServer().getInfo().getName()));
			String state = doc.first().getString("type");
			
			if(!state.equalsIgnoreCase("LOBBY")) {
				p.connect(ProxyServer.getInstance()
						.getServerInfo(bungee.getHubs().get(new SecureRandom().nextInt(bungee.getHubs().size()))));
			}else {
				bungee.messageUtils.sendMessage(p, bungee.messageUtils.ERROR_COLOR + "You are already connected to a lobby.", true);
			}
		}

	}
}
