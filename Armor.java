public class Armor extends Item {
    private int DEF;

    public Armor(String name, String description, int DEF) {
        super(name, description);
        this.DEF = DEF;
    }

    public int getDEF() {
        return this.DEF;
    }
}
