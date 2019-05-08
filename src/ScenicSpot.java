import java.util.HashMap;
import java.util.Map;

public class ScenicSpot {
    private String name;
    private long id;
    private int bh;
    private String introduction;
    public  ScenicSpot()
    {
        name="";
        id=0;
        introduction="";
    }
    public  ScenicSpot(String name,long id,String introduction,int bh)
    {
        this.introduction=introduction;
        this.id=id;
        this.name=name;
        this.bh=bh;
    }

    public int getBh() {
        return bh;
    }

    public String getName() {
        return name;
    }
    public  long getId()
    {
        return id;
    }
    public String getIntroduction()
    {
        return introduction;
    }
    public void setName(String name)
    {
        this.name=name;
    }
    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }
}
