package SPWallet;

import Command.Cmd_SPWallet;
import Command.Cmd_Wallet;
import Events.PlayerInstance;
import Model.WalletModel;
import WalletStore.WalletStore;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import sperias.gnaris.SPDatabase.SPDatabase;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;

public class SPWallet extends JavaPlugin {

    private final SPDatabase SPDatabase = (SPDatabase) Bukkit.getServer().getPluginManager().getPlugin("SP_Database");

    private final WalletStore walletStore = new WalletStore();

    private static SPWallet INSTANCE;

    @Override
    public void onEnable() {

        this.saveDefaultConfig();

        INSTANCE = this;

        Objects.requireNonNull(getCommand("wallet")).setExecutor(new Cmd_Wallet());
        Objects.requireNonNull(getCommand("spwallet")).setExecutor(new Cmd_SPWallet());

        getServer().getPluginManager().registerEvents(new PlayerInstance(), this);

        if(Bukkit.getOnlinePlayers().size() == 0) return;

        Bukkit.getOnlinePlayers().forEach(player -> {
            try {
                WalletModel walletModel = new WalletModel(player);
                walletStore.getWalletList().put(player.getUniqueId(), walletModel.getPlayerWallet());
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
        return walletStore;
    }
    public static Connection getSperiasDatabase() throws SQLException, ClassNotFoundException {
        return SPWallet.getInstance().SPDatabase.getSPDatabase().getDatabase();
    }
}
