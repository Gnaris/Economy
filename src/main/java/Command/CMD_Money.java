package Command;

import Command.Controller.MoneyController;
import Economy.EconomyPlugin;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMD_Money implements CommandExecutor {

    private EconomyPlugin plugin;

    public CMD_Money(EconomyPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

            if(!(sender instanceof Player)) return false;

            Player player = (Player) sender;
            MoneyController moneyController = new MoneyController(player, plugin);

            if(args.length == 0)
            {
                player.sendMessage("§aVous avez : " + plugin.getEconomies().get(player.getUniqueId()).getMoney() + plugin.getCurrency());
                return true;
            }

            if(args.length == 1)
            {
                Player target = Bukkit.getPlayer(args[0]);
                if(!moneyController.canWatchWallet(target)) return false;

                player.sendMessage("Argent de " + target.getName() + " : " + plugin.getEconomies().get(target.getUniqueId()) + plugin.getCurrency());
            }

            if(args.length == 2)
            {
                if(args[0].equalsIgnoreCase("setvisible"))
                {
                    if(!moneyController.canSetVisible(args[1])) return false;
                    if(args[1].equalsIgnoreCase("on"))
                    {
                        player.sendMessage("§aTout le monde pourra voir votre bourse :D");
                        plugin.getEconomies().get(player.getUniqueId()).setVisible(true);
                    }
                    if(args[1].equalsIgnoreCase("off"))
                    {
                        player.sendMessage("§cPlus personne pourra voir votre bourse :D");
                        plugin.getEconomies().get(player.getUniqueId()).setVisible(false);
                    }
                    return true;
                }
            }
            return true;
    }
}
