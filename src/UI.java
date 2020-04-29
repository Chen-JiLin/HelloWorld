//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;

public class UI {
    private JFrame frame; //五子棋游戏窗口
    private Chessboard chessboard = new Chessboard();//五子棋盘
    private Chess chess = new Chess();//五子棋业务逻辑
    private JMenuBar menu;//菜单栏
    private JMenu option;//菜单栏中的“选项”菜单
    private Action replayOption;//选项下拉项中的“重玩一项”选项
    private Action AIFirstOption;//选项下拉项中的“机器先手”选项
    private Action HumanFirstOption;//选项下拉项的“人类先手”选项

    public UI() {
    }

    public static void main(String[] args) {
        (new UI()).init();
    }

    public void init() {
        this.frame = new JFrame("简易五子棋-人机");
        this.menu = new JMenuBar();
        this.option = new JMenu("选项");
        this.menu.add(this.option);

        //把“重玩一盘”、“机器先手”、“人类先手”加入“选项”下拉项中
        this.replayOptionInit();
        this.option.add(this.replayOption);
        this.AIFirstOptionInit();
        this.option.add(this.AIFirstOption);
        this.HumanFirstOptionInit();
        this.option.add(this.HumanFirstOption);

        this.frame.setJMenuBar(this.menu);
        this.frame.add(this.chessboard);//加入棋盘

        //初始化棋盘
        this.chessboard.init();
        this.chess.init();

        //绑定鼠标事件，要下棋了，为了避免写无用的抽象方法的实现，用适配器
        this.chessboard.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                UI.this.play(e);
            }
        });

        //设置frame窗口左上角图标
        this.frame.setIconImage(this.frame.getToolkit().getImage("image/gobang.png"));
        this.frame.setSize(528, 591);
        this.frame.setDefaultCloseOperation(3);
        this.frame.setVisible(true);
    }

    public void replayOptionInit() {
        this.replayOption = new AbstractAction("重玩一盘", new ImageIcon("image/replay.png")) {
            public void actionPerformed(ActionEvent e) {
                UI.this.chessboard.init();
                UI.this.chess.init();
            }
        };
    }

    public void AIFirstOptionInit() {
        this.AIFirstOption = new AbstractAction("机器先手", new ImageIcon("image/robot.png")) {
            public void actionPerformed(ActionEvent e) {
                //棋盘还没有落子的时候可以选择“机器先手”，一旦有落子，选择“机器先手”失效
                if (UI.this.chessboard.isEmpty()) {
                    Chess.FIRST = -1;
                    //机器先手，则先在中间位置下一个棋子
                    UI.this.chessboard.addChessman(9, 9, -1);
                    UI.this.chess.addChessman(9, 9, -1);
                }

            }
        };
    }

    public void HumanFirstOptionInit() {
        this.HumanFirstOption = new AbstractAction("人类先手", new ImageIcon("image/human.png")) {
            public void actionPerformed(ActionEvent e) {
                //棋盘还没有落子的时候可以选择“人类先手”，一旦有落子，选择“人类先手”失效
                if (UI.this.chessboard.isEmpty()) {
                    Chess.FIRST = 1;
                }

            }
        };
    }

    public void play(MouseEvent e) {
        int cellSize = this.chessboard.getCellSize();//每个格子的边长
        int x = (e.getX() - 5) / cellSize;//像素值转换成棋盘坐标
        int y = (e.getY() - 5) / cellSize;//像素值转换成棋盘坐标
        boolean isLegal = this.chess.isLegal(x, y);//判断棋子是否合法
        if (isLegal) {
            this.chessboard.addChessman(x, y, 1);
            this.chess.addChessman(x, y, 1);
            if (this.chess.isWin(x, y, 1)) {
                JOptionPane.showMessageDialog(this.frame, "人类获胜", "Congratulations，您赢了！", -1);
                this.chessboard.init();
                this.chess.init();
                return;
            }

            Location loc = this.chess.searchLocation();
            this.chessboard.addChessman(loc);
            this.chess.addChessman(loc.getX(), loc.getY(), loc.getOwner());
            if (this.chess.isWin(loc.getX(), loc.getY(), -1)) {
                JOptionPane.showMessageDialog(this.frame, "机器获胜", "Sorry，您输了！", -1);
                this.chessboard.init();
                this.chess.init();
                return;
            }
        }

    }
}
