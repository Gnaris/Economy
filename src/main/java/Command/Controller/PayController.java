package Command.Controller;

import Controller.Controller;
import Economy.EconomyPlugin;
import org.bukkit.entity.Player;

public class PayController extends Controller {

    public PayController(Player player, EconomyPlugin plugin) {
        super(player, plugin);
    }

    public boolean canPay(Player target, String amount)
    {
        if(!this.existingTarget(target)) return false;
        if(!this.isGoodFormat(amount)) return false;
        if(target.getUniqueId() == player.getUniqueId())
        {
            player.sendMessage("§c^o^");
            return false;
        }

        if((plugin.getEconomies().get(player.getUniqueId()).getMoney() - Long.parseLong(amount)) < 0)
        {
            player.sendMessage("§cVous n'avez pas assez pour payer " + target.getName() + ". Il vous manque " + (Long.parseLong(amount) - plugin.getEconomies().get(player.getUniqueId()).getMoney()) + plugin.getCurrency());
            return false;
        }

        return true;
    }
}
