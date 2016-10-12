package com.andy.tool.listfilter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * 容器过滤
 * Created by zhangshouzheng on 2016/9/13.
 */
public class ListFilter {
    public interface Predicate<T> {
        boolean test(T o);
    }

    public static <T> void filter(Collection<T> collection, Predicate<T> predicate) {
        if ((collection != null) && (predicate != null)) {
            Iterator<T> itr = collection.iterator();
            while(itr.hasNext()) {
                T obj = itr.next();
                if (!predicate.test(obj)) {
                    itr.remove();
                }
            }
        }
    }

    public static void main(String[] args) {
        List list =new ArrayList();
        for (int i=0 ;i<10;i++){
            list.add(i);
            System.out.println(list.get(i));
        }
        filter(list, new Predicate<Integer>() {
            public boolean test(Integer i) {
                return i <= 5;
            }
        });

        for (int i=0 ;i<10;i++){
            list.add(i);
            System.out.println(list.get(i));
        }
    }
}
