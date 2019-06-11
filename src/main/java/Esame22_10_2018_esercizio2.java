import java.util.Stack;

public class Esame22_10_2018_esercizio2 {

    public static void main(String[] args) {
        TNode root = new TNode(15);

        TNode sxL1 = new TNode(9);
        TNode dxL1 = new TNode(22);
        root.sx = sxL1;
        root.dx = dxL1;

        TNode sxL2 = new TNode(5);
        TNode dxL2 = new TNode(10);
        sxL1.sx = sxL2;
        sxL1.dx = dxL2;
        sxL2 = new TNode(18);
        dxL2 = new TNode(30);
        dxL1.sx = sxL2;
        dxL1.dx = dxL2;

        int x = 1;
        int y = 55;
        System.out.println("RUN RICORSIVO: " + CHECKBST(root, x, y));
        System.out.println("RUN ITERATIVO: " + CHECKBST_ITER(root, x, y));
    }

    static class TNode {
        public TNode dx;
        public TNode sx;
        public int key;

        public TNode(int k) {
            this.key = k;
        }

        @Override
        public String toString() {
            return "TNode{" +
                    "dx=" + dx +
                    ", sx=" + sx +
                    ", key=" + key +
                    '}';
        }
    }

    static int CHECKBST(TNode T, int x, int y) {
        int a = 1;
        int b = 1;
        int val;
        int ret;

        if (T != null) {
            val = T.key;

            if (x <= val && val <= y) {
                a = CHECKBST(T.sx, x, val - 1);
                if(a != 0) {
                    b = CHECKBST(T.dx, val + 1, y);
                }
            }
            else {
                a = 0;
            }
        }
        ret = ((a==1) && (b==1)) ? 1 : 0;
        return ret;
    }

    static int CHECKBST_ITER(TNode T, int x, int y) {
        TNode currT = T, nextT;
        Integer currX = x, lastX = null;
        Integer currY = y;
        Integer nextX, nextY;
        Integer a = 1, b = 1, ret = 0, val;

        Stack<TNode> stackT = new Stack<>();
        Stack<Integer> stackA = new Stack<>();
        Stack<Integer> stackX = new Stack<>();
        Stack<Integer> stackY = new Stack<>();

        while (currT != null || !stackT.isEmpty()) {
            a = 1;
            b = 1;
            ret = ((a==1) && (b==1)) ? 1 : 0;

            if(currT != null) {
                val = currT.key;

                if (currX <= val && val <= currY) {
                    stackT.push(currT);
                    stackX.push(currX);
                    stackY.push(currY);

                    nextT = currT.sx;
                    nextX = currX;
                    nextY = val - 1;
                }
                else {
                    a = 0;
                    ret = ((a==1) && (b==1)) ? 1 : 0;

                    nextT = null; //Forza la risalita
                    nextX = currX;
                    nextY = currY;
                }
            }
            else {
                currT = stackT.peek();
                currX = stackX.peek();
                currY = stackY.peek();
                val = currT.key;

                if(lastX == currX) { //ritorno dalla prima chiamata
                    a = ret;

                    if(a != 0) {
                        stackA.push(a);

                        nextT = currT.dx;
                        nextX = val + 1;
                        nextY = currY;
                    }
                    else {
                        ret = ((a==1) && (b==1)) ? 1 : 0;
                        stackT.pop();
                        stackX.pop();
                        stackY.pop();

                        nextT = null;
                        nextX = currX;
                        nextY = currY;
                    }
                }
                else { // ritorno dalla seconda chiamata
                    b = ret;
                    a = stackA.peek();
                    ret = ((a==1) && (b==1)) ? 1 : 0;

                    stackT.pop();
                    stackX.pop();
                    stackY.pop();
                    stackA.pop();

                    nextT = null;
                    nextX = currX;
                    nextY = currY;
                }
            }
            lastX = currX;
            currT = nextT;
            currX = nextX;
            currY = nextY;
        }
        return ret;
    }
}
