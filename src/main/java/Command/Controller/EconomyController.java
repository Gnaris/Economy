package Command.Controller;

import Controller.Controller;
import Economy.EconomyPlugin;
import org.bukkit.entity.Player;

public class EconomyController extends Controller {


    public EconomyController(Player player, EconomyPlugin plugin) {
        super(player, plugin);
    }

    public boolean canAddMoney(Player target, String amount)
    {
        if(!this.existingTarget(target)) return false;
        return this.isGoodFormat(amount);
    }
    public boolean canRemoveMoney(Player target, String amount)
    {
        if(!this.existingTarget(target)) return false;
        if(!this.isGoodFormat(amount)) return false;
        if((plugin.getEconomies().get(target.getUniqueId()).getMoney() - Long.parseLong(amount)) < 0)
        {
            player.sendMessage("§cVous pouvez seulement lui retirer " + plugin.getEconomies().get(target.getUniqueId()).getMoney() + plugin.getCurrency());
            return false;
        }
        return true;
    }

    public boolean canSetMoney(Player target, String amount)
    {
        if(!this.existingTarget(target)) return false;
        return this.isGoodFormat(amount);
    }
}
