package Model.Thread;

import Model.WalletModel;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class UpdatePlayerWalletVisibleThread extends WalletModel implements Runnable {

    private boolean visible;
    private String uuid;

    public UpdatePlayerWalletVisibleThread(boolean visible, String uuid) {
        this.visible = visible;
        this.uuid = uuid;
    }

    @Override
    public void run() {
        try {
            this.updateWalletVisible(visible, uuid);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
