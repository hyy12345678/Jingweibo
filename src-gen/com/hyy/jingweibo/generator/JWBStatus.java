package com.hyy.jingweibo.generator;

import com.hyy.jingweibo.generator.DaoSession;
import de.greenrobot.dao.DaoException;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table JWBSTATUS.
 */
public class JWBStatus {

    private String created_at;
    private Long id;
    private String mid;
    private String idstr;
    private String text;
    private String source;
    private Boolean favorited;
    private Boolean truncated;
    private String in_reply_to_status_id;
    private String in_reply_to_user_id;
    private String in_reply_to_screen_name;
    private String thumbnail_pic;
    private String bmiddle_pic;
    private String original_pic;
    private Long status_geo_id;
    private Long status_user_id;
    private Long retweeted_status_id;
    private Integer reposts_count;
    private Integer comments_count;
    private Integer attitudes_count;
    private Integer mlevel;
    private String visible;
    private String pic_urls;
    private String received_user_id;
    private Long mills;

    /** Used to resolve relations */
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    private transient JWBStatusDao myDao;

    private JWBGeo jWBGeo;
    private Long jWBGeo__resolvedKey;

    private JWBUser jWBUser;
    private Long jWBUser__resolvedKey;

    private JWBStatus jWBStatus;
    private Long jWBStatus__resolvedKey;


    public JWBStatus() {
    }

    public JWBStatus(Long id) {
        this.id = id;
    }

    public JWBStatus(String created_at, Long id, String mid, String idstr, String text, String source, Boolean favorited, Boolean truncated, String in_reply_to_status_id, String in_reply_to_user_id, String in_reply_to_screen_name, String thumbnail_pic, String bmiddle_pic, String original_pic, Long status_geo_id, Long status_user_id, Long retweeted_status_id, Integer reposts_count, Integer comments_count, Integer attitudes_count, Integer mlevel, String visible, String pic_urls, String received_user_id, Long mills) {
        this.created_at = created_at;
        this.id = id;
        this.mid = mid;
        this.idstr = idstr;
        this.text = text;
        this.source = source;
        this.favorited = favorited;
        this.truncated = truncated;
        this.in_reply_to_status_id = in_reply_to_status_id;
        this.in_reply_to_user_id = in_reply_to_user_id;
        this.in_reply_to_screen_name = in_reply_to_screen_name;
        this.thumbnail_pic = thumbnail_pic;
        this.bmiddle_pic = bmiddle_pic;
        this.original_pic = original_pic;
        this.status_geo_id = status_geo_id;
        this.status_user_id = status_user_id;
        this.retweeted_status_id = retweeted_status_id;
        this.reposts_count = reposts_count;
        this.comments_count = comments_count;
        this.attitudes_count = attitudes_count;
        this.mlevel = mlevel;
        this.visible = visible;
        this.pic_urls = pic_urls;
        this.received_user_id = received_user_id;
        this.mills = mills;
    }

    /** called by internal mechanisms, do not call yourself. */
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getJWBStatusDao() : null;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getIdstr() {
        return idstr;
    }

