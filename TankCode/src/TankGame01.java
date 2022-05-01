import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Scanner;

public class TankGame01      extends JFrame {
    MyPanel myPanel=null;
    public String key;
    public static Scanner scanner=new Scanner(System.in);
    public static void main(String[] args) {
        TankGame01 tankGame01=new TankGame01();
    }

    TankGame01(){
        System.out.println("请输入选择 1:开始新游戏 2:开始上局游戏");
        key=scanner.next();
        myPanel=new MyPanel(key);
        this.add(myPanel);
        this.setSize(1300,750);
        //运行重绘线程
        new Thread(myPanel).start();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //增加监听事件
        this.addKeyListener(myPanel);
        this.setVisible(true);
        //增加响应关闭窗口的处理
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Recorder.keepRecord();
                System.exit(0);
            }
        });
    }
}
