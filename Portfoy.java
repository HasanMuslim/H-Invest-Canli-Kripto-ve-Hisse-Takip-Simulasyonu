import java.util.ArrayList;
import java.util.List;

public class Portfoy {
    private double bakiye;

    private List<IslemKaydi> islemGecmisi; 

    public Portfoy(double baslangicBakiye) {
        this.bakiye = baslangicBakiye;
        this.islemGecmisi = new ArrayList<>();
    }

    private class IslemKaydi {
        String islemTipi;
        String sembol;
        double miktar;
        double birimFiyat;

        IslemKaydi(String islemTipi, String sembol, double miktar, double birimFiyat) {
            this.islemTipi = islemTipi;
            this.sembol = sembol;
            this.miktar = miktar;
            this.birimFiyat = birimFiyat;
        }

        @Override
        public String toString() {

            return String.format("[%s] %-5s - %.4f adet (Fiyat: %.2f)", islemTipi, sembol, miktar, birimFiyat);
        }
    }

    
    public void satinAl(YatirimAraci arac, double miktar) throws BorsaException {
        double toplamTutar = miktar * arac.getFiyat();
        
        if (toplamTutar > bakiye) {
            throw new BorsaException("Yetersiz Bakiye! Gereken: " + toplamTutar + ", Mevcut: " + bakiye);
        }
        
        bakiye -= toplamTutar;
        arac.al(miktar);

        islemGecmisi.add(new IslemKaydi("ALIŞ", arac.getSembol(), miktar, arac.getFiyat()));
        System.out.println("İşlem Tamamlandı. Kalan Bakiye: " + bakiye + " USD");
    }

    public void satinAl(YatirimAraci arac, double butce, boolean tutarBazli) throws BorsaException {
        if (tutarBazli) {
            double alinacakAdet = butce / arac.getFiyat();
            satinAl(arac, alinacakAdet);
        }
    }

    public void satisYap(YatirimAraci arac, double miktar) throws BorsaException {
        if (arac.getAdet() < miktar) {
            throw new BorsaException("Yetersiz Adet! Elinizde sadece " + arac.getAdet() + " adet var.");
        }

        double tutar = arac.getFiyat() * miktar;

        this.bakiye += tutar;

        arac.sat(miktar);

        islemGecmisi.add(new IslemKaydi("SATIŞ", arac.getSembol(), miktar, arac.getFiyat()));
    }

    public void ozetGoster() {
        System.out.println("\n========= PORTFÖY RAPORU =========");
        System.out.println("Kasa Bakiyesi: " + String.format("%.2f", bakiye) + " USD");
        System.out.println("--- İşlem Geçmişi ---");
        for (IslemKaydi kayit : islemGecmisi) {
            System.out.println(kayit.toString());
        }
        System.out.println("==================================");
    }
    public double getBakiye() {
        return bakiye;
    }
}