package Entity;

import Model.EconomyModel;
import java.util.UUID;

public class Economy {

    private final UUID owner;
    private long money;
    private boolean visible;

    public Economy(UUID owner, long money, boolean visible) {
        this.owner = owner;
        this.money = money;
        this.visible = visible;
    }

    public void add(long amount)
    {
        this.money += amount;
        new EconomyModel().updateMoney(owner, this.money);
    }
    public void remove(long amount)
    {
        this.money -= amount;
        new EconomyModel().updateMoney(owner, this.money);
    }
    public void set(long amount)
    {
        this.money = amount;
        new EconomyModel().updateMoney(owner, this.money);
    }

    public UUID getOwner() {
        return this.owner;
    }
    public long getMoney() {
        return money;
    }
    public boolean isVisible() {
        return visible;
    }
    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
