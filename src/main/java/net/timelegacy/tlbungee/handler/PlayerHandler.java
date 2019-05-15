package net.timelegacy.tlbungee.handler;

import net.timelegacy.tlbungee.TLBungee;
import org.bson.Document;

import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Filters;

import net.md_5.bungee.api.connection.ProxiedPlayer;

public class PlayerHandler {

    private TLBungee bungee = TLBungee.getInstance();

    /**
     * Check if a player exists in the database
     *
     * @param player
     * @return
     */

    public boolean playerExists(ProxiedPlayer player) {
		FindIterable<Document> iterable = bungee.mongoDB.players
				.find(new Document("uuid", player.getUniqueId().toString()));
		return iterable.first() != null;
	}

    /**
     * Check if a player exists based on their username
     *
     * @param playerName
     * @return
     */

    public boolean playerExistsName(String playerName) {
		FindIterable<Document> iterable = bungee.mongoDB.players.find(new Document("username", playerName));
		return iterable.first() != null;
	}

    /**
     * Get the uuid of a player from the database
     *
     * @param playerName
     * @return
     */

    public String getUUID(String playerName) {
		String uuid = null;

		if (bungee.playerHandler.playerExistsName(playerName)) {
			FindIterable<Document> doc = bungee.mongoDB.players.find(Filters.eq("username", playerName));
			String uid = doc.first().getString("uuid");

			uuid = uid;
		}
		return uuid;
	}

	public void updateUsername(ProxiedPlayer player) {
		if (bungee.playerHandler.playerExists(player)) {
			bungee.mongoDB.players.updateOne(Filters.eq("uuid", player.getUniqueId().toString()),
					new Document("$set", new Document("username", player.getName())));
		}
	}

    /**
     * Get the uuid of a player on the server from the database
     *
     * @param playerName
     * @return
     */

	public String getUsername(ProxiedPlayer playerName) {
		String uuid = null;

		if (bungee.playerHandler.playerExists(playerName)) {
			FindIterable<Document> doc = bungee.mongoDB.players.find(Filters.eq("uuid", playerName.getUniqueId().toString()));
			String uid = doc.first().getString("username");

			uuid = uid;
		}
		return uuid;
	}
}
