package pl.medos.cmmsApi.model;

import java.util.Arrays;
public class Product {

    private Long id;
    private String name;
    private int index;
    private Material[] bom;
    private Process[] processes;
    private Cost cost;

    public Product() {
    }

    public Product(Long id, String name, int index, Material[] bom, Process[] processes, Cost cost) {
        this.id = id;
        this.name = name;
        this.index = index;
        this.bom = bom;
        this.processes = processes;
        this.cost = cost;
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

