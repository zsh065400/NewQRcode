package org.laosao.two.bean;

import cn.bmob.v3.BmobObject;

/**
 * Bmob云服务反馈表
 * Created by lenovo on 2015/2/15.
 */
public class FeedBackBmob extends BmobObject {
    private String project;
    private String content;
    private String call;

    /**
     * 构造器，初始化表数据
     */
    public FeedBackBmob(String project, String content, String call) {
        this.project = project;
        this.content = content;
        this.call = call;
    }


    /**
     * get set方法
     */
    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCall() {
        return call;
    }

    public void setCall(String call) {
        this.call = call;
    }
}
