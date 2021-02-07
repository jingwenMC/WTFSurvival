package top.jingwenmc.wtfsurvival.events;

public class LikeUpdateEvent extends CommonEventBase{
    long before;
    long after;

    public LikeUpdateEvent(long before,long after) {
        this.before = before;
        this.after = after;
    }

    public long getBefore() {
        return before;
    }

    public long getAfter() {
        return after;
    }
}
