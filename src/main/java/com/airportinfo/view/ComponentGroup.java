package com.airportinfo.view;

import java.util.ArrayList;

/**
 * ComponentView container.
 *
 * @author lalaalal
 */
public abstract class ComponentGroup extends ComponentView {
    private final ArrayList<ComponentView> componentViews = new ArrayList<>();

    /**
     * Add child ComponentView.
     *
     * @param componentView Child ComponentView
     */
    public void addComponentView(ComponentView componentView) {
        componentViews.add(componentView);
    }

    /**
     * Do own and children's onLocaleChange.
     */
    @Override
    public void onLocaleChange() {
        super.onLocaleChange();
        for (ComponentView componentView : componentViews)
            componentView.onLocaleChange();
    }
}
