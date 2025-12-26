import java.awt.*;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.Timer;
import javax.swing.table.*; 

public class Main extends JFrame {

    private Portfoy benimCuzdan;
    private List<YatirimAraci> piyasa;
    private Timer zamanlayici;
    private JLabel lblBakiye;
    private JTable tablo;
    private DefaultTableModel tabloModeli;
    private JTextArea logEkrani;
    private JButton btnOtoMod;

    public Main() {
        veriTabaniniHazirla();
        zamanlayici = new Timer(3000, e -> piyasayiHareketlendir(true));

        setTitle("H-Invest - Canlı Kripto ve Hisse Takip");
        setSize(950, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(240, 240, 245));

        JPanel ustPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        ustPanel.setBackground(new Color(44, 62, 80));
        
        JLabel lblBaslik = new JLabel("MEVCUT BAKİYE:");
        lblBaslik.setForeground(Color.WHITE);
        lblBaslik.setFont(new Font("Segoe UI", Font.BOLD, 18));

        lblBakiye = new JLabel(String.format("%.2f USD", benimCuzdan.getBakiye())); 
        lblBakiye.setForeground(new Color(46, 204, 113));
        lblBakiye.setFont(new Font("Consolas", Font.BOLD, 28));

        ustPanel.add(lblBaslik);
        ustPanel.add(lblBakiye);
        add(ustPanel, BorderLayout.NORTH);

        JPanel solPanel = new JPanel(new BorderLayout());
        solPanel.setBorder(BorderFactory.createTitledBorder(
            null, 
            "CANLI PİYASA", 
            javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, 
            javax.swing.border.TitledBorder.DEFAULT_POSITION, 
            new Font("Segoe UI", Font.BOLD, 14), 
            Color.DARK_GRAY
        ));
        
        String[] kolonlar = {"Sembol", "Fiyat (USD)", "Tür", "Adet"};
        tabloModeli = new DefaultTableModel(kolonlar, 0);

        tablo = new JTable(tabloModeli);
        tablo.setRowHeight(30);
        tablo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tabloVerileriniDoldur();

        solPanel.add(new JScrollPane(tablo), BorderLayout.CENTER);
        solPanel.setPreferredSize(new Dimension(550, 0));
        add(solPanel, BorderLayout.CENTER);

        JPanel sagPanel = new JPanel(new GridLayout(7, 1, 10, 15));
        sagPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        sagPanel.setBackground(new Color(236, 240, 241));

        btnOtoMod = new JButton("OTOMATİK MOD: KAPALI");
        stilVerButona(btnOtoMod, Color.GRAY);

        JButton btnYenile = new JButton("MANUEL YENİLE");
        stilVerButona(btnYenile, new Color(52, 152, 219));

        JButton btnAl = new JButton("ALIM EMRİ GİR");
        stilVerButona(btnAl, new Color(39, 174, 96));

        JButton btnSat = new JButton("SATIŞ EMRİ GİR");
        stilVerButona(btnSat, new Color(192, 57, 43));

        JButton btnOzet = new JButton("PORTFÖY DURUMU");
        stilVerButona(btnOzet, new Color(142, 68, 173));

        JButton btnCikis = new JButton("ÇIKIŞ");
        stilVerButona(btnCikis, Color.DARK_GRAY);

        sagPanel.add(btnOtoMod);
        sagPanel.add(btnYenile);
        sagPanel.add(new JSeparator());
        sagPanel.add(btnAl);
        sagPanel.add(btnSat);
        sagPanel.add(btnOzet);
        sagPanel.add(btnCikis);
        
        add(sagPanel, BorderLayout.EAST);

        JPanel altPanel = new JPanel(new BorderLayout());
        altPanel.setBorder(BorderFactory.createTitledBorder(
            null, "SİSTEM GÜNLÜĞÜ", 
            javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, 
            javax.swing.border.TitledBorder.DEFAULT_POSITION,
            new Font("Segoe UI", Font.BOLD, 12),
            Color.BLACK
        ));
        altPanel.setPreferredSize(new Dimension(0, 180));

        logEkrani = new JTextArea();
        logEkrani.setEditable(false);
        logEkrani.setBackground(Color.BLACK);
        logEkrani.setForeground(new Color(0, 255, 0));
        logEkrani.setFont(new Font("Consolas", Font.PLAIN, 12));
        logEkrani.setText("> Sistem başlatıldı...\n> Veriler yüklendi.\n");
        
        altPanel.add(new JScrollPane(logEkrani), BorderLayout.CENTER);
        add(altPanel, BorderLayout.SOUTH);

        btnOtoMod.addActionListener(e -> {
            if (zamanlayici.isRunning()) {
                zamanlayici.stop();
                btnOtoMod.setText("OTOMATİK MOD: KAPALI");
                btnOtoMod.setBackground(Color.GRAY);
                logEkle("Otomatik takip durduruldu.");
            } else {
                zamanlayici.start();
                btnOtoMod.setText("OTOMATİK MOD: AÇIK (3sn)");
                btnOtoMod.setBackground(new Color(255, 140, 0));
                logEkle("Otomatik takip başlatıldı.");
            }
        });

        btnYenile.addActionListener(e -> {
            piyasayiHareketlendir(false);
            logEkle("Piyasa manuel güncellendi.");
        });

        btnAl.addActionListener(e -> islemYap("AL"));
        btnSat.addActionListener(e -> islemYap("SAT"));

        btnOzet.addActionListener(e -> {
            benimCuzdan.ozetGoster();
            logEkle("Portföy detayları konsol çıktısına yazıldı.");
            JOptionPane.showMessageDialog(this, "Güncel Bakiye: " + lblBakiye.getText() + "\n(Detaylar için konsola bakınız)");
        });

        btnCikis.addActionListener(e -> System.exit(0));

        setVisible(true);
    }

