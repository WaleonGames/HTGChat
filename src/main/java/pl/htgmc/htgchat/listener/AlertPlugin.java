package pl.htgmc.htgchat.listener;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import me.clip.placeholderapi.PlaceholderAPI;
import pl.htgmc.htgchat.Main;
import pl.htgmc.htgloggers.HTGLoggers;

public class AlertPlugin implements Listener {
    private final Main plugin;
    private final HTGLoggers loggersPlugin;

    public AlertPlugin(Main plugin) {
        this.plugin = plugin;
        this.loggersPlugin = HTGLoggers.getInstance();  // Pobierz instancję HTGLoggers
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String joinMessage;

        if (!player.hasPlayedBefore()) {
            // Welcome message for new players
            joinMessage = plugin.getPluginConfig().getString("messages.new_message");
            joinMessage = PlaceholderAPI.setPlaceholders(player, joinMessage);
            joinMessage = ChatColor.translateAlternateColorCodes('&', joinMessage);
            event.setJoinMessage(joinMessage);

            // Log custom message
            if (loggersPlugin != null) {
                loggersPlugin.logCustomMessage("New player joined: " + player.getName());
            }
        } else {
            // Join message for returning players
            joinMessage = plugin.getPluginConfig().getString("messages.join_message");
            joinMessage = PlaceholderAPI.setPlaceholders(player, joinMessage);
            joinMessage = ChatColor.translateAlternateColorCodes('&', joinMessage);
            event.setJoinMessage(joinMessage);

            // Log custom message
            if (loggersPlugin != null) {
                loggersPlugin.logCustomMessage("Returning player joined: " + player.getName());
            }
        }

        // Send welcome messages to the player
        String[] welcomeMessages = plugin.getPluginConfig().getStringList("messages.welcome_new_player").toArray(new String[0]);
        StringBuilder messageBuilder = new StringBuilder();
        for (String message : welcomeMessages) {
            String processedMessage = PlaceholderAPI.setPlaceholders(player, message);
            processedMessage = ChatColor.translateAlternateColorCodes('&', processedMessage);
            messageBuilder.append(processedMessage).append("\n");
        }
        player.sendMessage(messageBuilder.toString().trim());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        String quitMessage = plugin.getPluginConfig().getString("messages.quit_message");
        quitMessage = PlaceholderAPI.setPlaceholders(player, quitMessage);
        quitMessage = ChatColor.translateAlternateColorCodes('&', quitMessage);
        event.setQuitMessage(quitMessage);

        // Log custom message
        if (loggersPlugin != null) {
            loggersPlugin.logCustomMessage("Player quit: " + player.getName());
        }
    }
}
