 class Edge {
    private long idx,idy;
    private double lenth;

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
}
