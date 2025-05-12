public class Enemy extends NPC {
    double currentHealth;
    double totalHealth;
    double attack;
    double defense;
    double runChance;
    double decision;
    String attackDialogue;
    String[] taunts;
    double healing;

    public Enemy(String name, int health, int attack, int defense, double runChance, double decision, String attackDialogue, String[] taunts, double healing) {
        super(name);
        this.currentHealth = (double)health;
        this.totalHealth = (double)health;
        this.attack = (double)attack;
        this.defense = (double)defense;
        this.runChance = runChance;
        this.decision = decision;
        this.attackDialogue = attackDialogue;
        this.taunts = taunts;
        this.healing = healing;
    }

    public double[] getHealth() {
        return new double[]{this.currentHealth, this.totalHealth};
    }

    public String getAttackDialogue() {
        return this.attackDialogue;
    }

    public String[] getTaunts() {
        return this.taunts;
    }

    public boolean tryRun() {
        return Math.random() < this.runChance;
    }

    public void setHealth(double currentHealth) {
        this.currentHealth = currentHealth;
    }

    public double getDecision() {
        return this.decision;
    }

    public double getAttack() {
        return this.attack;
    }

    public double getHealing() {
        return this.healing;
    }
}
