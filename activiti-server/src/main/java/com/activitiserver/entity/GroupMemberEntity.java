package com.activitiserver.entity;import lombok.Data;import org.apache.ibatis.annotations.Mapper;import javax.persistence.Column;import javax.persistence.Table;import java.io.Serializable;@Mapper@Table(name = "group_member")@Datapublic class GroupMemberEntity implements Serializable {    int id;    @Column(name = "group_id")    String groupId;    @Column(name = "username")    String userName;}