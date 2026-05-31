import java.io.*;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FileManager {

    // Simpan daftar menu ke file
    public static void simpanMenu(ArrayList<MenuItem> daftarMenu, String filename) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            for (MenuItem item : daftarMenu) {
                if (item instanceof Makanan) {
                    Makanan m = (Makanan) item;
                    bw.write("Makanan|" + m.getNama() + "|" + m.getHarga() + "|" + m.getJenisMakanan());
                } else if (item instanceof Minuman) {
                    Minuman m = (Minuman) item;
                    bw.write("Minuman|" + m.getNama() + "|" + m.getHarga() + "|" + m.getJenisMinuman());
                } else if (item instanceof Diskon) {
                    Diskon d = (Diskon) item;
                    bw.write("Diskon|" + d.getNama() + "|0|" + d.getPersenDiskon());
                }
                bw.newLine();
            }
            System.out.println("Menu berhasil disimpan ke file: " + filename);
        } catch (IOException e) {
            System.out.println("Gagal menyimpan menu: " + e.getMessage());
        }
    }

    // Muat daftar menu dari file
    public static ArrayList<MenuItem> muatMenu(String filename) {
        ArrayList<MenuItem> daftarMenu = new ArrayList<>();
        File file = new File(filename);
        if (!file.exists()) {
            System.out.println("File menu tidak ditemukan. Memulai dengan menu default.");
            return daftarMenu;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length < 4) continue;
                String tipe = parts[0];
                String nama = parts[1];
                double harga = Double.parseDouble(parts[2]);
                String extra = parts[3];

                if (tipe.equals("Makanan")) {
                    daftarMenu.add(new Makanan(nama, harga, extra));
                } else if (tipe.equals("Minuman")) {
                    daftarMenu.add(new Minuman(nama, harga, extra));
                } else if (tipe.equals("Diskon")) {
                    daftarMenu.add(new Diskon(nama, Double.parseDouble(extra)));
                }
            }
            System.out.println("Menu berhasil dimuat dari file: " + filename);
        } catch (IOException e) {
            System.out.println("Gagal memuat menu: " + e.getMessage());
        }
        return daftarMenu;
    }

    // Simpan struk ke file
    public static void simpanStruk(String struk, String namaPelanggan) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        String waktu = LocalDateTime.now().format(fmt);
        String filename = "struk_" + namaPelanggan.replaceAll(" ", "_") + "_" + waktu + ".txt";
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            bw.write(struk);
            System.out.println("Struk berhasil disimpan ke file: " + filename);
        } catch (IOException e) {
            System.out.println("Gagal menyimpan struk: " + e.getMessage());
        }
    }
}
