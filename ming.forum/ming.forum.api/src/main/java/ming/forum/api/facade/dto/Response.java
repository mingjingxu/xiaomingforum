package ming.forum.api.facade.dto;

/**
 * 返回前端的统一DTO类
 * @author mingJingxu
 * @date 2022-03-05
 */
public class Response 
{	
	/** 是否成功*/
	private boolean success;
    
	/** 错误编码*/
    private String errCode;
    
    /** 错误提示*/
    private String errMessage;
    
    /** 数据对象*/
    private Object data;

	public boolean isSuccess() 
	{
        return success;
    }
    
    public void setSuccess(boolean isSuccess) 
    {
        this.success = isSuccess;
    }
    
    public String getErrCode() 
    {
        return errCode;
    }
    
    public void setErrCode(String errCode) 
    {
        this.errCode = errCode;
    }
    
    public String getErrMessage() 
    {
        return errMessage;
    }
    
    public void setErrMessage(String errMessage) 
    {
        this.errMessage = errMessage;
    }
	
	public Object getData() 
	{
		return data;
	}

	public void setData(Object data) 
	{
		this.data = data;
	}

	/**
	 * 重写toString方法
	 */
    @Override
    public String toString() 
    {
        return "Response [isSuccess=" + success + ", errCode=" + errCode + ", errMessage=" + errMessage + "]";
    }

    /**
     * 构建失败的响应
     * @param errCode
     * @param errMessage
     * @return
     */
    public static Response buildFailure(String errCode, String errMessage) 
    {
        Response response = new Response();
        response.setSuccess(false);
        response.setErrCode(errCode);
        response.setErrMessage(errMessage);
        return response;
    }

    /**
     * 构建成功的响应
     * @return
     */
    public static Response buildSuccess()
    {
        Response response = new Response();
        response.setSuccess(true);
        return response;
    }

    /**
     * 构建包含数据的成功响应
     * @param data
     * @return
     */
    public static Response ofData(Object data)
    {
    	Response response = buildSuccess();
    	response.setData(data);
    	return response;
    }
}
