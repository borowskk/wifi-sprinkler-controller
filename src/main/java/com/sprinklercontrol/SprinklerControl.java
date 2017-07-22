package com.sprinklercontrol;

import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.visual.Swatch;
import javafx.scene.Scene;

public class SprinklerControl extends MobileApplication {

    public static final String SPRINKLER_VIEW_NAME = HOME_VIEW;

    @Override
    public void init() {
        addViewFactory(SPRINKLER_VIEW_NAME, () -> new SprinklerControlView(SPRINKLER_VIEW_NAME));
    }
    
    @Override
    public void stop(){
        SprinklerControlView view = (SprinklerControlView)getView();
        
        if (view.commThread != null) {
            view.commThread.stop();
        }
    }

    @Override
    public void postInit(Scene scene) {
        Swatch.BLUE.assignTo(scene);
    }
}
