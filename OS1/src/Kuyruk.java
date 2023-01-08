package package1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class Kuyruk {
	// Renkler listesi
	List<String> renkler = new ArrayList<String>();

	int timer;
	// pid numaras�n� almak i�in ProcessBuilder
	ProcessBuilder pb = new ProcessBuilder("java", "-version");
	ArrayList<Proses> liste = new ArrayList<Proses>();
	Random rnd = new Random();

	public Kuyruk() {

		renkler.add("\u001B[30m");
		renkler.add("\u001B[31m");
		renkler.add("\u001B[32m");
		renkler.add("\u001B[33m");
		renkler.add("\u001B[34m");
		renkler.add("\u001B[35m");
		renkler.add("\u001B[36m");
		renkler.add("\u001B[37m");
		renkler.add("\u001B[91m");
		renkler.add("\u001B[92m");
		renkler.add("\u001B[93m");
		renkler.add("\u001B[94m");
		renkler.add("\u001B[95m");
		renkler.add("\u001B[96m");
		renkler.add("\u001B[97m");

	}

	// proses ekle
	void enqueue(Proses p) {
		liste.add(p);

	}

	// Kuyruga proses eklemek icin.
	void ekle(int variszamani, int oncelik, int proseszamani) throws IOException {
		Proses p = new Proses();
		p.variszamani = variszamani;
		p.oncelik = oncelik;
		p.proseszamani = proseszamani;
		p.renk = renkler.get(rnd.nextInt(renkler.size() - 1));
		p.pid = pb.start();
		liste.add(p);

	}

	// proses cikar
	void dequeue() {
		liste.remove(liste.size() - 1);
	}

	// Prosesleri sirala
	List<Proses> sirala(List<Proses> pr) {
		Collections.sort(pr, new Comparator<Proses>() {
			public int compare(Proses p1, Proses p2) {
				return p1.variszamani - p2.variszamani;
			}
		});

		return pr;
	}

	// mainden cagrilan gercek zamanli prosesleri kontrol etmek amaciyla yazilan
	// fonksiyon.
	int kontrolet(List<Proses> gerceks, int zaman, List<Proses> oncelikli2, List<Proses> oncelikli3)
			throws InterruptedException {
		if (gerceks.size() != 0) {
			if (zaman >= gerceks.get(0).variszamani) {
				System.out.print(gerceks.get(0).renk + zaman + ".0000 sn proses basladi     ");
				int sayac2 = 0;
				while (sayac2 != gerceks.get(0).proseszamani) {
					if (sayac2 > 0)
						System.out.print(gerceks.get(0).renk + zaman + ".0000 sn proses yurutuluyor.");

					System.out.println(gerceks.get(0).renk + "    id:" + gerceks.get(0).pid.pid() + "   oncelik: "
							+ gerceks.get(0).oncelik + "   kalan sure: " + (gerceks.get(0).proseszamani - sayac2)
							+ " sn");

					// 1 saniye bekle.
					Thread.sleep(1000);

					sayac2++;

					gerceks.get(0).sayac++;

					// Onceligi dusuk ve askida kalma suresi 20 saniyeyi asmis mi kontrol edilir.
					if (oncelikli2.size() != 0)
						if (oncelikli2.get(0).basladimi == true
								& (zaman - oncelikli2.get(0).proseszamani + oncelikli2.get(0).sayac) > 20) {
							System.out
									.println(oncelikli2.get(0).renk + zaman + ".0000 sn zaman asimi.       " + "    id:"
											+ oncelikli2.get(0).pid.pid() + "   oncelik: " + oncelikli2.get(0).oncelik
											+ "   kalan sure: " + (oncelikli2.get(0).proseszamani) + " sn");
							oncelikli2.remove(0);
						}

					if (oncelikli3.size() != 0)
						if (oncelikli3.get(0).basladimi == true
								& (zaman - oncelikli3.get(0).proseszamani + oncelikli3.get(0).sayac) > 20) {
							System.out.println(oncelikli3.get(0).renk + zaman + ".0000 sn zaman asimi.   " + "    id:"
									+ oncelikli3.get(0).pid.pid() + "   oncelik: " + oncelikli3.get(0).oncelik
									+ "   kalan sure: " + (oncelikli3.get(0).proseszamani) + " sn");
							oncelikli3.remove(0);
						}

					zaman++;
					if (gerceks.get(0).sayac > 20)
						break;

				}

				// Eger proses zamani 20 den b�y�kse zaman asimina ugrat.
				if (gerceks.get(0).sayac > 20) {
					System.out.println(gerceks.get(0).renk + zaman + ".0000 sn zaman asimi.    " + "    id:"
							+ gerceks.get(0).pid.pid() + "   oncelik: " + gerceks.get(0).oncelik + "   kalan sure: "
							+ (gerceks.get(0).proseszamani - sayac2) + " sn");

				}

				else
					System.out.println(gerceks.get(0).renk + zaman + ".0000 sn proses sonlandi.   " + "    id:"
							+ gerceks.get(0).pid.pid() + "   oncelik: " + gerceks.get(0).oncelik + "   kalan sure: "
							+ (gerceks.get(0).proseszamani - sayac2) + " sn");

				gerceks.remove(0);
			}
		}
		return zaman;
	}