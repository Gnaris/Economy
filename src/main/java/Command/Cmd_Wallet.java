package Command;

import Controller.C_Wallet;
import Entity.Wallet;
import Model.Thread.UpdatePlayerWalletThread;
import SPWallet.SPWallet;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Cmd_Wallet implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

            if(!(sender instanceof Player)) return false;

            Player player = (Player) sender;
            Wallet PlayerWallet = SPWallet.getInstance().getWalletStore().getWalletList().get(player.getUniqueId());
            C_Wallet CWallet = new C_Wallet(player);
            switch (args.length)
            {
                    // Show money
                    case 0 :
                    {
                        PlayerWallet.countMoney();
                        break;
                    }
                    // Show target money
                    case 1 :
                    {
                        Player target = Bukkit.getPlayer(args[0]);
                        if(CWallet.existingTarget(target)) return false;
                        if(!CWallet.canWatchWallet(target)) return false;
                        Wallet TargetWallet = SPWallet.getInstance().getWalletStore().getWalletList().get(target.getUniqueId());
                        SPWallet.getInstance().getWalletStore().getWalletList().get(target.getUniqueId()).showMoney(player);
                        break;
                    }
                    // Set Visible Wallet
                    case 2 :
                    {
                        if(!CWallet.canSetVisible(args)) return false;
                        SPWallet.getInstance().getWalletStore().getWalletList().get(player.getUniqueId()).setVisible(args[1]);
                        break;
                    }
                    case 3 :
                    {
                        Player target = Bukkit.getPlayer(args[1]);
                        if(CWallet.existingTarget(target)) return false;
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
                        switch (args[0].toLowerCase())
                        {
                            case "pay" :
                            {
                                if(!CWallet.canPay(target, amount)) return false;
                                Wallet TargetWallet = SPWallet.getInstance().getWalletStore().getWalletList().get(target.getUniqueId());
                                PlayerWallet.remove(amount);
                                TargetWallet.add(amount);
                                break;
                            }
                            default:
                            {
                                if(args[0].equalsIgnoreCase("pay"))
                                {
                                    player.sendMessage("/wallet pay {nom du joueur}");
                                    break;
                                }
                            }
                        }
                        }
                    default:
                    {
                        player.sendMessage("§cMauvaise commande peut-être ?");
                        return false;
                    }
            }
            return true;
    }
}
