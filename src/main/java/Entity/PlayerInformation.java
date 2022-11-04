package Entity;

public class PlayerInformation {

    private final int id;
    private final String uuid;
    private final String dateArrivee;

    public PlayerInformation(int id, String uuid, String dateArrivee) {
        this.id = id;
        this.uuid = uuid;
        this.dateArrivee = dateArrivee;
    }

    public int getId() {
        return id;
    }

    public String getUuid() {
        return uuid;
    }

    public String getDateArrivee() {
        return dateArrivee;
    }
}
