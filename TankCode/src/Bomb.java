public class Bomb {
    int x,y;
    int life =9;//炸弹的生命周期
    boolean isAlive =true;

    public Bomb(int x, int y) {
        this.x = x;
        this.y = y;
    }
    //配合出现爆炸效果
    public void lifeDown(){
        if(life>0){
            life--;
        }else{
            isAlive=false;
        }
    }
}
