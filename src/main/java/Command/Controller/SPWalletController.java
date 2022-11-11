package Command.Controller;

import Controller.Controller;
import Model.Thread.UpdatePlayerWalletBalanceThread;
import SPWallet.SPWallet;
import org.bukkit.entity.Player;

public class SPWalletController  extends Controller {

    public SPWalletController(Player player) {
        super(player);
    }

    public boolean canAddMoney(Player target, String amount)
    {
        if(!this.existingTarget(target)) return false;
        if(!this.isLongFormat(amount)) return false;
        if(!player.hasPermission("sperias.wallet.command.spwallet") && !player.isOp())
        {
            player.sendMessage("§cVous n'avez pas accès à commande");
            return false;
        }

        player.sendMessage("§aVous avez rajouté " + Long.parseLong(amount) + SPWallet.getInstance().getConfig().getString("symbol") + " dans le wallet de " + target.getName());
        target.sendMessage("§a" + Long.parseLong(amount) + SPWallet.getInstance().getConfig().getString("symbol") + " ont été ajouté dans votre wallet");
        return true;
    }
    public boolean canRemoveMoney(Player target, String amount)
    {
        if(!this.existingTarget(target)) return false;
        if(!this.isLongFormat(amount)) return false;
        if(!player.hasPermission("sperias.wallet.command.spwallet") && !player.isOp())
        {
            player.sendMessage("§cVous n'avez pas accès à commande");
            return false;
        }

        if((playerWallet.getBalance() - Long.parseLong(amount)) < 0)
        {
            player.sendMessage("§cVous pouvez seulement lui retirer " + SPWallet.getInstance().getWalletStore().getWalletList().get(target.getUniqueId()).getBalance() + SPWallet.getInstance().getConfig().getString("symbol"));
            return false;
        }

        player.sendMessage("§aVous avez enlevé " + Long.parseLong(amount) + SPWallet.getInstance().getConfig().getString("symbol") + " à " + target.getName());
        target.sendMessage(player.getName() + " vous a retiré " + Long.parseLong(amount) + SPWallet.getInstance().getConfig().getString("symbol"));
        return true;
    }

    public boolean canSetMoney(Player target, String amount)
    {
        if(!this.existingTarget(target)) return false;
        if(!this.isLongFormat(amount)) return false;
        if(!player.hasPermission("sperias.wallet.command.spwallet") && !player.isOp())
        {
            player.sendMessage("§cVous n'avez pas accès à commande");
            return false;
        }

        player.sendMessage("§a" + target.getName() + " à maintenant " + Long.parseLong(amount) + SPWallet.getInstance().getConfig().getString("symbol"));
        target.sendMessage("§a Vous avez maintenant " + Long.parseLong(amount) + SPWallet.getInstance().getConfig().getString("symbol"));
        return true;
    }
}
