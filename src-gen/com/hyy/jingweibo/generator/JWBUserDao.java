package com.hyy.jingweibo.generator;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.hyy.jingweibo.generator.JWBUser;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table JWBUSER.
*/
public class JWBUserDao extends AbstractDao<JWBUser, Long> {

    public static final String TABLENAME = "JWBUSER";

    /**
     * Properties of entity JWBUser.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Idstr = new Property(1, String.class, "idstr", false, "IDSTR");
        public final static Property Screen_name = new Property(2, String.class, "screen_name", false, "SCREEN_NAME");
        public final static Property Name = new Property(3, String.class, "name", false, "NAME");
        public final static Property Province = new Property(4, Integer.class, "province", false, "PROVINCE");
        public final static Property City = new Property(5, Integer.class, "city", false, "CITY");
        public final static Property Location = new Property(6, String.class, "location", false, "LOCATION");
        public final static Property Description = new Property(7, String.class, "description", false, "DESCRIPTION");
        public final static Property Url = new Property(8, String.class, "url", false, "URL");
        public final static Property Profile_image_url = new Property(9, String.class, "profile_image_url", false, "PROFILE_IMAGE_URL");
        public final static Property Profile_url = new Property(10, String.class, "profile_url", false, "PROFILE_URL");
        public final static Property Domain = new Property(11, String.class, "domain", false, "DOMAIN");
        public final static Property Weihao = new Property(12, String.class, "weihao", false, "WEIHAO");
        public final static Property Gender = new Property(13, String.class, "gender", false, "GENDER");
        public final static Property Followers_count = new Property(14, Integer.class, "followers_count", false, "FOLLOWERS_COUNT");
        public final static Property Friends_count = new Property(15, Integer.class, "friends_count", false, "FRIENDS_COUNT");
        public final static Property Statuses_count = new Property(16, Integer.class, "statuses_count", false, "STATUSES_COUNT");
        public final static Property Favourites_count = new Property(17, Integer.class, "favourites_count", false, "FAVOURITES_COUNT");
        public final static Property Created_at = new Property(18, String.class, "created_at", false, "CREATED_AT");
        public final static Property Following = new Property(19, Boolean.class, "following", false, "FOLLOWING");
        public final static Property Allow_all_act_msg = new Property(20, Boolean.class, "allow_all_act_msg", false, "ALLOW_ALL_ACT_MSG");
        public final static Property Geo_enabled = new Property(21, Boolean.class, "geo_enabled", false, "GEO_ENABLED");
        public final static Property Verified = new Property(22, Boolean.class, "verified", false, "VERIFIED");
        public final static Property Verified_type = new Property(23, Integer.class, "verified_type", false, "VERIFIED_TYPE");
        public final static Property Remark = new Property(24, String.class, "remark", false, "REMARK");
        public final static Property Allow_all_comment = new Property(25, Boolean.class, "allow_all_comment", false, "ALLOW_ALL_COMMENT");
        public final static Property Avatar_large = new Property(26, String.class, "avatar_large", false, "AVATAR_LARGE");
        public final static Property Avatar_hd = new Property(27, String.class, "avatar_hd", false, "AVATAR_HD");
        public final static Property Verified_reason = new Property(28, String.class, "verified_reason", false, "VERIFIED_REASON");
        public final static Property Follow_me = new Property(29, Boolean.class, "follow_me", false, "FOLLOW_ME");
        public final static Property Online_status = new Property(30, Integer.class, "online_status", false, "ONLINE_STATUS");
        public final static Property Bi_followers_count = new Property(31, Integer.class, "bi_followers_count", false, "BI_FOLLOWERS_COUNT");
        public final static Property Lang = new Property(32, String.class, "lang", false, "LANG");
        public final static Property Star = new Property(33, String.class, "star", false, "STAR");
        public final static Property Mbtype = new Property(34, String.class, "mbtype", false, "MBTYPE");
        public final static Property Mbrank = new Property(35, String.class, "mbrank", false, "MBRANK");
        public final static Property Block_word = new Property(36, String.class, "block_word", false, "BLOCK_WORD");
    };


    public JWBUserDao(DaoConfig config) {
        super(config);
    }
    
    public JWBUserDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'JWBUSER' (" + //
                "'_id' INTEGER PRIMARY KEY ," + // 0: id
                "'IDSTR' TEXT," + // 1: idstr
                "'SCREEN_NAME' TEXT," + // 2: screen_name
                "'NAME' TEXT," + // 3: name
                "'PROVINCE' INTEGER," + // 4: province
                "'CITY' INTEGER," + // 5: city
                "'LOCATION' TEXT," + // 6: location
                "'DESCRIPTION' TEXT," + // 7: description
                "'URL' TEXT," + // 8: url
                "'PROFILE_IMAGE_URL' TEXT," + // 9: profile_image_url
                "'PROFILE_URL' TEXT," + // 10: profile_url
                "'DOMAIN' TEXT," + // 11: domain
                "'WEIHAO' TEXT," + // 12: weihao
                "'GENDER' TEXT," + // 13: gender
                "'FOLLOWERS_COUNT' INTEGER," + // 14: followers_count
                "'FRIENDS_COUNT' INTEGER," + // 15: friends_count
                "'STATUSES_COUNT' INTEGER," + // 16: statuses_count
                "'FAVOURITES_COUNT' INTEGER," + // 17: favourites_count
                "'CREATED_AT' TEXT," + // 18: created_at
                "'FOLLOWING' INTEGER," + // 19: following
                "'ALLOW_ALL_ACT_MSG' INTEGER," + // 20: allow_all_act_msg
                "'GEO_ENABLED' INTEGER," + // 21: geo_enabled
                "'VERIFIED' INTEGER," + // 22: verified
                "'VERIFIED_TYPE' INTEGER," + // 23: verified_type
                "'REMARK' TEXT," + // 24: remark
                "'ALLOW_ALL_COMMENT' INTEGER," + // 25: allow_all_comment
                "'AVATAR_LARGE' TEXT," + // 26: avatar_large
                "'AVATAR_HD' TEXT," + // 27: avatar_hd
                "'VERIFIED_REASON' TEXT," + // 28: verified_reason
                "'FOLLOW_ME' INTEGER," + // 29: follow_me
                "'ONLINE_STATUS' INTEGER," + // 30: online_status
                "'BI_FOLLOWERS_COUNT' INTEGER," + // 31: bi_followers_count
                "'LANG' TEXT," + // 32: lang
                "'STAR' TEXT," + // 33: star
                "'MBTYPE' TEXT," + // 34: mbtype
                "'MBRANK' TEXT," + // 35: mbrank
                "'BLOCK_WORD' TEXT);"); // 36: block_word
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'JWBUSER'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, JWBUser entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String idstr = entity.getIdstr();
        if (idstr != null) {
            stmt.bindString(2, idstr);
        }
 
        String screen_name = entity.getScreen_name();
        if (screen_name != null) {
            stmt.bindString(3, screen_name);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(4, name);
        }
 
        Integer province = entity.getProvince();
        if (province != null) {
            stmt.bindLong(5, province);
        }
 
        Integer city = entity.getCity();
        if (city != null) {
            stmt.bindLong(6, city);
        }
 
        String location = entity.getLocation();
        if (location != null) {
            stmt.bindString(7, location);
        }
 
        String description = entity.getDescription();
        if (description != null) {
            stmt.bindString(8, description);
        }
 
        String url = entity.getUrl();
        if (url != null) {
            stmt.bindString(9, url);
        }
 
        String profile_image_url = entity.getProfile_image_url();
        if (profile_image_url != null) {
            stmt.bindString(10, profile_image_url);
        }
 
        String profile_url = entity.getProfile_url();
        if (profile_url != null) {
            stmt.bindString(11, profile_url);
        }
 
        String domain = entity.getDomain();
        if (domain != null) {
            stmt.bindString(12, domain);
        }
 
        String weihao = entity.getWeihao();
        if (weihao != null) {
            stmt.bindString(13, weihao);
        }
 
        String gender = entity.getGender();
        if (gender != null) {
            stmt.bindString(14, gender);
        }
 
        Integer followers_count = entity.getFollowers_count();
        if (followers_count != null) {
            stmt.bindLong(15, followers_count);
        }
 
        Integer friends_count = entity.getFriends_count();
        if (friends_count != null) {
            stmt.bindLong(16, friends_count);
        }
 
        Integer statuses_count = entity.getStatuses_count();
        if (statuses_count != null) {
            stmt.bindLong(17, statuses_count);
        }
 
        Integer favourites_count = entity.getFavourites_count();
        if (favourites_count != null) {
            stmt.bindLong(18, favourites_count);
        }
 
        String created_at = entity.getCreated_at();
        if (created_at != null) {
            stmt.bindString(19, created_at);
        }
 
        Boolean following = entity.getFollowing();
        if (following != null) {
            stmt.bindLong(20, following ? 1l: 0l);
        }
 
        Boolean allow_all_act_msg = entity.getAllow_all_act_msg();
        if (allow_all_act_msg != null) {
            stmt.bindLong(21, allow_all_act_msg ? 1l: 0l);
        }
 
        Boolean geo_enabled = entity.getGeo_enabled();
        if (geo_enabled != null) {
            stmt.bindLong(22, geo_enabled ? 1l: 0l);
        }
 
        Boolean verified = entity.getVerified();
        if (verified != null) {
            stmt.bindLong(23, verified ? 1l: 0l);
        }
 
        Integer verified_type = entity.getVerified_type();
        if (verified_type != null) {
            stmt.bindLong(24, verified_type);
        }
 
        String remark = entity.getRemark();
        if (remark != null) {
            stmt.bindString(25, remark);
        }
 
        Boolean allow_all_comment = entity.getAllow_all_comment();
        if (allow_all_comment != null) {
            stmt.bindLong(26, allow_all_comment ? 1l: 0l);
        }
 
        String avatar_large = entity.getAvatar_large();
        if (avatar_large != null) {
            stmt.bindString(27, avatar_large);
        }
 
        String avatar_hd = entity.getAvatar_hd();
        if (avatar_hd != null) {
            stmt.bindString(28, avatar_hd);
        }
 
        String verified_reason = entity.getVerified_reason();
        if (verified_reason != null) {
            stmt.bindString(29, verified_reason);
        }
 
        Boolean follow_me = entity.getFollow_me();
        if (follow_me != null) {
            stmt.bindLong(30, follow_me ? 1l: 0l);
        }
 
        Integer online_status = entity.getOnline_status();
        if (online_status != null) {
            stmt.bindLong(31, online_status);
        }
 
        Integer bi_followers_count = entity.getBi_followers_count();
        if (bi_followers_count != null) {
            stmt.bindLong(32, bi_followers_count);
        }
 
        String lang = entity.getLang();
        if (lang != null) {
            stmt.bindString(33, lang);
        }
 
        String star = entity.getStar();
        if (star != null) {
            stmt.bindString(34, star);
        }
 
        String mbtype = entity.getMbtype();
        if (mbtype != null) {
            stmt.bindString(35, mbtype);
        }
 
        String mbrank = entity.getMbrank();
        if (mbrank != null) {
            stmt.bindString(36, mbrank);
        }
 
        String block_word = entity.getBlock_word();
        if (block_word != null) {
            stmt.bindString(37, block_word);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public JWBUser readEntity(Cursor cursor, int offset) {
        JWBUser entity = new JWBUser( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // idstr
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // screen_name
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // name
            cursor.isNull(offset + 4) ? null : cursor.getInt(offset + 4), // province
            cursor.isNull(offset + 5) ? null : cursor.getInt(offset + 5), // city
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // location
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // description
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // url
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // profile_image_url
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // profile_url
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // domain
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12), // weihao
            cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13), // gender
            cursor.isNull(offset + 14) ? null : cursor.getInt(offset + 14), // followers_count
            cursor.isNull(offset + 15) ? null : cursor.getInt(offset + 15), // friends_count
            cursor.isNull(offset + 16) ? null : cursor.getInt(offset + 16), // statuses_count
            cursor.isNull(offset + 17) ? null : cursor.getInt(offset + 17), // favourites_count
            cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18), // created_at
            cursor.isNull(offset + 19) ? null : cursor.getShort(offset + 19) != 0, // following
            cursor.isNull(offset + 20) ? null : cursor.getShort(offset + 20) != 0, // allow_all_act_msg
            cursor.isNull(offset + 21) ? null : cursor.getShort(offset + 21) != 0, // geo_enabled
            cursor.isNull(offset + 22) ? null : cursor.getShort(offset + 22) != 0, // verified
            cursor.isNull(offset + 23) ? null : cursor.getInt(offset + 23), // verified_type
            cursor.isNull(offset + 24) ? null : cursor.getString(offset + 24), // remark
            cursor.isNull(offset + 25) ? null : cursor.getShort(offset + 25) != 0, // allow_all_comment
            cursor.isNull(offset + 26) ? null : cursor.getString(offset + 26), // avatar_large
            cursor.isNull(offset + 27) ? null : cursor.getString(offset + 27), // avatar_hd
            cursor.isNull(offset + 28) ? null : cursor.getString(offset + 28), // verified_reason
            cursor.isNull(offset + 29) ? null : cursor.getShort(offset + 29) != 0, // follow_me
            cursor.isNull(offset + 30) ? null : cursor.getInt(offset + 30), // online_status
            cursor.isNull(offset + 31) ? null : cursor.getInt(offset + 31), // bi_followers_count
            cursor.isNull(offset + 32) ? null : cursor.getString(offset + 32), // lang
            cursor.isNull(offset + 33) ? null : cursor.getString(offset + 33), // star
            cursor.isNull(offset + 34) ? null : cursor.getString(offset + 34), // mbtype
            cursor.isNull(offset + 35) ? null : cursor.getString(offset + 35), // mbrank
            cursor.isNull(offset + 36) ? null : cursor.getString(offset + 36) // block_word
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, JWBUser entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setIdstr(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setScreen_name(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setName(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setProvince(cursor.isNull(offset + 4) ? null : cursor.getInt(offset + 4));
        entity.setCity(cursor.isNull(offset + 5) ? null : cursor.getInt(offset + 5));
        entity.setLocation(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setDescription(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setUrl(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setProfile_image_url(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setProfile_url(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setDomain(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setWeihao(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
        entity.setGender(cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13));
        entity.setFollowers_count(cursor.isNull(offset + 14) ? null : cursor.getInt(offset + 14));
        entity.setFriends_count(cursor.isNull(offset + 15) ? null : cursor.getInt(offset + 15));
        entity.setStatuses_count(cursor.isNull(offset + 16) ? null : cursor.getInt(offset + 16));
        entity.setFavourites_count(cursor.isNull(offset + 17) ? null : cursor.getInt(offset + 17));
        entity.setCreated_at(cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18));
        entity.setFollowing(cursor.isNull(offset + 19) ? null : cursor.getShort(offset + 19) != 0);
        entity.setAllow_all_act_msg(cursor.isNull(offset + 20) ? null : cursor.getShort(offset + 20) != 0);
        entity.setGeo_enabled(cursor.isNull(offset + 21) ? null : cursor.getShort(offset + 21) != 0);
        entity.setVerified(cursor.isNull(offset + 22) ? null : cursor.getShort(offset + 22) != 0);
        entity.setVerified_type(cursor.isNull(offset + 23) ? null : cursor.getInt(offset + 23));
        entity.setRemark(cursor.isNull(offset + 24) ? null : cursor.getString(offset + 24));
        entity.setAllow_all_comment(cursor.isNull(offset + 25) ? null : cursor.getShort(offset + 25) != 0);
        entity.setAvatar_large(cursor.isNull(offset + 26) ? null : cursor.getString(offset + 26));
        entity.setAvatar_hd(cursor.isNull(offset + 27) ? null : cursor.getString(offset + 27));
        entity.setVerified_reason(cursor.isNull(offset + 28) ? null : cursor.getString(offset + 28));
        entity.setFollow_me(cursor.isNull(offset + 29) ? null : cursor.getShort(offset + 29) != 0);
        entity.setOnline_status(cursor.isNull(offset + 30) ? null : cursor.getInt(offset + 30));
        entity.setBi_followers_count(cursor.isNull(offset + 31) ? null : cursor.getInt(offset + 31));
        entity.setLang(cursor.isNull(offset + 32) ? null : cursor.getString(offset + 32));
        entity.setStar(cursor.isNull(offset + 33) ? null : cursor.getString(offset + 33));
        entity.setMbtype(cursor.isNull(offset + 34) ? null : cursor.getString(offset + 34));
        entity.setMbrank(cursor.isNull(offset + 35) ? null : cursor.getString(offset + 35));
        entity.setBlock_word(cursor.isNull(offset + 36) ? null : cursor.getString(offset + 36));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(JWBUser entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(JWBUser entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}
