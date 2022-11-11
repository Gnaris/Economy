package Model.Thread;

import Model.WalletModel;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class UpdatePlayerWalletBalanceThread extends WalletModel implements Runnable {

    public UpdatePlayerWalletBalanceThread(Player player) {
        super(player);
    }

    @Override
    public void run() {
        try {
            this.updateWalletBalance(this.playerWallet);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
