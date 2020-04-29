//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JPanel;

public class Chessboard extends JPanel {
    public static final int CHESSBOARD_SIZE = 19;//棋盘大小19*19
    private ArrayList<Location> locationList = new ArrayList();//棋盘上所有可以落子的位置坐标等信息
    private Color backgroundColor = new Color(255, 245, 186);
    private Color lineColor = new Color(66, 66, 66);
    private int margin = 20;//棋盘边缘长度

    public Chessboard() {
    }

    //初始化棋盘
    public void init() {
        this.locationList.clear();
        this.repaint();
    }

    //覆盖paint方法
    public void paint(Graphics g) {
        super.paint(g);
        this.drawChessboard(g);
        this.drawChessman(g);
    }

    //画棋盘
    public void drawChessboard(Graphics g) {
        //先画背景
        g.setColor(this.backgroundColor);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        //画线
        g.setColor(this.lineColor);
        int cellSize = (this.getWidth() - 2 * this.margin) / 18;

        for(int i = 0; i < 19; ++i) {
            //画横线
            g.drawLine(this.margin, this.margin + i * cellSize, 19*cellSize, this.margin + i * cellSize);
            //画竖线
            g.drawLine(this.margin + i * cellSize, this.margin, this.margin + i * cellSize, 19*cellSize);
        }

    }

    //画棋子
    public void drawChessman(Graphics g) {
        for(int i = 0; i < this.locationList.size(); ++i) {
            Location loc = (Location)this.locationList.get(i);

            //根据先后手设置棋子为黑色和白色
            if (loc.getOwner() == Chess.FIRST) {
                g.setColor(Color.BLACK);
            } else {
                g.setColor(Color.WHITE);
            }

            int cellSize = (this.getWidth() - 2 * this.margin) / 18;
            //画棋子
            g.fillOval(this.margin + cellSize * loc.getX() - cellSize / 2, this.margin + cellSize * loc.getY() - cellSize / 2, cellSize, cellSize);
        }

    }

    public void addChessman(int x, int y, int owner) {
        this.locationList.add(new Location(x, y, owner));
        this.repaint();
    }

    public void addChessman(Location loc) {
        this.locationList.add(loc);
        this.repaint();
    }

    public int getCellSize() {
        return (this.getWidth() - 2 * this.margin) / 18;
    }

    //判断棋盘是否还没有棋子
    public boolean isEmpty() {
        return this.locationList.size() == 0 ? true : false;
    }
}
