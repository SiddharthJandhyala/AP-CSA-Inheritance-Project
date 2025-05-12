public class Weapon extends Item {
    private int ATK;

    public Weapon(String name, String description, int ATK) {
        super(name, description);
        this.ATK = ATK;
    }

    public int getATK() {
        return this.ATK;
    }
}
