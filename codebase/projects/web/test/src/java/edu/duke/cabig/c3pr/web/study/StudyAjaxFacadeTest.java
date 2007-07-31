package edu.duke.cabig.c3pr.web.study;

import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.web.ajax.StudyAjaxFacade;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.isA;
import static org.easymock.EasyMock.eq;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * User: kherm
 *
 * @author kherm manav.kher@semanticbits.com
 */
public class StudyAjaxFacadeTest extends AbstractStudyControllerTest{

    StudyAjaxFacade facade;

    @Override
    protected void setUp() throws Exception {
        super.setUp();    //To change body of overridden methods use File | Settings | File Templates.

        facade = new StudyAjaxFacade();
        facade.setStudyDao(studyDao);

    }

    public void testTableTest(){
        List<Study> dummyList = new ArrayList<Study>();
        dummyList.add(command);

         Map map = errors.getModel();
        map.put("searchType",new ArrayList<String>(){{
            add("id");
        }}
        );
           map.put("searchText",new ArrayList<String>(){{
            add("0");
        }}
        );

        expect(command.getId()).andReturn(new Integer("1"));
        expect(studyDao.searchByExample(isA(Study.class),eq(true))).andReturn(dummyList);
        replayMocks();

        assertTrue((facade.getTable(map,request)).length()>0);

        verifyMocks();

    }

}
