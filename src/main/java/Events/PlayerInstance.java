package Events;

import Command.Controller.WalletController;
import Entity.Wallet;
import Model.Thread.UpdatePlayerWalletBalanceThread;
import Model.Thread.UpdatePlayerWalletVisibleThread;
import Model.WalletModel;
import SPWallet.SPWallet;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.sql.SQLException;

public class PlayerInstance implements Listener{

    private final SPWallet plugin;

    public PlayerInstance(SPWallet plugin) {
        this.plugin = plugin;
    }

    @EventHandler (priority = EventPriority.NORMAL)
    public void onJoin(PlayerJoinEvent e) throws SQLException, ClassNotFoundException {
        if(plugin.getWalletStore().get(e.getPlayer().getUniqueId()) == null)
        {
            plugin.getWalletStore().put(e.getPlayer().getUniqueId(), new WalletModel().getPlayerWallet(e.getPlayer().getUniqueId().toString()));
        }
    }

    @EventHandler (priority = EventPriority.HIGH)
    public void onLeave(PlayerQuitEvent e) throws SQLException, ClassNotFoundException {
        Wallet playerWallet = plugin.getWalletStore().get(e.getPlayer().getUniqueId());
        new Thread(new UpdatePlayerWalletBalanceThread(playerWallet.getBalance(), e.getPlayer().getUniqueId().toString())).start();
        new Thread(new UpdatePlayerWalletVisibleThread(playerWallet.isVisible(), e.getPlayer().getUniqueId().toString())).start();
    }
}
