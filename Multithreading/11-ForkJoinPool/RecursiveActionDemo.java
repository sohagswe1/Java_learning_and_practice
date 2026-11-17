import java.util.concurrent.*;

public class RecursiveActionDemo {
    static class PrintTask extends RecursiveAction {
        private final int start, end;
        PrintTask(int start, int end) { this.start = start; this.end = end; }

        @Override
        protected void compute() {
            if (end - start <= 2) {
                for (int i = start; i <= end; i++) System.out.println("Value: " + i);
            } else {
                int mid = (start + end) / 2;
                invokeAll(new PrintTask(start, mid), new PrintTask(mid + 1, end));
            }
        }
    }

    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool();
        try {
            pool.invoke(new PrintTask(1, 10));
        } finally {
            pool.shutdown();
        }
    }
}
