/**
 * 
 */
package org.dragonli.tools.general;

import java.util.Queue;
import java.util.concurrent.ConcurrentMap;

import org.apache.log4j.Logger;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author freeangel
 *
 */
public class DataCachePool 
{
	protected static final Logger logger = Logger.getLogger(DataCachePool.class);
	
	protected static final ConcurrentMap<Class<?>,Queue<?>> dic 
		= new ConcurrentHashMap<Class<?>,Queue<?>>();
	protected static final ConcurrentMap<Object,Boolean> allElements 
	= new ConcurrentHashMap<>();
	
	public static <T> T get(Class<? extends T> clazz) throws Exception
	{
		Queue<T> r = createQueue(clazz);
		
		//r must be not null now
		T t = r.poll();
		if( t == null )
		{
			t =clazz.newInstance();
//			r.add(t);
		}
		allElements.remove(t);
		return t;
		
	}
	
	@SuppressWarnings("unchecked")
	private static <T> Queue<T> createQueue(Class<? extends T> clazz)
	{
		Queue<T> r = (Queue<T>) dic.get(clazz);
		if( r == null )
		{
			dic.putIfAbsent(clazz, new ConcurrentLinkedQueue<T>() ) ;
			r = (Queue<T>) dic.get(clazz);
		}
//		allElements.remove(r);
		return r;
	}
	
	public static <T> void back(T t)
	{
		back(t,"");
	}
	
	public static <T> void back(T t,String log)
	{
		if(t==null)
			return ;
		if( allElements.containsKey(t) )
		{
			logger.warn(" same object cant be backed again! it will be refused! ClassName:  "+t.getClass());
			return ;
		}
		
		allElements.put(t, true);
		@SuppressWarnings("unchecked")
		Queue<T> r = (Queue<T>) createQueue(t.getClass());
		if(t instanceof IDataCachePool)
			((IDataCachePool)t).clear();
		r.add(t);
	}
	
}
