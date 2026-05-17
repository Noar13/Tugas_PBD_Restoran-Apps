import java.util.ArrayList;
import java.util.Scanner;

public class RestoranApp {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        // DATA MENU
        ArrayList<String> menu = new ArrayList<>();
        ArrayList<Integer> harga = new ArrayList<>();
        ArrayList<String> kategori = new ArrayList<>();

        // MENU AWAL
        menu.add("Nasi Padang");
        harga.add(25000);
        kategori.add("Makanan");

        menu.add("Mie Goreng");
        harga.add(20000);
        kategori.add("Makanan");

        menu.add("Ayam Bakar");
        harga.add(30000);
        kategori.add("Makanan");

        menu.add("Es Teh");
        harga.add(10000);
        kategori.add("Minuman");

        menu.add("Jus Jeruk");
        harga.add(15000);
        kategori.add("Minuman");

        boolean jalan = true;

        while (jalan) {

            System.out.println("\n================================");
            System.out.println("       RESTORAN SEDERHANA       ");
            System.out.println("================================");

            System.out.println("1. Menu Pelanggan");
            System.out.println("2. Pengelolaan Menu");
            System.out.println("0. Keluar");

            System.out.print("Pilih menu : ");

            int pilih;

            while (!input.hasNextInt()) {
                System.out.print("Input salah! Pilih angka : ");
                input.next();
            }

            pilih = input.nextInt();
            input.nextLine();

            switch (pilih) {

                case 1:
                    menuPelanggan(input, menu, harga, kategori);
                    break;

                case 2:
                    pengelolaanMenu(input, menu, harga, kategori);
                    break;

                case 0:
                    jalan = false;
                    System.out.println("Program selesai.");
                    break;

                default:
                    System.out.println("Pilihan tidak tersedia!");
            }
        }

