package gui;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.components.PositionComponent;

public class PlayerControl extends Component {

    private PositionComponent position;
    private final double BAR_SPEED = 5;

    public void left(){
        position.translateX(-BAR_SPEED);
    }

    public void right(){
        position.translateX(BAR_SPEED);
    }
}
