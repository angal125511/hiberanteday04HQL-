package com.zrzy.test;

import com.zrzy.entity.User;
import com.zrzy.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: 徐大伟
 * User: ${和敬清寂}
 * Date: 2019/4/11
 * Time: 16:33
 */
public class UserTest {

    Session session = null;

    @Before // 之前
    public void before(){

        session= HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
    }

    @After
    public void after(){ // 之后

        session.getTransaction().commit(); // 事物提交
        session.close(); // 关闭session
    }

    @Test
    public void addUser(){

//        for (int i=10; i<10;i++){
//            User user = new User("angal"+i,"admin"+i);
//            session.save(user);
//        }

        User user = new User();
        user.setUsername("admin111");
        user.setPassword("admin111");
        session.save(user);

        User user1 = new User();
        user1.setUsername("admin222");
        user1.setPassword("admin222");
        session.save(user1);

    }

    @Test
    public void queryAll(){ // 查询全部

        String hql="from User"; // hql语句：from+类名
        Query<User> query = session.createQuery(hql,User.class);

        List<User> list = query.list();

        for (User user:list) {
            System.out.println(user);
        }

    }

    @Test
    public void queryOne(){ // 通过id查询

        String hql="from User where id=:sid"; // 问号以被废弃

        Query<User> query = session.createQuery(hql,User.class);
        query.setParameter("sid",1);
        User user = query.list().get(0);
        System.out.println(user);

    }

    @Test
    public void queryLike(){ // 模糊查询
        String hql="from User where id <:uid";
        Query<User> query = session.createQuery(hql, User.class);

        query.setParameter("uid",5);
        List<User> list = query.list();

        for (User user:list ) {
            System.out.println(user);
        }

    }

    @Test
    public void queryPage(){ // 分页查询

        String hql="from User";
        Query<User> query = session.createQuery(hql, User.class); // 创建查询
        query.setFirstResult(1); // 查询开始索引
        query.setMaxResults(5); // 每页显示条数

        List<User> list = query.list(); //获取查询信息

        for (User user:list) {
            System.out.println(user);
        }

    }

    @Test
    public void queryLile2(){ // 命名查询

        String name="queryUser"; // user.hbm.xml映射hql语句
        Query query = session.getNamedQuery(name);
        query.setParameter("username","a%");

        List list = query.list();
        for (Object user:list) {

            System.out.println(user);
        }

    }

    @Test
    public void queryProjection(){ // 投影查询
        String hql="select id,username,password from User";
        Query<Object[]> query = session.createQuery(hql, Object[].class);

        List<Object[]> list = query.list();
        for (Object[] obj:list) {

            System.out.println(Arrays.toString(obj));
            System.out.println(obj[0]);
        }

    }

    @Test
    public void queryCount(){ // 聚合查询
        String hql="select count(*) from User";
        Query<Object> query = session.createQuery(hql, Object.class);
        Object uniqueResult = query.uniqueResult();

        Number number = (Number) uniqueResult;
        int i = number.intValue();
        System.out.println("angal"+i);

    }

    @Test
    public void queryLoad(){

        User user = session.load(User.class,1); // load:删除
        User user1 = session.get(User.class,1); // get 查询

        System.out.println(user);
        System.out.println(user1);
    }



}
