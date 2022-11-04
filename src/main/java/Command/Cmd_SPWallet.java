package Command;

import Controller.C_Wallet;
import Entity.Wallet;
import SPWallet.SPWallet;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class Cmd_SPWallet implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player)) return false;

        Player player = (Player) sender;
        C_Wallet CWallet = new C_Wallet(player);

        switch (args.length)
        {
            case 3 :
            {
                Player target = Bukkit.getPlayer(args[1]);
                if(!CWallet.existingTarget(target)) return false;
                Wallet TargetWallet = SPWallet.getInstance().getWalletStore().getWalletList().get(target.getUniqueId());
                    int amount;
                    try {
                        amount = Integer.parseInt(args[2]);
                    }catch (NumberFormatException e)
                    {
                        player.sendMessage("§c" + args[2] + " n'est pas un montant valide");
                        return false;
                    }
                    if(amount < 0)
                    {
                        player.sendMessage("§cLe montant doit être positif");
                        return false;
                    }
                        switch(args[0].toLowerCase())
                        {
                            case "add" :
                            {
                                if(!CWallet.canAddMoney(target, amount)) return false;
                                SPWallet.getInstance().getWalletStore().getWalletList().get(target.getUniqueId()).add(amount);
                                break;
                            }
                            case "remove" :
                            {
                                if(!CWallet.canRemoveMoney(target, amount)) return false;
                                SPWallet.getInstance().getWalletStore().getWalletList().get(target.getUniqueId()).remove(amount);
                                break;
                            }
                            case "set" :
                            {
                                if(!CWallet.canSetMoney(target, amount)) return false;
                                SPWallet.getInstance().getWalletStore().getWalletList().get(target.getUniqueId()).set(amount);
                                break;
                            }
                            default:
                            {
                                player.sendMessage("§cMauvaise commande peut-être ?");
                                return false;
                            }
                        }
                break;
            }
        }
        return false;
    }
}
