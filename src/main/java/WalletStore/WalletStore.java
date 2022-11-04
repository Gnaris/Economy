package WalletStore;

import Entity.Wallet;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class WalletStore {

    private final Map<UUID, Wallet> WalletList = new HashMap<>();

    public Map<UUID, Wallet> getWalletList() {
        return WalletList;
    }
}
