package pl.htgmc.htgchat;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import pl.htgmc.htgchat.listener.AlertPlugin;
import pl.htgmc.htgloggers.HTGLoggers;

public class Main extends JavaPlugin {

    private static Main instance;
    private FileConfiguration config;
    private HTGLoggers loggersPlugin;  // Obiekt HTGLoggers

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig(); // Tworzy domyślny config.yml, jeśli nie istnieje
        config = getConfig();

        // Sprawdź, czy plugin HTGLoggers jest dostępny
        loggersPlugin = (HTGLoggers) Bukkit.getServer().getPluginManager().getPlugin("HTGLoggers");
        if (loggersPlugin != null && loggersPlugin.isEnabled()) {
            // Logowanie zdarzeń
            loggersPlugin.logCustomMessage("HTGChat został włączony i zintegrowany z HTGLoggers!");  // Przykład logowania
        } else {
            getLogger().warning("HTGLoggers plugin nie został znaleziony! Logowanie będzie niedostępne.");
        }

        // Rejestracja listenerów
        getServer().getPluginManager().registerEvents(new AlertPlugin(this), this);
    }

    @Override
    public void onDisable() {
        getLogger().info("HTGChat został wyłączony.");
    }

    public static Main getInstance() {
        return instance;
    }

    public FileConfiguration getPluginConfig() {
        return config;
    }
}
