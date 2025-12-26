public class HisseSenedi extends YatirimAraci {
    
    private String sektor;
    public HisseSenedi(String sembol, double baslangicFiyat, String sektor) {
        super(sembol, baslangicFiyat);
        this.sektor = sektor;
    }

    @Override
    public void fiyatGuncelle() {
        double degisimOrani = (Math.random() * 0.06) - 0.03;
        
        this.guncelFiyat += this.guncelFiyat * degisimOrani;
        
        if (this.guncelFiyat < 0) this.guncelFiyat = 0.0;
    }

    public String getSektor() {
        return sektor;
    }
}