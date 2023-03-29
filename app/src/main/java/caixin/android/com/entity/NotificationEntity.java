package caixin.android.com.entity;

public class NotificationEntity {
    /**
     * id : 4
     * uid : 3
     * type : 2
     * chat_type_id : 104345742934017
     * review :
     * status : 1
     * addtime : 2020-02-26 16:42:58
     * edatetime : 2020-02-26 16:44:07
     * info : {"id":"104345742934017","name":"群组测试1ert","description":"散大夫的萨法萨地方","affiliations_count":7}
     */

    private int id;
    private int uid;
    private int type;
    private String chat_type_id;
    private String review;
    private int status;
    private String addtime;
    private String edatetime;
    private InfoBean info;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getChat_type_id() {
        return chat_type_id;
    }

    public void setChat_type_id(String chat_type_id) {
        this.chat_type_id = chat_type_id;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getEdatetime() {
        return edatetime;
    }

    public void setEdatetime(String edatetime) {
        this.edatetime = edatetime;
    }

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public static class InfoBean {
        /**
         * id : 104345742934017
         * name : 群组测试1ert
         * description : 散大夫的萨法萨地方
         * affiliations_count : 7
         */

        private String id;
        private String name;
        private String description;
        private int affiliations_count;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getAffiliations_count() {
            return affiliations_count;
        }

        public void setAffiliations_count(int affiliations_count) {
            this.affiliations_count = affiliations_count;
        }
    }
}
