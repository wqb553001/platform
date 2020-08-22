
# 一. 所遇问题记录
#### 1. 模块间依赖问题
（1）模块A 依赖于 模块B
会出现：模块A 会 将 模块B 的 pom.xml 依赖一并引入

#### 2. Spring JPA 默认不支持 LIMIT
//加入：nativeQuery注解时，写原生sql，支持limit函数  
例：
@Query(nativeQuery=true, value = "SELECT u.*,bu.* from t_s_user u LEFT JOIN t_s_base_user bu ON u.id = bu.id LIMIT ?")
List<TSUser> findByLimit(int limitNum);