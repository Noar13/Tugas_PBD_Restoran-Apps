public class Diskon extends MenuItem {
    private double persenDiskon;

    public Diskon(String nama, double persenDiskon) {
        super(nama, 0, "Diskon");
        this.persenDiskon = persenDiskon;
    }

    public double getPersenDiskon() { return persenDiskon; }
    public void setPersenDiskon(double persenDiskon) { this.persenDiskon = persenDiskon; }

    public double hitungDiskon(double subtotal) {
        return subtotal * persenDiskon;
    }

    @Override
    public void tampilMenu() {
        System.out.printf("%-20s | %-10s | Diskon: %.0f%%%n",
            getNama(), getKategori(), persenDiskon * 100);
    }
}
