package com.doctor.assistant.userserver.springdata.entity;

import javax.persistence.*;

/**
 * 用户-账簿 实体
 */
@Entity
@Table(name = "tb_user_accountbook")
public class UserAccountbookEntity extends IdEntity implements java.io.Serializable {
    private TSUser tsUser;
    private AccountbookEntity accountbook;

	public UserAccountbookEntity() {
	}

	public UserAccountbookEntity(TSUser tsUser, AccountbookEntity accountbook) {
		this.tsUser = tsUser;
		this.accountbook = accountbook;
	}

	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    public TSUser getTsUser() {
        return tsUser;
    }

    public void setTsUser(TSUser tsDepart) {
        this.tsUser = tsDepart;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "accountbook_id")
    public AccountbookEntity getAccountbook() {
        return accountbook;
    }

    public void setAccountbook(AccountbookEntity accountbook) {
        this.accountbook = accountbook;
    }
}
