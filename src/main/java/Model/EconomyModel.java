package Model;

import Entity.Economy;
import org.bukkit.Bukkit;
import sperias.gnaris.SPDatabase.SPDatabase;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class EconomyModel {

    private SPDatabase database = (SPDatabase) Bukkit.getServer().getPluginManager().getPlugin("SP_Database");

    public Economy getPlayerEconomy(String uuid) throws SQLException, ClassNotFoundException {
        PreparedStatement stmt = database.getConnection().prepareStatement("SELECT uuid, balance, visible FROM player WHERE uuid = ?");
        stmt.setString(1, uuid);
        ResultSet result = stmt.executeQuery();
        Economy economy = null;
        while (result.next())
        {
            economy = new Economy(UUID.fromString(result.getString("uuid")), result.getLong("balance"), result.getBoolean("visible"));
        }
        return economy;
    }
    public void updateMoney(UUID uuid, long amount){
        new Thread(() -> {
            try{
                PreparedStatement stmt = database.getConnection().prepareStatement("UPDATE player SET balance = ? WHERE uuid = ?");
                stmt.setLong(1, amount);
                stmt.setString(2, uuid.toString());
                stmt.executeUpdate();
            }catch (SQLException | ClassNotFoundException e)
            {
                throw new RuntimeException(e);
            }
        }).start();
    }

    public void updateEconomyVisible(UUID uuid, boolean isVisible){
        new Thread(() -> {
            try{
                PreparedStatement stmt = database.getConnection().prepareStatement("UPDATE player SET visible = ? WHERE uuid = ?");
                stmt.setBoolean(1, isVisible);
                stmt.setString(2, uuid.toString());
                stmt.executeUpdate();
            }catch (SQLException | ClassNotFoundException e)
            {
                throw new RuntimeException(e);
            }
        }).start();
    }
}
