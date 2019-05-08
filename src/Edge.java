public class Edge {
    private long idx,idy;
    private double lenth;

    public double getLenth() {
        return lenth;
    }
    public Edge(long idx,long idy,double lenth)
    {
        this.lenth=lenth;
        this.idx=idx;
        this.idy=idy;
    }
}
