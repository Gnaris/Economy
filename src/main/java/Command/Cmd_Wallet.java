package Command;

import Command.Controller.WalletController;
import Entity.Wallet;
import SPWallet.SPWallet;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Cmd_Wallet implements CommandExecutor {

    private SPWallet plugin;

    public Cmd_Wallet(SPWallet plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

            if(!(sender instanceof Player)) return false;

            Player player = (Player) sender;
            Wallet playerWallet = plugin.getWalletStore().get(player.getUniqueId());
            WalletController walletController = new WalletController(player, plugin);

            if(args.length == 0)
            {
                return walletController.canGetBalance();
            }

            if(args.length == 1)
            {
                Player target = Bukkit.getPlayer(args[0]);
                if(!walletController.canWatchWallet(target)) return false;
            }

            if(args.length == 2)
            {
                if(args[0].equalsIgnoreCase("setvisible"))
                {
                    if(!walletController.canSetVisible(args[1])) return false;
                    if(args[1].equalsIgnoreCase("on"))
                    {
                        playerWallet.setVisible(true);
                    }
                    if(args[1].equalsIgnoreCase("off"))
                    {
                        playerWallet.setVisible(false);
                    }
                    return true;
                }
            }

            if(args.length == 3)
            {
                Player target = Bukkit.getPlayer(args[1]);
                if(args[0].equalsIgnoreCase("pay"))
                {
                    if(!walletController.canPay(target, args[2])) return false;
                    long balance = Long.parseLong(args[2]);
                    playerWallet.remove(balance);
                    plugin.getWalletStore().get(target.getUniqueId()).add(balance);
                }
            }

            return true;
    }
}
