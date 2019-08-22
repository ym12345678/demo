package com.domain.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * hql拼接获取结果工具类
 * @author RZH
 * @Date 2018年09月20日
 */
@Transactional(readOnly=true)
@Repository
public class JpaUtils {

    @Autowired
    @PersistenceContext
    private EntityManager entityManager;

    private static Logger log = LogManager.getLogger(JpaUtils.class);
    
    
    
    public int execute(String sql,Map<String,Object> parameters) {
    	Query query = entityManager.createNativeQuery(sql);
    	if (parameters != null) {
    		Iterator<Map.Entry<String, Object>> it = parameters.entrySet().iterator();
    		while (it.hasNext()) {
    			Map.Entry<String, Object> entry = it.next();
    			query.setParameter(entry.getKey(), entry.getValue());
    			}
    	}
    	return query.executeUpdate();

    }
    /**
     * 获取列表
     * @param hql
     * @param params
     * @param requiredType
     * @return
     */
    public  <T> List<T> list(String hql, Map<String, Object> params, Class<T> requiredType) {
        Query query = entityManager.createQuery(hql, requiredType);
        if (params != null) {
            for (String key : params.keySet()) {
                query.setParameter(key, params.get(key));
            }
        }
        return query.getResultList();
    }
    /**
     * 带条件获取分页数据
     * @param hql
     * @param params
     * @param pageable
     * @return
     */
    @SuppressWarnings("unchecked")
    public  <T> Page<T> page(String hql, Map<String, Object> params, Pageable pageable, Class<T> requiredType) {
        Query query = entityManager.createQuery(hql, requiredType);
        TypedQuery<Long> cQuery = (TypedQuery<Long>) entityManager.createQuery(QueryUtils.createCountQueryFor(hql));
        if (params != null) {
            for (String key : params.keySet()) {
                query.setParameter(key, params.get(key));
                cQuery.setParameter(key, params.get(key));
            }
        }
        query.setFirstResult((int)pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());
        return PageableExecutionUtils.getPage(query.getResultList(), pageable, ()->executeCountQuery(cQuery));
    }

    /**
     * 通过原生的sql待条件获取分页数据
     * @param sql
     * @param params
     * @param pageable
     * @return
     */
    @SuppressWarnings("unchecked")
    public  <T> Page<T> pageByNativeSql(String sql, Map<String, Object> params, Pageable pageable, Class<T> requiredType) {
        Query query = entityManager.createNativeQuery(sql,requiredType);
        TypedQuery<Long> cQuery = (TypedQuery<Long>) entityManager.createNativeQuery(QueryUtils.createCountQueryFor(sql));
        if (params != null) {
            for (String key : params.keySet()) {
                query.setParameter(key, params.get(key));
                cQuery.setParameter(key, params.get(key));
            }
        }
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());
        return PageableExecutionUtils.getPage(query.getResultList(), pageable, ()->executeCountQuery(cQuery));
    }

    /**
     * 返回查询的条数
     * @param query must not be {@literal null}.
     * @return
     */
    private Long executeCountQuery(TypedQuery<Long> query) {
        Assert.notNull(query, "TypedQuery must not be null!");
        List<Long> totals = query.getResultList();
        Long total = 0L;
        for (Long element : totals) {
            total += element == null ? 0 : element;
        }
        return total;
    }
    /** 返回map集合数据
    * @auther: LJ
    * @param sql
	* @param params
    * @return:
    */
    public List<Map<String, Object>> returnMaps(String sql, Map<String, Object> params) {
        log.debug("sql语句:"+sql+"参数params:"+params);
        Query q = entityManager.createNativeQuery(sql);
        if (params != null) {
            for (String key : params.keySet()){
                q.setParameter(key,params.get(key));
            }
        }
        q.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List<Map<String,Object>> resultList = q.getResultList();
        return resultList;
    }

    /**
     * 返回map集合数据
     *
     * @param q
     * @param params
     * @auther: LJ
     * @return:
     */
    public List<Map<String, Object>> returnMapsNew(Query q, Map<String, Object> params) {
        if (params != null) {
            for (String key : params.keySet()) {
                q.setParameter(key, params.get(key));
            }
        }
        q.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List<Map<String, Object>> resultList = q.getResultList();
        return resultList;
    }
}
