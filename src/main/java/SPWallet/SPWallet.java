package SPWallet;

import Command.Cmd_SPWallet;
import Command.Cmd_Wallet;
import Controller.C_Wallet;
import Events.PlayerInstance;
import SPGroupManager.SPGroupManager;
import WalletStore.WalletStore;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import sperias.gnaris.SPDatabase.SPDatabase;
import sperias.group.GroupStore.GroupStore;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;

public class SPWallet extends JavaPlugin {

    private final SPDatabase SPDatabase = (SPDatabase) Bukkit.getServer().getPluginManager().getPlugin("SP_Database");
    private final SPGroupManager SPGroupManager = (SPGroupManager) Bukkit.getServer().getPluginManager().getPlugin("SP_GroupManager");

    private final WalletStore WalletStore = new WalletStore();

    private static SPWallet INSTANCE;

    @Override
    public void onEnable() {

        INSTANCE = this;

        Objects.requireNonNull(getCommand("wallet")).setExecutor(new Cmd_Wallet());
        Objects.requireNonNull(getCommand("spwallet")).setExecutor(new Cmd_SPWallet());

        getServer().getPluginManager().registerEvents(new PlayerInstance(), this);

        if(Bukkit.getOnlinePlayers().size() == 0) return;

        Bukkit.getOnlinePlayers().stream().forEach(player -> {
            try {
                C_Wallet c_wallet = new C_Wallet(player);
                c_wallet.insertWalletToStore();
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static SPWallet getInstance()
    {
        return INSTANCE;
    }
    public WalletStore getWalletStore() {
        return WalletStore;
    }
    public static Connection getSperiasDatabase() throws SQLException, ClassNotFoundException {
        return SPWallet.getInstance().SPDatabase.getSPDatabase().getDatabase();
    }
    public static GroupStore getGroupStore()
    {
        return getInstance().SPGroupManager.getGroupStore();
    }
}
