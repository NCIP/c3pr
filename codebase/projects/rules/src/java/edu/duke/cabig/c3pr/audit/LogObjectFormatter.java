package edu.duke.cabig.c3pr.audit;

import java.util.List;

public class LogObjectFormatter {

    public String toTextTable(DecisionContext dc) throws Exception {
        String offSet = "\t\t\t";
        StringBuilder builder = new StringBuilder();
        int headerWidth = this.getTableWidth(dc);

        /**
         * Build header line
         */
        builder.append(offSet);
        builder.append(line(headerWidth));
        builder.append("\n");

        /**
         * Build Rule Context Line
         */
        int k = "Rule Context Information".length();
        int x = (headerWidth - k) / 2;

        builder.append(offSet);
        builder.append(columnSeperator());
        builder.append(this.blank(x - 1));
        builder.append("Rule Context Information");
        int rest = headerWidth - (x + k);
        builder.append(this.blank(rest - 2));
        builder.append(columnSeperator());
        builder.append("\n");

        /**
         * Build Bottom line
         */
        builder.append(offSet);
        builder.append(columnSeperator());
        builder.append(line(headerWidth - 2));
        builder.append(columnSeperator());
        builder.append("\n");

        /**
         * Build RuleContext Table Row 1
         */
        int colWidth = headerWidth / 2;
        builder.append(offSet);
        builder.append(columnSeperator());
        builder.append(this.blank(2));
        builder.append(LogTitle.RULE_SET_NAME.getTitle());
        rest = colWidth - 2 - LogTitle.RULE_SET_NAME.getTitle().length();
        builder.append(this.blank(rest - 2));
        builder.append(columnSeperator());
        builder.append(this.blank(2));
        builder.append(dc.getFiredRuleSetInfo().getRuleSetName());
        rest = headerWidth - (colWidth + 2 + dc.getFiredRuleSetInfo().getRuleSetName().length());
        builder.append(this.blank(rest - 4));
        builder.append(columnSeperator());
        builder.append("\n");
        builder.append(offSet);
        builder.append(columnSeperator());
        builder.append(line(headerWidth - 2));
        builder.append(columnSeperator());
        builder.append("\n");
        /**
         * Build RuleContext Table Row 2
         */
        colWidth = headerWidth / 2;
        builder.append(offSet);
        builder.append(columnSeperator());
        builder.append(this.blank(2));
        builder.append(LogTitle.RULE_SET_TYPE.getTitle());
        rest = colWidth - 2 - LogTitle.RULE_SET_TYPE.getTitle().length();
        builder.append(this.blank(rest - 2));
        builder.append(columnSeperator());
        builder.append(this.blank(2));
        builder.append(dc.getFiredRuleSetInfo().getRuleSetType());
        rest = headerWidth - (colWidth + 2 + dc.getFiredRuleSetInfo().getRuleSetType().length());
        builder.append(this.blank(rest - 4));
        builder.append(columnSeperator());
        builder.append("\n");
        builder.append(offSet);
        builder.append(columnSeperator());
        builder.append(line(headerWidth - 2));
        builder.append(columnSeperator());
        builder.append("\n");

        /**
         * Build RuleContext Table Row 3
         */
        colWidth = headerWidth / 2;
        builder.append(offSet);
        builder.append(columnSeperator());
        builder.append(this.blank(2));
        builder.append(LogTitle.STUDY_NAME.getTitle());
        rest = colWidth - 2 - LogTitle.STUDY_NAME.getTitle().length();
        builder.append(this.blank(rest - 2));
        builder.append(columnSeperator());
        builder.append(this.blank(2));
        builder.append(dc.getFiredRuleSetInfo().getStudyName());
        rest = headerWidth - (colWidth + 2 + dc.getFiredRuleSetInfo().getStudyName().length());
        builder.append(this.blank(rest - 4));
        builder.append(columnSeperator());
        builder.append("\n");
        builder.append(offSet);
        builder.append(columnSeperator());
        builder.append(line(headerWidth - 2));
        builder.append(columnSeperator());
        builder.append("\n");

        /**
         * Build RuleContext Table Row 4
         */
        colWidth = headerWidth / 2;
        builder.append(offSet);
        builder.append(columnSeperator());
        builder.append(this.blank(2));
        builder.append(LogTitle.ORGANIZATION_NAME.getTitle());
        rest = colWidth - 2 - LogTitle.ORGANIZATION_NAME.getTitle().length();
        builder.append(this.blank(rest - 2));
        builder.append(columnSeperator());
        builder.append(this.blank(2));
        builder.append(dc.getFiredRuleSetInfo().getOrganizationName());
        rest = headerWidth
                        - (colWidth + 2 + dc.getFiredRuleSetInfo().getOrganizationName().length());
        builder.append(this.blank(rest - 4));
        builder.append(columnSeperator());
        builder.append("\n");
        builder.append(offSet);
        builder.append(columnSeperator());
        builder.append(line(headerWidth - 2));
        builder.append(columnSeperator());
        builder.append("\n");

        /**
         * Build RuleContext Table Row 5
         */
        colWidth = headerWidth / 2;
        builder.append(offSet);
        builder.append(columnSeperator());
        builder.append(this.blank(2));
        builder.append(LogTitle.ORGANIZATION_ROLE.getTitle());
        rest = colWidth - 2 - LogTitle.ORGANIZATION_ROLE.getTitle().length();
        builder.append(this.blank(rest - 2));
        builder.append(columnSeperator());
        builder.append(this.blank(2));
        builder.append(dc.getFiredRuleSetInfo().getRole());
        rest = headerWidth - (colWidth + 2 + dc.getFiredRuleSetInfo().getRole().length());
        builder.append(this.blank(rest - 4));
        builder.append(columnSeperator());
        builder.append("\n");
        builder.append(offSet);
        builder.append(columnSeperator());
        builder.append(line(headerWidth - 2));
        builder.append(columnSeperator());
        builder.append("\n");

        /**
         * Build RuleContext Table Row 6
         */
        colWidth = headerWidth / 2;
        builder.append(offSet);
        builder.append(columnSeperator());
        builder.append(this.blank(2));
        builder.append(LogTitle.TIME_OF_EXECUTION.getTitle());
        rest = colWidth - 2 - LogTitle.TIME_OF_EXECUTION.getTitle().length();
        builder.append(this.blank(rest - 2));
        builder.append(columnSeperator());
        builder.append(this.blank(2));
        builder.append(dc.getFiredRuleSetInfo().getExecutionDate().toString());
        rest = headerWidth
                        - (colWidth + 2 + dc.getFiredRuleSetInfo().getExecutionDate().toString()
                                        .length());
        builder.append(this.blank(rest - 4));
        builder.append(columnSeperator());
        builder.append("\n");
        builder.append(offSet);
        builder.append(columnSeperator());
        builder.append(line(headerWidth - 2));
        builder.append(columnSeperator());
        builder.append("\n");

        /**
         * Build Asserted Objects Line
         */
        /**
         * k = "Asserted Objects".length(); x = (headerWidth-k)/2;
         * 
         * builder.append(offSet); builder.append(columnSeperator());
         * builder.append(this.blank(x-1)); builder.append("Asserted Objects"); rest =
         * headerWidth-(x+k); builder.append(this.blank(rest-2)); builder.append(columnSeperator());
         * builder.append("\n"); builder.append(offSet); builder.append(columnSeperator());
         * builder.append(line(headerWidth-2)); builder.append(columnSeperator());
         * builder.append("\n");
         */

        /**
         * Build Asserted Objects Table Row 1
         */
        /**
         * List<Object> objects = dc.getAssertedObjects();
         * 
         * for(int s=0;s<objects.size();s++){ Object obj = objects.get(s); if(obj instanceof
         * AdverseEvent){ drawAdverseEvent(builder,offSet,headerWidth,(AdverseEvent)obj); } if(obj
         * instanceof Study){ drawStudy(builder,offSet,headerWidth,(Study)obj); } if(obj instanceof
         * StudyAgent){ drawStudyAgent(builder,offSet,headerWidth,(StudyAgent)obj); } }
         */
        drawRuleExecutionSummary(builder, offSet, headerWidth, dc);

        return builder.toString();

    }