    public void setIdstr(String idstr) {
        this.idstr = idstr;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Boolean getFavorited() {
        return favorited;
    }

    public void setFavorited(Boolean favorited) {
        this.favorited = favorited;
    }

    public Boolean getTruncated() {
        return truncated;
    }

    public void setTruncated(Boolean truncated) {
        this.truncated = truncated;
    }

    public String getIn_reply_to_status_id() {
        return in_reply_to_status_id;
    }

    public void setIn_reply_to_status_id(String in_reply_to_status_id) {
        this.in_reply_to_status_id = in_reply_to_status_id;
    }

    public String getIn_reply_to_user_id() {
        return in_reply_to_user_id;
    }

    public void setIn_reply_to_user_id(String in_reply_to_user_id) {
        this.in_reply_to_user_id = in_reply_to_user_id;
    }

    public String getIn_reply_to_screen_name() {
        return in_reply_to_screen_name;
    }

    public void setIn_reply_to_screen_name(String in_reply_to_screen_name) {
        this.in_reply_to_screen_name = in_reply_to_screen_name;
    }

    public String getThumbnail_pic() {
        return thumbnail_pic;
    }

    public void setThumbnail_pic(String thumbnail_pic) {
        this.thumbnail_pic = thumbnail_pic;
    }

    public String getBmiddle_pic() {
        return bmiddle_pic;
    }

    public void setBmiddle_pic(String bmiddle_pic) {
        this.bmiddle_pic = bmiddle_pic;
    }

    public String getOriginal_pic() {
        return original_pic;
    }

    public void setOriginal_pic(String original_pic) {
        this.original_pic = original_pic;
    }

    public Long getStatus_geo_id() {
        return status_geo_id;
    }

    public void setStatus_geo_id(Long status_geo_id) {
        this.status_geo_id = status_geo_id;
    }

    public Long getStatus_user_id() {
        return status_user_id;
    }

    public void setStatus_user_id(Long status_user_id) {
        this.status_user_id = status_user_id;
    }

    public Long getRetweeted_status_id() {
        return retweeted_status_id;
    }

    public void setRetweeted_status_id(Long retweeted_status_id) {
        this.retweeted_status_id = retweeted_status_id;
    }

    public Integer getReposts_count() {
        return reposts_count;
    }

    public void setReposts_count(Integer reposts_count) {
        this.reposts_count = reposts_count;
    }

    public Integer getComments_count() {
        return comments_count;
    }

    public void setComments_count(Integer comments_count) {
        this.comments_count = comments_count;
    }

    public Integer getAttitudes_count() {
        return attitudes_count;
    }

    public void setAttitudes_count(Integer attitudes_count) {
        this.attitudes_count = attitudes_count;
    }

    public Integer getMlevel() {
        return mlevel;
    }

    public void setMlevel(Integer mlevel) {
        this.mlevel = mlevel;
    }

    public String getVisible() {
        return visible;
    }

    public void setVisible(String visible) {
        this.visible = visible;
    }

    public String getPic_urls() {
        return pic_urls;
    }

    public void setPic_urls(String pic_urls) {
        this.pic_urls = pic_urls;
    }

    public String getReceived_user_id() {
        return received_user_id;
    }

    public void setReceived_user_id(String received_user_id) {
        this.received_user_id = received_user_id;
    }

    public Long getMills() {
        return mills;
    }

    public void setMills(Long mills) {
        this.mills = mills;
    }

    /** To-one relationship, resolved on first access. */
    public JWBGeo getJWBGeo() {
        Long __key = this.status_geo_id;
        if (jWBGeo__resolvedKey == null || !jWBGeo__resolvedKey.equals(__key)) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            JWBGeoDao targetDao = daoSession.getJWBGeoDao();
            JWBGeo jWBGeoNew = targetDao.load(__key);
            synchronized (this) {
                jWBGeo = jWBGeoNew;
            	jWBGeo__resolvedKey = __key;
            }
        }
        return jWBGeo;
    }

    public void setJWBGeo(JWBGeo jWBGeo) {
        synchronized (this) {
            this.jWBGeo = jWBGeo;
            status_geo_id = jWBGeo == null ? null : jWBGeo.getId();
            jWBGeo__resolvedKey = status_geo_id;
        }
    }

    /** To-one relationship, resolved on first access. */
    public JWBUser getJWBUser() {
        Long __key = this.status_user_id;
        if (jWBUser__resolvedKey == null || !jWBUser__resolvedKey.equals(__key)) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            JWBUserDao targetDao = daoSession.getJWBUserDao();
            JWBUser jWBUserNew = targetDao.load(__key);
            synchronized (this) {
                jWBUser = jWBUserNew;
            	jWBUser__resolvedKey = __key;
            }
        }
        return jWBUser;
    }

    public void setJWBUser(JWBUser jWBUser) {
        synchronized (this) {
            this.jWBUser = jWBUser;
            status_user_id = jWBUser == null ? null : jWBUser.getId();
            jWBUser__resolvedKey = status_user_id;
        }
    }

    /** To-one relationship, resolved on first access. */
    public JWBStatus getJWBStatus() {
        Long __key = this.retweeted_status_id;
        if (jWBStatus__resolvedKey == null || !jWBStatus__resolvedKey.equals(__key)) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            JWBStatusDao targetDao = daoSession.getJWBStatusDao();
            JWBStatus jWBStatusNew = targetDao.load(__key);
            synchronized (this) {
                jWBStatus = jWBStatusNew;
            	jWBStatus__resolvedKey = __key;
            }
        }
        return jWBStatus;
    }

    public void setJWBStatus(JWBStatus jWBStatus) {
        synchronized (this) {
            this.jWBStatus = jWBStatus;
            retweeted_status_id = jWBStatus == null ? null : jWBStatus.getId();
            jWBStatus__resolvedKey = retweeted_status_id;
        }
    }

    /** Convenient call for {@link AbstractDao#delete(Object)}. Entity must attached to an entity context. */
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.delete(this);
    }

    /** Convenient call for {@link AbstractDao#update(Object)}. Entity must attached to an entity context. */
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.update(this);
    }

    /** Convenient call for {@link AbstractDao#refresh(Object)}. Entity must attached to an entity context. */
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.refresh(this);
    }

}