    private void islemYap(String tur) {
        boolean timerCalisiyorMu = zamanlayici.isRunning();
        if(timerCalisiyorMu) zamanlayici.stop();

        int secilenRow = tablo.getSelectedRow();
        if (secilenRow == -1) {
            JOptionPane.showMessageDialog(this, "Lütfen tablodan bir varlık seçin!", "Hata", JOptionPane.WARNING_MESSAGE);
            if(timerCalisiyorMu) zamanlayici.start();
            return;
        }

        YatirimAraci arac = piyasa.get(secilenRow);
        String mesaj = (tur.equals("AL")) ? arac.getSembol() + " için kaç adet alacaksınız?" : arac.getSembol() + " varlığından kaç adet satacaksınız?";
        String miktarStr = JOptionPane.showInputDialog(this, mesaj);

        if (miktarStr != null && !miktarStr.isEmpty()) {
            try {
                double miktar = Double.parseDouble(miktarStr);
                if (tur.equals("AL")) {
                    benimCuzdan.satinAl(arac, miktar);
                    logEkle("[ALIM BAŞARILI] " + miktar + " adet " + arac.getSembol());
                } else {
                    benimCuzdan.satisYap(arac, miktar);
                    logEkle("[SATIŞ BAŞARILI] " + miktar + " adet " + arac.getSembol());
                }

                bakiyeGuncelle();
                tabloGuncelle();
            } catch (BorsaException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "İşlem Hatası", JOptionPane.ERROR_MESSAGE);
                logEkle("[HATA] " + ex.getMessage());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Lütfen geçerli bir sayı girin.", "Hata", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                logEkle("[HATA] Beklenmedik bir sorun oluştu.");
            }
        }

        if(timerCalisiyorMu) zamanlayici.start();
    }

    private void piyasayiHareketlendir(boolean otomatik) {
        for (YatirimAraci arac : piyasa) arac.fiyatGuncelle();
        tabloGuncelle();
    }

    private void veriTabaniniHazirla() {
        benimCuzdan = new Portfoy(100000);
        piyasa = new ArrayList<>();
        piyasa.add(new Kripto("BTC", 92000));
        piyasa.add(new Kripto("ETH", 3200));
        piyasa.add(new Kripto("BNB", 890));
        piyasa.add(new Kripto("TIA", 0.58));
        piyasa.add(new HisseSenedi("THYAO", 6.50, "Ulaşım"));
        piyasa.add(new HisseSenedi("ASELS", 4.20, "Savunma"));
    }

    private void tabloVerileriniDoldur() {
        for (YatirimAraci arac : piyasa) {
            tabloModeli.addRow(new Object[]{
                arac.getSembol(), 
                String.format("%.2f", arac.getFiyat()), 
                (arac instanceof Kripto) ? "Kripto" : "Hisse", 
                arac.getAdet()
            });
        }
    }

    private void tabloGuncelle() {
        tabloModeli.setRowCount(0);
        tabloVerileriniDoldur();
    }

    private void bakiyeGuncelle() {
        lblBakiye.setText(String.format("%.2f USD", benimCuzdan.getBakiye()));
    }

    private void logEkle(String msg) {
        logEkrani.append("> " + msg + "\n");
        logEkrani.setCaretPosition(logEkrani.getDocument().getLength());
    }

    private void stilVerButona(JButton btn, Color renk) {
        btn.setBackground(renk);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(renk.darker(), 1),
            BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main());
    }
}   