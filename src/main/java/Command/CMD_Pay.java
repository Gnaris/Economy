package Command;

import Command.Controller.PayController;
import Economy.EconomyAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMD_Pay implements CommandExecutor {

    private EconomyAPI plugin;

    public CMD_Pay(EconomyAPI plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player)) return false;
        Player player = (Player) sender;
        PayController payController = new PayController(player, plugin);

        if(args.length == 3)
        {
            Player target = Bukkit.getPlayer(args[1]);
            if(!payController.canPay(target, args[2])) return false;
            long amount = Long.parseLong(args[2]);
            plugin.getEconomies().get(player.getUniqueId()).remove(amount);
            plugin.getEconomies().get(target.getUniqueId()).add(amount);

            player.sendMessage("§aVous avez envoyé à " + target.getName() + " " + amount + plugin.getCurrency());
            target.sendMessage("§aVous avez reçu " + amount + plugin.getCurrency() + " de la part de " + player.getName());
            return true;
        }
        return false;
    }
}
