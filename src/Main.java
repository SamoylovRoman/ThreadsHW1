import java.util.Arrays;

public class Main {

    static final int SIZE = 10000000;
    static final int HALF = SIZE / 2;


    public static void main(String[] args) {
        Method1();
        Method2();

        System.out.print("END of MAIN");

    }

    static public void Method1() {
        float[] arr = new float[SIZE];
        Arrays.fill(arr, 1);
        long a = System.currentTimeMillis();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        System.out.print("Worked time of Method1() with one thread  --> " + (System.currentTimeMillis() - a) + "msec\n");
        System.out.println("Some element 1 = " + arr[700]);
        System.out.println("Some element 2 = " + arr[7000000]);
    }

    static public void Method2() {
        float[] arr = new float[SIZE];
        Arrays.fill(arr, 1);
        long a = System.currentTimeMillis();
        float[] a1 = new float[HALF];
        float[] a2 = new float[HALF];
        System.arraycopy(arr, 0, a1, 0, HALF);
        System.arraycopy(arr, HALF, a2, 0, HALF);
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < HALF; i++) {
                    a1[i] = (float) (a1[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
                }
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int j = 0; j < HALF; j++) {
                    a2[j] = (float) (a2[j] * Math.sin(0.2f + (j+HALF) / 5) * Math.cos(0.2f + (j+HALF) / 5)
                            * Math.cos(0.4f + (j+HALF) / 2));
                }
            }
        });
        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.arraycopy(a1, 0, arr, 0, HALF);
        System.arraycopy(a2, 0, arr, HALF, HALF);
        System.out.print("Worked time of Method2() with two threads --> " + (System.currentTimeMillis() - a) + "msec\n");
        System.out.println("Some element 1 = " + arr[700]);
        System.out.println("Some element 2 = " + arr[7000000]);
    }
}
