package edu.duke.cabig.c3pr.web.ajax;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.extremecomponents.table.bean.Column;
import org.extremecomponents.table.bean.Row;
import org.extremecomponents.table.bean.Table;
import org.extremecomponents.table.context.Context;
import org.extremecomponents.table.context.HttpServletRequestContext;
import org.extremecomponents.table.core.TableModel;
import org.extremecomponents.table.core.TableModelImpl;

import edu.duke.cabig.c3pr.dao.ParticipantDao;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.SystemAssignedIdentifier;

public class SearchParticipantAjaxFacade {
	private static Log log = LogFactory.getLog(SearchParticipantAjaxFacade.class);
	
	public ParticipantDao participantDao ;

    public Object build(TableModel model, Collection participants) throws Exception {

        Table table = model.getTableInstance();
        table.setAutoIncludeParameters(false);
        table.setTableId("assembler");
        table.setItems(participants);
        table.setAction(model.getContext().getContextPath() + "/pages/personAndOrganization/participant/createParticipant");
        table.setTitle("Participants");
        table.setShowPagination(true);
        table.setRowsDisplayed(15);
        table.setOnInvokeAction("buildTable('assembler')");
        table.setImagePath(model.getContext().getContextPath() + "/images/table/*.gif");
        table.setShowExports(false);
        table.setSortable(true);
        model.addTable(table);

        Row row = model.getRowInstance();
        row.setHighlightRow(Boolean.TRUE);
        row.setInterceptor("edu.duke.cabig.c3pr.web.ajax.ParticipantRowInterceptor");
        model.addRow(row);

        Column name = model.getColumnInstance();
        name.setTitle("Name");
        name.setProperty("fullName");
        model.addColumn(name);
        
        Column primaryIdentifier = model.getColumnInstance();
        primaryIdentifier.setTitle("Primary identifier");
        primaryIdentifier.setProperty("primaryIdentifierValue");
        model.addColumn(primaryIdentifier);
        
        Column gender = model.getColumnInstance();
        gender.setTitle("Gender");
        gender.setProperty("administrativeGenderCode");
        model.addColumn(gender);
        
        Column races = model.getColumnInstance();
        races.setTitle("Race(s)");
        races.setProperty("raceCodes");
        races.setCell((SubjectRaceDisplayCell.class).getName());
        model.addColumn(races);
        
        Column birthDate = model.getColumnInstance();
        birthDate.setTitle("Birth date");
        birthDate.setProperty("birthDateStr");
        model.addColumn(birthDate);
        
        return model.assemble();
    }

    public String getTable(Map<String, List> parameterMap, String[] params,
                    HttpServletRequest request) {
        Participant participant = new Participant();
        String text = "" ;
        String type = "" ;

        if (!StringUtils.isEmpty(params[0])) {
            type = params[0];
        }
        if (!StringUtils.isEmpty(params[1])) {
            text = params[1] ;
        }

        log.debug("search string = " + text + "; type = " + type);

        if ("N".equals(type)) {
            participant.setLastName(text);
        }
        if ("F".equals(type)) {
            participant.setFirstName(text);
        }
        if ("Identifier".equals(type)) {
            SystemAssignedIdentifier identifier = new SystemAssignedIdentifier();
            identifier.setValue(text);
            // FIXME:
            participant.addIdentifier(identifier);
        }

        List<Participant> participants = participantDao.searchByExample(participant);

        Context context = null;
        if (parameterMap == null) {
            context = new HttpServletRequestContext(request);
        }
        else {
            context = new HttpServletRequestContext(request, parameterMap);
        }

        TableModel model = new TableModelImpl(context);
        try {
            return build(model, participants).toString();
        }
        catch (Exception e) {
            log.error("Exception caught in SearchParticipantFacade");
            e.printStackTrace();
        }

        return "";
    }

	public ParticipantDao getParticipantDao() {
		return participantDao;
	}

	public void setParticipantDao(ParticipantDao participantDao) {
		this.participantDao = participantDao;
	}

}
