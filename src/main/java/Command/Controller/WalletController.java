package Command.Controller;

import Controller.Controller;
import Entity.Wallet;
import Model.Thread.UpdatePlayerWalletVisibleThread;
import SPWallet.SPWallet;
import org.bukkit.entity.Player;

public class WalletController  extends Controller {


    public WalletController(Player player, SPWallet plugin) {
        super(player, plugin);
    }

    public boolean canGetBalance()
    {
        player.sendMessage("Bourse : " + playerWallet.getBalance() + config.getString("symbol"));
        return true;
    }

    public boolean canWatchWallet(Player target)
    {
        if(!this.existingTarget(target)) return false;
        Wallet targetWallet = plugin.getWalletStore().get(target.getUniqueId());
        if(!targetWallet.isVisible())
        {
            player.sendMessage("§cVous n'avez pas l'autorisation de voir sa bourse");
            return false;
        }

        player.sendMessage("Bourse de " + target.getName() + " : " + targetWallet.getBalance() + config.getString("symbol"));
        return true;
    }

    public boolean canSetVisible(String value)
    {
        if(!value.equalsIgnoreCase("on") && !value.equalsIgnoreCase("off"))
        {
            player.sendMessage("§cON ou OFF");
            return false;
        }
        if(value.equalsIgnoreCase("on"))
        {
            player.sendMessage("§aTout le monde pourra voir votre bourse :D");
        }
        if(value.equalsIgnoreCase("off"))
        {
            player.sendMessage("§cPlus personne pourra voir votre bourse :D");
        }
        return true;
    }

    public boolean canPay(Player target, String amount)
    {
        if(!this.existingTarget(target)) return false;
        if(!this.isLongFormat(amount)) return false;
        if(target.getUniqueId() == player.getUniqueId())
        {
            player.sendMessage("§c^o^");
            return false;
        }

        if((playerWallet.getBalance() - Long.parseLong(amount)) < 0)
        {
            player.sendMessage("§cComment tu veux payer " + amount + config.getString("symbol") + " à " + target.getName() + " alors que tu as " + playerWallet.getBalance() + config.getString("symbol") + " ?");
            return false;
        }

        player.sendMessage("§aVous avez envoyé à " + target.getName() + " " + Long.parseLong(amount) + config.getString("symbol") + "$");
        target.sendMessage("§aVous avez reçu " + Long.parseLong(amount) + config.getString("symbol") + " de la part de " + player.getName());
        return true;
    }
}
