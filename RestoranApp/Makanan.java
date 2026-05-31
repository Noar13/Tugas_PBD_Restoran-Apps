public class Makanan extends MenuItem {
    private String jenisMakanan;

    public Makanan(String nama, double harga, String jenisMakanan) {
        super(nama, harga, "Makanan");
        this.jenisMakanan = jenisMakanan;
    }

    public String getJenisMakanan() { return jenisMakanan; }
    public void setJenisMakanan(String jenisMakanan) { this.jenisMakanan = jenisMakanan; }

    @Override
    public void tampilMenu() {
        System.out.printf("%-20s | %-10s | %-10s | Rp %,.0f%n",
            getNama(), getKategori(), jenisMakanan, getHarga());
    }
}
