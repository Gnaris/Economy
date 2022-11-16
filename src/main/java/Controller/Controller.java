package Controller;

import Entity.Wallet;
import SPWallet.SPWallet;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public abstract class Controller{

    protected Player player;
    protected Wallet playerWallet;
    protected SPWallet plugin;
    protected FileConfiguration config;

    public Controller(Player player, SPWallet plugin) {
        this.player = player;
        this.plugin = plugin;
        this.playerWallet = plugin.getWalletStore().get(player.getUniqueId());
        this.config = plugin.getConfig();
    }

    protected boolean existingTarget(Player target)
    {
        if(target == null)
        {
            player.sendMessage("§cCe joueur n'existe pas ou n'est pas connecté");
            return false;
        }

        return true;
    }

    protected boolean isLongFormat(String amount)
    {
        try{
            Long.parseLong(amount);
        }catch (NumberFormatException e)
        {
            player.sendMessage("§c" + amount + " n'est pas un montant valide");
            return false;
        }
        if(Long.parseLong(amount) < 0)
        {
            player.sendMessage("§c" + amount + " ne doit pas être en négatif");
            return false;
        }

        return true;
    }

    public boolean havePermission(String permission)
    {
        if(!player.hasPermission(permission) && !player.isOp())
        {
            player.sendMessage("§cVous n'avez pas les permission");
            return false;
        }
        return true;
    }
}