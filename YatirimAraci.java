public abstract class YatirimAraci implements ITakaslanabilir {
    
    protected String sembol;
    protected double guncelFiyat;
    protected double adet;

    public YatirimAraci(String sembol, double baslangicFiyat) {
        this.sembol = sembol;
        this.guncelFiyat = baslangicFiyat;
        this.adet = 0;
    }

    public abstract void fiyatGuncelle();
    
    @Override
    public void al(double miktar) {
        this.adet += miktar;
        System.out.println(miktar + " adet " + sembol + " portföye eklendi.");
    }

    @Override
    public void sat(double miktar) {
        if (this.adet >= miktar) {
            this.adet -= miktar;
            System.out.println(miktar + " adet " + sembol + " portföyden çıkarıldı.");
        } else {
            System.out.println("Hata: Yetersiz adet! Elinizde sadece " + this.adet + " var.");
        }
    }

    public String getSembol() {
        return sembol;
    }

    public double getFiyat() {
        return guncelFiyat;
    }

    public double getAdet() {
        return adet;
    }

    public String toString() {
        return String.format("%-5s | Fiyat: %8.2f USD | Adet: %8.4f", sembol, guncelFiyat, adet);
    }

    public void durumGoster() {
        System.out.println(String.format("%-5s | Fiyat: %10.2f USD | Portföydeki Adet: %.4f", sembol, guncelFiyat, adet));
    }
}