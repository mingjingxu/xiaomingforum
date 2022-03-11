package ming.forum.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动类
 * @author mingJingxu
 * @date 2022-03-05
 */
@SpringBootApplication(scanBasePackages = {"ming.forum.*"})
public class App 
{
	private static Logger logger = LoggerFactory.getLogger(App.class);
	//操作系统名称Key
	public static final String osNameKey = "os.name";
	
	//windows操作系统名称标识
	public static final String windowsOsName = "windows";
	
	//Mac OS 名称标识
	public static final String macOsName = "mac";
	
	//默认端口号
	public static final int port = 8080;
	
    public static void main( String[] args )
    {
    	SpringApplication.run(App.class,args);
    	
    	//启动浏览器打开网站
    	try
    	{
    		//Windows启动浏览器
    		String osName = System.getProperty(osNameKey).toLowerCase();
    		logger.info("操作系统名称：" + osName);
    		if(osName.contains(windowsOsName))
    		{
    			Runtime.getRuntime().exec("cmd /c start http://localhost:" + port);
    		}
    		//Mac OS启动浏览器
    		else if(osName.contains(macOsName))
    		{
    			Runtime.getRuntime().exec("open -a Safari http://localhost:" + port);
    		}
    	}
    	catch(Exception e)
    	{
    		logger.error("启动浏览器失败，" + e.getMessage());
    	}
    }
}
