package package1;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FCFS {
	public static void schedule(List<Proses> proses) throws InterruptedException {
		// Sort the processes by their arrival time
		Collections.sort(proses, new Comparator<Proses>() {
			public int compare(Proses p1, Proses p2) {
				return p1.variszamani - p2.variszamani;
			}
		});

		int sayac = 0;
		for (Proses p : proses) {
			while (p.proseszamani != sayac) {
				System.out.println(p.oncelik);
				Thread.sleep(1000);
				sayac++;
			}
			sayac = 0;
		}

	}

}
