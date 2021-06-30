package view.clocks;

import eu.hansolo.medusa.Gauge;
import eu.hansolo.medusa.GaugeBuilder;
import eu.hansolo.medusa.Section;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.HashMap;

public class ClockController {

    @FXML
    BorderPane airspeed, altitude, heading, yaw, roll, pitch;
    public HashMap<String,Gauge> gaugeMap;

    public Gauge gauge1;
    public Gauge gauge2;
    public Gauge gauge3;
    public Gauge gauge4;
    public Gauge gauge5;
    public Gauge gauge6;


    public void createClocks(){
        gauge1 = GaugeBuilder.create()
                .skinType(Gauge.SkinType.MODERN)
                .prefSize(100, 100)
                .sections(new Section(85, 90, "", Color.rgb(204, 0, 0, 0.5)),
                        new Section(90, 95, "", Color.rgb(204, 0, 0, 0.75)),
                        new Section(95, 100, "", Color.rgb(204, 0, 0)))
                .sectionTextVisible(true)
                .minValue(0)
                .maxValue(100)
                .title("airSpeed")
                .unit("UNIT")
                .animated(true)
                .build();
        airspeed.setCenter(gauge1);

        gauge2 = GaugeBuilder.create()
                .skinType(Gauge.SkinType.MODERN)
                .prefSize(100, 100)
                .sections(new Section(85, 90, "", Color.rgb(204, 0, 0, 0.5)),
                        new Section(90, 95, "", Color.rgb(204, 0, 0, 0.75)),
                        new Section(95, 100, "", Color.rgb(204, 0, 0)))
                .sectionTextVisible(true)
                .minValue(-1000)
                .maxValue(860)
                .title("altitude")
                .unit("UNIT")
                .animated(true)
                .build();
        altitude.setCenter(gauge2);

        gauge3 = GaugeBuilder.create()
                .skinType(Gauge.SkinType.MODERN)
                .prefSize(100, 100)
                .sections(new Section(85, 90, "", Color.rgb(204, 0, 0, 0.5)),
                        new Section(90, 95, "", Color.rgb(204, 0, 0, 0.75)),
                        new Section(95, 100, "", Color.rgb(204, 0, 0)))
                .sectionTextVisible(true)
                .minValue(0)
                .maxValue(350)
                .title("heading")
                .unit("UNIT")
                .animated(true)
                .build();
        heading.setCenter(gauge3);

        gauge4 = GaugeBuilder.create()
                .skinType(Gauge.SkinType.MODERN)
                .prefSize(100, 100)
                .sections(new Section(85, 90, "", Color.rgb(204, 0, 0, 0.5)),
                        new Section(90, 95, "", Color.rgb(204, 0, 0, 0.75)),
                        new Section(95, 100, "", Color.rgb(204, 0, 0)))
                .sectionTextVisible(true)
                .minValue(-30)
                .maxValue(90)
                .title("yaw")
                .unit("UNIT")
                .animated(true)
                .build();
        yaw.setCenter(gauge4);

        gauge5 = GaugeBuilder.create()
                .skinType(Gauge.SkinType.MODERN)
                .prefSize(100, 100)
                .sections(new Section(85, 90, "", Color.rgb(204, 0, 0, 0.5)),
                        new Section(90, 95, "", Color.rgb(204, 0, 0, 0.75)),
                        new Section(95, 100, "", Color.rgb(204, 0, 0)))
                .sectionTextVisible(true)
                .minValue(-38)
                .maxValue(40)
                .title("roll")
                .unit("UNIT")
                .animated(true)
                .build();
        roll.setCenter(gauge5);

        gauge6 = GaugeBuilder.create()
                .skinType(Gauge.SkinType.MODERN)
                .prefSize(100, 100)
                .sections(new Section(85, 90, "", Color.rgb(204, 0, 0, 0.5)),
                        new Section(90, 95, "", Color.rgb(204, 0, 0, 0.75)),
                        new Section(95, 100, "", Color.rgb(204, 0, 0)))
                .sectionTextVisible(true)
                .minValue(-10)
                .maxValue(17)
                .title("pitch")
                .unit("UNIT")
                .animated(true)
                .build();
        pitch.setCenter(gauge6);

        gaugeMap = new HashMap<>();
        gaugeMap.put("airSpeed",gauge1);
        gaugeMap.put("altitude",gauge2);
        gaugeMap.put("heading",gauge3);
        gaugeMap.put("yaw",gauge4);
        gaugeMap.put("roll",gauge5);
        gaugeMap.put("pitch",gauge6);
    }

    public void updateMinMax(HashMap<String, ArrayList<Integer>> setting_map){
        gaugeMap.get("airSpeed").setMaxValue(setting_map.get("airSpeed").get(1));
        gaugeMap.get("airSpeed").setMinValue(setting_map.get("airSpeed").get(2));
        gaugeMap.get("airSpeed").setValue(gaugeMap.get("airSpeed").getMinValue()>0?gaugeMap.get("airSpeed").getMinValue():0);
        gaugeMap.get("altitude").setMaxValue(setting_map.get("altitude").get(1));
        gaugeMap.get("altitude").setMinValue(setting_map.get("altitude").get(2));
        gaugeMap.get("altitude").setValue(gaugeMap.get("altitude").getMinValue()>0?gaugeMap.get("altitude").getMinValue():0);
        gaugeMap.get("heading").setMaxValue(setting_map.get("heading").get(1));
        gaugeMap.get("heading").setMinValue(setting_map.get("heading").get(2));
        gaugeMap.get("heading").setValue(gaugeMap.get("heading").getMinValue()>0?gaugeMap.get("heading").getMinValue():0);
        gaugeMap.get("yaw").setMaxValue(setting_map.get("yaw").get(1));
        gaugeMap.get("yaw").setMinValue(setting_map.get("yaw").get(2));
        gaugeMap.get("yaw").setValue(gaugeMap.get("yaw").getMinValue()>0?gaugeMap.get("yaw").getMinValue():0);
        gaugeMap.get("roll").setMaxValue(setting_map.get("roll").get(1));
        gaugeMap.get("roll").setMinValue(setting_map.get("roll").get(2));
        gaugeMap.get("roll").setValue(gaugeMap.get("roll").getMinValue()>0?gaugeMap.get("roll").getMinValue():0);
        gaugeMap.get("pitch").setMaxValue(setting_map.get("pitch").get(1));
        gaugeMap.get("pitch").setMinValue(setting_map.get("pitch").get(2));
        gaugeMap.get("pitch").setValue(gaugeMap.get("pitch").getMinValue()>0?gaugeMap.get("pitch").getMinValue():0);
    }

}