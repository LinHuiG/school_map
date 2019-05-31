import java.io.Serializable;

class Edge implements Serializable ,Comparable{
    private long idx,idy;
    private double lenth;
    private int bh;
     double getLenth() {
        return lenth;
    }
    long getIdx(){
         return idx;
    }
    long getIdy(){
         return idy;
    }
     Edge(long idx,long idy,double lenth)
    {
        this.lenth=lenth;
        this.idx=idx;
        this.idy=idy;
    }

    Edge(int bh,double lenth)
    {
        this.bh=bh;
        this.lenth=lenth;
    }

    public int getBh() {
        return bh;
    }

    @Override
    public int compareTo(Object o) {
        return Double.compare(this.lenth , ((Edge)o).getLenth());
    }
}
