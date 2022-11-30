package Events;

import Model.EconomyModel;
import Economy.EconomyAPI;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.SQLException;

public class PlayerManagement implements Listener{

    private final EconomyAPI plugin;

    public PlayerManagement(EconomyAPI plugin) {
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
