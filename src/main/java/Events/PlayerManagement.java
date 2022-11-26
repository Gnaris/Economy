package Events;

import Model.EconomyModel;
import Economy.EconomyPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.sql.SQLException;

public class PlayerManagement implements Listener{

    private final EconomyPlugin plugin;

    public PlayerManagement(EconomyPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) throws SQLException, ClassNotFoundException {
        if(!plugin.getEconomies().containsKey(e.getPlayer().getUniqueId()))
        {
            plugin.getEconomies().put(e.getPlayer().getUniqueId(), new EconomyModel().getPlayerEconomy(e.getPlayer().getUniqueId().toString()));
        }
    }
}
