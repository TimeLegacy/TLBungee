package net.timelegacy.tlbungee.utils;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class MessageUtils {

	public String messagePrefix = "&b&lMineAqua&8:&r ";
	public String MAIN_COLOR = "&f";
	public String SECOND_COLOR = "&b";
	public String SUCCESS_COLOR = "&a";
    public String ERROR_COLOR = "&c";

	public String noPermission = messagePrefix + "&cYou do not have permission to perform this action!";

	/**
	 * No Permission
	 *
	 * @param p
	 */


	public void noPerm(ProxiedPlayer p) {
		sendMessage(p, noPermission, false);
	}

	/**
	 * Send Message
	 *
	 * @param p
	 * @param message
	 * @param usePrefix
	 */

	public void sendMessage(ProxiedPlayer p, String message, Boolean usePrefix) {
		if (p == null || message == null)
			return;

		if (usePrefix)
			message = messagePrefix + message;

		p.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
	}

	/**
	 * Send Message
	 *
	 * @param p
	 * @param message
	 * @param prefix
	 */

	public void sendMessage(ProxiedPlayer p, String message, String prefix) {
		if (p == null || message == null)
			return;

		p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + message));
	}

	/**
	 * Send Message
	 *
	 * @param p
	 * @param message
	 * @param prefix
	 */

	public void sendMessage(CommandSender p, String message, String prefix) {
		if (p == null || message == null)
			return;

		p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + message));
	}

	/**
	 * Send Message
	 *
	 * @param p
	 * @param message
	 * @param usePrefix
	 */

	public void sendMessage(CommandSender p, String message, Boolean usePrefix) {
		if (p == null || message == null)
			return;

		if (usePrefix)
			message = messagePrefix + message;

		p.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
	}

	/**
	 * Color
	 *
	 * @param input
	 * @return
	 */

	public String c(String input) {
		return ChatColor.translateAlternateColorCodes('&', input);
	}

	/**
	 * MD5 Encrypt
	 *
	 * @param md5
	 * @return
	 */

	public String MD5(String md5) {
		try {
			java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
			byte[] array = md.digest(md5.getBytes());
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < array.length; ++i) {
				sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100), 1, 3);
			}

			return sb.toString();

		} catch (java.security.NoSuchAlgorithmException e) {
			return md5;
		}
	}

	/**
	 * Help Message
	 *
	 * @param p
	 * @param command
	 * @param desc
	 */

	public void helpMenu(ProxiedPlayer p, String command, String desc) {
		sendMessage(p, "&f" + command, false);
		sendMessage(p, " &7&o" + desc, false);
	}

	/**
	 * Help Message
	 *
	 * @param p
	 * @param command
	 * @param desc
	 */

	public void helpMenu(CommandSender p, String command, String desc) {
		sendMessage(p, "&f" + command, false);
		sendMessage(p, " &7&o" + desc, false);
	}

	/**
	 * Help Message
	 *
	 * @param p
	 * @param command
	 * @param desc
	 * @param prefix
	 */

	public void helpMenu(CommandSender p, String command, String desc, String prefix) {
		sendMessage(p, "&f" + command, prefix);
		sendMessage(p, " &7&o" + desc, prefix);
	}

	/**
	 * Help Message
	 *
	 * @param p
	 * @param command
	 * @param desc
	 * @param prefix
	 */

	public void helpMenu(ProxiedPlayer p, String command, String desc, String prefix) {
		sendMessage(p, "&f" + command, prefix);
		sendMessage(p, " &7&o" + desc, prefix);
	}

	/**
	 * Friendlyify
	 *
	 * @param name
	 * @return
	 */

	public String friendlyify(String name) {
		String n = name;

		n = n.replaceAll("_", "");
		n = n.substring(0, 1).toUpperCase() + n.substring(1).toLowerCase();

		return n;
	}

}
