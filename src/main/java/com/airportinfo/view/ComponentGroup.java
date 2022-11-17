package com.airportinfo.view;

import java.util.ArrayList;

/**
 * ComponentView container.
 *
 * @author lalaalal
 */
public abstract class ComponentGroup extends ComponentView {
    protected final ArrayList<ComponentView> componentViews = new ArrayList<>();

    /**
     * Add child ComponentView.
     *
     * @param componentView Child ComponentView
     */
    public void addComponentView(ComponentView componentView) {
        componentViews.add(componentView);
    }

    /**
     * Remove child ComponentView.
     *
     * @param componentView Child ComponentView to remove.
     */
    public void removeComponentView(ComponentView componentView) {
        componentViews.remove(componentView);
    }

    /**
     * Number of component views.
     *
     * @return Number of component views.
     */
    public int getNumComponents() {
        return componentViews.size();
    }

    /**
     * Remove all component views.
     */
    public void clear() {
        componentViews.clear();
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

    /**
     * Do own and children's onThemeChange.
     */
    @Override
    public void onThemeChange() {
        super.onThemeChange();
        for (ComponentView componentView : componentViews)
            componentView.onThemeChange();
    }
}
