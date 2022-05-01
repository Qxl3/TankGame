public class Shot implements Runnable{
    int x;//子弹x坐标
    int y;//子弹y坐标
    int direct;
    int speed=6;
    boolean isAlive=true;

    public Shot(int x, int y, int direct) {
        this.x = x;
        this.y = y;
        this.direct = direct;
    }

    @Override
    //射击
    public void run() {
        while(true){
            //休眠50毫秒防止绘画速度跟不上(调节speed也同样的道理,但需要把speed调的很低)
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //根据方向射击子弹
            switch (direct){
                case 0: y-=speed;
                    break;
                case 1: x+=speed;
                    break;
                case 2: y+=speed;
                    break;
                case 3: x-=speed;
                    break;
            }
            if(direct==0)
            //如果子弹撞到边界则会结束线程
            //如果子弹射击到敌方坦克也会结束
            if(!(x>=0&&x<=1000&&y>=0&&y<=750&&isAlive)){
                isAlive=false;
                break;//跳出循环退出子弹线程
            }
        }
    }
}
