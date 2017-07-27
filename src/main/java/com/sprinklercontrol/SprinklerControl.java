/*
 * Copyright 2017 Kyle Borowski
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sprinklercontrol;

import com.sprinklercontrol.view.SprinklerControlView;
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
        
        if (view.commTask != null) {
            view.commTask.stop();
        }
    }

    @Override
    public void postInit(Scene scene) {
        Swatch.BLUE.assignTo(scene);
    }
}
