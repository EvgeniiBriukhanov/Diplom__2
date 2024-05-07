package order;

import java.util.List;

public class OrderInfo {
    private List<String > ingredients;

    public OrderInfo(List<String > ingredients) {
        this.ingredients = ingredients;
    }
    public OrderInfo() {}

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }
}
