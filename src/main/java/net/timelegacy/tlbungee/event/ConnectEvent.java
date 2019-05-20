package net.timelegacy.tlbungee.event;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import java.security.SecureRandom;
import net.md_5.bungee.api.AbstractReconnectHandler;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.event.ServerKickEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.timelegacy.tlbungee.TLBungee;
import net.timelegacy.tlbungee.ToggleOptions;
import net.timelegacy.tlbungee.handler.RankHandler;
import net.timelegacy.tlbungee.mongodb.MongoDB;
import net.timelegacy.tlbungee.utils.MessageUtils;
import org.bson.Document;

public class ConnectEvent implements Listener {

	private static TLBungee plugin = TLBungee.getPlugin();

	private static MongoCollection<Document> servers = MongoDB.mongoDatabase.getCollection("servers");

    private static ServerInfo randomHub() {
        return ProxyServer.getInstance()
						.getServerInfo(
								plugin.getHubs().get(new SecureRandom().nextInt(plugin.getHubs().size())));
    }

	@EventHandler
	public void onServerJoin(ServerConnectEvent event) {
		if (plugin.whitelist
				&& !(RankHandler.getRank(event.getPlayer().getName()).getPriority() >= 7)) {
			System.out.println(RankHandler.getRank(event.getPlayer().getName()).getPriority());
			event.getPlayer().disconnect(MessageUtils
					.colorize(MessageUtils.ERROR_COLOR + "Network under maintenance! Check back later..."));
    }

		if (!plugin.toggleOptions.containsKey(event.getPlayer().getName())) {
			plugin.toggleOptions.put(event.getPlayer().getName(), new ToggleOptions());
		}

		// if (event.getTarget().getName().contains("HUB")) {
		if (((event.getPlayer().getServer() == null) || (
				(plugin.getHubs().contains(event.getTarget().getName()))
						&& (!plugin.getHubs().contains(event.getPlayer().getServer().getInfo().getName()))))
				&& (plugin.getHubs().contains(event.getTarget().getName()))) {

			ServerInfo target = randomHub();
			if (target.canAccess(event.getPlayer())) {
				try {

					if (RankHandler.getRank(event.getPlayer().getName()).getPriority() >= 9) {
            event.setTarget(randomHub());
						MessageUtils.sendMessage(
                event.getPlayer(),
								MessageUtils.MAIN_COLOR
                    + "You have joined "
										+ MessageUtils.SECOND_COLOR
                    + target.getName(),
                true);
          }
        } catch (Exception e) {
					System.out.print(e.getMessage());
				}
			}
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onKick(ServerKickEvent e) {
		ProxiedPlayer p = e.getPlayer();
		ServerInfo kickedFrom = null;

		if (e.getPlayer().getServer() != null) {
			kickedFrom = e.getPlayer().getServer().getInfo();
		} else if (plugin.getProxy().getReconnectHandler() != null) {
			kickedFrom = plugin.getProxy().getReconnectHandler().getServer(e.getPlayer());
		} else {
			kickedFrom = AbstractReconnectHandler.getForcedHost(e.getPlayer().getPendingConnection());
			if (kickedFrom == null)
				kickedFrom = ProxyServer.getInstance()
						.getServerInfo(e.getPlayer().getPendingConnection().getListener().getDefaultServer());
		}

		// ABC123

		FindIterable<Document> doc = servers.find(Filters.eq("uuid", kickedFrom.getName()));
		String state = doc.first().getString("type");

		if (!state.equalsIgnoreCase("LOBBY")) {
			e.setCancelled(true);
			e.setCancelServer(randomHub());

			//MessageUtils.sendMessage(p, MessageUtils.MAIN_COLOR + "Disconnected: &7" + e.getKickReason(), false);
		}
	}
}
