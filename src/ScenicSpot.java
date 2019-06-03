import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;

/***
 * 景点类
 * name为景点名字 可重复
 * id为景点id 不可重复 作为景点唯一标识
 * introduction 为景点介绍
 * pointadjoin为与景点直接相邻的路径的集合 用以判断该景点与其他景点是否直接相邻
 */
class ScenicSpot implements Serializable {
    private String name;
    private long id;
    private int bh;
    private String introduction;
    private Set<Long> pointadjoin = new TreeSet<>();

    ScenicSpot() {
        name = "";
        id = 0;
        introduction = "";
    }

    ScenicSpot(String name, long id, String introduction, int bh) {
        this.introduction = introduction;
        this.id = id;
        this.name = name;
        this.bh = bh;
    }

    void addEdgeTo(long toid) {
        if (!pointadjoin.contains(toid)) pointadjoin.add(toid);
    }

    void delEdgeTo(long toid) {
        if (pointadjoin.contains(toid)) pointadjoin.remove(toid);
    }

    Set getPointadjoin() {
        return pointadjoin;
    }

    int getBh() {
        return bh;
    }

    String getName() {
        return name;
    }

    long getId() {
        return id;
    }

    String getIntroduction() {
        return introduction;
    }

    void setName(String name) {
        this.name = name;
    }

    void setIntroduction(String introduction) {
        this.introduction = introduction;
    }
}
