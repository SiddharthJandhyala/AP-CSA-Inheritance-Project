import java.util.ArrayList;
import java.util.Scanner;
import java.lang.Math;

public class Main {
    public static final String RESET = "\u001B[0m";
    public static final String BLACK = "\u001B[30m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String GREY = "\u001B[37m";
    public static final String WHITE = "";

    static Consumable donut = new Consumable("Pizza","From Dominoes", 4, 0, 0, 1, 1);
    static Consumable lasagna = new Consumable("Chocolate","Nice and sweet", 5, 4, 0, 1.5, 1);
    static Consumable seaurchin = new Consumable("Spicy McChicken","The epitome of American consumerism", 7, 6, 7, 1.7, 1.9);
    static Weapon stick = new Weapon("stick","from outside", 3);
    static Armor shirt = new Armor("shirt","name one nirvana song", 5);
    static Move punch = new Move("Punch", 15, 1);
    static Move focus = new Move("Meditation", 0, 2);
    static Move chargedPunch = new Move("Fire Punch", 12, 1.2);

    public static double currentHealth = 100;
    public static double totalHealth = 100;
    public static double currentAttack = 0;
    public static double totalAttack = 10;
    public static double currentDefense = 10;
    public static double totalDefense = 10;
    public static Consumable[] consumables = {donut, lasagna, seaurchin};
    public static Weapon currentWeapon = stick;
    public static Armor currentArmor = shirt;
    public static Move[] moves = {punch, null, focus, chargedPunch};
    //item[0] - Training Sword, item[1] - Pen
    static Scanner input = new Scanner(System.in);
    static int score = 0;
    static boolean coward = false;

    public static void main(String[] args) {
        Enemy[] es = new Enemy[6];
        Enemy Dragon = new Enemy("Dragon",200,10,10, 0.3, 0.5, "[cWHITE][s5]Dragon clawed at you!", new String[] {"[cWHITE][s5]“No human has ever survived my lair...”", "[cWHITE][s5]“Ready to be burnt to a crisp?”", "[cWHITE][s5]“*agressive snarling*”"}, 6);
        Enemy Zombie = new Enemy("Zombie",40,4,4, 0.8, 0.9, "[cWHITE][s5]Zombie lunged at you!", new String[] {"[cWHITE][s5]“bRaInZzZzZ...”", "[cWHITE][s5]“blegh...”", "[cWHITE][s5]“*groaning noises*”"}, 6);
        Enemy Witch = new Enemy("Witch",90,10,6, 0.4, 0.8, "[cWHITE][s5]Witch hexed you!", new String[] {"[cWHITE][s5]“Prepare to face the wrath of the orb!”", "[cWHITE][s5]“Always pack a healing potion on you!”", "[cWHITE][s5]“My wand will make quick work of you!”"}, 10);
        Enemy Slime = new Enemy("Slime",20,6,2, 0.2, 0.9, "[cWHITE][s5]Slime bounced at you!", new String[] {"[cWHITE][s5]“*gelatinous noises*”", "[cWHITE][s5]“*gurgling noises*”", "[cWHITE][s5]“*concerning squishing sounds*”"}, 5);
        Enemy Knight = new Enemy("Knight",70,10,10, 0.1, 0.95, "[cWHITE][s5]Knight attacked you!", new String[] {"[cWHITE][s5]“I'm not actually empl*yed by the king.”", "[cWHITE][s5]“I fight for honor (gold)!”", "[cWHITE][s5]“It's scary how sweaty I get in this suit of armor.”"}, 8);
        Enemy CollegeBoard = new Enemy("CollegeBoard",200,5,1, 0.05, 0.8, "[cWHITE][s5]CollegeBoard crushed your spirit!", new String[] {"[cWHITE][s5]“I'm the worst company in America!”", "[cWHITE][s5]“If you think this fight is hard try that one ASCII MCQ.”", "[cWHITE][s5]“If you beat me I'm giving you a 2!”"}, 20);

        es[0] = Dragon; es[1] = Zombie; es[2] = Witch; es[3] = Slime; es[4] = Knight; es[5] = CollegeBoard;

        String name="";
        new Dialogue("[cYELLOW][s5]Please state your name: ").speak();
        name = input.nextLine();
        new Dialogue("[cYELLOW][s5]Welcome, [cGREEN]"+name+".\n").speak();
        new Dialogue("[cYELLOW]At times in the game, you will be given options, " +
                "type the corresponding number to choose that option.[s700]\n").speak();
        System.out.println();
        new Dialogue("[cYELLOW]You have one goal:[s1000] [s5][cRED]SURVIVE.[s1000]").speak();
        while (battle(es[(int) (Math.random()*es.length)])) {
            score++;
        }
        System.out.println();
        System.out.println();
        new BattleBar(40).speak();
        if (!coward) {
            System.out.println();
            System.out.println();
            new Dialogue("[cYELLOW][s5]Your score was: [cBLUE]"+score).speak();
            System.out.println();
            new Dialogue("[cYELLOW][s5]Thanks for playing!").speak();
        } else {
            System.out.println();
            System.out.println();
            new Dialogue("[cRED][s5]Score canceled (game quit early)").speak();
        }
    }

    public static void timeWait(int time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static double atkCalc(double ATK, Weapon weapon, double mod) {
        if (ATK == 0) {
            return 0;
        } else {
            return (ATK + weapon.getATK()) * mod;
        }
    }

    public static double defCalc(double DMG, double DEF, double mod) {
        return (DMG - (DMG/100) * DEF) * mod;
    }

    public static boolean battle(Enemy enemy) {
        String action;
        String turn = "menu";
        currentAttack = totalAttack;
        currentDefense = totalDefense;
        double atkMod = 1;
        double defMod = 1;
        int bblength = 40;
        Dialogue enemyTitle = new Dialogue("[cWHITE][s5]You are fighting "+enemy.getName()+"! Score: "+score);
        Dialogue actions = new Dialogue("[cWHITE][s5]1) Actions");
        Dialogue items = new Dialogue("[cWHITE][s5]2) Items");
        Dialogue run = new Dialogue("[cWHITE][s5]3) Run");
        Dialogue quitEarly = new Dialogue("[cWHITE][s5]4) Quit");
        Dialogue selectChoice = new Dialogue("[cWHITE][s5]Type your action: ");
        Dialogue itemsMenu = new Dialogue("[cWHITE][s5]Items Menu");
        Dialogue movesMenu = new Dialogue("[cWHITE][s5]Actions Menu");
        String latestMove;
        boolean ended = false;
        while (!ended) {
            if (enemy.getHealth()[0] <= 0) {
                System.out.println();
                System.out.println();
                new BattleBar(bblength).speak();
                System.out.println();
                System.out.println();
                new Dialogue("[cWHITE][s5]Battle won![s1000]").speak();
                ended = true;
                turn = "lebron";
                enemy.setHealth(enemy.getHealth()[1]);
                return true;
            }

            if (currentHealth <= 0) {
                System.out.println();
                System.out.println();
                new BattleBar(bblength).speak();
                System.out.println();
                System.out.println();
                new Dialogue("[cWHITE][s5]You lost the battle![s1000]").speak();
                ended = true;
                turn = "lebron";
                enemy.setHealth(enemy.getHealth()[1]);
                return false;
            }

            if (turn.equals("menu")) {
                System.out.println();
                System.out.println();
                new BattleBar(bblength).speak();
                System.out.println();
                System.out.println();
                enemyTitle.speak();
                System.out.println();
                System.out.println();
                new Dialogue("[cWHITE][s5]HP: "+currentHealth+"/"+totalHealth).speak();
                System.out.println();
                new Dialogue("[cWHITE][s5]ATK: "+currentAttack).speak();
                System.out.println();
                new Dialogue("[cWHITE][s5]DEF: "+currentDefense).speak();
                System.out.println();
                System.out.println();
                new Dialogue("[cWHITE][s5]Enemy HP: "+enemy.getHealth()[0]+"/"+enemy.getHealth()[1]).speak();
                System.out.println();
                System.out.println();
                actions.speak();
                System.out.println();
                items.speak();
                System.out.println();
                run.speak();
                System.out.println();
                quitEarly.speak();
                System.out.println();
                System.out.println();
                selectChoice.speak();
                action = input.nextLine();
                if (action.equals("1")) {
                    turn = "moves";
                } else if (action.equals("2")) {
                    turn = "items";
                } else if (action.equals("3")) {
                    turn = "run";
                } else if (action.equals("4")) {
                    turn = "quit";
                } else {
                    new Dialogue("\n[cRED][s5]Error: Incorrect Input[s500]").speak();
                    turn = "menu";
                }
            }

            if (turn.equals("moves")) {
                System.out.println();
                System.out.println();
                new BattleBar(bblength).speak();
                System.out.println();
                System.out.println();
                movesMenu.speak();
                System.out.println();
                System.out.println();
                int c=0;
                for (int i=0; i<moves.length; i++) {
                    if (moves[i] != null) {
                        c++;
                    }
                }
                Move[] validMoves = new Move[c];
                int j = 0;
                for (int i=0; i<moves.length; i++) {
                    if (moves[i] != null) {
                        validMoves[j] = moves[i];
                        j++;
                    }
                }
                for (int i=0; i<validMoves.length; i++) {
                    new Dialogue("[cWHITE][s5]"+(i+1)+") "+validMoves[i].getName()).speak();
                    System.out.println();
                }
                new Dialogue("[cWHITE][s5]"+(validMoves.length+1)+") Back to menu").speak();
                System.out.println();
                System.out.println();
                selectChoice.speak();
                action = input.nextLine();
                boolean b = true;
                try {
                    Integer.parseInt(action);
                } catch (NumberFormatException e) {
                    new Dialogue("\n[cRED][s5]Error: Incorrect Input[s500]").speak();
                    turn = "menu";
                    b = false;
                }
                if (b) {
                    if (action.equals("" + (validMoves.length + 1))) {
                        turn = "menu";
                    } else if (Integer.parseInt(action) < (validMoves.length + 1) && Integer.parseInt(action) > 0) {
                        enemy.setHealth(enemy.getHealth()[0] - atkCalc(validMoves[Integer.parseInt(action) - 1].getStats()[0], currentWeapon, atkMod));
                        atkMod = atkMod * validMoves[Integer.parseInt(action) - 1].getStats()[1];
                        currentAttack += 2;
                        latestMove = validMoves[Integer.parseInt(action) - 1].getName();
                        turn = "enemy";
                    } else {
                        new Dialogue("\n[cRED][s5]Error: Incorrect Input[s500]").speak();
                        turn = "menu";
                    }
                }
            }

            if (turn.equals("items")) {
                System.out.println();
                System.out.println();
                new BattleBar(bblength).speak();
                System.out.println();
                System.out.println();
                int c=0;
                for (int i=0; i<consumables.length; i++) {
                    if (consumables[i] != null) {
                        c++;
                    }
                }
                if (c == 0) {
                    new Dialogue("[cWHITE][s5]You don't have any items![s1000] ").speak();
                    turn = "menu";
                    System.out.println();
                } else {
                    itemsMenu.speak();
                    System.out.println();
                    System.out.println();
                    Consumable[] validConsumables = new Consumable[c];
                    int j = 0;
                    for (int i = 0; i < consumables.length; i++) {
                        if (consumables[i] != null) {
                            validConsumables[j] = consumables[i];
                            j++;
                        }
                    }
                    for (int i = 0; i < validConsumables.length; i++) {
                        new Dialogue("[cWHITE][s5]" + (i + 1) + ") " + validConsumables[i].getName() + ": +" + validConsumables[i].getHP() + " HP, +" + validConsumables[i].getATK() + " ATK, +" + validConsumables[i].getDEF() + " DEF").speak();
                        System.out.println();
                        new Dialogue("[cWHITE][s5]" + validConsumables[i].getDescription()).speak();
                        System.out.println();
                    }
                    new Dialogue("[cWHITE][s5]" + (validConsumables.length + 1) + ") Back to menu").speak();
                    System.out.println();
                    System.out.println();
                    selectChoice.speak();
                    action = input.nextLine();
                    boolean b = true;
                    try {
                        Integer.parseInt(action);
                    } catch (NumberFormatException e) {
                        new Dialogue("\n[cRED][s5]Error: Incorrect Input[s500]").speak();
                        turn = "menu";
                        b = false;
                    }
                    if (b) {
                        if (action.equals("" + (validConsumables.length + 1))) {
                            turn = "menu";
                        } else if (Integer.parseInt(action) < (validConsumables.length + 1) && Integer.parseInt(action) > 0) {
                            currentAttack += validConsumables[Integer.parseInt(action) - 1].getATK();
                            currentDefense += validConsumables[Integer.parseInt(action) - 1].getDEF();
                            currentHealth += validConsumables[Integer.parseInt(action) - 1].getHP();
                            if (currentHealth > totalHealth) currentHealth = totalHealth;
                            defMod *= validConsumables[Integer.parseInt(action) - 1].getDefMod();
                            atkMod *= validConsumables[Integer.parseInt(action) - 1].getAtkMod();
                            turn = "enemy";
                            for (int i = 0; i < consumables.length; i++) {
                                if (consumables[i] != null && (validConsumables[Integer.parseInt(action) - 1] == consumables[i])) {
                                    consumables[i] = null;
                                }
                            }
                        } else {
                            new Dialogue("\n[cRED][s5]Error: Incorrect Input[s500]").speak();
                            turn = "menu";
                        }
                    }
                }
            }

            if (turn.equals("run")) {
                if (enemy.tryRun()) {
                    System.out.println();
                    System.out.println();
                    new BattleBar(bblength).speak();
                    System.out.println();
                    System.out.println();
                    new Dialogue("[cWHITE][s5]You ran away![s1000]").speak();
                    enemy.setHealth(enemy.getHealth()[1]);
                    score--;
                    ended = true;
                } else {
                    System.out.println();
                    System.out.println();
                    new BattleBar(bblength).speak();
                    System.out.println();
                    System.out.println();
                    new Dialogue("[cWHITE][s5]You failed to escape.[s1000] [s5]"+enemy.getName()+" still has you in their clutches!").speak();
                    turn = "menu";
                }
            }

            if (turn.equals("enemy")) {
                System.out.println();
                System.out.println();
                new BattleBar(bblength).speak();
                System.out.println();
                System.out.println();
                if (Math.random() > enemy.getDecision()) {
                    new Dialogue("[cWHITE][s5]"+enemy.getName()+"'s attack failed.[s1000]").speak();
                    turn = "menu";
                } else {
                    if (enemy.getHealth()[0]+enemy.getHealing()>enemy.getHealth()[1]) {
                        new Dialogue("[cWHITE][s5]"+enemy.getAttackDialogue()+"[s1000]").speak();
                        currentHealth -= enemy.getAttack() + (double) ((int) (Math.random()*11-5));
                    } else if (Math.random() > 0.2) {
                        new Dialogue("[cWHITE][s5]" + enemy.getAttackDialogue() + "[s1000]").speak();
                        currentHealth -= enemy.getAttack();
                    } else {
                        new Dialogue("[cWHITE][s5]"+enemy.getName()+" healed up![s200]").speak();
                        enemy.setHealth(enemy.getHealth()[0]+enemy.getHealing());
                        System.out.println();
                        new Dialogue("[cWHITE][s5][s1000]"+enemy.getTaunts()[(int) (Math.random() * enemy.getTaunts().length)]).speak();
                    }
                    turn = "menu";
                }
            }

            if (turn.equals("quit")) {
                System.out.println();
                System.out.println();
                new BattleBar(bblength).speak();
                System.out.println();
                System.out.println();
                new Dialogue("[cYELLOW][s5]Are you sure you want to quit? (y/n)").speak();
                System.out.println();
                System.out.println();
                selectChoice.speak();
                action = input.nextLine();
                if (action.equals("y")) {
                    ended = true;
                    turn = "lebron";
                    coward = true;
                    return false;
                } else if (action.equals("n")) {
                    turn = "menu";
                } else {
                    new Dialogue("\n[cRED][s5]Error: Incorrect Input[s500]").speak();
                    turn = "menu";
                }
            }
        }
        return true;
    }
}
