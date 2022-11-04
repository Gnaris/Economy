package Entity;

import Model.Thread.UpdatePlayerWalletThread;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;

public class Wallet {

    private final Player owner;
    private int money;
    private boolean visible;

    public Wallet(Player owner, int money, boolean visible) {
        this.owner = owner;
        this.money = money;
        this.visible = visible;
    }

    public void countMoney()
    {
        this.owner.sendMessage(ChatColor.of("#C5D2DC") + "Votre bourse : " + this.money + "$");
    }

    public void showMoney(Player whoever)
    {
        whoever.sendMessage(ChatColor.of("#C5D2DC") + "Bourse de " + this.owner.getName() + " : " + this.money + "$");
    }

    public void add(int amount)
    {
        this.money += amount;
        Thread UpdatePlayerWallet = new Thread(new UpdatePlayerWalletThread(this.owner));
        UpdatePlayerWallet.start();
    }
    public void remove(int amount)
    {
        this.money -= amount;
        Thread UpdatePlayerWallet = new Thread(new UpdatePlayerWalletThread(this.owner));
        UpdatePlayerWallet.start();
    }
    public void set(int amount)
    {
        this.money = amount;
        Thread UpdatePlayerWallet = new Thread(new UpdatePlayerWalletThread(this.owner));
        UpdatePlayerWallet.start();
    }


    public Player getOwner() {
        return this.owner;
    }
    public int getMoney() {
        return money;
    }
    public boolean isVisible() {
        return visible;
    }
    public void setVisible(String arg) {
        if(arg.equalsIgnoreCase("on")) this.visible = true;
        if(arg.equalsIgnoreCase("off")) this.visible = false;
    }
}
