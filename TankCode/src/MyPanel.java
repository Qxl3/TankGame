import javax.security.auth.kerberos.KerberosTicket;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.EnumMap;
import java.util.Vector;
import java.util.concurrent.ForkJoinPool;

//画板
public class MyPanel extends Panel implements KeyListener ,Runnable {
    //定义一个存放上局游戏信息的集合
    Vector<Node> nodes =new Vector<>();
    //声明玩家坦克
    MyTank myTank=null;
    //声明敌方坦克,利用vector防止多线程安全问题
    Vector<EnemyTank> enemyTanks = new Vector<>();
    int enemyTankSize;
    //声明爆炸效果(用集合存储可以同时多处爆炸)
    Vector<Bomb> bombs=new Vector<>();
    //声明三张图片,用于初始化爆炸效果
    Image image1=null;
    Image image2=null;
    Image image3=null;

    //构造器实例化所有坦克
    public MyPanel(String key){
        //判断文件是否存在，不存在的话就只能开启新游戏
        File file = new File(Recorder.getRecordFile());
        if(file.exists()){
            //获得上局信息
            nodes=Recorder.getNodes();
        }else{
            System.out.println("文件不存在，只能开启新的游戏");
            key="1";
        }

        //初始化我方坦克
        myTank=new MyTank(500,200);
        switch (key){
            case "1"://开始新游戏
                Recorder.setAllEnemyTankNum(0);
                enemyTankSize=3;
                //初始化敌方坦克
                for(int i=0;i<enemyTankSize;i++){
                    //创建一个敌方坦克对象
                    EnemyTank enemyTank = new EnemyTank((100*(i+1)),0);
                    //将坦克加入坦克集合
                    enemyTanks.add(enemyTank);
                    //启动坦克线程
                    new  Thread(enemyTank).start();
                }
                for (int i = 0; i < enemyTankSize; i++) {
                    EnemyTank enemyTank = enemyTanks.get(i);
                    enemyTank.enemyTanks=enemyTanks;
                }
                break;
            case "2"://恢复上局游戏
                //初始化敌方坦克
                for(int i=0;i<nodes.size();i++){
                    //创建一个敌方坦克对象
                    EnemyTank enemyTank = new EnemyTank(nodes.get(i).getX(),nodes.get(i).getY(),nodes.get(i).getDirect());
                    //将坦克加入坦克集合
                    enemyTanks.add(enemyTank);
                    //启动坦克线程
                    new  Thread(enemyTank).start();
                }
                for (int i = 0; i < nodes.size(); i++) {
                    EnemyTank enemyTank = enemyTanks.get(i);
                    enemyTank.enemyTanks=enemyTanks;
                }
                break;
            default:
                System.out.println("你的输入有误");
        }

        //初始化爆炸图片
        image1=Toolkit.getDefaultToolkit().getImage(MyPanel.class.getResource("/bomb_1.gif"));
        image2=Toolkit.getDefaultToolkit().getImage(MyPanel.class.getResource("/bomb_2.gif"));
        image3=Toolkit.getDefaultToolkit().getImage(MyPanel.class.getResource("/bomb_3.gif"));

        //播放指定音乐
        new AePlayWave("TankCode//src//111.wav").start();
    }
    //记录玩家击毁敌方坦克信息
    public void showInfo(Graphics g){
        g.setColor(Color.black);
        g.setFont(new Font("宋体",Font.BOLD,25));
        g.drawString("您累计击毁敌方坦克:",1020,30);
        paintTank(1020,60,g,0,0);
        g.setColor(Color.black);
        g.drawString( Recorder.getAllEnemyTankNum()+"",1080,100);
    }

