package Model;

import Entity.Wallet;
import SPWallet.SPWallet;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WalletModel {

    protected Player player;
    protected Wallet playerWallet;

    public WalletModel(Player player) {
        this.player = player;
        this.playerWallet = SPWallet.getInstance().getWalletStore().getWalletList().get(player.getUniqueId());
    }

    public Wallet getPlayerWallet() throws SQLException, ClassNotFoundException {
        PreparedStatement stmt = SPWallet.getSperiasDatabase().prepareStatement("SELECT p.id, p.uuid, pw.amount, pw.visible FROM player p JOIN player_wallet pw ON pw.playerID = p.id WHERE uuid = ?");
        stmt.setString(1, player.getUniqueId().toString());
        ResultSet result = stmt.executeQuery();
        Wallet wallet = null;
        while (result.next())
        {
            wallet = new Wallet(player, result.getLong("amount"), result.getBoolean("visible"));
        }
        return wallet;
    }
    public void updateWalletBalance(Wallet playerWallet) throws SQLException, ClassNotFoundException {
        PreparedStatement stmt = SPWallet.getSperiasDatabase().prepareStatement("UPDATE player_wallet pw JOIN player p ON p.id = pw.playerID SET pw.amount = ? WHERE p.uuid = ?");
        stmt.setLong(1, playerWallet.getBalance());
        stmt.setString(2, SPWallet.getInstance().getWalletStore().getWalletList().get(player.getUniqueId()).getOwner().getUniqueId().toString());
        stmt.executeUpdate();
    }

    public void updateWalletVisible(Wallet playerWallet) throws SQLException, ClassNotFoundException {
        PreparedStatement stmt = SPWallet.getSperiasDatabase().prepareStatement("UPDATE player_wallet pw JOIN player p ON p.id = pw.playerID SET pw.visible = ? WHERE p.uuid = ?");
        stmt.setBoolean(1, playerWallet.isVisible());
        stmt.setString(2, playerWallet.getOwner().getUniqueId().toString());
        stmt.executeUpdate();
    }
}
