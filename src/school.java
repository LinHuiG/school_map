import java.util.*;

public class school {
    Map ScenicSpots=new HashMap();
    Map ScenicSpotEdges=new HashMap();
    Map bh_id=new HashMap();
    int points=0;
    Queue<Integer> delpoints=new LinkedList<Integer>();
    Set<Integer>isdel=new TreeSet<>();
    long bhToid(int bh)
    {
        if(!bh_id.containsKey(bh))return -1;
        return (long)bh_id.get(bh);
    }
    int idTobh(long id)
    {
        if (!ScenicSpots.containsKey(id))return -1;
        return ((ScenicSpot)(ScenicSpots.get(id))).getBh();
    }
    public void addScenicSpot(String name,long id,String introduction)
    {
        if(ScenicSpots.containsKey(id))
        {
            System.out.println("该id已存在");
            return;
        }
        int bh;
        if(delpoints.isEmpty())
        {
            points++;
            bh=points;
        }
        else
        {
            bh=delpoints.poll();
            isdel.remove(bh);
        }
        bh_id.put(bh,id);
        ScenicSpot scenicSpot=new ScenicSpot(name,id,introduction,bh);
        ScenicSpots.put(id,scenicSpot);
        System.out.println("添加成功");
    }
    public void addScenicSpotEdge(long ida,long idb,double lenth)
    {
        if(ida>idb)
        {
            long t=ida;
            ida=idb;
            idb=t;
        }
        String key=ida+"_"+idb;
        Edge e=new Edge(ida,idb,lenth);

        if(getScenicSpotEdge(ida,idb)>lenth) ScenicSpotEdges.put(key,e);
    }
    public double getScenicSpotEdge(long ida,long idb)
    {
        if(ida>idb)
        {
            long t=ida;
            ida=idb;
            idb=t;
        }
        String key=ida+"_"+idb;
        if(!ScenicSpotEdges.containsKey(key)||!ScenicSpots.containsKey(ida) || !ScenicSpots.containsKey(idb))
        {
            return Double.MAX_VALUE;
        }
        Edge e=(Edge)ScenicSpotEdges.get(key) ;
        return e.getLenth();
    }

    public double getScenicSpotEdge(int bha,int bhb)
    {
        long ida=bhToid(bha);
        long idb=bhToid(bhb);
        return getScenicSpotEdge( ida, idb);
    }
    public void delScenicSpot(long id)
    {
        int thisbh=idTobh(id);
        isdel.add(thisbh);
        delpoints.add(thisbh);
        ScenicSpots.remove(id);
        Iterator iterator = ScenicSpots.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry entry = (Map.Entry)iterator.next();
            long keyid = (long)entry.getKey();
            delScenicSpotEdge(keyid,id);
        }
    }
    public void delScenicSpotEdge(long ida,long idb)
    {
        if(ida>idb)
        {
            long t=ida;
            ida=idb;
            idb=t;
        }
        String key=ida+"_"+idb;
        if(ScenicSpotEdges.containsKey(key))ScenicSpotEdges.remove(key);
    }
    public String getIntroduction(long id)
    {
        return ((ScenicSpot) ScenicSpots.get(id)).getIntroduction();
    }
    public StringBuffer  pf(int bg,int i, int[] hs,StringBuffer sb)
    {
        String s=((ScenicSpot)ScenicSpots.get(bhToid(i))).getName();
        if (i == bg) {

            sb.append(""+s);
        }
        else{
            pf(bg,hs[i], hs, sb);
            sb.append("->" + s);
        }
        return sb;
    }
    public String getRoadBetween(long ida , long idb)
    {

        int vertex=points;
        int initial_point = idTobh(ida);//起始点
        int end_point=idTobh(idb);
        double[] dis = new double[vertex + 1];
        double[] flag = new double[vertex + 1];//标记不参与找最小值的点
        int[] hs = new int[vertex + 1];//回溯找上一个节点
        for (int i = 1; i <= vertex; i++) {
            dis[i] = getScenicSpotEdge(initial_point,i);//初始化起始点到这些顶点的距离
            flag[i] = getScenicSpotEdge(initial_point,i);
            hs[i] = initial_point;//每一个节点的上一个节点都是起始点;
        }
        List list = new ArrayList();
        dis[initial_point] = 0;
        flag[initial_point] = Double.MAX_VALUE;
        list.add(initial_point);
        while (list.size() != vertex) {
            double min = Double.MAX_VALUE;
            int index = initial_point;
            for (int i = 1; i <= vertex; i++) {
                if (flag[i] <= min) {
                    min = flag[i];
                    index = i;
                }
            }
            list.add(index);
            flag[index] = Double.MAX_VALUE;
            //寻找index节点出度,如果通过到这些出度的点的值小于直达的点，则更新里面的值。
            for (int i = 1; i <= vertex; i++) {
                if (dis[index] + getScenicSpotEdge(index,i) <= dis[i]) {
                    hs[i] = index;
                    dis[i] = getScenicSpotEdge(index,i) + dis[index];
                    flag[i] = getScenicSpotEdge(index,i) + dis[index];
                }
            }
        }

        StringBuffer sb = new StringBuffer();
        if(dis[end_point]>=Double.MAX_VALUE-1)return "无路可走";
        for(int i=1;i<=vertex;i++)System.out.println(hs[i]);
        return pf(initial_point,end_point,hs,sb)+"="+dis[end_point];
    }
}
