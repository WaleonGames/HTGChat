package pl.htgmc.htgloggers;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;

public class HTGLoggers extends JavaPlugin {

    private static HTGLoggers instance;
    private Path logFilePath;

    @Override
    public void onEnable() {
        instance = this;

        // Inicjalizacja pliku logów
        initializeLogFile();

        // Przykład logowania podczas uruchamiania
        logCustomMessage("HTGLoggers został pomyślnie włączony.");
    }

    @Override
    public void onDisable() {
        // Przykład logowania podczas wyłączania
        logCustomMessage("HTGLoggers został wyłączony.");
    }

    // Inicjalizuje plik logów
    private void initializeLogFile() {
        try {
            logFilePath = Paths.get(getDataFolder().toString(), "htg_loggers.log");
            if (!Files.exists(logFilePath)) {
                Files.createDirectories(logFilePath.getParent());
                Files.createFile(logFilePath);
            }
        } catch (IOException e) {
            getLogger().log(Level.SEVERE, "Nie można utworzyć pliku logów: " + e.getMessage(), e);
        }
    }

    // Loguje wiadomości do pliku i na konsolę
    public void logCustomMessage(String message) {
        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String formattedMessage = "[" + timestamp + "] " + message;

        // Logowanie na konsolę
        Bukkit.getLogger().info(formattedMessage);

        // Logowanie do pliku
        try {
            Files.write(logFilePath, (formattedMessage + System.lineSeparator()).getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            getLogger().log(Level.SEVERE, "Nie można zapisać wiadomości do pliku logów: " + e.getMessage(), e);
        }
    }

    public static HTGLoggers getInstance() {
        return instance;
    }
}
