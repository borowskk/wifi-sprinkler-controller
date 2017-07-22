package com.sprinklercontrol;

import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class SprinklerControlView extends View {
    private ScheduleTable table = null;
  
    //public final List<Integer> zones = new ArrayList<>(java.util.Arrays.asList(12, 14));

    public final List<Integer> zones = new ArrayList<>(java.util.Arrays.asList(0, 2, 4, 6, 8, 10, 12, 14));

    private final Text time = new Text();
    
    private final List<SprinklerToggleButton> buttons = new ArrayList<>();
    
    public Thread commThread = null;
    
    public SprinklerControlView(String name) {
        super(name);
        
        SprinklerEventHandler commHandler = new SprinklerEventHandler(this);
        CommTask commTask = new CommTask("192.168.1.186", 8080, zones, commHandler);
        commThread = new Thread(commTask);
        
        VBox root = new VBox();
        root.setSpacing(5);
        root.setPadding(new Insets(5, 5, 5, 5));
        root.setMaxWidth(350);
        
        root.getChildren().add(new HeadingLabel("TIME"));      
        root.getChildren().add(time);

        root.getChildren().add(new HeadingLabel("ON/OFF"));        
        for (int zone : zones) {
            SprinklerToggleButton btn = new SprinklerToggleButton((byte) zone, "Toggle Zone " + String.format("%02d", zone), commTask);
            root.getChildren().add(btn);
            buttons.add(btn);
        }

        root.getChildren().add(new HeadingLabel("Schedule"));
        table = new ScheduleTable(commTask);
        root.getChildren().add(table);

        root.getChildren().add(new HeadingLabel("Add To Schedule"));        
        root.getChildren().add(new AddScheduleWidget(commTask, table));

        commThread.start();

        ScrollPane pane = new ScrollPane();
        
        pane.setContent(root);
        pane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        
        setCenter(pane);
    }

    void clearTable() {
        table.getItems().clear();
    }

    void addToTable(ScheduleEntry entry) {
        if (!table.getItems().contains(entry)) {
            table.getItems().add(entry);
        }
    }

    void removeFromTable(int rowIndex) {
        table.getItems().remove(rowIndex);
    }

    void setTime(int hour, int minute) {
        String hourString = String.format("%02d", hour);
        String minuteString = String.format("%02d", minute);
        time.setText(hourString + ":" + minuteString);
    }
    
    void setButtonState(int zone, boolean toggleOn) {
        int i = zones.indexOf(zone);
        if (i != -1) {
            buttons.get(i).setSelected(toggleOn);
        }
    }
    
    @Override
    protected void updateAppBar(AppBar appBar) {
        appBar.setNavIcon(MaterialDesignIcon.MENU.button(e -> System.out.println("Menu")));
        appBar.setTitleText("DIY Sprinkler Control");
        appBar.getActionItems().add(MaterialDesignIcon.SEARCH.button(e -> System.out.println("Search")));
    }
    
}
