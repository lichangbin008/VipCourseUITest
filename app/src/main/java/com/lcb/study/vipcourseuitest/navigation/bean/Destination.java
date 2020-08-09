package com.lcb.study.vipcourseuitest.navigation.bean;

/**
 * Created by ${lichangbin} on 2020/8/9.
 */
public class Destination {
    private boolean isFragment;

    private boolean asStarter;

    private boolean needLogin;

    private String pageUrl;

    private String className;

    private int id;

    public void setIsFragment(boolean isFragment){
        this.isFragment = isFragment;
    }
    public boolean getIsFragment(){
        return this.isFragment;
    }
    public void setAsStarter(boolean asStarter){
        this.asStarter = asStarter;
    }
    public boolean getAsStarter(){
        return this.asStarter;
    }
    public void setNeedLogin(boolean needLogin){
        this.needLogin = needLogin;
    }
    public boolean getNeedLogin(){
        return this.needLogin;
    }
    public void setPageUrl(String pageUrl){
        this.pageUrl = pageUrl;
    }
    public String getPageUrl(){
        return this.pageUrl;
    }
    public void setClassName(String className){
        this.className = className;
    }
    public String getClassName(){
        return this.className;
    }
    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return this.id;
    }
}
