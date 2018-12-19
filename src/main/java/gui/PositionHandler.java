package gui;

import java.util.Random;

class PositionHandler {

    private static int n;
    private static int m;
    private static final int BRICK_WIDTH = 60;
    private static final int BRICK_HEIGHT = 25;
    private static Random random = new Random(123456);
    private static boolean[][] used;

    PositionHandler(int w, int h){
        n = w/BRICK_WIDTH;
        m = (h/2)/BRICK_HEIGHT;
        used = new boolean[n][m];
    }

    int[] getPosition(){
        int[] p = new int[2];
        do {
            p[0] = random.nextInt(n);
            p[1] = random.nextInt(m);
        } while(used[p[0]][p[1]]);
        used[p[0]][p[1]] = true;
        p[0] *= BRICK_WIDTH;
        p[1] = (p[1]+3) * BRICK_HEIGHT;
        return p;
    }
}
