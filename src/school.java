import java.io.*;
import java.util.*;

public class school implements Serializable {
    Map ScenicSpots=new HashMap();
    Map ScenicSpotEdges=new HashMap();
    Map bh_id=new HashMap();
    int points=0;
    String init_path="data";
    String init_dir="map_init.bin";
    Queue<Integer> delpoints=new LinkedList<Integer>();
    Set<Integer>isdel=new TreeSet<>();
    public school()
    {
        try
        {
            File aFile=new File(init_path+"/"+init_dir);
            FileInputStream fileInputStream=new FileInputStream(aFile);
            ObjectInputStream objectInputStream=new ObjectInputStream(fileInputStream);
            school sc=(school) objectInputStream.readObject();
            this.ScenicSpots=sc.ScenicSpots;
            this.ScenicSpotEdges=sc.ScenicSpotEdges;
            this.bh_id=sc.bh_id;
            this.points=sc.points;
            this.delpoints=sc.delpoints;
            this.isdel=sc.isdel;
            System.out.println("初始化成功");
        }
        catch (Exception e)
        {
            System.out.println("初始化失败，未找到数据文件");
        }
    }
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
        System.out.println("点添加成功");
    }
    public void addScenicSpotEdge(long ida,long idb,double lenth)
    {
        if(!(ScenicSpots.containsKey(ida)))
        {
            System.out.println("无"+ida+"点\n");
            return;
        }
        if(!(ScenicSpots.containsKey(idb)))
        {
            System.out.println("无"+idb+"点\n");
            return;
        }
        if(ida==idb)
        {
            System.out.println("请输入不同点的id");
            return;
        }
        if(lenth<=0)
        {
            System.out.println("路径不能为负数");
            return;
        }
        if(ida>idb)
        {
            long t=ida;
            ida=idb;
            idb=t;
        }
        String key=ida+"_"+idb;
        Edge e=new Edge(ida,idb,lenth);

        if(getScenicSpotEdge(ida,idb)>lenth)
        {
            ScenicSpotEdges.put(key,e);
            ((ScenicSpot)ScenicSpots.get(ida)).addEdgeTo(idb);
            ((ScenicSpot)ScenicSpots.get(idb)).addEdgeTo(ida);
            System.out.println("边添加成功");
            return;
        }
        System.out.println("边添加失败，已有更短边");
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
        if(!ScenicSpots.containsKey(id))
        {
            System.out.println("无此点\n");
            return;
        }
        int thisbh=idTobh(id);
        isdel.add(thisbh);
        delpoints.add(thisbh);
        Set<Long> set=new TreeSet<>();
        set.addAll(((ScenicSpot)ScenicSpots.get(id)).getPointadjoin());
        for (Object str : set) {
            long idto = (long)str;
            delScenicSpotEdge(idto,id);
        }
        ScenicSpots.remove(id);
        System.out.println("点删除成功");
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
        if(ScenicSpotEdges.containsKey(key))
        {
            ScenicSpotEdges.remove(key);
            ((ScenicSpot)ScenicSpots.get(ida)).delEdgeTo(idb);
            ((ScenicSpot)ScenicSpots.get(idb)).delEdgeTo(ida);
            System.out.println("边删除成功");
        }
        else {
            System.out.println("无此边\n");
        }
    }
    public String getIntroduction(long id)
    {
        if(!ScenicSpots.containsKey(id))
        {
            return "查无此点\n";
        }
        ScenicSpot sc=(ScenicSpot) ScenicSpots.get(id);
        return "name:"+sc.getName()+"\nintroduction:"+sc.getIntroduction();
    }
    public StringBuffer  getRoad(int bg,int i, int[] hs,StringBuffer sb)
    {
        String s=((ScenicSpot)ScenicSpots.get(bhToid(i))).getName();
        if (i == bg) {

            sb.append(""+s);
        }
        else{
            getRoad(bg,hs[i], hs, sb);
            sb.append("->" + s);
        }
        return sb;
    }
    public String getRoadBetween(long ida , long idb)
    {
        if(!ScenicSpots.containsKey(ida))return "没有"+ida+"点";
        if(!ScenicSpots.containsKey(idb))return "没有"+idb+"点";
        int vertex=points;
        int initial_point = idTobh(ida);//起始点
        int end_point=idTobh(idb);
        double[] dis = new double[vertex + 1];
        boolean [] flag = new boolean[vertex + 1];//标记不参与找最小值的点
        int[] hs = new int[vertex + 1];//回溯找上一个节点
        Queue<Edge> minpoint = new PriorityQueue<>(vertex);

        for (int i = 1; i <= vertex; i++) {
            dis[i] = getScenicSpotEdge(initial_point,i);//初始化起始点到这些顶点的距离
            flag[i] = false;
            hs[i] = initial_point;//每一个节点的上一个节点都是起始点;
            if(dis[i]<Double.MAX_VALUE-1) minpoint.add(new Edge(i,dis[i]));
        }
        dis[initial_point] = 0;
        flag[initial_point] = true;//已经查过
        while (!minpoint.isEmpty()) {
            Edge t=minpoint.poll();
            int index = t.getBh();
            while (flag[index]&&!minpoint.isEmpty())
            {
                t=minpoint.poll();
                index = t.getBh();
            }

            if(index==end_point)break;//优化，找到终点直接输出
            flag[index] = true;
            //寻找index节点出度,如果通过到这些出度的点的值小于直达的点，则更新里面的值。
            for (int i = 1; i <= vertex; i++) {
                if (!flag[i]&&dis[index] + getScenicSpotEdge(index,i) <= dis[i]) {
                    hs[i] = index;
                    dis[i] = getScenicSpotEdge(index,i) + dis[index];
                    minpoint.add(new Edge(i,dis[i]));
                }
            }

        }
        StringBuffer sb = new StringBuffer();
        if(dis[end_point]>=Double.MAX_VALUE-1)return "无路可走";
        //for(int i=1;i<=vertex;i++)System.out.println(hs[i]);
        return getRoad(initial_point,end_point,hs,sb)+"="+dis[end_point];
    }
    public void save()
    {
        File Filepath=new File(init_path);
        FileOutputStream fileOutputStream=null;
        try {
            if (!Filepath.exists())
            {
                System.out.println(111);
                Filepath.mkdir();
            }
            File initFile=new File(init_path+"/"+init_dir);
            fileOutputStream = new FileOutputStream(initFile);
            ObjectOutputStream objectOutputStream=new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(this);
            objectOutputStream.flush();
            objectOutputStream.close();
            System.out.println("保存成功");
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            System.out.println("保存失败，FileNotFoundException");
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("保存失败，IOException");
        }finally {
            if(fileOutputStream!=null)
            {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }
}
