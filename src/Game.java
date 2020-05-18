
import Person.*;
import Room.Room;

import java.util.*;


public class Game {
    private ArrayList <Npc> enemies = new ArrayList <Npc>();
    private ArrayList <Npc> peoples = new ArrayList <Npc>();
    private ArrayList <Room> land = new ArrayList <Room>();
    private Room currentroom;
    private Hero hero;
    private String own;
    private  String yours;

    public Game() {
        welcome();
        setDefault();
    }

    public void setDefault() {
        setLand();
        setHero();
        setLevel();
        setPeoples();   //顺序最好不要颠倒
    }

    public void setEnemys(int number, int health, int damage, int armour) {
        for (int j = 0; j < 4; j++) {
            for (int i = 0; i < number / 3; i++) {
                Npc enemy = new Npc(damage, health, armour, true, land.get(j + 3), yours + j + i);
                // 要保证敌方的攻击大于hero的护甲值,不然会造成hero回血的笑话
                enemies.add(enemy);
            }
        }

    }

    public void setPeoples() {
        for (int j = 0; j < 4; j++) {
            Random r = new Random();
            int max = r.nextInt(2);  //每个地点随机分配1~3个平民
            for (int i = 0; i < max + 1; i++) {
                Npc people = new Npc(12, 20, max, true, land.get(j + 1), "老百姓" + i + j);
                peoples.add(people);
            }
        }

    }

    public void setHero() {
        System.out.println("你的身份是：");
        System.out.println("1.忠臣");
        System.out.println("2.叛军");
        Scanner info = new Scanner(System.in);
        int choose = info.nextInt();
        if (choose == 1) {
            hero = new Hero(100, 15, 140, 5, true, currentroom, 100);
            System.out.println("现在，你是一位忠臣,你的目标是歼灭叛军");
            System.out.println("敌人分布在 小村庄，大村庄，集市中心和敌方基地");
            System.out.println("找出并击败他们就获得了胜利");
            own = "忠臣";
            yours ="叛军";

        } else if (choose == 2) {
            hero = new Hero(80, 25, 70, 5, true, currentroom, 80);
            System.out.println("现在，你是一位叛军，你的目标是反抗忠臣");
            System.out.println("利用你的伪装，进入敌方基地清理掉他们");
            System.out.println("你领导的反抗军就获得了胜利");
            own = "叛军";
            yours ="忠臣";
        } else {
            System.out.println("输入错误（请选择对应的番号）");
            setHero();
        }
    }

    public void setLevel() {
        System.out.println("游戏难度：");
        System.out.println("1.休闲");
        System.out.println("2.专业");
        System.out.println("3.大师");
        System.out.println("你选择：");
        Scanner info = new Scanner(System.in);
        int nd = info.nextInt();
        switch (nd) {
            case 1:
                setEnemys(4, 20, 10, 4);
                break;
            case 2:
                setEnemys(8, 30, 12, 5);
                break;
            case 3:
                setEnemys(8, 40, 14, 6);
                break;
            default:
                System.out.println("输入错误（请选择对应的番号）");
                setLevel();
                break;
        }
    }


    public void setLand() {
        Room home, grassland, mountain, valley, smallvillage, target, downtown, bigvillage;

        home = new Room("我方基地");
        grassland = new Room("草地");
        mountain = new Room("大山");
        valley = new Room("峡谷");
        smallvillage = new Room("小村庄");
        bigvillage = new Room("大村庄");
        downtown = new Room("集市中心");
        target = new Room("敌方基地");


        home.setExit("东边", grassland);

        grassland.setExit("西边", home);
        grassland.setExit("北边", mountain);
        grassland.setExit("南边", valley);

        valley.setExit("北边", grassland);
        valley.setExit("东边", bigvillage);

        mountain.setExit("南边", grassland);
        mountain.setExit("东边", smallvillage);

        smallvillage.setExit("西边", mountain);
        smallvillage.setExit("南边", downtown);

        bigvillage.setExit("西边", valley);
        bigvillage.setExit("北边", downtown);

        downtown.setExit("东边", target);
        downtown.setExit("北边", smallvillage);
        downtown.setExit("南边", bigvillage);

        target.setExit("西边",downtown);

        land.add(home);
        land.add(mountain);
        land.add(grassland);
        land.add(smallvillage);
        land.add(bigvillage);
        land.add(downtown);
        land.add(target);


        currentroom = home;

    }

    public void welcome() {
        System.out.println("游戏名： 只狗<能死两次>");
        System.out.println("欢迎来到只狗的云世界......");
        System.out.println("游戏说明：");
        System.out.println("-------------------------------------");
        System.out.println("  操作模式为: 行动+空格键+对象       ");
        System.out.println(" 可供选择的行动有:  砍向  走向  收买     ");
        System.out.println("-------------------------------------");
        System.out.println("此游戏不能回血，不能购买道具，但能收买平民");
    }

    public void go(String direction) {
        Room r = hero.getLocation().gotoNextroom(direction);
        if (r == null) {
            System.out.println("--  这里没有路!  --");
        } else {
            hero.setLocation(r);
            System.out.println("--  你来到了 " + r.getDescription() + "!  --");
        }
    }

