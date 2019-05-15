package net.timelegacy.tlbungee;

import net.timelegacy.tlbungee.commands.ConnectCommand;
import net.timelegacy.tlbungee.commands.FindPlayerCommand;
import net.timelegacy.tlbungee.commands.GlobalWhiteListCommand;
import net.timelegacy.tlbungee.commands.HubCommand;
import net.timelegacy.tlbungee.commands.KickCommand;
import net.timelegacy.tlbungee.commands.PrivateMessageCommand;
import net.timelegacy.tlbungee.commands.PrivateMessageReplyCommand;
import net.timelegacy.tlbungee.commands.RefreshNetworkCommand;
import net.timelegacy.tlbungee.commands.ToggleOptionsCommand;
import net.timelegacy.tlbungee.event.ConnectEvent;
import net.timelegacy.tlbungee.event.PlayerEvent;
import net.timelegacy.tlbungee.event.ServerPingEvent;
import net.timelegacy.tlbungee.handler.MultiplierHandler;
import net.timelegacy.tlbungee.handler.PlayerHandler;
import net.timelegacy.tlbungee.handler.Rank;
import net.timelegacy.tlbungee.handler.RankHandler;
import net.timelegacy.tlbungee.mongodb.MongoDB;
import net.timelegacy.tlbungee.utils.MessageUtils;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import org.bson.Document;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class TLBungee extends Plugin implements Listener {

	public boolean whitelist = false;
	public List<String> whitelisted = new ArrayList<String>();

	private static TLBungee plugin = null;

	public static TLBungee getInstance() {
		return plugin;
	}

	public HashMap<String, String> messagesToReturn = new HashMap<String, String>();
	public HashMap<String, ToggleOptions> toggleOptions = new HashMap<String, ToggleOptions>();

	public List<String> hubs = new LinkedList<String>();

	// Handlers
	public MultiplierHandler multiplierHandler;
	public PlayerHandler playerHandler;
	public RankHandler rankHandler;
	// Utils
	public MessageUtils messageUtils;

	public MongoDB mongoDB;

	public Configuration config;

	private void init() {

		multiplierHandler = new MultiplierHandler();
		playerHandler = new PlayerHandler();
		rankHandler = new RankHandler();
		// Utils
		messageUtils = new MessageUtils();

		mongoDB = new MongoDB();
	}

	@Override
	public void onEnable() {

		plugin = this;
		try {
			config = ConfigurationProvider.getProvider(YamlConfiguration.class)
					.load(new File(getDataFolder(), "config.yml"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		ProxyServer.getInstance().getServers().clear();

		init();
		mongoDB.connect(config.getString("URI"));

		load();

		rankHandler.loadRanks();

	}

	@Override
	public void onDisable() {
		// todo

		mongoDB.disconnect();
	}

	private void addClassPath(final URL url) throws IOException {
		final URLClassLoader sysloader = (URLClassLoader) ClassLoader.getSystemClassLoader();
		final Class<URLClassLoader> sysclass = URLClassLoader.class;
		try {
			final Method method = sysclass.getDeclaredMethod("addURL", URL.class);
			method.setAccessible(true);
			method.invoke(sysloader, url);
		} catch (final Throwable t) {
			t.printStackTrace();
			throw new IOException("Error adding " + url + " to system classloader");
		}
	}

	private void load() {

		registerCommands();
		registerEvents();

		whitelisted.clear();

		for (Rank rank : rankHandler.rankList) {
			if (rank.getPriority() >= 7) {
				MongoCursor<Document> cursor = mongoDB.players
                        .find(Filters.eq("rank", rank.getName().toUpperCase()))
						.iterator();

				while (cursor.hasNext()) {
					Document doc = cursor.next();
					whitelisted.add(doc.getString("uuid"));
				}
			}
		}
		

		getServersAndHubs();
	}

	public void getServersAndHubs() {
		ProxyServer.getInstance().getServers().clear();
		hubs.clear();

		try {

			MongoCursor<Document> cursor = mongoDB.servers.find().iterator();
			while (cursor.hasNext()) {
				Document doc = cursor.next();

				String name = doc.getString("uid");
				String address = doc.getString("ip");
				String type = doc.getString("type");
				int prt = doc.getInteger("port");

				if (!ProxyServer.getInstance().getServers().containsKey(name)) {

					ServerInfo serverInfo = ProxyServer.getInstance().constructServerInfo(name,
							new InetSocketAddress(address, prt), "", false);
					ProxyServer.getInstance().getServers().put(name, serverInfo);

					if (type.equalsIgnoreCase("LOBBY")) {
						if (hubs.contains(serverInfo.getName())) {
							System.out.print("Already found the server " + serverInfo.getName() + " on the list");
						} else {
							hubs.add(serverInfo.getName());
							System.out.print("Added the server " + serverInfo.getName() + " to the list.");
						}
					}

				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private void registerCommands() {
		plugin.getProxy().getPluginManager().registerCommand(plugin, new PrivateMessageCommand());
		plugin.getProxy().getPluginManager().registerCommand(plugin, new PrivateMessageReplyCommand());
		plugin.getProxy().getPluginManager().registerCommand(plugin, new FindPlayerCommand());
		plugin.getProxy().getPluginManager().registerCommand(plugin, new ToggleOptionsCommand());

		// i havent updated the party code to have the new message colors shits fuck this fuck
		//plugin.getProxy().getPluginManager().registerCommand(plugin, new PartyCommand());

		plugin.getProxy().getPluginManager().registerCommand(plugin, new GlobalWhiteListCommand());
		plugin.getProxy().getPluginManager().registerCommand(plugin, new KickCommand());
		plugin.getProxy().getPluginManager().registerCommand(plugin, new ConnectCommand());
		plugin.getProxy().getPluginManager().registerCommand(plugin, new RefreshNetworkCommand());
		plugin.getProxy().getPluginManager().registerCommand(plugin, new HubCommand());
	}

	private void registerEvents() {
		plugin.getProxy().getPluginManager().registerListener(plugin, new ServerPingEvent());
		plugin.getProxy().getPluginManager().registerListener(plugin, new ConnectEvent());
		plugin.getProxy().getPluginManager().registerListener(plugin, new PlayerEvent());
	}

	public List<String> getHubs() {
		return hubs;
	}

	/*
	 * public static void listHubs() { hubs.clear();
	 * 
	 * for (ServerInfo server : ProxyServer.getInstance().getServers() .values()) {
	 * if (server.getName().startsWith("HUB")) { if
	 * (hubs.contains(server.getName())) { System.out.print(
	 * "Already found the server " + server.getName() + " on the list"); } else {
	 * hubs.add(server.getName()); System.out.print("Added the server " +
	 * server.getName() + " to the list."); } } } }
	 */
}
