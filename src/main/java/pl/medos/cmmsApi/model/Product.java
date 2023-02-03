package pl.medos.cmmsApi.model;

import pl.medos.cmmsApi.utility.Cost;

import javax.persistence.*;
import java.util.Arrays;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int index;
    @Transient
    private Material[] bom;
    @Transient
    private Process[] processes;
    @OneToOne
    @JoinColumn(name = "cost_id")
    private Cost cost;

    public Product() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Material[] getBom() {
        return bom;
    }

    public void setBom(Material[] bom) {
        this.bom = bom;
    }

    public Process[] getProcesses() {
        return processes;
    }

    public void setProcesses(Process[] processes) {
        this.processes = processes;
    }

    public Cost getCost() {
        return cost;
    }

    public void setCost(Cost cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", index=" + index +
                ", bom=" + Arrays.toString(bom) +
                ", processes=" + Arrays.toString(processes) +
                ", cost=" + cost +
                '}';
    }
}

