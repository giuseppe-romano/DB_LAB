import java.util.Stack;

public class Esame23_02_2018_Traccia1_esercizio2 {

    public static void main(String[] args) {
        TNode root = new TNode(1);

        TNode sxL1 = new TNode(9);
        TNode dxL1 = new TNode(3);
     //   root.sx = sxL1;
        root.dx = dxL1;
//
//        TNode sxL2 = new TNode(4);
//        TNode dxL2 = new TNode(-1);
//        sxL1.sx = sxL2;
//        sxL1.dx = dxL2;
//        sxL2 = new TNode(15);
//        dxL2 = new TNode(7);
//        dxL1.sx = sxL2;
//        dxL1.dx = dxL2;

        int k = 1;
        System.out.println("RUN RICORSIVO: " + ALG(root));
        System.out.println("RUN ITERATIVO: " + ALG_ITER(root));
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

    static Integer ALG(int[] A, int i, int j) {
        Integer ret = 0;
        Integer x = A[i];
        Integer y = A[j];
        Integer q;

        if (i <= j) {
            q = (i + j)/2;
            if(i < q) {
                x = ALG(A, i, q);
            }
            ret = ret + x;
            if(q + 1 < j) {
                y = ALG(A, q + 1, j);
            }
            ret = ret + y;
        }

        return ret;
    }

    static Integer ALG_ITER(int[] A, int i, int j) {
        Integer x, q, y, ret = 0;
        int currI = i;
        int currJ = j;

        Stack<Integer> stackI = new Stack<>();
        Stack<Integer> stackJ = new Stack<>();

        while (currI <= currJ || !stackI.isEmpty()) {
            if (currI <= currJ) {
                q = (currI + currJ)/ 2;

                stackT.push(currT);
                stackX.push(x);

                nextT = currT.dx;
                ret = 1;
            }
            else {
                currT = stackT.peek();
                x = stackX.peek();

                if (lastT != currT.sx && currT.sx != null) {
                    a = ret;
                    x = a + x;

                    stackX.pop();
                    stackX.push(x);

                    nextT = currT.sx;
                }
                else {
                    y = ret;
                    ret = x * y * currT.key;

                    stackX.pop();
                    stackT.pop();

                    nextT = null; //forza la risalita
                }
            }
            lastT = currT;
            currT = nextT;
        }
        return ret;
    }
}
