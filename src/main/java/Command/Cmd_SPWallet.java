package Command;

import Command.Controller.SPWalletController;
import Entity.Wallet;
import SPWallet.SPWallet;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;

public class Cmd_SPWallet implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player)) return false;

        Player player = (Player) sender;
        Map<UUID, Wallet> walletList = SPWallet.getInstance().getWalletStore().getWalletList();
        SPWalletController spWalletController = new SPWalletController(player);

        if(args.length == 3)
        {
            Player target = Bukkit.getPlayer(args[1]);
            if(args[0].equalsIgnoreCase("add"))
            {
                if(!spWalletController.canAddMoney(target, args[2])) return false;
                walletList.get(target.getUniqueId()).add(Long.parseLong(args[2]));
            }

            if(args[0].equalsIgnoreCase("remove"))
            {
                if(!spWalletController.canRemoveMoney(target, args[2])) return false;
                walletList.get(target.getUniqueId()).remove(Long.parseLong(args[2]));
            }

            if(args[0].equalsIgnoreCase("set"))
            {
                if(!spWalletController.canSetMoney(target, args[2])) return false;
                walletList.get(target.getUniqueId()).set(Long.parseLong(args[2]));
            }
        }
        return false;
    }
}
