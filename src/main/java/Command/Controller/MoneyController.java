package Command.Controller;

import Controller.Controller;
import Entity.Economy;
import Economy.EconomyPlugin;
import org.bukkit.entity.Player;

public class MoneyController extends Controller {


    public MoneyController(Player player, EconomyPlugin plugin) {
        super(player, plugin);
    }

    public boolean canWatchWallet(Player target)
    {
        if(!this.existingTarget(target)) return false;
        Economy targetEconomy = plugin.getEconomies().get(target.getUniqueId());
        if(!targetEconomy.isVisible())
        {
            player.sendMessage("§cVous n'avez pas l'autorisation de voir son argent");
            return false;
        }

        return true;
    }

    public boolean canSetVisible(String value)
    {
        if(!value.equalsIgnoreCase("on") && !value.equalsIgnoreCase("off"))
        {
            player.sendMessage("§cON ou OFF");
            return false;
        }
        return true;
    }
}
