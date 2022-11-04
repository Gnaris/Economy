package Controller;

import SPWallet.SPWallet;
import org.bukkit.entity.Player;
import sperias.group.Controller.C_Group;
import sperias.group.Entity.PlayerGroup;

public class C_Wallet extends WalletController {

    PlayerGroup PlayerGroup;
    C_Group CGroup;

    public C_Wallet(Player player)
    {
        super(player);
        PlayerGroup = SPWallet.getGroupStore().getPlayerGroupList().get(player.getUniqueId());
        CGroup = new C_Group(player);
    }


    public boolean existingTarget(Player target)
    {
        if(target == null)
        {
            player.sendMessage("§cCe joueur n'existe pas ou n'est pas connecté");
            return false;
        }

        return true;
    }

    public boolean canWatchWallet(Player who)
    {
        if(!SPWallet.getInstance().getWalletStore().getWalletList().get(who.getUniqueId()).isVisible())
        {
            player.sendMessage("§cVous n'avez pas l'autorisation de voir sa bourse");
            return false;
        }

        return true;
    }

    public boolean canSetVisible(String[] args)
    {
        if(!args[0].equalsIgnoreCase("visible"))
        {
            player.sendMessage("§cMauvaise commande peut-être ?");
            return false;
        }
        if(!args[1].equalsIgnoreCase("on") && !args[1].equalsIgnoreCase("off"))
        {
            player.sendMessage("§cON = Oui je veux montrer publiquement || §c§lOFF = Non je veux cacher ma bourse");
            return false;
        }

        if(args[1].equalsIgnoreCase("on")) player.sendMessage("§aTout le monde pourra voir votre bourse :D");
        if(args[1].equalsIgnoreCase("off")) player.sendMessage("§cPlus personne pourra voir votre bourse :D");
        return true;
    }

    public boolean canPay(Player target, int amount)
    {
        if(target.getUniqueId() == player.getUniqueId())
        {
            player.sendMessage("§cEt puis quoi encore ? C'est bien tenté mais non ☻");
            return false;
        }
        int caller_amount = SPWallet.getInstance().getWalletStore().getWalletList().get(player.getUniqueId()).getMoney();
        if(caller_amount - amount < 0)
        {
            player.sendMessage("§cComment tu veux payer " + amount + "$ à " + target.getName() + " alors que tu as " + this.PlayerWallet.getMoney() + "$ ?");
            return false;
        }

        player.sendMessage("§aVous avez envoyé à " + target.getName() + " " + amount + "$");
        target.sendMessage("§aVous avez reçu " + amount + "$ de la part de " + player.getName());
        return true;
    }

    public boolean canAddMoney(Player target, int amount)
    {
        if(!CGroup.hasPermission("sperias.wallet.command.add") && !player.isOp())
        {
            player.sendMessage("§cVous n'avez pas accès à commande");
            return false;
        }

        player.sendMessage("§aVous avez rajouté " + amount + "$ dans le wallet de " + target.getName());
        target.sendMessage("§a" + amount + "$ ont été rajouté dans votre wallet");
        return true;
    }
    public boolean canRemoveMoney(Player target, int amount)
    {
        if(!CGroup.hasPermission("sperias.wallet.command.remove") && !player.isOp())
        {
            player.sendMessage("§cVous n'avez pas accès à commande");
            return false;
        }
        if(PlayerWallet.getMoney() - amount < 0)
        {
            player.sendMessage("§cVous pouvez seulement lui retirer " + SPWallet.getInstance().getWalletStore().getWalletList().get(target.getUniqueId()).getMoney() + "$");
            return false;
        }

        player.sendMessage("§aVous avez enlevé " + amount + "$ à " + target.getName());
        target.sendMessage(player.getName() + " vous a retiré " + amount + "$");
        return true;
    }
    public boolean canSetMoney(Player target, int amount)
    {
        if(!CGroup.hasPermission("sperias.wallet.command.set") && !player.isOp())
        {
            player.sendMessage("§cVous n'avez pas accès à commande");
            return false;
        }

        player.sendMessage("§a" + target.getName() + " à maintenant " + amount + "$");
        target.sendMessage("§a Vous avez maintenant " + amount + "$");
        return true;
    }
}