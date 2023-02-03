//package pl.medos.cmmsApi.model;
//
//import javax.persistence.*;
//
//@Entity
//public class Stock {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    private String name;
//    private int stockRegistry;
//    @ManyToOne
//    @JoinColumn
//    private Part part;
//    @ManyToOne
//    @JoinColumn(name = "material_id")
//    private Material material;
//    @OneToOne
//    @JoinColumn(name = "cost_id")
//    private Cost cost;
//
//    public Stock() {
//    }
//
//    public Stock(Long id, String name, int stockRegistry, Part part, Material material, Cost cost) {
//        this.id = id;
//        this.name = name;
//        this.stockRegistry = stockRegistry;
//        this.part = part;
//        this.material = material;
//        this.cost = cost;
//    }
//
//    public Cost getCost() {
//        return cost;
//    }
//
//    public void setCost(Cost cost) {
//        this.cost = cost;
//    }
//
//    public Material getMaterial() {
//        return material;
//    }
//
//    public void setMaterial(Material material) {
//        this.material = material;
//    }
//
//    public Part getPart() {
//        return part;
//    }
//
//    public void setPart(Part part) {
//        this.part = part;
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public int getStockRegistry() {
//        return stockRegistry;
//    }
//
//    public void setStockRegistry(int stockRegistry) {
//        this.stockRegistry = stockRegistry;
//    }
//
//    @Override
//    public String toString() {
//        return "Stock{" +
//                "id=" + id +
//                ", name='" + name + '\'' +
//                ", stockRegistry=" + stockRegistry +
//                ", part=" + part +
//                ", material=" + material +
//                ", cost=" + cost +
//                '}';
//    }
//}
