import java.io.*;
import java.util.Scanner;

public class Main {
    public static school s;
    public static void save()
    {
        File aFile=new File("map_init.bin");
        FileOutputStream fileOutputStream=null;
        try {
            fileOutputStream = new FileOutputStream(aFile);
            ObjectOutputStream objectOutputStream=new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(s);
            objectOutputStream.flush();
            objectOutputStream.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
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
    public static void main(String[] args) {
        s=new school();
        int xz;
        Scanner cin=new Scanner(System.in);
        while (true)
        {
            System.out.println(
                    "1:加点\n" +
                    "2:加边\n" +
                    "3:查询路径\n" +
                    "4:删边\n" +
                    "5:删点\n" +
                    "6:查信息\n"+
                     "7:退出\n"
                    );
            xz=cin.nextInt();
            if(xz==1)
            {
                System.out.println("输入name，id，introduction");
                String name=cin.next();
                long id=cin.nextLong();
                String introduction=cin.next();
                s.addScenicSpot(name,id,introduction);
            }
            else if(xz==2)
            {
                System.out.println("输入ida,idb,lenth");
                long x=cin.nextLong();
                long y=cin.nextLong();
                double l=cin.nextDouble();
                s.addScenicSpotEdge(x,y,l);
            }
            else if(xz==3)
            {
                System.out.println("输入ida,idb");
                long x=cin.nextLong();
                long y=cin.nextLong();
                System.out.println(s.getRoadBetween(x,y));
            }
            else if(xz==4)
            {
                System.out.println("输入ida,idb");
                long x=cin.nextLong();
                long y=cin.nextLong();
                s.delScenicSpotEdge(x,y);
            }
            else if(xz==5)
            {
                System.out.println("输入id");
                long x=cin.nextLong();
                s.delScenicSpot(x);
            }
            else if(xz==6)
            {
                System.out.println("输入id");
                long x=cin.nextLong();
                System.out.println(s.getIntroduction(x));
            }
            else if(xz==7)
            {
                s.save(s);
                System.out.println("保存成功");
                break;
            }
        }
    }
}
