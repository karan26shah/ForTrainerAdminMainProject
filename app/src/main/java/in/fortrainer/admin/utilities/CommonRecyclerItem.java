package in.fortrainer.admin.utilities;

import java.util.ArrayList;
import java.util.List;

import retrofit2.http.POST;

/**
 * Created by HBB20 on 7/14/15.
 * Recycler view provides flexibility to show items of multiple types....
 * this class will give flexibility to attach type of particular item so that it provide ease for providing layout.
 * later on along with itemType, many other property can be included as option.
 */
public class CommonRecyclerItem {
    private ItemType itemType;
    private Object item;
    private List<Object> options;

    public CommonRecyclerItem(ItemType itemType, Object item) {
        this.itemType = itemType;
        this.item = item;
    }

    public CommonRecyclerItem(ItemType itemType, Object item, Object... options) {
        this.itemType = itemType;
        this.item = item;
        this.options = new ArrayList<>();
        for (Object option : options) {
            this.options.add(option);
        }
    }

    public CommonRecyclerItem(ItemType itemType, Object item, List<Object> options) {
        this.itemType = itemType;
        this.item = item;
        this.options = options;
    }

    public static List<CommonRecyclerItem> generate(ItemType itemType, List<?> itemList, Object... options) {
        List<CommonRecyclerItem> commonRecyclerItems = new ArrayList<>();
        for (Object item : itemList) {
            commonRecyclerItems.add(new CommonRecyclerItem(itemType, item, options));
        }
        return commonRecyclerItems;
    }

    public static List<CommonRecyclerItem> generate(ItemType itemType, Object item, Object... options) {
        List<CommonRecyclerItem> commonRecyclerItems = new ArrayList<>();
        commonRecyclerItems.add(new CommonRecyclerItem(itemType, item, options));
        return commonRecyclerItems;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }

    public Object getItem() {
        return item;
    }

    public void setItem(Object item) {
        this.item = item;
    }

    public List<Object> getOptions() {
        return options;
    }

    public void setOptions(List<Object> options) {
        this.options = options;
    }

    public enum ItemType {
        LOADING(0),
        BANNER(1),
        POSTS(2),
        CARD_ACK(3),
        ORDERS(4),
        EVENTS(5),
        PRODUCTS(6),
        APPS(7);


        private final int id;

        ItemType(int value) {
            this.id = value;
        }

        public int getId() {
            return id;
        }

        public boolean matches(int id) {
            return this.id == id;
        }
    }
}

