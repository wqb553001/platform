
# 一. 所遇问题记录
#### 1. 模块间依赖问题
（1）模块A 依赖于 模块B
会出现：模块A 会 将 模块B 的 pom.xml 依赖一并引入

#### 2. Spring JPA 默认不支持 LIMIT
//加入：nativeQuery注解时，写原生sql，支持limit函数  
例：
@Query(nativeQuery=true, value = "SELECT u.*,bu.* from t_s_user u LEFT JOIN t_s_base_user bu ON u.id = bu.id LIMIT ?")
List<TSUser> findByLimit(int limitNum);


#### 2. Browserslist: caniuse-lite is outdated. Please run next command `npm update`   Failed to compile.  
  cnpm i caniuse-lite@latest  
  cnpm i caniuse-lite browserslist@latest

#### 2. 在idea中maven项目 jar包下载不完整解决办法
较暴力的办法 ———— 进入到.m2，执行命令:  
    find ./ -cmin -30 -print -exec rm -rf {} \;  
原理:将最近30分钟新下载的包全部删除,然后再重新下载就好喽

