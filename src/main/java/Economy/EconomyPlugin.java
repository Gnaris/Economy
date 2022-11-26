package Economy;

import Command.CMD_Money;
import Command.CMD_Pay;
import Command.Cmd_Economy;
import Entity.Economy;
import Events.PlayerManagement;
import Model.EconomyModel;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class EconomyPlugin extends JavaPlugin {

    private final Map<UUID, Economy> economies = new HashMap<>();

    @Override
    public void onEnable() {

        this.saveDefaultConfig();

        Objects.requireNonNull(getCommand("money")).setExecutor(new CMD_Money(this));
        Objects.requireNonNull(getCommand("pay")).setExecutor(new CMD_Pay(this));
        Objects.requireNonNull(getCommand("economy")).setExecutor(new Cmd_Economy(this));

        getServer().getPluginManager().registerEvents(new PlayerManagement(this), this);

        if(Bukkit.getOnlinePlayers().size() > 0)
        {
            EconomyModel economyModel = new EconomyModel();
            Bukkit.getOnlinePlayers().forEach(player -> {
                try {
                    economies.put(player.getUniqueId(), economyModel.getPlayerEconomy(player.getUniqueId().toString()));
                } catch (SQLException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

    @Override
    public void onDisable() {

    }

    public Map<UUID, Entity.Economy> getEconomies() {
        return economies;
    }

    public String getCurrency() {
        return getConfig().getString("currency");
    }
}
