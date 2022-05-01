public class Tank {
    private int x;
    private int y;
    private int direct;//坦克方向
    private int speed=20;
    boolean isAlive;
    public Tank(int x, int y) {
        this.x = x;
        this.y = y;
    }


    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getDirect() {
        return direct;
    }

    public void setDirect(int direct) {
        this.direct = direct;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    //坦克移动
    public void moveUp(){y-=speed;}
    public void moveDown(){y+=speed;}
    public void moveRight(){x+=speed;}
    public void moveLeft(){x-=speed;}
}
