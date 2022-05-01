import java.io.*;
import java.util.Vector;

public class Recorder {
    //记录我方击毁敌人坦克数
    private static int allEnemyTankNum=0;
    private static Vector<EnemyTank> enemyTanks=new Vector<>();
    //定义IO对象
    private static BufferedWriter bw=null;
    private static BufferedReader br=null;
    private static String recordFile="TankCode//src//myRecord.txt";
    //定义Node集合来保存敌方坦克信息
    private static  Vector<Node> nodes=new Vector<>();

    //用于读取上局游戏,用于恢复上局游戏相关信息
    public static  Vector<Node> getNodes(){
        try {
            br=new BufferedReader(new FileReader(recordFile));
            allEnemyTankNum=Integer.parseInt(br.readLine());
            String line="";
            while((line= br.readLine())!=null){
                String[] xyd=line.split(" ");//按照空格进行分割
                Node node = new Node(Integer.parseInt(xyd[0]), Integer.parseInt(xyd[1]), Integer.parseInt(xyd[2]));
                nodes.add(node);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(br!=null){
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return nodes;
    }

    public static int getAllEnemyTankNum() {
        return allEnemyTankNum;
    }

    public static void setEnemyTanks(Vector<EnemyTank> enemyTanks) {
        Recorder.enemyTanks = enemyTanks;
    }

    public static void setAllEnemyTankNum(int allEnemyTankNum) {
        Recorder.allEnemyTankNum = allEnemyTankNum;
    }

    //记录我方击毁坦克的数量
    public static void addAllEnemyTankNum(){
        Recorder.allEnemyTankNum++;
    }

    public static String getRecordFile() {
        return recordFile;
    }

    //提供方法,当游戏退出时,将击毁敌方坦克数量和敌方坦克信息保存
    public static void keepRecord(){
        try {
            bw = new BufferedWriter(new FileWriter(recordFile));
            bw.write(allEnemyTankNum+"\r\n");
            for(int i=0;i<enemyTanks.size();i++){
                EnemyTank enemyTank = enemyTanks.get(i);
                bw.write(enemyTank.getX()+" "+enemyTank.getY()+" "+enemyTank.getDirect()+"\r\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(bw!=null){
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
