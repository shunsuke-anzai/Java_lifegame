package lifegame09B22002;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;

public class BoardModel {
    private boolean[][] cells;
    private int cols;
    private int rows;
    private int cell_x = 12;
    private int cell_y = 12;
    private ArrayList<BoardListener> listeners;
    private Deque<boolean[][]> history;
    private int maxHistorySize = 32;

    public BoardModel(int c, int r) {
        cols = c;
        rows = r;
        cells = new boolean[rows][cols];
        listeners = new ArrayList<BoardListener>();
        history = new ArrayDeque<>();
    }

    public void addListener(BoardListener listener) {
        listeners.add(listener);
    }

    private void fireUpdate() {
        for (BoardListener listener : listeners) {
            listener.updated(this);
        }
    }

    public int getCols() {
        return cols;
    }

    public int getRows() {
        return rows;
    }

    public void printForDebug() {
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                if (cells[x][y] == false) {
                    System.out.print(".");
                } else {
                    System.out.print("*");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    public void changeCellState(int x, int y) {
        boolean[][] currentCells = new boolean[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                currentCells[i][j] = cells[i][j];
            }
        }
        history.push(currentCells);

        if (history.size() > maxHistorySize) {
            history.pollLast();
        }

        if (cells[x][y] == false) {
            cells[x][y] = true;
        } else {
            cells[x][y] = false;
        }

        fireUpdate();
    }

    public void next() {
        boolean[][] newCells = new boolean[rows][cols];
        for (int x = 0; x < cols; x++) {
            for (int y = 0; y < rows; y++) {
                int liveNeighbors = countLiveNeighbors(x, y);
                if (cells[x][y]) {
                    if (liveNeighbors < 2 || liveNeighbors > 3) {
                        newCells[x][y] = false;
                    } else {
                        newCells[x][y] = true;
                    }
                } else {
                    if (liveNeighbors == 3) {
                        newCells[x][y] = true;
                    } else {
                        newCells[x][y] = false;
                    }
                }
            }
        }
        history.push(cells);
        if (history.size() > maxHistorySize) {
            history.pollLast();
        }
        cells = newCells;

        fireUpdate();
    }

    private int countLiveNeighbors(int x, int y) {
        int liveNeighbors = 0;
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (dx == 0 && dy == 0) {
                    continue;
                }

                int newX = x + dx;
                int newY = y + dy;

                if (newX >= 0 && newX < cols && newY >= 0 && newY < rows && cells[newX][newY]) {
                    liveNeighbors++;
                }
            }
        }
        return liveNeighbors;
    }

    public void undo() {
        if (!history.isEmpty()) {
            boolean[][] previousCells = history.pop();
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    cells[i][j] = previousCells[i][j];
                }
            }
            fireUpdate();
        }
    }

    public boolean isUndoable() {
        return !history.isEmpty();
    }

    public boolean isAlive(int x, int y) {
        if (cells[x][y]) {
            return true;
        } else {
            return false;
        }
    }

    public void searchPoint(int x, int y, int w, int h) {
        int l, m, n = 12, o = 12;

        if (w > h) {
            l = h;
        } else {
            l = w;
        }
        m = l / 14;
        for (int a = 0; a < 12; a++) {
            if (a * m + m < x && x < a * m + 2 * m) {
                n = a;
            }
        }
        for (int a = 0; a < 12; a++) {
            if (a * m + m < y && y < a * m + 2 * m) {
                o = a;
            }
        }
        if (n == 12 || o == 12) {
            cell_x = 12;
            cell_y = 12;
        } else {
            cell_x = n;
            cell_y = o;
        }
    }

   

    public int getCellX() {
        return cell_x;
    }

    public int getCellY() {
        return cell_y;
    }
}
