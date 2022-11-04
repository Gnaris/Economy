package Controller;

import Entity.Wallet;
import Model.M_Wallet;
import SPWallet.SPWallet;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public abstract class WalletController extends M_Wallet {

    protected Wallet PlayerWallet;

    public WalletController(Player player)
    {
        super(player);
        this.PlayerWallet = SPWallet.getInstance().getWalletStore().getWalletList().get(player.getUniqueId());
    }

    public void insertWalletToStore() throws SQLException, ClassNotFoundException {
        SPWallet.getInstance().getWalletStore().getWalletList().put(this.player.getUniqueId(), this.getPlayerWallet(this.player));
    }

    public boolean isNewPlayer() throws SQLException, ClassNotFoundException {
        return this.getPlayerWallet(this.player) == null;
    }
}
