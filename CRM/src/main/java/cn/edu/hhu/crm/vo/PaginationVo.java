package cn.edu.hhu.crm.vo;

import java.util.List;

public class PaginationVo<T> {
    private int num;
    private List<T> actList;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public List<T> getActList() {
        return actList;
    }

    public void setActList(List<T> actList) {
        this.actList = actList;
    }
}
