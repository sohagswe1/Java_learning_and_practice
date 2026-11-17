import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ForkJoinPool;

public class RecursiveTaskDemo {
    static class SumTask extends RecursiveTask<Long> {
        private final int start, end;
        SumTask(int start, int end) { this.start = start; this.end = end; }

        @Override
        protected Long compute() {
            if (end - start <= 10) {
                long sum = 0;
                for (int i = start; i <= end; i++) sum += i;
                return sum;
            }
            int middle = (start + end) / 2;
            SumTask left = new SumTask(start, middle);
            SumTask right = new SumTask(middle + 1, end);
            left.fork();
            long rightResult = right.compute();
            return left.join() + rightResult;
        }
    }

    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool();
        try {
            System.out.println("Sum: " + pool.invoke(new SumTask(1, 100)));
        } finally {
            pool.shutdown();
        }
    }
}
