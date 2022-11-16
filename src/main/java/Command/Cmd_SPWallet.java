package Command;

import Command.Controller.SPWalletController;
import SPWallet.SPWallet;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class Cmd_SPWallet implements CommandExecutor {

    private SPWallet plugin;
    private FileConfiguration config;

    public Cmd_SPWallet(SPWallet plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfig();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player)) return false;

        Player player = (Player) sender;
        SPWalletController spWalletController = new SPWalletController(player, plugin);
        if(!spWalletController.havePermission("sperias.wallet.command.spwallet")) return false;

        if(args.length == 3)
        {
            Player target = Bukkit.getPlayer(args[1]);
            String amount = args[2];
            if(args[0].equalsIgnoreCase("add"))
            {
                if(!spWalletController.canAddMoney(target, amount)) return false;

                plugin.getWalletStore().get(target.getUniqueId()).add(Long.parseLong(args[2]));
                player.sendMessage("§aVous avez rajouté " + Long.parseLong(amount) + config.getString("symbol") + " dans le wallet de " + target.getName());
                target.sendMessage("§a" + Long.parseLong(amount) + config.getString("symbol") + " ont été ajouté dans votre wallet");
                return true;
            }

            if(args[0].equalsIgnoreCase("remove"))
            {
                if(!spWalletController.canRemoveMoney(target, amount)) return false;

                plugin.getWalletStore().get(target.getUniqueId()).remove(Long.parseLong(args[2]));
                player.sendMessage("§aVous avez enlevé " + Long.parseLong(amount) + config.getString("symbol") + " à " + target.getName());
                target.sendMessage(player.getName() + " vous a retiré " + Long.parseLong(amount) + config.getString("symbol"));
                return true;
            }

            if(args[0].equalsIgnoreCase("set"))
            {
                if(!spWalletController.canSetMoney(target, amount)) return false;

                plugin.getWalletStore().get(target.getUniqueId()).set(Long.parseLong(args[2]));
                player.sendMessage("§a" + target.getName() + " à maintenant " + Long.parseLong(amount) + config.getString("symbol"));
                target.sendMessage("§aVous avez maintenant " + Long.parseLong(amount) + config.getString("symbol"));
                return true;
            }
        }

        return false;
    }
}
