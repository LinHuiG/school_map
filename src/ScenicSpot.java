public class ScenicSpot {
    private String name;
    private long id;
    private int bh;
    private String introduction;
    ScenicSpot()
    {
        name="";
        id=0;
        introduction="";
    }
    ScenicSpot(String name,long id,String introduction,int bh)
    {
        this.introduction=introduction;
        this.id=id;
        this.name=name;
        this.bh=bh;
    }

    int getBh() {
        return bh;
    }

    String getName() {
        return name;
    }
    long getId()
    {
        return id;
    }
    String getIntroduction()
    {
        return introduction;
    }
    void setName(String name)
    {
        this.name=name;
    }
    void setIntroduction(String introduction) {
        this.introduction = introduction;
    }
}
