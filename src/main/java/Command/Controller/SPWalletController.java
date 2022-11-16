package Command.Controller;

import Controller.Controller;
import Model.Thread.UpdatePlayerWalletBalanceThread;
import SPWallet.SPWallet;
import org.bukkit.entity.Player;

public class SPWalletController  extends Controller {


    public SPWalletController(Player player, SPWallet plugin) {
        super(player, plugin);
    }

    public boolean canAddMoney(Player target, String amount)
    {
        if(!this.existingTarget(target)) return false;
        return this.isLongFormat(amount);
    }
    public boolean canRemoveMoney(Player target, String amount)
    {
        if(!this.existingTarget(target)) return false;
        if(!this.isLongFormat(amount)) return false;
        if((playerWallet.getBalance() - Long.parseLong(amount)) < 0)
        {
            player.sendMessage("§cVous pouvez seulement lui retirer " + plugin.getWalletStore().get(target.getUniqueId()).getBalance() + config.getString("symbol"));
            return false;
        }
        return true;
    }

    public boolean canSetMoney(Player target, String amount)
    {
        if(!this.existingTarget(target)) return false;
        if(!this.isLongFormat(amount)) return false;

        return true;
    }
}
