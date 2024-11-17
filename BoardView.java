package lifegame09B22002;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class BoardView extends JPanel implements MouseListener, MouseMotionListener{
	
	private int cell_x_pre,cell_y_pre;
	private BoardModel boardmodel;
	private JButton undoButton;

	public BoardView(BoardModel boardmodel,JButton undoButton) {
		this.boardmodel = boardmodel;
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.undoButton = undoButton;
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		int w,h,l,m;
		
		w = this.getWidth();
		h = this.getHeight();
		
		if(w>h) {
			l=h;
		}else {
			l=w;
		}
		m = l/14;
		
		for(int a = m; a < 14*m; a += m) {
		g.drawLine(m, a, 13*m, a);
		g.drawLine(a, m, a, 13*m);
		}
	
		for(int x = 0; x < 12; x++) {
			for(int y = 0; y < 12; y++) {
				if(boardmodel.isAlive(x,y)) {
				g.fillRect(x*m+m, y*m+m, m, m);
				}
			}
		}
		
	}
	
	
	public void mouseClicked(MouseEvent e) {
	}
	public void mouseEntered(MouseEvent e) {
	}
	public void mouseExited(MouseEvent e) {
	}
	public void mousePressed(MouseEvent e) {
		int x = e.getX();
	    int y = e.getY();
	    boardmodel.searchPoint(x, y, getWidth(), getHeight());
	    if(boardmodel.getCellX()!=12 && boardmodel.getCellY()!=12) {
	    boardmodel.changeCellState(boardmodel.getCellX(), boardmodel.getCellY());
        cell_x_pre = boardmodel.getCellX();
        cell_y_pre = boardmodel.getCellY();
	    repaint();
	    if(undoButton != null && boardmodel.isUndoable()==true) {
	    	undoButton.setEnabled(true);
	    }else {
	    	undoButton.setEnabled(false);
	    }
	    }
	}
	public void mouseReleased(MouseEvent e) {
	}
	public void mouseDragged(MouseEvent e) {
		 int x = e.getX();
		 int y = e.getY();
		 boardmodel.searchPoint(x, y, getWidth(), getHeight());
		 if(boardmodel.getCellX()!=12 && boardmodel.getCellY()!=12) {
		 if (boardmodel.getCellX() != cell_x_pre || boardmodel.getCellY() != cell_y_pre) {
	         boardmodel.changeCellState(boardmodel.getCellX(), boardmodel.getCellY());
	         cell_x_pre = boardmodel.getCellX();
	         cell_y_pre = boardmodel.getCellY();
	         repaint();
	       }
		 }
	}
	public void mouseMoved(MouseEvent e) {
	}
		
	
}