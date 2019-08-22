package com.domain.utils;

import com.domain.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.FatalBeanException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import static org.springframework.beans.BeanUtils.getPropertyDescriptor;

/**
 * 复制属性值 jpa动态更新属性
 *
 * @author: LJ
 * @create: 2018-12-07
 **/
public class DaoUtils {
    /**
     * 根据需要更新的值 动态更新数据库表字段   使用局限性：对象表中的属性值不能为基本类型，应为基本类型不是对象属性有默认属性值 ，比如0；而copyProperties根据属性值不为空才复制属性
     *
     * @param crudRepository DAO
     * @param t              更新对象
     * @param id             更新对象id
     * @auther: LJ
     * @return:
     */
    public static <T> void update(CrudRepository<T, String> crudRepository, T t, String id) {
//        T one = crudRepository.findOne(id);
//        copyProperties(t, one);
//        crudRepository.save(one);
    }


    public static void copyProperties(Object source, Object target) {
        Assert.notNull(source, "Source must not be null");
        Assert.notNull(target, "Target must not be null");
        Class<?> actualEditable = target.getClass();
        PropertyDescriptor[] targetPds = BeanUtils.getPropertyDescriptors(actualEditable);
        for (PropertyDescriptor targetPd : targetPds) {
            Method writeMethod = targetPd.getWriteMethod();
            if (writeMethod != null) {
                PropertyDescriptor sourcePd = getPropertyDescriptor(source.getClass(), targetPd.getName());
                if (sourcePd != null) {
                    Method readMethod = sourcePd.getReadMethod();
                    if (readMethod != null &&
                            ClassUtils.isAssignable(writeMethod.getParameterTypes()[0], readMethod.getReturnType())) {
                        try {
                            if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                                readMethod.setAccessible(true);
                            }
                            Object value = readMethod.invoke(source);
                            if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                                writeMethod.setAccessible(true);
                            }

                            if (value != null&&!excludeSpecialAttributes(value,targetPd.getName())) {
                                writeMethod.invoke(target, value);
                            }

                            if(source instanceof User && "getIp".equals(readMethod.getName())){
                                writeMethod.invoke(target, value);
                            }

                        } catch (Throwable ex) {
                            throw new FatalBeanException(
                                    "Could not copy property '" + targetPd.getName() + "' from source to target", ex);
                        }
                    }
                }

            }
        }
    }

    //排除特殊字段
    private static boolean excludeSpecialAttributes(Object value,String propertyDescriptorName){
        if ("financeRemark".equals(propertyDescriptorName)||"riskRemark".equals(propertyDescriptorName)||"userRemark".equals(propertyDescriptorName)){
            return false;
        }
        return value.equals("");
    }
}
