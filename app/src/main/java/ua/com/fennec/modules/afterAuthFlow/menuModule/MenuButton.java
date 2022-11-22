package ua.com.fennec.modules.afterAuthFlow.menuModule;

import android.content.Context;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;

import ua.com.fennec.R;

public enum MenuButton {
    products,
    productsGroup,
    profile,
    staff,
    statistic,
    historyOfSale,
    spends,
    points,
    shops,
    panel,
    logout;

    public MenuButtonData getTitle(Context context) {

        String title;
        Drawable image;
        switch (this) {
            case products:
                title = context.getString(R.string.products);
                image = context.getDrawable(R.drawable.image_menu_product);
                break;
            case productsGroup:
                title = context.getString(R.string.product_groups);
                image = context.getDrawable(R.drawable.image_menu_product_groups);
                break;
            case profile:
                title = context.getString(R.string.profile);
                image = context.getDrawable(R.drawable.image_menu_profile);
                break;
            case staff:
                title = context.getString(R.string.staff);
                image = context.getDrawable(R.drawable.image_menu_staff);
                break;
            case statistic:
                title = context.getString(R.string.statistics);
                image = context.getDrawable(R.drawable.image_menu_statistic);
                break;
            case historyOfSale:
                title = context.getString(R.string.sales_history);
                image = context.getDrawable(R.drawable.image_menu_history);
                break;
            case spends:
                title = context.getString(R.string.expenses);
                image = context.getDrawable(R.drawable.image_menu_spends);
                break;
            case points:
                title = context.getString(R.string.shops);
                image = context.getDrawable(R.drawable.image_menu_points);
                break;
            case shops:
                title = context.getString(R.string.trading_networks);
                image = context.getDrawable(R.drawable.image_menu_shops);
                break;
            case panel:
                title = context.getString(R.string.panel);
                image = context.getDrawable(R.drawable.image_menu_panel);
                break;
            case logout:
                title = context.getString(R.string.logout);
                image = context.getDrawable(R.drawable.image_menu_logout);
                break;
            default:
                title = "asdasda";
                image = context.getDrawable(R.drawable.image_menu_product);
                break;
        }
        return new MenuButtonData(title, image);
    }


    public static ArrayList<MenuButton> getArray() {
        ArrayList<MenuButton> arrayList = new ArrayList<>();
        arrayList.add(MenuButton.products);
        arrayList.add(MenuButton.productsGroup);
        arrayList.add(MenuButton.profile);
        arrayList.add(MenuButton.staff);
        arrayList.add(MenuButton.statistic);
        arrayList.add(MenuButton.historyOfSale);
        arrayList.add(MenuButton.spends);
        arrayList.add(MenuButton.points);
        arrayList.add(MenuButton.shops);
        arrayList.add(MenuButton.panel);
        arrayList.add(MenuButton.logout);
        return arrayList;
    }
}

class MenuButtonData {
    public String title;
    public Drawable image;

    public MenuButtonData(String title, Drawable image) {
        this.title = title;
        this.image = image;
    }
}
