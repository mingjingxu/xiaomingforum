package ming.forum.api;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * 执行所有控制器单元测试
 * @author mingJingxu
 * @date 2022-03-08
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({UserControllerTest.class, CommentControllerTest.class})
public class SuiteExecuteTests 
{

}
