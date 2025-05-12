class Dialogue {
    private String color;
    private String dialogue;
    private int speed;
    private int endDelay;
    private int command;
    private final String RESET = "\u001b[0m";
    private final String BLACK = "\u001b[30m";
    private final String RED = "\u001b[31m";
    private final String GREEN = "\u001b[32m";
    private final String YELLOW = "\u001b[33m";
    private final String BLUE = "\u001b[34m";
    private final String PURPLE = "\u001b[35m";
    private final String CYAN = "\u001b[36m";
    private final String GREY = "\u001b[37m";
    private final String WHITE = "";

    public Dialogue(String dialogue) {
        this.dialogue = dialogue;
        this.speed = 20;
        this.color = "";
        this.endDelay = 0;
        this.command = 0;
    }

    public void speak() {
        this.command = 0;
        System.out.print("\u001b[0m");
        ArrayList<Integer> openBrack = new ArrayList();
        ArrayList<Integer> closeBrack = new ArrayList();

        for(int i = 0; i < this.dialogue.length(); ++i) {
            if (this.dialogue.charAt(i) == '[') {
                openBrack.add(i);
            } else if (this.dialogue.charAt(i) == ']') {
                closeBrack.add(i);
            }
        }

        for(int i = 0; i < this.dialogue.length(); ++i) {
            if (i >= (Integer)openBrack.get(this.command) && i <= (Integer)closeBrack.get(this.command)) {
                String temp = this.dialogue.substring((Integer)openBrack.get(this.command) + 1, (Integer)closeBrack.get(this.command));
                if (temp.charAt(0) == 'c') {
                    switch (temp.substring(1)) {
                        case "WHITE" -> this.color = "";
                        case "RED" -> this.color = "\u001b[31m";
                        case "YELLOW" -> this.color = "\u001b[33m";
                        case "GREY" -> this.color = "\u001b[37m";
                        case "CYAN" -> this.color = "\u001b[36m";
                        case "GREEN" -> this.color = "\u001b[32m";
                    }
                } else if (temp.charAt(0) == 's') {
                    this.speed = Integer.parseInt(temp.substring(1));
                } else if (temp.charAt(0) == 'w') {
                    this.endDelay = Integer.parseInt(temp.substring(1));
                }

                i += (Integer)closeBrack.get(this.command) - (Integer)openBrack.get(this.command);
                if (this.command < openBrack.size() - 1) {
                    ++this.command;
                }
            } else {
                String var10001 = this.color;
                System.out.print(var10001 + this.dialogue.charAt(i) + "\u001b[0m");

                try {
                    Thread.sleep((long)this.speed);
                } catch (InterruptedException var8) {
                    Thread.currentThread().interrupt();
                }
            }
        }

        System.out.print("\u001b[0m");

        try {
            Thread.sleep((long)this.endDelay);
        } catch (InterruptedException var7) {
            Thread.currentThread().interrupt();
        }

    }
}
