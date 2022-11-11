package Model.Thread;

import Model.WalletModel;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class UpdatePlayerWalletVisibleThread extends WalletModel implements Runnable {

    public UpdatePlayerWalletVisibleThread(Player player) {
        super(player);
    }

    @Override
    public void run() {
        try {
            this.updateWalletVisible(this.playerWallet);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