    public void chop(String description) {
        int m = 0;
        Iterator <Npc> list = this.enemies.iterator();
        while (list.hasNext()) {
            Npc i = list.next();
            if (i.getDescription().equals(description)) {
                System.out.println("你发起了对" + i.getDescription() + "进攻");
                if (hero.getLength() > 0) {
                    System.out.println("你的" + hero.getLength() + "位随从，也发起了攻击 ");
                }
                int damage = hero.getDamage() + 10 * hero.getLength();
                System.out.println("对其造成 " + damage + " 伤害");
                int health = i.getHealth() + i.getArmour() * (hero.getLength() + 1) - damage;
                i.setHealth(health);
                if (!i.check()) {
                    System.out.println("--  "+i.getDescription() + ",被击毙了  --");
                    System.out.println("你获得了 30赏金");
                    list.remove();
                    hero.business(30);  //赏金为20
                }
                m = 1;
            }
        }
        if (m == 0) {
            System.out.println("--  请珍惜每次挥刀的机会  --");
        }

    }

    public void buy(String description) {
        Iterator <Npc> lista = peoples.iterator();
        while (lista.hasNext()) {
            Npc u = lista.next();
            if (u.getDescription().equals(description)) {
                int employ = 40;
                if (hero.getProperty() >= employ) {
                    hero.business(-employ);
                    System.out.println("--  你消费了40金币  --");
                    System.out.println(u.getDescription()+"脸都笑烂了, 并追上了你");
                    lista.remove();
                    hero.addfollowers(u);
                } else {
                    System.out.println("--  你没有足够的资金 雇佣平民！ --");
                }
            }
        }
    }

    public void attack(Room r) {
        int mine = 0;
        int others = 0;
        Random oo = new Random();
        for (Person u :
                enemies) {
            float o =oo.nextFloat();
            if (u.getLocation() == r && u.getHealth() > 0) {
                if (hero.getLength() ==0){
                    mine++;
                }
                else{
                    if(o>0.5)                       //敌人有一半的可能对自己或随从造成伤害
                        mine++;
                    else
                        others++;
                }
            }
        }
        hero.setHealth(hero.getHealth() + mine * hero.getArmour() - mine *enemies.get(0).getDamage() );
        System.out.println("你受到来自" + mine + "个 敌人的攻击");
        if(hero.getLength() !=0){
            Npc tar =hero.getFollowers().get(0);
            tar.setHealth( tar.getHealth()+others*tar.getArmour() -others*enemies.get(0).getDamage());

            System.out.println("你的随从受到来自"+others+"个 敌人的攻击");
            if(!tar.check()){
                hero.getFollowers().remove(tar);
                System.out.println("--  你的一位随从牺牲了  --");
            }
        }
    }

    public void manu() {
        int m = 0;
        System.out.println("  ");
        System.out.println("角色：" + own + "   健康值：" + hero.getHealth());
        if(hero.getLength() ==0){
            System.out.println("暂时没有随从跟随.");
        }
        else{
            System.out.println("你的追随者有：");
            for (Npc np:
                 hero.getFollowers()) {
                System.out.println(np.getDescription()+" 健康值："+np.getHealth()+"  ，伤害值："+np.getDamage());
            }
        }
        System.out.println("———— **  " + hero.getLocation().getDescription() + "  ** ————");
        System.out.print("出口有: ");
        System.out.println(hero.getLocation().getDirectiondesc(hero.getLocation()));
        System.out.println("        侦察敌人中.......");
        for (Npc i :
                enemies) {
            if (i.getLocation() == hero.getLocation()) {
                System.out.println(i.getDescription() + " ，健康值：" + i.getHealth()+"  ，护甲值："+i.getArmour());
                m++;
            }
        }
            if (m == 0) {
                System.out.println("这里很安全，没有敌人!");
            }
            System.out.println("你现在身上有：" + hero.getProperty() + "  金币");
            for (Npc o :
                    peoples) {
                if (o.getLocation() == hero.getLocation()) {
                    System.out.println("这里有一位待招募平民：" + o.getDescription());
                    System.out.println("健康值 " + o.getHealth() + "  ,伤害值 " + o.getDamage() + "   ,护甲值 " + o.getArmour());
                }
            }
            System.out.println("你会采取什么样的策略：");
        }


    public void play() {
        Scanner info = new Scanner(System.in);
        int value;
        int live = 1;

        while (true) {
            value = 0;
            manu();

            String action = info.nextLine();
            String[] words = action.split(" ");
            if (words[0].equals("砍向")) {
                chop(words[1]);
                attack(hero.getLocation());
            }
            if (words[0].equals("走向")) {
                go(words[1]);
            }
            if (words[0].equals("收买")) {
                buy(words[1]);
            }
            if (hero.getHealth() <= 0) {
                if (live == 2) {
                    System.out.println("你最终失败了");
                    System.out.println("(可以尝试其他难度和角色)");
                    break;
                } else {
                    live = 2;
                    hero.setHealth(hero.getMax());
                    System.out.println("--   你用魂技能  使自己重返于世  珍惜这次机会吧   --");
                }
            }
            if (own.equals("忠臣")) {
                for (Npc i :
                        enemies) {
                    if (i.getHealth() > 0) {
                        value = 1;
                    }
                }
            } else {
                for (Npc u :
                        enemies) {
                    if (u.getLocation().getDescription().equals("敌方基地")) {
                        if (u.getHealth() > 0)
                            value = 1;
                    }
                }
            }
            if (value == 0) {
                System.out.println(" ");
                System.out.println("--   恭喜你，圆满完成了任务   --");
                System.out.println("(可以尝试其他难度和角色)");
                break;
            }
        }
    }


    public static void main(String[] args) {
        Game game = new Game();
        game.play();
    }
}
