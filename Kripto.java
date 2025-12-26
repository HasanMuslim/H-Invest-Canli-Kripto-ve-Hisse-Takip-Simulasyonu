public class Kripto extends YatirimAraci {
    
    public Kripto(String sembol, double baslangicFiyat) {
        super(sembol, baslangicFiyat);
    }

    @Override
    public void fiyatGuncelle() {
        double degisimOrani = (Math.random() * 0.20) - 0.10; 
        
        this.guncelFiyat += this.guncelFiyat * degisimOrani;

        if (this.guncelFiyat < 0) this.guncelFiyat = 0.01;
    }
}