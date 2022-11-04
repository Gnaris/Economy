package Model.Thread;

import Model.M_Wallet;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class UpdatePlayerWalletThread extends M_Wallet implements Runnable {

    public UpdatePlayerWalletThread(Player player) {
        super(player);
    }

    @Override
    public void run() {
        try {
            this.updateWallet(this.player);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
