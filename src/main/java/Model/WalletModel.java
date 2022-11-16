package Model;

import Entity.Wallet;
import org.bukkit.Bukkit;
import sperias.gnaris.SPDatabase.SPDatabase;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class WalletModel {

    private SPDatabase database = (SPDatabase) Bukkit.getServer().getPluginManager().getPlugin("SP_Database");

    public Wallet getPlayerWallet(String uuid) throws SQLException, ClassNotFoundException {
        PreparedStatement stmt = database.getConnection().prepareStatement("SELECT uuid, balance, visible FROM player WHERE uuid = ?");
        stmt.setString(1, uuid);
        ResultSet result = stmt.executeQuery();
        Wallet wallet = null;
        while (result.next())
        {
            wallet = new Wallet(Bukkit.getPlayer(UUID.fromString(result.getString("uuid"))), result.getLong("balance"), result.getBoolean("visible"));
        }
        return wallet;
    }
    public void updateWalletBalance(long balance, String uuid) throws SQLException, ClassNotFoundException {
        PreparedStatement stmt = database.getConnection().prepareStatement("UPDATE player SET balance = ? WHERE uuid = ?");
        stmt.setLong(1, balance);
        stmt.setString(2, uuid);
        stmt.executeUpdate();
    }

    public void updateWalletVisible(boolean visible, String uuid) throws SQLException, ClassNotFoundException {
        PreparedStatement stmt = database.getConnection().prepareStatement("UPDATE player SET visible = ? WHERE uuid = ?");
        stmt.setBoolean(1, visible);
        stmt.setString(2, uuid);
        stmt.executeUpdate();
    }
}
