package main;
import main.Globals.PROPERTY;
import main.Globals.SPRITEATTACHMENTS;

public class GridObject {

    // instance vars
    private boolean interactable;
    private boolean pushable;
    private PROPERTY property;
    private SPRITEATTACHMENTS sprite;

    // constructors
    GridObject(boolean interactable, boolean pushable, PROPERTY property, SPRITEATTACHMENTS sprite) {
        this.interactable = interactable;
        this.pushable = pushable;
        this.property = property;
        this.sprite = sprite;
    }

    GridObject(boolean interactable, boolean pushable, SPRITEATTACHMENTS sprite) {
        this.interactable = interactable;
        this.pushable = pushable;
        this.property = PROPERTY.NONE;
        this.sprite = sprite;
    }

    GridObject(PROPERTY property, SPRITEATTACHMENTS sprite) {
        this.interactable = false;
        this.pushable = false;
        this.property = property;
        this.sprite = sprite;
    }

    GridObject() {
        this.interactable = false;
        this.pushable = false;
        this.property = PROPERTY.NONE;
        this.sprite = SPRITEATTACHMENTS.EMPTY;
    }

    // methods
    public void setInteractable(boolean b) { interactable = b; }
    public void setPushable(boolean b) { pushable = b; }
    public void setProperty(PROPERTY p) { property = p; }
    public void setSpriteAttachments(SPRITEATTACHMENTS s) { sprite = s; }

    public boolean getInteractable() { return interactable; }
    public boolean getPushable() { return pushable; }
    public PROPERTY getProperty() { return property; }
    public SPRITEATTACHMENTS getSprite() { return sprite; }
}
