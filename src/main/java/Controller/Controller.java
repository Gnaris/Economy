package Controller;

import Entity.Wallet;
import SPWallet.SPWallet;
import org.bukkit.entity.Player;

public abstract class Controller{

    protected Player player;
    protected Wallet playerWallet;

    public Controller(Player player)
    {
        this.player = player;
        this.playerWallet = SPWallet.getInstance().getWalletStore().getWalletList().get(player.getUniqueId());
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

        return true;
    }
}