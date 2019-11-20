Proje checkout edilip Intellij içine import edildiğin otomatik olarak Spring Boot projesi olarak 
görülecektir. Sonrasında ise `SpringJpaPrimefacesApplication` sınıfının main class'ı çalıştırılarak
uygulama ayağa kaldırılır.

Uygulama başladıktan sonra `http://localhost:8080/mindworks/login.xhtml` URL'inden `mindworks` ve `m`
bilgileriyle login olunulur.

Veritabanı ve gerekli veriler otomatik olarak oluşturulur. Embedded H2 sunucusuna uygulamaya
**login** olduktan sonra `http://localhost:8080/mindworks/console` URL'inden ulaşılıp tablolar
incelenip SQL'ler çalıştırılabilir. (JDBC URL olarak `jdbc:h2:mem:mw` kullanılmalı)

Şimdilik sadece kullanıcıya ait olan kitaplar gösteriliyor ve yeni bir kitap ekleme işlemi 
yapılabiliyor. İstenilen diğer örnekler de zamanla eklenebilir.