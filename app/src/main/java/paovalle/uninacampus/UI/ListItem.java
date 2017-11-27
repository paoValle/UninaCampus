package paovalle.uninacampus.UI;

/**
 * Created by paolo on 27/11/2017.
 */

public class ListItem {
    private String itemTitle;

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public ListItem(String title){
        this.itemTitle = title;
    }
}