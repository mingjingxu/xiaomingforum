package ming.forum.api.config;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;

/**
 * 自动填充字段配置
 * @author mingJingxu
 * @date 2022-03-05
 */
@Component
public class MingForumMetaObjectHandler implements MetaObjectHandler
{
	private static String CREATE_TIME = "createTime";
    private static String UPDATE_TIME = "updateTime";
    private static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 新增数据时，填充新增时间和修改时间
     * @Param
     * @return
     **/
    @Override
    public void insertFill(MetaObject metaObject)
    {
        String nowTime = new SimpleDateFormat(DATE_FORMAT).format(new Date());
        this.setFieldValByName(CREATE_TIME, nowTime, metaObject);
        this.setFieldValByName(UPDATE_TIME, nowTime, metaObject);
    }

    /**
     * 修改数据时，填充修改时间
     * @Param
     * @return
     **/
    @Override
    public void updateFill(MetaObject metaObject)
    {
        String nowTime = new SimpleDateFormat(DATE_FORMAT).format(new Date());
        this.setFieldValByName(UPDATE_TIME, nowTime, metaObject);
    }
}
