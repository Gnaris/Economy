package Entity;

import Model.Thread.UpdatePlayerWalletBalanceThread;
import org.bukkit.entity.Player;

public class Wallet {

    private final Player owner;
    private long balance;
    private boolean visible;

    public Wallet(Player owner, long money, boolean visible) {
        this.owner = owner;
        this.balance = money;
        this.visible = visible;
    }

    public void add(long amount)
    {
        this.balance += amount;
        new Thread(new UpdatePlayerWalletBalanceThread(owner)).start();
    }
    public void remove(long amount)
    {
        this.balance -= amount;
        new Thread(new UpdatePlayerWalletBalanceThread(owner)).start();
    }
    public void set(long amount)
    {
        this.balance = amount;
        new Thread(new UpdatePlayerWalletBalanceThread(owner)).start();
    }


    public Player getOwner() {
        return this.owner;
    }
    public long getBalance() {
        return balance;
    }
    public boolean isVisible() {
        return visible;
    }
    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
