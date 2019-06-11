import java.util.Objects;
import java.util.Stack;

public class Esame24_01_2019_esercizio2 {

    public static void main(String[] args) {
        TNode root = new TNode(1);

        TNode sxL1 = new TNode(9);
        TNode dxL1 = new TNode(3);
        root.sx = sxL1;
        root.dx = dxL1;

        TNode sxL2 = new TNode(4);
        TNode dxL2 = new TNode(12);
        sxL1.sx = sxL2;
        sxL1.dx = dxL2;
        sxL2 = new TNode(15);
        dxL2 = new TNode(7);
        dxL1.sx = sxL2;
        dxL1.dx = dxL2;

        int k = 1;
        System.out.println("RUN RICORSIVO: " + ALG(root, k));
        System.out.println("RUN ITERATIVO: " + ALG_ITER(root, k));
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

    static TNode BEST(TNode a, TNode b, int k) {
        if(a == null || b == null) {
            if (a != null) {
                return a;
            }
            else {
                return b;
            }
        }

        if(a.key > b.key) {
            return a;
        }
        else {
            return b;
        }
    }

    static TNode ALG(TNode T, int k) {
        TNode a = null;
        TNode b = null;
        TNode ret = null;

        if (T != null) {
            if(T.key > k) {
                a = ALG(T.sx, k);
                b = T;
            }
            else if(T.key < k) {
                a = T;
                b = ALG(T.dx, k);
            }
            else {
                a = ALG(T.sx, k);
                b = ALG(T.dx, k);
            }
            ret = BEST(a, b, k);
        }

        return ret;
    }

    static TNode ALG_ITER(TNode T, int k) {
        TNode currT = T, lastT = null, nextT;
        TNode a, b, ret = null;

        Stack<TNode> stackT = new Stack<>();
        Stack<TNode> stackA = new Stack<>();

        while (currT != null || !stackT.isEmpty()) {
            if (currT != null) {
                ret = null;

                if (currT.key > k) {
                    nextT = currT.sx;
                }
                else if (currT.key < k) {
                    nextT = currT.dx;
                }
                else {
                    nextT = currT.sx;
                }
                stackT.push(currT);
            }
            else {
                currT = stackT.peek();

                if (currT.key > k) {
                    a = ret;
                    b = currT;

                    stackT.pop();
                    nextT = null; //forza la risalita

                    ret = BEST(a, b, k);
                }
                else if (currT.key < k) {
                    a = currT;
                    b = ret;

                    stackT.pop();
                    nextT = null; //forza la risalita

                    ret = BEST(a, b, k);
                }
                else {
                    if(lastT == currT.sx) { //ritorno prima chiamata
                        a = ret;
                        stackA.push(a);

                        nextT = currT.dx; //prepara per la seconda chiamata ricorsiva
                    }
                    else {
                        a = stackA.peek();
                        b = ret;

                        stackA.pop();
                        stackT.pop();
                        nextT = null; //forza la risalita

                        ret = BEST(a, b, k);
                    }
                }

            }
            lastT = currT;
            currT = nextT;
        }
        return ret;
    }
}
