package SPWallet;

import Command.Cmd_SPWallet;
import Command.Cmd_Wallet;
import Entity.Wallet;
import Events.PlayerInstance;
import Model.WalletModel;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import sperias.gnaris.SPDatabase.SPDatabase;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class SPWallet extends JavaPlugin {

    private final Map<UUID, Wallet> walletStore = new HashMap<>();

    private static SPWallet INSTANCE;

    @Override
    public void onEnable() {

        this.saveDefaultConfig();

        INSTANCE = this;

        Objects.requireNonNull(getCommand("wallet")).setExecutor(new Cmd_Wallet(this));
        Objects.requireNonNull(getCommand("spwallet")).setExecutor(new Cmd_SPWallet(this));

        getServer().getPluginManager().registerEvents(new PlayerInstance(this), this);

        if(Bukkit.getOnlinePlayers().size() == 0) return;

        Bukkit.getOnlinePlayers().forEach(player -> {
            try {
                walletStore.put(player.getUniqueId(), new WalletModel().getPlayerWallet(player.getUniqueId().toString()));
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }


    public Map<UUID, Wallet> getWalletStore(){return walletStore;}
}
