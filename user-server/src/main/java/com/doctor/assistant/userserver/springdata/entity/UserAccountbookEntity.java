package com.doctor.assistant.userserver.springdata.entity;

import javax.persistence.*;

/**
 * 用户-账簿 实体
 */
@Entity
@Table(name = "tb_user_accountbook")
public class UserAccountbookEntity extends IdEntity implements java.io.Serializable {
    @Column(name = "user_id")
    private TSUser tsUser;
    @Column(name = "accountbook_id")
    private AccountbookEntity accountbook;

//	public UserAccountbookEntity() {
//	}
//
//	public UserAccountbookEntity(TSUser tsUser, AccountbookEntity accountbook) {
//		this.tsUser = tsUser;
//		this.accountbook = accountbook;
//	}

	@ManyToOne(targetEntity = TSUser.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)//外键名称，参考主键
    public TSUser getTsUser() {
        return tsUser;
    }

    public void setTsUser(TSUser tsDepart) {
        this.tsUser = tsDepart;
    }

    @ManyToOne(targetEntity = AccountbookEntity.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "accountbook_id", referencedColumnName = "id", insertable = false, updatable = false)
    public AccountbookEntity getAccountbook() {
        return accountbook;
    }

    public void setAccountbook(AccountbookEntity accountbook) {
        this.accountbook = accountbook;
    }
}
