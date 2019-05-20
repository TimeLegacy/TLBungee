package net.timelegacy.tlbungee.handler;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import java.util.UUID;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.timelegacy.tlbungee.mongodb.MongoDB;
import org.bson.Document;

public class PlayerHandler {

	public static MongoCollection<Document> players = MongoDB.mongoDatabase.getCollection("players");

	public static boolean playerExists(ProxiedPlayer player) {
		FindIterable<Document> iterable = players
				.find(new Document("uuid", player.getUniqueId().toString()));
		return iterable.first() != null;
	}

	public static boolean playerExistsName(String playerName) {
		FindIterable<Document> iterable = players.find(new Document("username", playerName));
		return iterable.first() != null;
	}

	public static boolean playerExistsUUID(UUID uuid) {
		FindIterable<Document> iterable = players.find(new Document("uuid", uuid.toString()));
		return iterable.first() != null;
	}

	public static String getUUID(String playerName) {
		String uuid = null;

		if (playerExistsName(playerName)) {
			FindIterable<Document> doc = players.find(Filters.eq("username", playerName));
			String uid = doc.first().getString("uuid");

			uuid = uid;
		}
		return uuid;
	}

	public static void updateUsername(ProxiedPlayer player) {
		if (playerExists(player)) {
			players.updateOne(Filters.eq("uuid", player.getUniqueId().toString()),
					new Document("$set", new Document("username", player.getName())));
		}
	}

	public static String getUsername(ProxiedPlayer playerName) {
		String uuid = null;

		if (playerExists(playerName)) {
			FindIterable<Document> doc = players
					.find(Filters.eq("uuid", playerName.getUniqueId().toString()));
			String uid = doc.first().getString("username");

			uuid = uid;
		}
		return uuid;
	}
}
