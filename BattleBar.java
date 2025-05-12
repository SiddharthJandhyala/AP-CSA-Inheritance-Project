public class BattleBar extends Dialogue {
    private int len;

    public BattleBar(int length) {
        super("[s1][cGREY]+~");
        this.len = length;
    }

    public void speak() {
        for(int i = 0; i < this.len; ++i) {
            super.speak();
        }

        (new Dialogue("[cGREY][s1]+")).speak();
    }
}
