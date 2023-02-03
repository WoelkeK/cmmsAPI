package pl.medos.cmmsApi.model;

import pl.medos.cmmsApi.utility.Cost;

import javax.persistence.*;
import java.util.Arrays;

@Entity
public class Part {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @OneToOne(optional = false)
    @JoinColumn(name = "supplier_id", nullable = false)

    private Supplier supplier;
    @Transient
    private Material[] materials;
    @Transient
    private Process[] processes;
    private int quantity;
    @OneToOne
    @JoinColumn(name = "cost_id")
    private Cost cost;

    public Part() {
    }

    public Part(Supplier supplier, Material[] materials, Process[] processes, int quantity, Cost cost) {
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

    public Material[] getMaterials() {
        return materials;
    }

    public void setMaterials(Material[] materials) {
        this.materials = materials;
    }

    public Process[] getProcesses() {
        return processes;
    }

    public void setProcesses(Process[] processes) {
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
                ", materials=" + Arrays.toString(materials) +
                ", processes=" + Arrays.toString(processes) +
                ", quantity=" + quantity +
                ", cost=" + cost +
                '}';
    }
}
