package Command;

import Command.Controller.EconomyController;
import Economy.EconomyAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Cmd_Economy implements CommandExecutor {

    private EconomyAPI plugin;

    public Cmd_Economy(EconomyAPI plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player)) return false;

        Player player = (Player) sender;
        EconomyController economyController = new EconomyController(player, plugin);
        if(!economyController.havePermission("sperias.wallet.command.economy")) return false;

        if(args.length == 3)
        {
            Player target = Bukkit.getPlayer(args[1]);
            String amount = args[2];
            if(args[0].equalsIgnoreCase("add"))
            {
                if(!economyController.canAddMoney(target, amount)) return false;

                plugin.getEconomies().get(target.getUniqueId()).add(Long.parseLong(args[2]));
                player.sendMessage("§aVous avez fabriqué " + amount + plugin.getCurrency() + " pour " + target.getName());
                target.sendMessage("§a" + Long.parseLong(amount) + plugin.getCurrency() + " ont été ajouté dans votre compte");
                return true;
            }

            if(args[0].equalsIgnoreCase("remove"))
            {
                if(!economyController.canRemoveMoney(target, amount)) return false;

                plugin.getEconomies().get(target.getUniqueId()).remove(Long.parseLong(args[2]));
                player.sendMessage("§aVous avez enlevé " + Long.parseLong(amount) + plugin.getCurrency() + " dans le compte de " + target.getName());
                target.sendMessage("§a" + player.getName() + " vous a piqué " + Long.parseLong(amount) + plugin.getCurrency() + " dans votre compte");
                return true;
            }

            if(args[0].equalsIgnoreCase("set"))
            {
                if(!economyController.canSetMoney(target, amount)) return false;

                plugin.getEconomies().get(target.getUniqueId()).set(Long.parseLong(args[2]));
                player.sendMessage("§a" + target.getName() + " a maintenant " + Long.parseLong(amount) + plugin.getCurrency() + " dans son compte");
                target.sendMessage("§aVous avez maintenant " + Long.parseLong(amount) + plugin.getCurrency() + " dans votre compte");
                return true;
            }
        }

        return false;
    }
}
