import java.util.ArrayList;

public class Menu {
    private ArrayList<MenuItem> daftarMenu;

    public Menu() {
        daftarMenu = new ArrayList<>();
    }

    public void tambahItem(MenuItem item) {
        daftarMenu.add(item);
        System.out.println("Menu '" + item.getNama() + "' berhasil ditambahkan!");
    }

    public void hapusItem(int index) throws Exception {
        if (index < 0 || index >= daftarMenu.size()) {
            throw new Exception("Nomor menu tidak valid! Menu tidak ditemukan.");
        }
        String nama = daftarMenu.get(index).getNama();
        daftarMenu.remove(index);
        System.out.println("Menu '" + nama + "' berhasil dihapus!");
    }

    public void ubahHarga(int index, double hargaBaru) throws Exception {
        if (index < 0 || index >= daftarMenu.size()) {
            throw new Exception("Nomor menu tidak valid! Menu tidak ditemukan.");
        }
        daftarMenu.get(index).setHarga(hargaBaru);
        System.out.println("Harga berhasil diubah!");
    }

    public MenuItem getItem(int index) throws Exception {
        if (index < 0 || index >= daftarMenu.size()) {
            throw new Exception("Nomor menu tidak valid! Menu tidak ditemukan.");
        }
        return daftarMenu.get(index);
    }

    public ArrayList<MenuItem> getDaftarMenu() {
        return daftarMenu;
    }

    public int getSize() {
        return daftarMenu.size();
    }

    public void tampilSemuaMenu() {
        if (daftarMenu.isEmpty()) {
            System.out.println("Menu masih kosong!");
            return;
        }
        System.out.println("\n╔══════════════════════════════════════════════════════╗");
        System.out.println("║                   DAFTAR MENU                       ║");
        System.out.println("╠══════════════════════════════════════════════════════╣");
        System.out.printf("║ %-3s %-20s %-10s %-10s %-10s ║%n", "No", "Nama", "Kategori", "Jenis", "Harga");
        System.out.println("╠══════════════════════════════════════════════════════╣");
        for (int i = 0; i < daftarMenu.size(); i++) {
            MenuItem item = daftarMenu.get(i);
            System.out.print("║ " + (i + 1) + ". ");
            item.tampilMenu();
        }
        System.out.println("╚══════════════════════════════════════════════════════╝");
    }
}
