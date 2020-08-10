package com.doctor.assistant.userserver.springdata.service;

import com.doctor.assistant.userserver.springdata.entity.DepartDetailEntity;
import com.doctor.assistant.userserver.springdata.entity.TSUser;
import com.doctor.assistant.userserver.springdata.entity.UserDepartEntity;
import com.doctor.assistant.userserver.springdata.repository.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.*;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private DepartRepository departRepository;
    @Autowired
    private DepartDetailRepository departDetailRepository;
    @Autowired
    private UserDepartRepository userDepartRepository;
    @PersistenceContext
    private EntityManager entityManager;

    public List<TSUser> findByRoleName(String roleName){
//        TSRole role = roleRepository.findFirstByRoleName(roleName);
//        role.getId();
        String queryStr = "SELECT u FROM TSUser u WHERE u.id IN(" +
                "SELECT ru.TSUser.id FROM TSRoleUser ru WHERE ru.TSRole.id in(" +
                    "SELECT r.id FROM TSRole r WHERE r.roleName = '"+roleName+
                    "')" +
                ")";
        Query query = entityManager.createQuery(queryStr);
        List<TSUser> resultList = query.getResultList();
        return resultList;
    }

    public List<DepartDetailEntity> findDepartDetailByAccountbookIdAndDepartDetailId(String accountbookId){
        if(StringUtils.isBlank(accountbookId)){
            accountbookId = "2c91e3ec6ad89cfb016ae4657a010362";
        }
        List<DepartDetailEntity> detailEntityList = departDetailRepository.findByAccountbookId(accountbookId);
        return detailEntityList;
    }

    public List<TSUser> findUserByAccountbookIdAndDepartDetailId(String accountbookId, String departDetailId){
        if(StringUtils.isBlank(accountbookId)){
            accountbookId = "2c91e3ec6ad89cfb016ae4657a010362";
        }
        List<UserDepartEntity> userDepartEntities = userDepartRepository.findUserDepartEntitiesByAccountbookIdAndDepartDetail_Id(accountbookId, departDetailId);
        if(CollectionUtils.isEmpty(userDepartEntities)){
            return null;
        }
        List<TSUser> userList = new ArrayList<>(userDepartEntities.size());
        for (UserDepartEntity userDepartEntity : userDepartEntities){
//            Set<UserDepartEntity> userDepartEntitySet = new HashSet<>();
//            userDepartEntitySet.add(userDepartEntity);
            String userId = userDepartEntity.getUserId();
            Optional<TSUser> user = userRepository.findById(userId);
            TSUser userInfo = user.get();
//            userInfo.setUserDepartDetailSet(userDepartEntitySet);
            userList.add(userInfo);
        }
        return userList;
    }

//    public TSUser findByEmpNoStr(String empNo){
//        String sqlStr = "SELECT u.*,u.accountbook.accountbookName = a.accountbookName,u.departDetail.depart.departName = d.departName FROM TSUser u \n" +
//                "LEFT JOIN TSBaseUser bu \t\t\t\t\t\t\t\t\tON u.ID = bu.ID \n" +
//                "LEFT JOIN UserAccountbookEntity ua \t\t\tON u.ID = ua.userId  \n" +
//                "LEFT JOIN AccountbookEntity a \t\t\t\t\t\tON a.ID = ua.accountbookId\n" +
//                "LEFT JOIN UserDepartEntity ud\t\t\t\t\t\tON u.ID = ud.userId \n" +
//                "LEFT JOIN DepartDetailEntity dd\t\t\t\t\tON dd.ID = ud.departDetailId \n" +
//                "LEFT JOIN DepartEntity d \t\t\t\t\t\t\t\tON d.ID = dd.departId \n" +
//                "WHERE u.emp_no = 'U00148';";
//
//        Query query = entityManager.createQuery(sqlStr);
//        TSUser result = (TSUser) query.getSingleResult();
//        return result;
//    }

    public static void main(String[] args) {

    }
}
