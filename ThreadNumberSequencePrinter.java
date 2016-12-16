import java.util.concurrent.atomic.AtomicInteger;

public class ThreadNumberSequencePrinter {

	AtomicInteger num = new AtomicInteger(0);
	Object monitor = new Object();

	class Printer implements Runnable {

		int threadId;
		int numOfThreads;

		Printer(int threadId, int numOfThreads) {
			this.threadId = threadId;
			this.numOfThreads = numOfThreads;
		}

		@Override
		public void run() {

			while (true) {
				try {
					synchronized (monitor) {
						if (num.get() % numOfThreads != threadId) {
							monitor.wait();
						} else {
							System.out.println("Thread ID : " + threadId + ", Value : " + num.getAndIncrement());
							Thread.sleep(1500);
							monitor.notifyAll();
						}
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		}

	}

	public static void main(String[] args) {

		ThreadNumberSequencePrinter sequencePrinter = new ThreadNumberSequencePrinter();

		Thread t1 = new Thread(sequencePrinter.new Printer(0, 3));
		Thread t2 = new Thread(sequencePrinter.new Printer(1, 3));
		Thread t3 = new Thread(sequencePrinter.new Printer(2, 3));

		t2.start();
		t3.start();
		t1.start();
	}

}
