package net.timelegacy.tlbungee.handler;

import net.timelegacy.tlbungee.TLBungee;
import org.bson.Document;

import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Filters;

public class MultiplierHandler {

	private TLBungee bungee = TLBungee.getInstance();

	public Integer getMultiplier() {
		FindIterable<Document> doc = bungee.mongoDB.settings.find(Filters.eq("name", "multiplier"));
		int multiplier = doc.first().getInteger("amount");

		return multiplier;
	}

	public Boolean isMultiplierEnabled() {
		FindIterable<Document> doc = bungee.mongoDB.settings.find(Filters.eq("name", "multiplier"));
		return doc.first().getBoolean("enabled");
	}
}
