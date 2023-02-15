//package pl.medos.cmmsApi.repository.entity;
//
//@Entity
//public class SensorEntity {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    private String name;
//    private String type;
//    private Double readings;
//    private String status;
//    private Boolean isConnected;
//
//    public SensorEntity() {
//    }
//
//    public SensorEntity(Long id, String name, String type, Double readings, String status, Boolean isConnected) {
//        this.id = id;
//        this.name = name;
//        this.type = type;
//        this.readings = readings;
//        this.status = status;
//        this.isConnected = isConnected;
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
//    public String getType() {
//        return type;
//    }
//
//    public void setType(String type) {
//        this.type = type;
//    }
//
//    public Double getReadings() {
//        return readings;
//    }
//
//    public void setReadings(Double readings) {
//        this.readings = readings;
//    }
//
//    public String getStatus() {
//        return status;
//    }
//
//    public void setStatus(String status) {
//        this.status = status;
//    }
//
//    public Boolean getIsConnected() {
//        return isConnected;
//    }
//
//    public void setIsConnected(Boolean isConnected) {
//        this.isConnected = isConnected;
//    }
//
//    @Override
//    public String toString() {
//        return "Sensor{" +
//                "id=" + id +
//                ", name='" + name + '\'' +
//                ", type='" + type + '\'' +
//                ", readings=" + readings +
//                ", status='" + status + '\'' +
//                ", isConnected=" + isConnected +
//                '}';
//    }
//}
