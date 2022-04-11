package com.dev.common.database.dynamic;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * @author long.guo
 * @since 1/30/21
 */
@Entity
public class Dynamic {
    @PrimaryKey
    public Long id = null;
    public long userId;
    public String title;
    public String content;
    public long createTime;
    //附件
    public String attachment;
    //是否置顶
    public int top=1;
    //是否管理员
    public boolean isAdmin;
    public String dynamicType = "其他";

    public Dynamic(long userId, String title, String content, String attachment,int top,boolean isAdmin) {
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.attachment = attachment;
        this.top = top;
        this.isAdmin = isAdmin;
        createTime = System.currentTimeMillis();
    }

    public int isTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    @Override
    public String toString() {
        return "Dynamic{" +
                "id=" + id +
                ", userId=" + userId +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", attachment='" + attachment + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
