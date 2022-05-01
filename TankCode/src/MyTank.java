import java.util.Vector;

public class MyTank extends Tank {
    //我方坦克射击
    Shot shot=null;
    //创建子弹集合
    Vector<Shot> shots=new Vector<>();

    public MyTank(int x, int y) {
        super(x, y);
        this.setSpeed(5);
        this.setDirect(0);
        isAlive=true;
    }

    //玩家坦克射击
    public void shotEnemy(){
        if(shots.size()<=3) {//决定我方可同时发射子弹最大数量
            //根据玩家坦克的位置和方向来初始化坦克的子弹的位置和方向
            switch (getDirect()) {
                case 0://向上
                    shot = new Shot(getX() + 20, getY(), 0);
                    break;
                case 1://向右
                    shot = new Shot(getX() + 60, getY() + 20, 1);
                    break;
                case 2://向下
                    shot = new Shot(getX() + 20, getY() + 60, 2);
                    break;
                case 3://向左
                    shot = new Shot(getX() + 20, getY() + 20, 3);
                    break;
            }
            //把新建的子弹添加子弹集合中
            shots.add(shot);
            //启动子弹线程
            new Thread(shot).start();
        }
    }
}
