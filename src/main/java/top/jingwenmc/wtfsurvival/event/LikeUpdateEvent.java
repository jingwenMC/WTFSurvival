package top.jingwenmc.wtfsurvival.event;

public class LikeUpdateEvent extends CommonEventBase{
    String uid;
    long before;
    long after;

    public LikeUpdateEvent(String uid,long before,long after) {
        this.uid = uid;
        this.before = before;
        this.after = after;
    }


    public String getUid() {
        return uid;
    }

    public long getBefore() {
        return before;
    }

    public long getAfter() {
        return after;
    }
}
