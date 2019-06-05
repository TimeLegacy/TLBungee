package net.timelegacy.tlbungee.utils;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class MessageUtils {

  public static String messagePrefix = "";
  public static String MAIN_COLOR = "&e";
  public static String SECOND_COLOR = "&6";
  public static String SUCCESS_COLOR = "&a";
  public static String ERROR_COLOR = "&c";
  public static String noPermission = "&cYou do not have permission to perform this action!";

  public static void noPerm(ProxiedPlayer p) {
    sendMessage(p, noPermission, false);
  }

  public static void sendMessage(ProxiedPlayer p, String message, Boolean usePrefix) {
    if (p == null || message == null) return;

    if (usePrefix) message = messagePrefix + message;

    p.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
  }

  public static void sendMessage(ProxiedPlayer p, String message, String prefix) {
    if (p == null || message == null) return;

    p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + message));
  }

  public static void sendMessage(CommandSender p, String message, String prefix) {
    if (p == null || message == null) return;

    p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + message));
  }

  public static void sendMessage(CommandSender p, String message, Boolean usePrefix) {
    if (p == null || message == null) return;

    if (usePrefix) message = messagePrefix + message;

    p.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
  }

  public static String colorize(String input) {
    return ChatColor.translateAlternateColorCodes('&', input);
  }

  public static String MD5(String md5) {
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

  public static void helpMenu(ProxiedPlayer p, String command, String desc) {
    sendMessage(p, "&f" + command, false);
    sendMessage(p, " &7&o" + desc, false);
  }

  public static void helpMenu(CommandSender p, String command, String desc) {
    sendMessage(p, "&f" + command, false);
    sendMessage(p, " &7&o" + desc, false);
  }

  public static void helpMenu(CommandSender p, String command, String desc, String prefix) {
    sendMessage(p, "&f" + command, prefix);
    sendMessage(p, " &7&o" + desc, prefix);
  }

  public static void helpMenu(ProxiedPlayer p, String command, String desc, String prefix) {
    sendMessage(p, "&f" + command, prefix);
    sendMessage(p, " &7&o" + desc, prefix);
  }

  public static String friendlyify(String name) {
    String n = name;

    n = n.replaceAll("_", "");
    n = n.substring(0, 1).toUpperCase() + n.substring(1).toLowerCase();

    return n;
  }
}