    @Override
    //画出游戏所处区域(paint函数会自动调用)
    public void paint(Graphics g) {
        //记录坦克存活信息
        Recorder.setEnemyTanks(enemyTanks);
        super.paint(g);
        //填充游戏所处区域,默认黑色
        g.fillRect(0,0,1000,750);
        //画出游戏信息
        showInfo(g);
        //画出玩家坦克
        if(myTank.isAlive) {
            paintTank(myTank.getX(), myTank.getY(), g, myTank.getDirect(), 0);
        }
        //画出敌方所有坦克
        for(int i=0;i<enemyTanks.size();i++){
            EnemyTank enemyTank=enemyTanks.get(i);
            if(enemyTank.isAlive) {//当敌人坦克活着的时候才会绘画敌方坦克
                paintTank(enemyTank.getX(), enemyTank.getY(), g, enemyTank.getDirect(), 1);
                //画出该敌方坦克所有子弹
                for (int j = 0; j < enemyTank.shots.size(); j++) {
                    //取出子弹
                    Shot shot = enemyTank.shots.get(j);
                    //绘制
                    if (shot.isAlive) {
                        g.draw3DRect(shot.x, shot.y, 1, 1, false);
                    } else {
                        //从Vector中移除
                        //如果子弹不从Vector中移除,则子弹会一直占着子弹集合中的一个位置,当后期刷新子弹时会造成空间浪费
                        enemyTank.shots.remove(shot);
                    }
                }
            }
        }
        //画出玩家子弹
        for(int i=0;i<myTank.shots.size();i++) {
            Shot shot=myTank.shots.get(i);
            if (shot != null && shot.isAlive ) {//防止子弹不停的画
                g.draw3DRect(shot.x, shot.y, 2, 2, false);
            }else{
                 myTank.shots.remove(shot);
            }
        }

        //画出爆炸(集合中有爆炸)
        for (int i = 0; i < bombs.size(); i++) {
            Bomb bomb = bombs.get(i);
            if(bomb.life>6){
                g.drawImage(image1,bomb.x,bomb.y,60,60,this);
                sleep(100);//休眠一段时间防止还未画完就快速刷新导致看不见图片
            }else if(bomb.life>3){
                g.drawImage(image2,bomb.x,bomb.y,60,60,this);
                sleep(100);//主线程决定画板上的内容,画板的repaint并不改变画板内容
            }else{
                g.drawImage(image3,bomb.x,bomb.y,60,60,this);
                sleep(100);
            }
            //让炸弹生命减少
            bomb.lifeDown();
            if(bomb.life==0){
                bombs.remove(bomb);
            }
        }
    }

    //坦克绘画
    //direct->坦克方向
    //type->坦克类型
    public void paintTank(int x,int y,Graphics g,int direct,int type){
        //根据坦克类型:画出不同玩家的坦克
        switch (type){
            case 0://玩家坦克
                g.setColor(Color.PINK);//玩家坦克为粉色
                break;
            case 1://敌人坦克
                g.setColor(Color.YELLOW);//敌方坦克为黄色
                break;
        }

        //根据坦克方向:画出不同方向的坦克
        //direct 表示方向(0:向上 1:向右 2:向下 3:向左)
        switch(direct){
            case 0://坦克方向向上
                g.fill3DRect(x,y,10,60,false);
                g.fill3DRect(x+30,y,10,60,false);
                g.fill3DRect(x+10,y+10,20,40,false);
                g.fillOval(x+10,y+20,20,20);
                g.drawLine(x+20,y+30,x+20,y);
                break;
            case 1://坦克方向向右
                g.fill3DRect(x,y,60,10,false);
                g.fill3DRect(x,y+30,60,10,false);
                g.fill3DRect(x+10,y+10,40,20,false);
                g.fillOval(x+20,y+10,20,20);
                g.drawLine(x+30,y+20,x+60,y+20);
                break;
            case 2://坦克方向向下
                g.fill3DRect(x,y,10,60,false);
                g.fill3DRect(x+30,y,10,60,false);
                g.fill3DRect(x+10,y+10,20,40,false);
                g.fillOval(x+10,y+20,20,20);
                g.drawLine(x+20,y+30,x+20,y+60);
                break;
            case 3://坦克方向向左
                g.fill3DRect(x,y,60,10,false);
                g.fill3DRect(x,y+30,60,10,false);
                g.fill3DRect(x+10,y+10,40,20,false);
                g.fillOval(x+20,y+10,20,20);
                g.drawLine(x+30,y+20,x,y+20);
                break;
        }
    }
    //监听器的方法重新实现
    @Override
    public void keyTyped(KeyEvent e) {

    }

