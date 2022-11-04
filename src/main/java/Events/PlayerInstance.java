package Events;

import Controller.C_Wallet;
import Model.Thread.UpdatePlayerWalletThread;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.sql.SQLException;

public class PlayerInstance implements Listener{

    private C_Wallet CWallet;

    @EventHandler (priority = EventPriority.HIGH)
    public void onJoin(PlayerJoinEvent e) throws SQLException, ClassNotFoundException {

        this.CWallet = new C_Wallet(e.getPlayer());
        if(CWallet.isNewPlayer())
        {
            CWallet.createNewWallet(e.getPlayer());
            Bukkit.broadcastMessage(ChatColor.of("#C5D2DC") + "" + ChatColor.BOLD +"Bienvenue " + e.getPlayer().getName() + " sur Sperias !");
        }
        CWallet.insertWalletToStore();
    }

    @EventHandler (priority = EventPriority.HIGH)
    public void onLeave(PlayerQuitEvent e) throws SQLException, ClassNotFoundException {
        Thread UpdatePlayerWallet = new Thread(new UpdatePlayerWalletThread(e.getPlayer()));
        UpdatePlayerWallet.start();
    }
}
