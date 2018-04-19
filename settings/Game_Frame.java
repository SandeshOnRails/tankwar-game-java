package settings;

public class Game_Frame {

    private int frame = 0;
    private int frame_Size;
    private int movement[];
    private Game_Mode gameEvent;
    int pos = 0;

    public Game_Watch(Game_Mode game, int movement[], int frame_Size) {
        this.gameEvent = game;
        this.movement = movement;
        this.frame_Size = frame_Size;
    }

    public void play() {
        if (frame < movement[movement.length-1]) {
            frame += frame_Size;
            if (frame == movement[pos]) {
                gameEvent.setValue(pos++);
            }
        } else {
            frame = pos = 0;
        }
    }
}
