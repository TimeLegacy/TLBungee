package net.timelegacy.tlbungee.event;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.TabCompleteEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.timelegacy.tlbungee.TLBungee;

public class PlayerEvent implements Listener {

	private static TLBungee plugin = TLBungee.getPlugin();

	@EventHandler
	public void onTabComplete(TabCompleteEvent e) {
		String partialPlayerName = e.getCursor().toLowerCase();

		int lastSpaceIndex = partialPlayerName.lastIndexOf(' ');
		if (lastSpaceIndex >= 0) {
			partialPlayerName = partialPlayerName.substring(lastSpaceIndex + 1);
		}
		for (ProxiedPlayer p : plugin.getProxy().getPlayers()) {
			if (p.getName().toLowerCase().startsWith(partialPlayerName)) {
				e.getSuggestions().add(p.getName());
			}
		}
	}

}
