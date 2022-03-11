package ming.forum.api.common;

import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 应用程序容器工具类
 * @author mingJingxu
 * @date 2022-03-06
 */
@Component
public class ApplicationContextHelper implements ApplicationContextAware
{
	private static ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException 
	{
		ApplicationContextHelper.applicationContext = applicationContext;		
	}

	/**
	 * 根据类型获取Bean
	 * @param <T>
	 * @param targetClz
	 * @return
	 */
    public static<T> T getBean(Class<T> targetClz)
    {
        T beanInstance = null;
        //优先按type查
        try 
        {
            beanInstance = (T) applicationContext.getBean(targetClz);
        }
        catch (Exception e)
        {
        	throw new RuntimeException("根据类型获取Bean异常", e);
        }
        //按name查
        if(beanInstance == null)
        {
            String simpleName = targetClz.getSimpleName();
            //首字母小写
            simpleName = Character.toLowerCase(simpleName.charAt(0)) + simpleName.substring(1);
            beanInstance = applicationContext.getBean(simpleName, targetClz);
        }
        if(beanInstance == null)
        {
            throw new RuntimeException("Component " + targetClz + " can not be found in Spring Container");
        }
        return beanInstance;
    }
    
    /**
     * 根据名称获取Bean
     * @param claz
     * @return
     */
    public static Object getBean(String claz)
    {
        return ApplicationContextHelper.applicationContext.getBean(claz);
    }

    /**
     * 获取指定接口的所有实现类
     **/
    public static<T> Map<String, T> getBeansOfType(Class<T> targetClz)
    {
        return ApplicationContextHelper.applicationContext.getBeansOfType(targetClz);
    }
}
