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

    @EventHandler (priority = EventPriority.NORMAL)
    public void onJoin(PlayerJoinEvent e) throws SQLException, ClassNotFoundException {
        if(SPWallet.getInstance().getWalletStore().getWalletList().get(e.getPlayer().getUniqueId()) == null)
        {
            SPWallet.getInstance().getWalletStore().getWalletList().put(e.getPlayer().getUniqueId(), new WalletModel(e.getPlayer()).getPlayerWallet());
        }
    }

    @EventHandler (priority = EventPriority.HIGH)
    public void onLeave(PlayerQuitEvent e) throws SQLException, ClassNotFoundException {
        Thread updatePlayerWalletBalanceThread = new Thread(new UpdatePlayerWalletBalanceThread(e.getPlayer()));
        updatePlayerWalletBalanceThread.start();
        Thread updatePlayerWalletVisibleThread = new Thread(new UpdatePlayerWalletVisibleThread(e.getPlayer()));
        updatePlayerWalletVisibleThread.start();
    }
}
