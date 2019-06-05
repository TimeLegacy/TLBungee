package net.timelegacy.tlbungee;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
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
import net.timelegacy.tlbungee.handler.RankHandler;
import net.timelegacy.tlbungee.mongodb.MongoDB;
import org.bson.Document;

public class TLBungee extends Plugin implements Listener {

  private static TLBungee plugin = null;
  public boolean whitelist = false;
  public HashMap<String, String> messagesToReturn = new HashMap<String, String>();
  public HashMap<String, ToggleOptions> toggleOptions = new HashMap<String, ToggleOptions>();
  public List<String> hubs = new LinkedList<String>();
  public Configuration config;

  public static TLBungee getPlugin() {
    return plugin;
  }

  @Override
  public void onEnable() {

    plugin = this;
    try {
      config =
          ConfigurationProvider.getProvider(YamlConfiguration.class)
              .load(new File(getDataFolder(), "config.yml"));
    } catch (IOException e1) {
      // TODO Auto-generated catch block
      e1.printStackTrace();
    }

    ProxyServer.getInstance().getServers().clear();

    MongoDB.connect(config.getString("URI"));

    load();

    RankHandler.loadRanks();
  }

  @Override
  public void onDisable() {
    MongoDB.disconnect();
  }

  private void load() {

    registerCommands();
    registerEvents();

    getServersAndHubs();
  }

  public void getServersAndHubs() {
    ProxyServer.getInstance().getServers().clear();
    hubs.clear();

    MongoCollection<Document> servers = MongoDB.mongoDatabase.getCollection("servers");

    try {

      MongoCursor<Document> cursor = servers.find().iterator();
      while (cursor.hasNext()) {
        Document doc = cursor.next();

        String name = doc.getString("uuid");
        String address = doc.getString("ip");
        int prt = doc.getInteger("port");
        String type = doc.getString("type");

        if (!ProxyServer.getInstance().getServers().containsKey(name)) {

          ServerInfo serverInfo =
              ProxyServer.getInstance()
                  .constructServerInfo(name, new InetSocketAddress(address, prt), "", false);
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
}
