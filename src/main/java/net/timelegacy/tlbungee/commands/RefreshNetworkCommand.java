package net.timelegacy.tlbungee.commands;

import net.timelegacy.tlbungee.TLBungee;
import net.timelegacy.tlbungee.handler.Rank;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import org.bson.Document;

public class RefreshNetworkCommand extends Command {

	private static TLBungee bungee = TLBungee.getInstance();

	public RefreshNetworkCommand() {
		super("refreshnetwork", "");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {

		if (sender instanceof ProxiedPlayer) {

			ProxiedPlayer p = (ProxiedPlayer) sender;

            Rank r = bungee.rankHandler.getRank(p.getName());
            if (r.getPriority() >= 9) {

				ProxyServer.getInstance().getServers().clear();
				bungee.hubs.clear();

				bungee.getServersAndHubs();

				bungee.whitelisted.clear();

                for (Rank rank : bungee.rankHandler.rankList) {
                    if (rank.getPriority() >= 7) {
                        MongoCursor<Document> cursor = bungee.mongoDB.players
                                .find(Filters.eq("rank", rank.getName()))
                                .iterator();

                        while (cursor.hasNext()) {
                            Document doc = cursor.next();
                            bungee.whitelisted.add(doc.getString("uuid"));
                        }
                    }
                }

				bungee.messageUtils.sendMessage(p, bungee.messageUtils.SUCCESS_COLOR + "Successfuly refreshed servers & whitelisted players.", true);

			} else {
				bungee.messageUtils.noPerm(p);
			}
		}

	}
}

/*
 * 
 * ProxyServer.getInstance().getServers().clear(); hubs.clear();
 * 
 * try {
 * 
 * ResultSet res = DB.querySQL("SELECT * FROM `servers`;"); while (res.next()) {
 * String name = res.getString("name"); String address = res.getString("ip");
 * int prt = res.getInt("port");
 * 
 * if (!ProxyServer.getInstance().getServers() .containsKey(name)) {
 * 
 * ServerInfo serverInfo = ProxyServer.getInstance() .constructServerInfo(name,
 * new InetSocketAddress(address, prt), "", false);
 * ProxyServer.getInstance().getServers().put(name, serverInfo);
 * 
 * if (serverInfo.getName().startsWith("HUB")) { if
 * (hubs.contains(serverInfo.getName())) {
 * System.out.print("Already found the server " + serverInfo.getName() +
 * " on the list"); } else { hubs.add(serverInfo.getName());
 * System.out.print("Added the server " + serverInfo.getName() +
 * " to the list."); } }
 * 
 * } }
 * 
 */
