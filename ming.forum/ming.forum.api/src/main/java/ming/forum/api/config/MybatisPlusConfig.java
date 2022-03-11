package ming.forum.api.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;

/**
 * Mybatis配置
 * @author mingJingxu
 * @date 2022-03-05
 */
@EnableTransactionManagement
@Configuration
@MapperScan({"ming.forum.api.repository.cmd", "ming.forum.api.repository.query"})
public class MybatisPlusConfig 
{
	/**
	 * 分页配置
	 * @return
	 */
	@Bean
    public PaginationInterceptor paginationInterceptor()
	{
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        return paginationInterceptor;
    }
	
	/**
	 * 乐观锁
	 * @return
	 */
	@Bean
    public OptimisticLockerInterceptor optimisticLoker() 
	{
        return new OptimisticLockerInterceptor();
    }
}
