import com.sun.javafx.scene.traversal.Direction;

import java.util.Vector;

public class EnemyTank extends Tank implements Runnable{
    Vector<Shot> shots=new Vector<>();
    //得到所有敌人坦克的Vector
    Vector<EnemyTank> enemyTanks=new Vector<>();
    //提供方法获得MyPanel中的敌人坦克集合
    public void setEnemyTanks(Vector<EnemyTank> enemyTanks){
        this.enemyTanks=enemyTanks;
    }
    //判断当前敌人坦克,是否和其他敌人坦克发生重叠或者碰撞
    public boolean isTouchEnemyTank() {
        //判断当前敌人坦克(this) 方向
        switch (this.getDirect()) {
            case 0: //上
                //让当前敌人坦克和其它所有的敌人坦克比较
                for (int i = 0; i < enemyTanks.size(); i++) {
                    //从vector 中取出一个敌人坦克
                    EnemyTank enemyTank = enemyTanks.get(i);
                    //不和自己比较
                    if (enemyTank != this) {
                        //如果敌人坦克是上/下
                        if (enemyTank.getDirect() == 0 || enemyTank.getDirect() == 2) {
                            //2. 当前坦克 左上角的坐标 [this.getX(), this.getY()]
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 40
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 60) {
                                return true;
                            }
                            //3. 当前坦克 右上角的坐标 [this.getX() + 40, this.getY()]
                            if (this.getX() + 40 >= enemyTank.getX()
                                    && this.getX() + 40 <= enemyTank.getX() + 40
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 60) {
                                return true;
                            }
                        }
                        //如果敌人坦克是 右/左
                        if (enemyTank.getDirect() == 1 || enemyTank.getDirect() == 3) {
                            //2. 当前坦克 左上角的坐标 [this.getX(), this.getY()]
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 60
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 40) {
                                return true;
                            }
                            //3. 当前坦克 右上角的坐标 [this.getX() + 40, this.getY()]
                            if (this.getX() + 40 >= enemyTank.getX()
                                    && this.getX() + 40 <= enemyTank.getX() + 60
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 40) {
                                return true;
                            }
                        }
                    }

                }
                break;
            case 1: //右
                //让当前敌人坦克和其它所有的敌人坦克比较
                for (int i = 0; i < enemyTanks.size(); i++) {
                    //从vector 中取出一个敌人坦克
                    EnemyTank enemyTank = enemyTanks.get(i);
                    //不和自己比较
                    if (enemyTank != this) {
                        //如果敌人坦克是上/下
                        if (enemyTank.getDirect() == 0 || enemyTank.getDirect() == 2) {
                            //2. 当前坦克 右上角的坐标 [this.getX() + 60, this.getY()]
                            if (this.getX() + 60 >= enemyTank.getX()
                                    && this.getX() + 60 <= enemyTank.getX() + 40
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 60) {
                                return true;
                            }
                            //3. 当前坦克 右下角的坐标 [this.getX() + 60, this.getY() + 40]
                            if (this.getX() + 60 >= enemyTank.getX()
                                    && this.getX() + 60 <= enemyTank.getX() + 40
                                    && this.getY() + 40 >= enemyTank.getY()
                                    && this.getY() + 40 <= enemyTank.getY() + 60) {
                                return true;
                            }
                        }
                        //如果敌人坦克是 右/左
                        if (enemyTank.getDirect() == 1 || enemyTank.getDirect() == 3) {
                            //2. 当前坦克 右上角的坐标 [this.getX() + 60, this.getY()]
                            if (this.getX() + 60 >= enemyTank.getX()
                                    && this.getX() + 60 <= enemyTank.getX() + 60
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 40) {
                                return true;
                            }
                            //3. 当前坦克 右下角的坐标 [this.getX() + 60, this.getY() + 40]
                            if (this.getX() + 60 >= enemyTank.getX()
                                    && this.getX() + 60 <= enemyTank.getX() + 60
                                    && this.getY() + 40 >= enemyTank.getY()
                                    && this.getY() + 40 <= enemyTank.getY() + 40) {
                                return true;
                            }
                        }
                    }
                }
                break;
            case 2: //下
                //让当前敌人坦克和其它所有的敌人坦克比较
                for (int i = 0; i < enemyTanks.size(); i++) {
                    //从vector 中取出一个敌人坦克
                    EnemyTank enemyTank = enemyTanks.get(i);
                    //不和自己比较
                    if (enemyTank != this) {
                        //如果敌人坦克是上/下
                        if (enemyTank.getDirect() == 0 || enemyTank.getDirect() == 2) {
                            //2. 当前坦克 左下角的坐标 [this.getX(), this.getY() + 60]
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 40
                                    && this.getY() + 60 >= enemyTank.getY()
                                    && this.getY() + 60 <= enemyTank.getY() + 60) {
                                return true;
                            }
                            //3. 当前坦克 右下角的坐标 [this.getX() + 40, this.getY() + 60]
                            if (this.getX() + 40 >= enemyTank.getX()
                                    && this.getX() + 40 <= enemyTank.getX() + 40
                                    && this.getY() + 60 >= enemyTank.getY()
                                    && this.getY() + 60 <= enemyTank.getY() + 60) {
                                return true;
                            }
                        }
                        //如果敌人坦克是 右/左
                        if (enemyTank.getDirect() == 1 || enemyTank.getDirect() == 3) {
                            //2. 当前坦克 左下角的坐标 [this.getX(), this.getY() + 60]
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 60
                                    && this.getY() + 60 >= enemyTank.getY()
                                    && this.getY() + 60 <= enemyTank.getY() + 40) {
                                return true;
                            }
                            //3. 当前坦克 右下角的坐标 [this.getX() + 40, this.getY() + 60]
                            if (this.getX() + 40 >= enemyTank.getX()
                                    && this.getX() + 40 <= enemyTank.getX() + 60
                                    && this.getY() + 60 >= enemyTank.getY()
                                    && this.getY() + 60 <= enemyTank.getY() + 40) {
                                return true;
                            }
                        }
                    }
                }
                break;
            case 3: //左
                //让当前敌人坦克和其它所有的敌人坦克比较
                for (int i = 0; i < enemyTanks.size(); i++) {
                    //从vector 中取出一个敌人坦克
                    EnemyTank enemyTank = enemyTanks.get(i);
                    //不和自己比较
                    if (enemyTank != this) {
                        //如果敌人坦克是上/下
                        if (enemyTank.getDirect() == 0 || enemyTank.getDirect() == 2) {
                            //2. 当前坦克 左上角的坐标 [this.getX(), this.getY() ]
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 40
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 60) {
                                return true;
                            }
                            //3. 当前坦克 左下角的坐标 [this.getX(), this.getY() + 40]
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 40
                                    && this.getY() + 40 >= enemyTank.getY()
                                    && this.getY() + 40 <= enemyTank.getY() + 60) {
                                return true;
                            }
                        }
                        //如果敌人坦克是 右/左
                        if (enemyTank.getDirect() == 1 || enemyTank.getDirect() == 3) {
                            //2. 当前坦克 左上角的坐标 [this.getX(), this.getY() ]
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 60
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 40) {
                                return true;
                            }
                            //3. 当前坦克 左下角的坐标 [this.getX(), this.getY() + 40]
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 60
                                    && this.getY() + 40 >= enemyTank.getY()
                                    && this.getY() + 40 <= enemyTank.getY() + 40) {
                                return true;
                            }
                        }
                    }
                }
                break;
        }
        return  false;
    }

    EnemyTank(int x,int y){
        super(x,y);
        this.setSpeed(1);
        this.setDirect(2);
        isAlive=true;
    }
    EnemyTank(int x,int y,int direct){
        super(x,y);
        this.setSpeed(1);
        this.setDirect(direct);
        isAlive=true;
    }

    @Override
    public void run() {//多线程控制敌方坦克自由移动
        while(true) {
            if(isAlive&&shots.size()==0){
                Shot shot=null;
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
                shots.add(shot);
                new Thread(shot).start();
            }
            int step = (int) (Math.random() * 40);
            switch (this.getDirect()) {
                case 0:
                    for (int i = 0; i < step; i++) {
                        if(getY()>0&&!isTouchEnemyTank()) moveUp();
                        else break;;
                        MyPanel.sleep(30);
                    }
                    break;
                case 1:
                    for (int i = 0; i < step; i++) {
                        if(getX()+60<1000&&!isTouchEnemyTank()) moveRight();
                        else break;
                        MyPanel.sleep(30);
                    }
                    break;
                case 2:
                    for (int i = 0; i < step; i++) {
                        if(getY()+60<750&&!isTouchEnemyTank()) moveDown();
                        else break;
                        MyPanel.sleep(30);
                    }
                    break;
                case 3:
                    for (int i = 0; i < step; i++) {
                        if(getX()>0&&!isTouchEnemyTank()) moveLeft();
                        else break;
                        MyPanel.sleep(30);
                    }
                    break;
            }

            //修改方向
            setDirect((int) (Math.random() * 4));
            if (!isAlive) break;
        }
    }
}
