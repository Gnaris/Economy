package Model;

import Entity.Wallet;
import SPWallet.SPWallet;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class M_Wallet {

    protected Player player;

    public M_Wallet(Player player) {
        this.player = player;
    }

    public Wallet getPlayerWallet(Player player) throws SQLException, ClassNotFoundException {
        PreparedStatement stmt = SPWallet.getSperiasDatabase().prepareStatement(
        "SELECT p.id, p.uuid, pw.amount, pw.visible FROM player p JOIN player_wallet pw ON pw.playerID = p.id WHERE uuid = ?"
        );
        stmt.setString(1, player.getUniqueId().toString());
        ResultSet result = stmt.executeQuery();
        Wallet wallet = null;
        while (result.next())
        {
            wallet = new Wallet(player, result.getInt("amount"), result.getBoolean("visible"));
        }
        return wallet;
    }
    public void createNewWallet(Player player) throws SQLException, ClassNotFoundException {
        PreparedStatement
        stmt = SPWallet.getSperiasDatabase().prepareStatement(
                "INSERT INTO player VALUES (NULL, ?, NOW());"
        );
        stmt.setString(1, player.getUniqueId().toString());
        stmt.executeUpdate();
    }
    public void updateWallet(Player player) throws SQLException, ClassNotFoundException {

        Wallet wallet = SPWallet.getInstance().getWalletStore().getWalletList().get(player.getUniqueId());

        PreparedStatement
        stmt = SPWallet.getSperiasDatabase().prepareStatement(
                "UPDATE player_wallet pw JOIN player p ON p.id = pw.playerID SET pw.amount = ? WHERE p.uuid = ?"
        );
        stmt.setInt(1, wallet.getMoney());
        stmt.setString(2, SPWallet.getInstance().getWalletStore().getWalletList().get(player.getUniqueId()).getOwner().getUniqueId().toString());
        stmt.executeUpdate();

        stmt = SPWallet.getSperiasDatabase()
                .prepareStatement(
                        "UPDATE player_wallet pw JOIN player p ON p.id = pw.playerID SET pw.visible = ? WHERE p.uuid = ?"
                );
        stmt.setBoolean(1, wallet.isVisible());
        stmt.setString(2, SPWallet.getInstance().getWalletStore().getWalletList().get(player.getUniqueId()).getOwner().getUniqueId().toString());
        stmt.executeUpdate();
    }
}
