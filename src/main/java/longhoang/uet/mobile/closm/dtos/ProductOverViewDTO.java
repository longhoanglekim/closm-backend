package longhoang.uet.mobile.closm.dtos;


public class ProductOverviewDTO {
    private String name;
    private double price;
    private String category;
    private String image;
    private double rating;

    public ProductOverviewDTO(String name, double price, String category, String image, double rating) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.image = image;
        this.rating = rating;
    }
}