        input.close();
    }

    // ================= MENU PELANGGAN =================
    public static void menuPelanggan(
            Scanner input,
            ArrayList<String> menu,
            ArrayList<Integer> harga,
            ArrayList<String> kategori) {

        String[] pesananMenu = new String[4];
        int[] pesananJumlah = new int[4];
        int[] pesananSubtotal = new int[4];

        System.out.println("\n========== MENU ==========");

        for (int i = 0; i < menu.size(); i++) {
            System.out.println((i + 1) + ". "
                    + menu.get(i)
                    + " | "
                    + kategori.get(i)
                    + " | Rp "
                    + harga.get(i));
        }

        int jumlahPesanan;

        while (true) {

            System.out.print("\nJumlah menu yang ingin dipesan (max 4): ");

            if (input.hasNextInt()) {

                jumlahPesanan = input.nextInt();
                input.nextLine();

                if (jumlahPesanan >= 1 && jumlahPesanan <= 4) {
                    break;
                }
            } else {
                input.next();
            }

            System.out.println("Input tidak valid!");
        }

        int total = 0;
        int totalItem = 0;
        double diskon = 0;
        double promoMinuman = 0;

        for (int i = 0; i < jumlahPesanan; i++) {

            System.out.println("\nPesanan ke-" + (i + 1));

            System.out.print("Nama Menu : ");
            String namaMenu = input.nextLine();

            System.out.print("Jumlah : ");
            int jumlah = input.nextInt();
            input.nextLine();

            boolean ditemukan = false;

            for (int j = 0; j < menu.size(); j++) {

                if (namaMenu.equalsIgnoreCase(menu.get(j))) {

                    pesananMenu[i] = menu.get(j);
                    pesananJumlah[i] = jumlah;

                    int subtotal = harga.get(j) * jumlah;

                    // PROMO MINUMAN
                    if (kategori.get(j).equalsIgnoreCase("Minuman")
                            && subtotal > 50000) {

                        int gratis = jumlah / 2;

                        subtotal = (jumlah - gratis)
                                * harga.get(j);

                        promoMinuman += gratis
                                * harga.get(j);
                    }

                    pesananSubtotal[i] = subtotal;

                    total += subtotal;
                    totalItem += jumlah;

                    ditemukan = true;
                    break;
                }
            }

            if (!ditemukan) {
                System.out.println("Menu tidak ditemukan!");
            }
        }

        // DISKON
        if (total > 100000) {
            diskon = total * 0.10;
        }

        double totalSetelahDiskon = total - diskon;

        // PAJAK
        double pajak = totalSetelahDiskon * 0.10;

        // PELAYANAN
        int pelayanan = 20000;

        double grandTotal =
                totalSetelahDiskon
                        + pajak
                        + pelayanan;

        // CETAK STRUK
        System.out.println("\n================================");
        System.out.println("         STRUK PEMBAYARAN       ");
        System.out.println("================================");

        for (int i = 0; i < jumlahPesanan; i++) {

            if (pesananMenu[i] != null) {

                System.out.printf("%d %-18s Rp %,d\n",
                        pesananJumlah[i],
                        pesananMenu[i],
                        pesananSubtotal[i]);
            }
        }

        System.out.println("--------------------------------");

        System.out.println("Total Item : " + totalItem);

        System.out.printf("Subtotal : Rp %,.0f\n",
                total * 1.0);

        if (diskon > 0) {
            System.out.printf("Diskon 10%% : -Rp %,.0f\n",
                    diskon);
        }

        if (promoMinuman > 0) {
            System.out.printf("Promo Minuman : -Rp %,.0f\n",
                    promoMinuman);
        }

        System.out.printf("Pajak 10%% : Rp %,.0f\n",
                pajak);

        System.out.printf("Pelayanan : Rp %,d\n",
                pelayanan);

        System.out.println("--------------------------------");

        System.out.printf("Grand Total : Rp %,.0f\n",
                grandTotal);

        System.out.println("================================");
    }

    // ================= PENGELOLAAN MENU =================
    public static void pengelolaanMenu(
            Scanner input,
            ArrayList<String> menu,
            ArrayList<Integer> harga,
            ArrayList<String> kategori) {

        boolean kembali = false;

        while (!kembali) {

            System.out.println("\n===== PENGELOLAAN MENU =====");

            System.out.println("1. Lihat Menu");
            System.out.println("2. Tambah Menu");
            System.out.println("3. Ubah Harga Menu");
            System.out.println("4. Hapus Menu");
            System.out.println("0. Kembali");

            System.out.print("Pilih : ");

            int pilih;

            while (!input.hasNextInt()) {
                System.out.print("Input salah! : ");
                input.next();
            }

            pilih = input.nextInt();
            input.nextLine();

            switch (pilih) {

                case 1:

                    tampilMenu(menu, harga, kategori);
                    break;

                case 2:

                    System.out.print("Nama menu baru : ");
                    String namaBaru = input.nextLine();

                    System.out.print("Harga : ");
                    int hargaBaru = input.nextInt();
                    input.nextLine();

                    System.out.print("Kategori (Makanan/Minuman) : ");
                    String kategoriBaru = input.nextLine();

                    menu.add(namaBaru);
                    harga.add(hargaBaru);
                    kategori.add(kategoriBaru);

                    System.out.println("Menu berhasil ditambahkan!");
                    break;

                case 3:

                    tampilMenu(menu, harga, kategori);

                    System.out.print("Pilih nomor menu : ");
                    int ubah = input.nextInt();
                    input.nextLine();

                    if (ubah >= 1 && ubah <= menu.size()) {

                        System.out.print("Harga baru : ");
                        int hargaEdit = input.nextInt();
                        input.nextLine();

                        System.out.print(
                                "Yakin ingin mengubah? (Ya/Tidak) : ");

                        String konfirmasi =
                                input.nextLine();

                        if (konfirmasi.equalsIgnoreCase("Ya")) {

                            harga.set(ubah - 1, hargaEdit);

                            System.out.println(
                                    "Harga berhasil diubah!");
                        } else {

                            System.out.println(
                                    "Perubahan dibatalkan.");
                        }
                    } else {

                        System.out.println("Nomor menu tidak valid!");
                    }

                    break;

                case 4:

                    tampilMenu(menu, harga, kategori);

                    System.out.print("Pilih nomor menu : ");
                    int hapus = input.nextInt();
                    input.nextLine();

                    if (hapus >= 1 && hapus <= menu.size()) {

                        System.out.print(
                                "Yakin ingin menghapus? (Ya/Tidak) : ");

                        String konfirmasi =
                                input.nextLine();

                        if (konfirmasi.equalsIgnoreCase("Ya")) {

                            menu.remove(hapus - 1);
                            harga.remove(hapus - 1);
                            kategori.remove(hapus - 1);

                            System.out.println(
                                    "Menu berhasil dihapus!");
                        } else {

                            System.out.println(
                                    "Penghapusan dibatalkan.");
                        }
                    } else {

                        System.out.println("Nomor menu tidak valid!");
                    }

                    break;

                case 0:

                    kembali = true;
                    break;

                default:

                    System.out.println("Pilihan tidak tersedia!");
            }
        }
    }

    // ================= TAMPIL MENU =================
    public static void tampilMenu(
            ArrayList<String> menu,
            ArrayList<Integer> harga,
            ArrayList<String> kategori) {

        System.out.println("\n===== DAFTAR MENU =====");

        for (int i = 0; i < menu.size(); i++) {

            System.out.println((i + 1)
                    + ". "
                    + menu.get(i)
                    + " | "
                    + kategori.get(i)
                    + " | Rp "
                    + harga.get(i));
        }
    }
}