package net.timelegacy.tlbungee.event;

import net.timelegacy.tlbungee.TLBungee;
import net.timelegacy.tlbungee.ToggleOptions;
import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Filters;
import net.md_5.bungee.api.AbstractReconnectHandler;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.event.ServerKickEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import org.bson.Document;

import java.security.SecureRandom;

public class ConnectEvent implements Listener {

	private static TLBungee bungee = TLBungee.getInstance();

    private static ServerInfo randomHub() {
        return ProxyServer.getInstance()
                .getServerInfo(bungee.getHubs().get(new SecureRandom().nextInt(bungee.getHubs().size())));
    }

	@EventHandler
	public void onServerJoin(ServerConnectEvent event) {
    if (bungee.whitelist
        && bungee.rankHandler.getRank(event.getPlayer().getUUID()).getPriority() >= 7) {
      event.getPlayer().disconnect(bungee.messageUtils
          .c(bungee.messageUtils.ERROR_COLOR + "Network under maintenance! Check back later..."));
    }

		if (!bungee.toggleOptions.containsKey(event.getPlayer().getName())) {
			bungee.toggleOptions.put(event.getPlayer().getName(), new ToggleOptions());
		}

		// if (event.getTarget().getName().contains("HUB")) {
		if (((event.getPlayer().getServer() == null) || ((bungee.getHubs().contains(event.getTarget().getName()))
				&& (!bungee.getHubs().contains(event.getPlayer().getServer().getInfo().getName()))))
				&& (bungee.getHubs().contains(event.getTarget().getName()))) {

			ServerInfo target = randomHub();
			if (target.canAccess(event.getPlayer())) {
				try {
					event.setTarget(randomHub());
                    bungee.messageUtils.sendMessage(event.getPlayer(), bungee.messageUtils.MAIN_COLOR + "You have joined " + bungee.messageUtils.SECOND_COLOR + target.getName(), true);
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
		} else if (bungee.getProxy().getReconnectHandler() != null) {
			kickedFrom = bungee.getProxy().getReconnectHandler().getServer(e.getPlayer());
		} else {
			kickedFrom = AbstractReconnectHandler.getForcedHost(e.getPlayer().getPendingConnection());
			if (kickedFrom == null)
				kickedFrom = ProxyServer.getInstance()
						.getServerInfo(e.getPlayer().getPendingConnection().getListener().getDefaultServer());
		}

		// ABC123

		FindIterable<Document> doc = bungee.mongoDB.servers.find(Filters.eq("uid", kickedFrom.getName()));
		String state = doc.first().getString("type");

		if (!state.equalsIgnoreCase("LOBBY")) {
			e.setCancelled(true);
			e.setCancelServer(randomHub());

            //bungee.messageUtils.sendMessage(p, bungee.messageUtils.MAIN_COLOR + "Disconnected: &7" + e.getKickReason(), false);
		}
	}

	@EventHandler
	public void onServerLeave(PlayerDisconnectEvent e) {
		ProxiedPlayer p = e.getPlayer();

		bungee.rankHandler.playerCache.remove(e.getPlayer().getName());
	}


    //TODO
	/*@EventHandler
	public void onSwitch(ServerSwitchEvent e) {
		ProxiedPlayer p = e.getPlayer();
		if (bungee.party.partylist.contains(p.getName())) {
			ServerInfo si = p.getServer().getInfo();

			bungee.messageUtils.sendMessage(p, "&eThe party has joined the server &b" + si.getName(),
					"&6&lPARTY&8: &r");
			for (ProxiedPlayer x : ProxyServer.getInstance().getPlayers()) {
				if ((bungee.party.inparty.containsKey(x.getName()))
						&& (bungee.party.inparty.get(x.getName()) == p.getName())) {
					bungee.messageUtils.sendMessage(x, "&eThe party has joined the server &b" + si.getName(),
							"&6&lPARTY&8: &r");
					x.connect(si);
				}
			}
		}
	}*/

}
