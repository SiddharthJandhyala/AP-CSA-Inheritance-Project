public class Consumable extends Item {
    private int HP;
    private int ATK;
    private int DEF;
    private double atkMod;
    private double defMod;

    public Consumable(String name, String description, int HP, int ATK, int DEF, double atkMod, double defMod) {
        super(name, description);
        this.HP = HP;
        this.ATK = ATK;
        this.DEF = DEF;
        this.atkMod = atkMod;
        this.defMod = defMod;
    }

    public String getDescription() {
        return "Item description: “" + super.getDescription() + "”";
    }

    public int getHP() {
        return this.HP;
    }

    public int getATK() {
        return this.ATK;
    }

    public int getDEF() {
        return this.DEF;
    }

    public double getAtkMod() {
        return this.atkMod;
    }

    public double getDefMod() {
        return this.defMod;
    }
}
