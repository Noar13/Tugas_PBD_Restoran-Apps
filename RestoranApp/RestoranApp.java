import java.util.Scanner;
import java.util.ArrayList;

public class RestoranApp {

    static Scanner sc = new Scanner(System.in);
    static Menu menu = new Menu();
    static final String FILE_MENU = "menu.txt";

    public static void main(String[] args) {
        // Muat menu dari file jika ada
        ArrayList<MenuItem> loaded = FileManager.muatMenu(FILE_MENU);
        if (loaded.isEmpty()) {
            // Data default jika file belum ada
            menu.tambahItem(new Makanan("Nasi Padang", 25000, "Berat"));
            menu.tambahItem(new Makanan("Mie Goreng", 20000, "Berat"));
            menu.tambahItem(new Makanan("Ayam Bakar", 30000, "Berat"));
            menu.tambahItem(new Minuman("Es Teh", 10000, "Dingin"));
            menu.tambahItem(new Minuman("Jus Jeruk", 15000, "Dingin"));

        } else {
            for (MenuItem item : loaded) {
                menu.getDaftarMenu().add(item);
            }
        }

        boolean running = true;
        while (running) {
            System.out.println("\n╔══════════════════════════════════╗");
            System.out.println("║       RESTORAN SEDERHANA         ║");
            System.out.println("╠══════════════════════════════════╣");
            System.out.println("║  1. Tampilkan Menu               ║");
            System.out.println("║  2. Tambah Item Menu             ║");
            System.out.println("║  3. Ubah Harga Menu              ║");
            System.out.println("║  4. Hapus Menu                   ║");
            System.out.println("║  5. Buat Pesanan                 ║");
            System.out.println("║  0. Keluar                       ║");
            System.out.println("╚══════════════════════════════════╝");
            System.out.print("Pilih menu: ");

            int pilihan = bacaInt();
            switch (pilihan) {
                case 1: menu.tampilSemuaMenu(); break;
                case 2: tambahItemMenu(); break;
                case 3: ubahHargaMenu(); break;
                case 4: hapusMenu(); break;
                case 5: buatPesanan(); break;
                case 0:
                    FileManager.simpanMenu(menu.getDaftarMenu(), FILE_MENU);
                    System.out.println("Program selesai. Sampai jumpa!");
                    running = false;
                    break;
                default:
                    System.out.println("Pilihan tidak tersedia!");
            }
        }
        sc.close();
    }

    static void tambahItemMenu() {
        System.out.println("\n=== TAMBAH ITEM MENU ===");
        System.out.println("1. Makanan");
        System.out.println("2. Minuman");
        System.out.println("3. Diskon");
        System.out.print("Pilih jenis: ");
        int jenis = bacaInt();

        if (jenis == 1) {
            System.out.print("Nama makanan: ");
            String nama = sc.nextLine();
            System.out.print("Harga: ");
            double harga = bacaDouble();
            System.out.print("Jenis makanan (Berat/Ringan): ");
            String jenisMakanan = sc.nextLine();
            menu.tambahItem(new Makanan(nama, harga, jenisMakanan));

        } else if (jenis == 2) {
            System.out.print("Nama minuman: ");
            String nama = sc.nextLine();
            System.out.print("Harga: ");
            double harga = bacaDouble();
            System.out.print("Jenis minuman (Dingin/Panas): ");
            String jenisMinuman = sc.nextLine();
            menu.tambahItem(new Minuman(nama, harga, jenisMinuman));

        } else if (jenis == 3) {
            System.out.print("Nama diskon: ");
            String nama = sc.nextLine();
            System.out.print("Persen diskon (contoh: 10 untuk 10%%): ");
            double persen = bacaDouble() / 100;
            menu.tambahItem(new Diskon(nama, persen));

        } else {
            System.out.println("Pilihan tidak valid!");
        }
    }

    static void ubahHargaMenu() {
        menu.tampilSemuaMenu();
        System.out.print("Pilih nomor menu yang ingin diubah harganya: ");
        int nomor = bacaInt();
        System.out.print("Harga baru: ");
        double hargaBaru = bacaDouble();
        System.out.print("Yakin ingin mengubah? (Ya/Tidak): ");
        String konfirmasi = sc.nextLine();
        if (konfirmasi.equalsIgnoreCase("Ya")) {
            try {
                menu.ubahHarga(nomor - 1, hargaBaru);
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        } else {
            System.out.println("Perubahan dibatalkan.");
        }
    }

    static void hapusMenu() {
        menu.tampilSemuaMenu();
        System.out.print("Pilih nomor menu yang ingin dihapus: ");
        int nomor = bacaInt();
        System.out.print("Yakin ingin menghapus? (Ya/Tidak): ");
        String konfirmasi = sc.nextLine();
        if (konfirmasi.equalsIgnoreCase("Ya")) {
            try {
                menu.hapusItem(nomor - 1);
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        } else {
            System.out.println("Penghapusan dibatalkan.");
        }
    }

    static void buatPesanan() {
        System.out.println("\n=== BUAT PESANAN ===");
        System.out.print("Nama pelanggan: ");
        String namaPelanggan = sc.nextLine();
        Pesanan pesanan = new Pesanan(namaPelanggan);

        System.out.print("Berapa jenis menu yang ingin dipesan (max 4)? ");
        int jumlahJenis = bacaInt();
        if (jumlahJenis < 1 || jumlahJenis > 4) {
            System.out.println("Input tidak valid! Harus antara 1-4.");
            return;
        }

        menu.tampilSemuaMenu();

        for (int i = 0; i < jumlahJenis; i++) {
            System.out.println("\nPesanan ke-" + (i + 1) + ":");
            System.out.print("Nomor menu: ");
            int nomorMenu = bacaInt();
            System.out.print("Jumlah: ");
            int jumlah = bacaInt();

            try {
                MenuItem item = menu.getItem(nomorMenu - 1);
                // Skip item Diskon dari pesanan
                if (item instanceof Diskon) {
                    System.out.println("Item diskon tidak bisa dipesan langsung.");
                    i--;
                    continue;
                }
                pesanan.tambahPesanan(item, jumlah);
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                i--;
            }
        }

        // Tampil struk
        pesanan.tampilStruk(menu);

        // Simpan struk ke file
        System.out.print("Simpan struk ke file? (Ya/Tidak): ");
        String simpan = sc.nextLine();
        if (simpan.equalsIgnoreCase("Ya")) {
            FileManager.simpanStruk(pesanan.getStrukAsString(menu), namaPelanggan);
        }
    }

    // Helper: baca integer dengan validasi
    static int bacaInt() {
        while (!sc.hasNextInt()) {
            System.out.print("Input harus angka! Coba lagi: ");
            sc.next();
        }
        int val = sc.nextInt();
        sc.nextLine();
        return val;
    }

    // Helper: baca double dengan validasi
    static double bacaDouble() {
        while (!sc.hasNextDouble()) {
            System.out.print("Input harus angka! Coba lagi: ");
            sc.next();
        }
        double val = sc.nextDouble();
        sc.nextLine();
        return val;
    }
}
