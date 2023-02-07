package pl.medos.cmmsApi.model;

public class Part {

    private Supplier supplier;
    private Material materials;
    private Process processes;
    private int quantity;
    private Cost cost;

    public Part() {
    }

    public Part(Supplier supplier, Material materials, Process processes, int quantity, Cost cost) {
        this.supplier = supplier;
        this.materials = materials;
        this.processes = processes;
        this.quantity = quantity;
        this.cost = cost;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Material getMaterials() {
        return materials;
    }

    public void setMaterials(Material materials) {
        this.materials = materials;
    }

    public Process getProcesses() {
        return processes;
    }

    public void setProcesses(Process processes) {
        this.processes = processes;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Cost getCost() {
        return cost;
    }

    public void setCost(Cost cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "Part{" +
                "supplier=" + supplier +
                ", materials=" + materials +
                ", processes=" + processes +
                ", quantity=" + quantity +
                ", cost=" + cost +
                '}';
    }
}
