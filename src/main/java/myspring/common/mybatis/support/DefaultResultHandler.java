package myspring.common.mybatis.support;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;

public class DefaultResultHandler implements ResultHandler {
    private List list;

    public DefaultResultHandler()
    {
        list = new ArrayList();
    }

    public void handleRow(Object valueObject)
    {
        list.add(valueObject);
    }

    public List getList()
    {
        return list;
    }

    public void setList(List list)
    {
        this.list = list;
    }
    
	@Override
	public void handleResult(ResultContext context) {
		Object object = context.getResultObject();
        list.add(object);
	}

}
