import java.util.ArrayList;
import java.util.Arrays;

public class Board {
    private final int[][] tiles;
    private final int N;
    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles){

        this.N = tiles.length;

        this.tiles = new int[N][N];
        for(int i=0; i<N; i++){
            System.arraycopy(tiles[i], 0, this.tiles[i], 0, N );
        }
    }

    // string representation of this board
    public String toString(){
        StringBuilder str = new StringBuilder();
        str.append(N);
        str.append('\n');
        for(int i=0; i<N; i++){
            for(int j=0; j<N; j++){
                str.append(tiles[i][j]).append(" ");
            }
            str.append('\n');
        }
        return str.toString();
    }

    // board dimension n
    public int dimension(){
        return this.N;
    }

    // number of tiles out of place
    public int hamming(){
        int count =0;
        for(int i=0; i<N; i++){
            for(int j=0; j<N; j++){
                int goalValue = (N) * (i) + j+1;
                if(tiles[i][j] == 0){
                    continue;
                }
                if(tiles[i][j] != goalValue){
                    count++;
                }
            }
        }
        return count;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan(){
        int count =0;
        for(int i=0; i<N; i++){
            for(int j=0; j<N; j++){

                int currentValue = tiles[i][j] - 1 ;
                int correctRow = currentValue / N;
                int correctCol = currentValue % N;

                if(currentValue == -1){
                    continue;
                }

                // 목표 위치 발견됨 ( correctRow, correctCol)
                count += Math.abs(correctRow - i);
                count += Math.abs(correctCol -j );

            }
        }
        return count;
    }


    // is this board the goal board?
    public boolean isGoal(){
        return hamming() == 0;
    }

    // does this board equal y?
    public boolean equals(Object y){
        if (y == this) return true;
        if (y == null) return false;
        if(y.getClass() != this.getClass()) return false;
        Board that = (Board)y;
        return Arrays.deepEquals(that.tiles,this.tiles);
    }

    // all neighboring boards
    public Iterable<Board> neighbors(){
        int x = 0;
        int y = 0;

        int dimention = N-1;
        for(int i=0; i<=dimention; i++){
            for(int j=0; j<=dimention; j++){
                if(tiles[i][j] == 0){
                    x = i; y =j;
                    break;
                }
            }
            if(x!=0){
                break;
            }
        }

        int[] xLocation = {-1, +1, 0,0};
        int[] yLocation = {0,0, +-1,+1};
        ArrayList<Board> boards = new ArrayList<>();
        final int[][] original = tiles.clone();

        for(int i=0; i<4; i++){
            int currentX = x + xLocation[i];
            int currentY = y + yLocation[i];
            if(currentX >= 0 && currentY >= 0 && currentX <= dimention && currentY <= dimention){
                boards.add(new Board(swap(x,y,currentX,currentY,original.clone())));
            }
        }
        return boards;
    }


    private int[][] swap(int x1, int y1, int x2, int y2, int[][] original){
        int[][] tmpArray = new int[original.length][original.length];
        //여기가 문제
        for(int i=0; i<N; i++){
            System.arraycopy(original[i], 0, tmpArray[i], 0, N);
        }
//        tmpArray = original.clone();


        int tmp = tmpArray[x2][y2];
        tmpArray[x2][y2] = tmpArray[x1][y1];
        tmpArray[x1][y1] = tmp;

        return tmpArray;
    }


    // a board that is obtained by exchanging any pair of tiles
    public Board twin(){
        int row1 =-1 ,col1 =-1,row2=-1,col2=-1;

        for(int i=0; i<N; i++){
            for(int j=0; j<N; j++){
                if(tiles[i][j] != 0){
                    if(row1 == -1){
                        row1 = i; col1 = j;
                    }else{
                        row2 = i; col2 = j;
                        return new Board(swap(row1,col1,row2,col2,this.tiles.clone()));
                    }
                }
            }
        }
        return new Board(swap(row1,col1,row2,col2,this.tiles.clone()));
    }

    // unit testing (not graded)

}
