package Model.Thread;

import Model.WalletModel;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class UpdatePlayerWalletBalanceThread extends WalletModel implements Runnable {

    private long balance;
    private String uuid;

    public UpdatePlayerWalletBalanceThread(long balance, String uuid) {
        this.balance = balance;
        this.uuid = uuid;
    }

    @Override
    public void run() {
        try {
            this.updateWalletBalance(balance, uuid);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
