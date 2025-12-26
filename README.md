# 📈 H-Invest | Canlı Kripto ve Hisse Takip Simülasyonu

![Java](https://img.shields.io/badge/Language-Java-orange) ![GUI](https://img.shields.io/badge/Interface-Swing-blue) ![OOP](https://img.shields.io/badge/Concept-OOP-green)

Nesneye Yönelik Programlama (OOP) prensipleri kullanılarak geliştirilmiş, grafiksel kullanıcı arayüzüne (GUI) sahip dinamik bir finansal piyasa simülasyonudur.

---

## 📝 Projenin Amacı ve Kapsamı
Bu proje, klasik veri tabanı otomasyonlarının aksine, dinamik bir **finansal piyasa simülasyonu** gerçekleştirmeyi amaçlar.

Kullanıcıya sanal bir bakiye verilir; kullanıcı **Kripto Para** ve **Hisse Senedi** gibi farklı davranışlara sahip yatırım araçlarını alıp satabilir, portföyünü yönetebilir ve piyasadaki anlık dalgalanmalara göre kar/zarar analizi yapabilir.

## ✨ Projenin Özellikleri

* **⏱️ Canlı Piyasa Simülasyonu:** `Timer` mekanizması ile piyasa verileri her 3 saniyede bir otomatik olarak güncellenir.
* **🖥️ Görsel Arayüz (GUI):** Java Swing kütüphanesi ile geliştirilen modern dashboard tasarımı.
* **📊 Varlık Çeşitliliği:** Yüksek volatiliteye sahip "Kripto Paralar" ve daha stabil "Hisse Senetleri".
* **💰 Portföy Yönetimi:** Anlık bakiye takibi, alım-satım emirleri ve varlık yönetimi.
* **📜 İşlem Günlüğü (Log):** Yapılan tüm işlemler ve sistem hataları, arayüzdeki "Log Ekranı" üzerinden anlık raporlanır.

---

## 🏗️ Teknik Mimari ve Kullanılan OOP Yapıları

Proje, modülerlik ve sürdürülebilirlik ilkelerine uygun olarak tasarlanmıştır.

### 1. Sınıf Hiyerarşisi (Inheritance & Abstraction)
* **`YatirimAraci` (Abstract Class):** Tüm yatırım araçlarının atasıdır. `sembol`, `fiyat`, `adet` gibi ortak özellikleri tutar.
* **`Kripto` ve `HisseSenedi`:** Ata sınıftan türetilmiştir. **Polimorfizm** kullanılarak `fiyatGuncelle()` metodu her sınıfta farklı davranış sergileyecek şekilde ezilmiştir (Override).

### 2. Arayüz (Interface)
* **`ITakaslanabilir`:** Varlıkların alınıp satılabilir olduğunu garanti eden sözleşmedir. Sisteme gelecekte "Altın", "Döviz" gibi sınıflar eklendiğinde sistem bozulmadan genişletilebilir.

### 3. Kapsülleme (Encapsulation)
* **`Portfoy`:** Kullanıcının bakiyesi ve varlıkları `private` olarak korunmuştur. Erişim sadece kontrollü metotlar (`satinAl`, `satisYap`) üzerinden sağlanır.

### 4. Hata Yönetimi (Exception Handling)
* **`BorsaException`:** Projeye özgü hata sınıfıdır. Yetersiz bakiye veya olmayan varlığı satma gibi durumlarda programın çökmesini engeller ve kullanıcıyı uyarır.

---

## 🚀 Kurulum ve Çalıştırma

Projenin çalıştırılabilir `.exe` veya `.jar` dosyasını indirdikten sonra:

1.  Bilgisayarınızda **Java (JRE 8 veya üzeri)** yüklü olduğundan emin olun.
2.   IDE'niz üzerinden projenin **.jar** ve ardından **.exe** dosyasını oluşturduktan sonra dosyaya çift tıklayarak simülasyonu başlatın.
3.  "Otomatik Mod" butonuna basarak piyasayı canlandırabilirsiniz.

---

## 📸 Ekran Görüntüleri

![H-Invest.exe program Arayüzü](

---

**Geliştirici:** [Hasan Recep MÜSLİM]
**Ders:** Nesneye Yönelik Programlama