    public String toXML(DecisionContext dc) {
        return null;
    }

 
    public void drawRuleExecutionSummary(StringBuilder builder, String offSet, int headerWidth,
                    DecisionContext dc) throws Exception {
        int k = "Exeution Summary".length();
        int x = (headerWidth - k) / 2;

        builder.append(offSet);
        builder.append(columnSeperator());
        builder.append(this.blank(x - 1));
        builder.append("Exeution Summary");
        int rest = headerWidth - (x + k);
        builder.append(this.blank(rest - 2));
        builder.append(columnSeperator());
        builder.append("\n");
        builder.append(offSet);
        builder.append(columnSeperator());
        builder.append(line(headerWidth - 2));
        builder.append(columnSeperator());
        builder.append("\n");

        int colWidth = headerWidth / 3;
        builder.append(offSet);
        builder.append(columnSeperator());
        builder.append(this.blank(2));
        builder.append(LogTitle.RULE_NAME.getTitle());
        rest = colWidth - 2 - LogTitle.RULE_NAME.getTitle().length();
        builder.append(this.blank(rest - 2));

        builder.append(columnSeperator());
        builder.append(this.blank(2));
        builder.append(LogTitle.CONDITION_MET.getTitle());
        rest = colWidth - 2 - LogTitle.CONDITION_MET.getTitle().length();
        builder.append(this.blank(rest - 2));

        builder.append(columnSeperator());
        builder.append(this.blank(2));
        builder.append(LogTitle.FIRED.getTitle());
        rest = colWidth - 2 - LogTitle.FIRED.getTitle().length();
        builder.append(this.blank(rest - 5));
        builder.append(columnSeperator());
        builder.append("\n");

        builder.append(offSet);
        builder.append(columnSeperator());
        builder.append(line(headerWidth - 2));
        builder.append(columnSeperator());
        builder.append("\n");

        /**
         * Row 2
         */
        dc.buildExecutionSummary();
        List<RuleExecutionStatus> res = dc.getExecutionSummary();
        for (int w = 0; w < res.size(); w++) {
            RuleExecutionStatus r = res.get(w);

            builder.append(offSet);
            builder.append(columnSeperator());
            builder.append(this.blank(2));
            builder.append(r.getRuleName());
            rest = colWidth - 2 - r.getRuleName().length();
            builder.append(this.blank(rest - 2));

            builder.append(columnSeperator());
            builder.append(this.blank(2));
            builder.append(r.isConditionMet());
            rest = colWidth - 2 - String.valueOf(r.isConditionMet()).length();
            builder.append(this.blank(rest - 2));

            builder.append(columnSeperator());
            builder.append(this.blank(2));
            builder.append(r.isFired());
            rest = colWidth - 2 - String.valueOf(r.isFired()).length();
            builder.append(this.blank(rest - 5));
            builder.append(columnSeperator());
            builder.append("\n");

            builder.append(offSet);
            builder.append(columnSeperator());
            builder.append(line(headerWidth - 2));
            builder.append(columnSeperator());
            builder.append("\n");

        }

    }

    /**
     * ________________________________________________________
     * 
     * @param dc
     * @return ________________________________________________________
     */

    private int getTableWidth(DecisionContext dc) {
//        AssertedObjectTableWidthFinder aw = new AssertedObjectTableWidthFinder();
        int i = 0;
        int j = dc.getFiredRuleSetInfo().getMaxWidth();
        // int k = aw.maxWidth(dc.getAssertedObjects());

        if (j > i) {
            i = j;
        }
        /**
         * if(k>i){ i=k; }
         */
        return i * 3;
    }

    private String line(int width) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < width + 1; i++) {
            builder.append("_");
        }
        return builder.toString();
    }

    private String columnSeperator() {
        return "|";
    }

    private String blank(int width) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < width + 1; i++) {
            builder.append(" ");
        }
        return builder.toString();
    }

}
