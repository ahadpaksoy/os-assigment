package package1;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;


public class main {
	static int sayac=0;

	
	
	
	@SuppressWarnings({ "removal", "resource" })
	public static void main(String[] args) throws InterruptedException, IOException {
	
		//Kuyruk s�n�f�ndan olusturulan nesneler
		Kuyruk gercek=new Kuyruk();
		Kuyruk kullanici =new Kuyruk();
		Kuyruk ana=new Kuyruk();
        
		//txt okumak i�in olusturulan degiskenler
        
		
		String filename ;
		filename = "testing.txt";
		BufferedReader br = null;
		String sCurrentLine;
         
         br = new BufferedReader(new FileReader(filename));
       
         //txt okumasi gerceklesti ilgili yerlere igili degiskenler atandi.
         while ((sCurrentLine = br.readLine()) != null) {
             
             String a[] = sCurrentLine.split(", ");
             int variszamani = new Integer(a[0]);
             int oncelik = new Integer(a[1]);
             int prosseszamani = new Integer(a[2]);
           
             
             
             if(oncelik==0) {
            	 gercek.ekle(variszamani,oncelik,prosseszamani);
             }
             else
            	 kullanici.ekle(variszamani, oncelik, prosseszamani);
             
             ana.ekle(variszamani, oncelik, prosseszamani);
            	 
             
         }
            
		
		
		
		
		//Gercek zamanl� ve Kullanici prosesleri listeleri
		List<Proses> sirali=ana.sirala(ana.liste);
		List<Proses> gerceks=gercek.sirala(gercek.liste);
		List<Proses> kullanicis=kullanici.sirala(kullanici.liste);
		
		Kuyruk onceliklikuyruk2=new Kuyruk();
		
		Kuyruk onceliklikuyruk3=new Kuyruk();
		
		
		
		int zaman=sirali.get(0).variszamani;
		
		
		for(int i=0;i<sirali.size();i++)
		{
				//Gercek zamanli proses
				if(gerceks.size()!=0)
				while(gerceks.size()!=0&zaman>=gerceks.get(0).variszamani)
				{
				zaman=gercek.kontrolet(gerceks, zaman,onceliklikuyruk2.liste,onceliklikuyruk3.liste);
				
				
				
				if(gerceks.size()==0)
					break;
				
				
				}
			
		
			//1.oncelige sahip prosesler
			if(kullanicis.size()!=0)
			{
			
			if(zaman>=kullanicis.get(0).variszamani&kullanicis.get(0).oncelik==1)
			{
				while(zaman>=kullanicis.get(0).variszamani&kullanicis.get(0).oncelik==1) {
					
					zaman=kullanici.kontrolet2(kullanicis,zaman);
					
					if(kullanicis.get(0).proseszamani>0) {
					onceliklikuyruk2.enqueue(kullanicis.get(0));
					
					
					}
					
					if(gerceks.size()!=0)
					while(gerceks.size()!=0&zaman>=gerceks.get(0).variszamani)
					{
						
					zaman=gercek.kontrolet(gerceks, zaman,onceliklikuyruk2.liste,onceliklikuyruk3.liste);
					
					if(gerceks.size()==0)
						break;
					
					
					}
					
					
					kullanicis.remove(0);
					
				
					if(kullanicis.size()==0)
						break;
				
					
				
			}
			}
			}
			
			
			//2.oncelige sahip prosesler
			if(kullanicis.size()!=0)
			{
			
			while(zaman>=kullanicis.get(0).variszamani&kullanicis.get(0).oncelik==2) {
				onceliklikuyruk2.liste.add(0, kullanicis.get(0));
				
				zaman=kullanici.oncelik2(kullanicis, zaman, onceliklikuyruk2, onceliklikuyruk3, kullanici, gercek, gerceks);
				kullanicis.remove(0);
				
		}
		}
			
			//1.onceliklikte calismis daha sonra 2.oncelik seviyesine gelen prosesler.
			if(onceliklikuyruk2.liste.size()!=0)
			{
			
			if(zaman>=onceliklikuyruk2.liste.get(0).variszamani) {
				
				
				zaman=kullanici.oncelik2(kullanicis, zaman, onceliklikuyruk2, onceliklikuyruk3, kullanici, gercek, gerceks);
				
				
		}
		}
			
			//3.oncelik seviyesine sahip porsesler
			if(kullanicis.size()!=0)
			{
				
				
				if(zaman>=kullanicis.get(0).variszamani&kullanicis.get(0).oncelik==3) {
					{
						onceliklikuyruk3.liste.add(0,kullanicis.get(0));
					zaman=kullanici.kontrolet3(onceliklikuyruk3.liste, zaman);
					
					
				  kullanicis.remove(0);   
					
					
					
					}
			
			}
			
			
		
		}
	     //2.oncelik seviyesinden 3.oncelik seviyesine gecen prosesler.
		if(onceliklikuyruk3.liste.size()!=0)
		{
		
		if(zaman>=onceliklikuyruk3.liste.get(0).variszamani) {
			
			
			zaman=kullanici.kontrolet3(onceliklikuyruk3.liste, zaman);
			
			
	}
	}
			
		

	}

	}
}
