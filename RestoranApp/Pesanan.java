import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Pesanan {
    private String namaPelanggan;
    private ArrayList<MenuItem> itemDipesan;
    private ArrayList<Integer> jumlahItem;
    private ArrayList<Double> hargaItem;

    public Pesanan(String namaPelanggan) {
        this.namaPelanggan = namaPelanggan;
        this.itemDipesan = new ArrayList<>();
        this.jumlahItem = new ArrayList<>();
        this.hargaItem = new ArrayList<>();
    }

    public void tambahPesanan(MenuItem item, int jumlah) {
        double totalHarga = item.getHarga() * jumlah;

        // Promo minuman: beli 2 gratis 1 jika total > 50000
        if (item.getKategori().equalsIgnoreCase("Minuman") && totalHarga > 50000) {
            int gratis = jumlah / 2;
            totalHarga = (jumlah - gratis) * item.getHarga();
            System.out.println("  >> Promo Minuman! Beli " + jumlah + " gratis " + gratis);
        }

        itemDipesan.add(item);
        jumlahItem.add(jumlah);
        hargaItem.add(totalHarga);
        System.out.println("  >> " + item.getNama() + " x" + jumlah + " ditambahkan ke pesanan.");
    }

    public double hitungSubtotal() {
        double subtotal = 0;
        for (double h : hargaItem) subtotal += h;
        return subtotal;
    }

    public double hitungDiskonMenu(Menu menu) {
        // Cek apakah ada item Diskon di menu
        for (MenuItem item : menu.getDaftarMenu()) {
            if (item instanceof Diskon) {
                Diskon d = (Diskon) item;
                double subtotal = hitungSubtotal();
                if (subtotal > 100000) {
                    return d.hitungDiskon(subtotal);
                }
            }
        }
        // Default diskon 10% jika subtotal > 100000
        double subtotal = hitungSubtotal();
        if (subtotal > 100000) return subtotal * 0.10;
        return 0;
    }

    public double hitungTotal(Menu menu) {
        double subtotal = hitungSubtotal();
        double diskon = hitungDiskonMenu(menu);
        double setelahDiskon = subtotal - diskon;
        double pajak = setelahDiskon * 0.10;
        double pelayanan = 20000;
        return setelahDiskon + pajak + pelayanan;
    }

    public void tampilStruk(Menu menu) {
        System.out.println(getStrukAsString(menu));
    }

    public String getStrukAsString(Menu menu) {
        StringBuilder sb = new StringBuilder();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String waktu = LocalDateTime.now().format(fmt);

        sb.append("\n================================\n");
        sb.append("       STRUK PEMBAYARAN         \n");
        sb.append("================================\n");
        sb.append("Pelanggan : ").append(namaPelanggan).append("\n");
        sb.append("Waktu     : ").append(waktu).append("\n");
        sb.append("--------------------------------\n");

        int totalItem = 0;
        for (int i = 0; i < itemDipesan.size(); i++) {
            sb.append(String.format("%dx %-18s Rp %,.0f%n",
                jumlahItem.get(i), itemDipesan.get(i).getNama(), hargaItem.get(i)));
            totalItem += jumlahItem.get(i);
        }

        double subtotal = hitungSubtotal();
        double diskon = hitungDiskonMenu(menu);
        double setelahDiskon = subtotal - diskon;
        double pajak = setelahDiskon * 0.10;
        double pelayanan = 20000;
        double grandTotal = setelahDiskon + pajak + pelayanan;

        sb.append("--------------------------------\n");
        sb.append(String.format("Total Item  : %d%n", totalItem));
        sb.append(String.format("Subtotal    : Rp %,.0f%n", subtotal));
        if (diskon > 0) sb.append(String.format("Diskon      : -Rp %,.0f%n", diskon));
        sb.append(String.format("Pajak 10%%   : Rp %,.0f%n", pajak));
        sb.append(String.format("Pelayanan   : Rp %,.0f%n", pelayanan));
        sb.append("================================\n");
        sb.append(String.format("GRAND TOTAL : Rp %,.0f%n", grandTotal));
        sb.append("================================\n");
        sb.append("   Terima kasih Datang Kembali   \n");
        sb.append("================================\n");

        return sb.toString();
    }

    public String getNamaPelanggan() { return namaPelanggan; }
}
