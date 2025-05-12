public class Move {
    private String name;
    private double damage;
    private double boost;

    public Move(String name, double damage, double boost) {
        this.name = name;
        this.damage = damage;
        this.boost = boost;
    }

    public String getName() {
        return this.name;
    }

    public double[] getStats() {
        return new double[]{this.damage, this.boost};
    }
}
