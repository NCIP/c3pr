package edu.duke.cabig.c3pr.utils.web.spring.tabbedflow;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.easymock.classextension.EasyMock;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import edu.duke.cabig.c3pr.AbstractTestCase;
import edu.duke.cabig.c3pr.domain.AbstractMutableDeletableDomainObject;

public class RowManagableTabTest extends AbstractTestCase{

    RowManagableTab rowManagableTab;
    HttpServletRequest request;
    CommandClass command;
    DomainObjectDummy domainObjectDummy;
    Errors errors;
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        rowManagableTab=new RowManagableTab(){};
        request=registerMockFor(HttpServletRequest.class);
        command=new CommandClass();
        command.list=new ArrayList();
        domainObjectDummy=new DomainObjectDummy();
        errors=registerMockFor(Errors.class);
        command.list.add(domainObjectDummy);
    }
    
    public void testDeleteRowByHashCodeSoftDelete() throws Exception{
        EasyMock.expect(request.getParameter(rowManagableTab.getCollectionParamName())).andReturn("list");
        EasyMock.expect(request.getParameter(rowManagableTab.getDeleteHashCodeParamName())).andReturn("HC#"+domainObjectDummy.hashCode());
        EasyMock.expect(request.getParameter(rowManagableTab.getSoftDeleteParamName())).andReturn("true");
        replayMocks();
        rowManagableTab.deleteRow(request, command, errors);
        assertEquals("true", domainObjectDummy.getRetiredIndicator());
        verifyMocks();
    }
    
    public void testDeleteRowByHashCode() throws Exception{
        EasyMock.expect(request.getParameter(rowManagableTab.getCollectionParamName())).andReturn("list");
        EasyMock.expect(request.getParameter(rowManagableTab.getDeleteHashCodeParamName())).andReturn("HC#"+domainObjectDummy.hashCode());
        expectNullRequestParam(rowManagableTab.getSoftDeleteParamName());
        replayMocks();
        rowManagableTab.deleteRow(request, command, errors);
        assertEquals(0, command.list.size());
        verifyMocks();
    }
    
    public void testDeleteRowByHashCodeIndexNotFound() throws Exception{
        EasyMock.expect(request.getParameter(rowManagableTab.getCollectionParamName())).andReturn("list");
        EasyMock.expect(request.getParameter(rowManagableTab.getDeleteHashCodeParamName())).andReturn("HC#1212");
        replayMocks();
        ModelAndView modelAndView=rowManagableTab.deleteRow(request, command, errors);
        assertEquals(1, command.list.size());
        assertEquals("Unmatched hashCode/Id", modelAndView.getModel().get(rowManagableTab.getFreeTextModelName()));
        verifyMocks();
    }
    
    public void testDeleteRowByID() throws Exception{
        domainObjectDummy.setId(1);
        EasyMock.expect(request.getParameter(rowManagableTab.getCollectionParamName())).andReturn("list");
        EasyMock.expect(request.getParameter(rowManagableTab.getDeleteHashCodeParamName())).andReturn("ID#1");
        expectNullRequestParam(rowManagableTab.getSoftDeleteParamName());
        replayMocks();
        rowManagableTab.deleteRow(request, command, errors);
        assertEquals(0, command.list.size());
        verifyMocks();
    }
    class CommandClass{
        private List list;

        public List getList() {
            return list;
        }

        public void setList(List list) {
            this.list = list;
        }
        
    }
    
    class DomainObjectDummy extends AbstractMutableDeletableDomainObject{
    }
    
    private void expectNullRequestParam(String param){
        EasyMock.expect(request.getParameter(rowManagableTab.getSoftDeleteParamName())).andReturn(null);
        for (int i = 0; i < WebUtils.SUBMIT_IMAGE_SUFFIXES.length; i++) {
            String suffix = WebUtils.SUBMIT_IMAGE_SUFFIXES[i];
            EasyMock.expect(request.getParameter(param+ suffix)).andReturn(null);
        }
    }
}
