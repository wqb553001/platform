package com.doctor.assistant.userserver.springdata.entity;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;

/**
 * 用户-账簿 实体
 */
@Entity
@Table(name = "tb_user_accountbook")
public class UserAccountbookEntity extends IdEntity implements java.io.Serializable {
    @ManyToOne
    @JoinColumn(name="user_id")
    private String userId;

    private String accountbookId;
    private TSUser tsUser;
    private AccountbookEntity accountbook;

    @Column(name = "user_id", insertable = false, updatable = false)//外键名称，参考主键
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Transient
    public String getAccountbookId() {
        return accountbookId;
    }

    public void setAccountbookId(String accountbookId) {
        this.accountbookId = accountbookId;
    }

    @Transient
    public TSUser getTsUser() {
        return tsUser;
    }

    public void setTsUser(TSUser tsDepart) {
        this.tsUser = tsDepart;
    }

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "accountbook_id")
    @NotFound(action= NotFoundAction.IGNORE)
    public AccountbookEntity getAccountbook() {
        return accountbook;
    }

    public void setAccountbook(AccountbookEntity accountbook) {
        this.accountbook = accountbook;
    }
}