    //处理wasd键按下的情况
    @Override
    public void keyPressed(KeyEvent e) {
        //根据方向键来控制坦克的移动
        if(e.getKeyCode()==KeyEvent.VK_W){
            myTank.setDirect(0);
            if(myTank.getY()>0) myTank.moveUp();
        }else if(e.getKeyCode()==KeyEvent.VK_S){
            myTank.setDirect(2);
            if(myTank.getY()+60<750) myTank.moveDown();
        }else if(e.getKeyCode()==KeyEvent.VK_A){
            myTank.setDirect(3);
            if(myTank.getX()>0) myTank.moveLeft();
        }else if(e.getKeyCode()==KeyEvent.VK_D){
            myTank.setDirect(1);
            if(myTank.getX()+60<1000) myTank.moveRight();
        }

        //通过J键控制玩家坦克子弹发射
        if(e.getKeyCode()==KeyEvent.VK_J&& myTank.isAlive) {
            myTank.shotEnemy();
        }

        //让面板重绘
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    //判断坦克是否击中坦克
    //在重绘的同时去判断是否击中
    public void hitTank(Shot s,Tank Tank){
        //判断子弹是否击中坦克
        switch (Tank.getDirect()){
            case 0://向上
            case 2://向下
                if(s.x>Tank.getX()&&s.x<Tank.getX()+40&&s.y>Tank.getY()&&s.y<Tank.getY()+60){
                    s.isAlive=false;
                    Tank.isAlive=false;
                    //enemyTanks.remove(enemyTank);
                    //创建Bomb对象,加入到Bombs集合中
                    Bomb bomb = new Bomb(Tank.getX(), Tank.getY());
                    bombs.add(bomb);
                }
                break;
            case 1://向左
            case 3://向右
                if(s.x>Tank.getX()&&s.x<Tank.getX()+60&&s.y>Tank.getY()&&s.y<Tank.getY()+40){
                    s.isAlive=false;
                    Tank.isAlive=false;
                    //enemyTanks.remove(enemyTank);
                    //创建Bomb对象,加入到Bombs集合中
                    Bomb bomb = new Bomb(Tank.getX(), Tank.getY());
                    bombs.add(bomb);
                }
                break;
        }
    }

    //遍历玩家子弹判断是否击中敌方坦克
    public void hitEnemyTank(){
        for(int j=0;j<myTank.shots.size();j++) {
            Shot shot=myTank.shots.get(j);
            if (shot != null && shot.isAlive) {
                for (int i = 0; i < enemyTanks.size(); i++) {
                    EnemyTank enemyTank = enemyTanks.get(i);
                    hitTank(shot, enemyTank);
                    //判断坦克是否死亡
                    if(enemyTank.isAlive==false) {
                        enemyTanks.remove(enemyTank);
                        Recorder.addAllEnemyTankNum();
                    }
               }
            }
        }
    }

    //判断敌方坦克是否击中我方坦克
    public void hitMyTank(){
        for(int i=0;i<enemyTanks.size();i++){
            EnemyTank enemyTank =enemyTanks.get(i);
            for(int j=0;j<enemyTank.shots.size();j++){
                Shot shot=enemyTank.shots.get(j);
                if(shot!=null&&shot.isAlive){
                    hitTank(shot,myTank);
                }
            }
        }
    }

    @Override
    //画板刷新当成一个线程来不停的刷新
    public void run() {
        while(true){
//            try {
//                Thread.sleep(1);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            //判断玩家子弹是否击中敌方坦克
            hitEnemyTank();
            //判断地方子弹是否击中玩家坦克(结束效果还未做)
            hitMyTank();
            //不断重绘面板
            int i=0;
            //while (i++<100) {
                this.repaint();
            //}
        }
    }
    //线程休眠
    public static void sleep(int time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
