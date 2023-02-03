package pl.medos.cmmsApi.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Entity
public class Cost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double netCost;
    private double grossCost;

    public Cost() {
    }

    public Cost(Long id, double netCost, double grossCost) {
        this.id = id;
        this.netCost = netCost;
        this.grossCost = grossCost;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getNetCost() {
        return netCost;
    }

    public void setNetCost(double netCost) {
        this.netCost = netCost;
    }

    public double getGrossCost() {
        return grossCost;
    }

    public void setGrossCost(double grossCost) {
        this.grossCost = grossCost;
    }

    @Override
    public String toString() {
        return "Cost{" +
                "id=" + id +
                ", netCost=" + netCost +
                ", grossCost=" + grossCost +
                '}';
    }
}
