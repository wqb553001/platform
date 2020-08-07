package com.doctor.assistant.userserver.springdata.service;

import com.doctor.assistant.userserver.springdata.entity.TSUser;
import com.doctor.assistant.userserver.springdata.repository.RoleRepository;
import com.doctor.assistant.userserver.springdata.repository.UserRepository;
import com.doctor.assistant.userserver.springdata.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserRoleService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;
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
        Map map = new HashMap();
        String put1 = (String)map.put("aa", "aaaaa");
        System.out.println(put1);
        String put2 = (String)map.put("aa", "bbbb");
        System.out.println(put2);
        String put3 = (String)map.get("aa");
        System.out.println(put3);
        String put4 = (String)map.put("aa", null);
        System.out.println(put4);
        String put5 = (String)map.get("aa");
        System.out.println(put5);

    }
}
