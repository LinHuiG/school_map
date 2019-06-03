import java.io.Serializable;

/***
 * 边类
 * 作为保存在表中的边时，参数有两端点与路径长度
 * 作为查询路径中的边类时，内有编号bh 该对象作为起点到编号为bh点的边
 * 已重写compareTo算法，以长度为compare的对象
 */
class Edge implements Serializable, Comparable {
    private long idx, idy;
    private double lenth;
    private int bh;

    double getLenth() {
        return lenth;
    }

    long getIdx() {
        return idx;
    }

    long getIdy() {
        return idy;
    }

    Edge(long idx, long idy, double lenth) {
        this.lenth = lenth;
        this.idx = idx;
        this.idy = idy;
    }

    Edge(int bh, double lenth) {
        this.bh = bh;
        this.lenth = lenth;
    }

    public int getBh() {
        return bh;
    }

    @Override
    public int compareTo(Object o) {
        return Double.compare(this.lenth, ((Edge) o).getLenth());
    }
}
