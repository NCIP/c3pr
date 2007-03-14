<%@page contentType="text/css" language="java" %>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags" %>

/* general style propertyies */
body { margin: 0 0 0 0; padding: 0; font-family: arial, verdana, sans-serif; font-size: 11px; color: #000000; background: #F4F4F5; }

ul, ol { margin-top: 0; }

/* general link properties */
a:link, a:visited { color: #6E81A6; }
a:hover, a:active { color: #89ABD5; }

/* general form object properties */
select { font-family: arial, verdana, sans-serif; font-size: 11px; }
input { background: #EBEFF5; font-family: arial, verdana, sans-serif; font-size: 10px; padding: 0 3px 0 3px; }

/* header bar */
.header { background: repeat-x top url(<tags:imageUrl name="header_BG.gif"/>); }

/* top nav */
#topNav { color: #E6E6E6; font-weight: bold; background-color: #5C5C5C; }
#topNav .left { padding: 0 0 0 8px; white-space: nowrap; }
#topNav .right { padding: 0; text-align: right; white-space: nowrap; }
#topNav .divider { width: 6px; }
#topNav .current { color: #3E3F6B; height: 18px; background-color:#FFFFFF; padding: 3px 24px 0 10px; vertical-align: bottom; }
#topNav .currentR { height: 20px; width: 6px; }
#topNav a {
    text-decoration: none; background-color:#747474;
    border: solid #D6D6D6; border-width: 1px 0 0 1px;
    padding: 2px 24px 0 10px; vertical-align: bottom;
}
#topNav a:link, #topNav a:visited { color: #E6E6E6; }
#topNav a:hover, #topNav a:active { color: #FFFFFF; }
#topNav .right a {
    background-color: transparent;
    border: none;
}

/* sub nav */
#subNav {
    background: #FFFFFF; border-bottom: 2px solid #3E3F6B;
    padding-left: 10px; color: #6E81A6; font-weight: bold; line-height: 20px;
}
#subNav .welcome { text-decoration: none; font-weight: normal; }
#subNav .spacer { margin: 0 12px 0 12px; }
#subNav .left { padding-right: 10px; white-space: nowrap; }
#subNav .right { padding-right: 10px; white-space: nowrap; }
#subNav a { font-weight: normal; }
#subNav a:link, #subNav a:visited { text-decoration: none; }
#subNav a:hover, #subNav a:active { text-decoration: underline; }

/* title - left of search */
.titleArea { margin: 10px 0 10px 0; }
#title { white-space: nowrap; font-size: 12px; font-weight: bold; color: #6E81A6; }


/* top page search */
/*#search { white-space: nowrap; margin: 0; padding: 0; color: #6E81A6; font-weight: bold; }*/
.search .field1 { border: 1px solid #CCCCCC; background: #FFFFFF; height: 18px; font-size: 11px; line-height: 18px; }

/* main padding properties of the working area body */
.workArea { margin: 10px; }

/* blue bar current title */
#current {
    padding: 4px 0 4px 8px; background: #6E81A6 url(<tags:imageUrl name="blueCorner.gif"/>) no-repeat top right;
    white-space: nowrap; font-size: 14px; font-weight: bold;
    color: #FFFFFF; line-height: 17px;
}
#current .spacer { margin-right: 50px; }

/* main dispaly table that holds all the content */
.display {
    border-left: 1px solid #6E81A6;
    background: #D7D9E3 url(<tags:imageUrl name="display_BG.gif"/>) repeat-y right;
    padding: 0 8px 8px 5px;
}
.display_B {
    background: #F4F4F5 url(<tags:imageUrl name="display_B.gif"/>) repeat-x top;
    vertical-align: top; height: 4px; border: none;
}

/* Level 2 tabs */
#level2 {
    background: #6E81A6 url(<tags:imageUrl name="level2_BG.gif"/>) repeat-x top;
    padding: 9px 0 0 7px; margin: 0;
    white-space: nowrap; color: #333333;
}
#level2 li.tab {
    display: inline;
    margin: 0;
}
#level2 a { background-color: transparent; border: none; }
#level2 a:link, #level2 a:visited { color: #333333; text-decoration: none; }
#level2 a:hover, #level2 a:active { color: #000000; text-decoration: underline; }
#level2 .tab a {
    background: #E9E9EB url(<tags:imageUrl name="tab2_BG.gif"/>) repeat-x top;
    padding: 2px 10px 0 8px; vertical-align: bottom;
}
#level2 .tab.current a {
    background-image: url(<tags:imageUrl name="tab2_h_BG.gif"/>);
    font-weight: bold; color: #3E3F6B;
}
#level2 .tab.current a {
    background-color: transparent; border: none; padding: 2px 10px 0 8px;
}
#level2 .tab.current a:link, #level2 .current a:visited { color: #6E81A6; text-decoration: none; }
#level2 .tab.current a:hover, #level2 .current a:active { color: #89ABD5; text-decoration: underline; }

/* properties of the area directly under the tabs that has the angled graphic */
#level2-spacer { background: #E9E9EB; border: none; height: 8px; }

/* tab table */
.tabs { margin-top: 4px; background: #D7D9E3 url(<tags:imageUrl name="level3_BG.gif"/>) no-repeat top right; }

/* tabs */
.tabDisplay { background: none; padding-left: 0; white-space: nowrap; color: #333333; }
.tabDisplay a:link, .tabDisplay a:visited {
    color: #333333; text-decoration: none; 
}
.tabDisplay a:hover, .tabDisplay a:active {
    color: #000000; text-decoration: underline;
}
.tabDisplay .current {
    background: #E9E9EB url(<tags:imageUrl name="tab3_h_BG.gif"/>) repeat-x top;
    font-weight: bold; color: #6E81A6; padding: 2px 10px 0 8px; vertical-align: bottom;
}
.tabDisplay .current a {
    padding: 2px 10px 0 8px;
}
.tabDisplay .current a:link, .tabDisplay .current a:visited {
    color: #6E81A6; text-decoration: none;
}
.tabDisplay .current a:hover, .tabDisplay .current a:active {
    color: #89ABD5; text-decoration: underline;
}
.tabDisplay .tab { background: #E9E9EB url(<tags:imageUrl name="tab3_BG.gif"/>) repeat-x top; padding-bottom: 2px; padding: 2px 10px 0 8px; vertical-align: bottom;}

/* properties of the area directly under the tabs that has the angled graphic */
.tabBotL { background: #F5F5F8 url(<tags:imageUrl name="whiteCorner.gif"/>) no-repeat top right; border-left: 1px solid #6E81A6; height: 18px;}
.tabBotR { background: #F5F5F8 url(<tags:imageUrl name="whiteCorner.gif"/>) no-repeat top right; border-left: 1px solid #6E81A6; height: 18px;}

/* left area content */
.contentL { background: #FFFFFF url(<tags:imageUrl name="content_BG.gif"/>) repeat-y right; border-left: 1px solid #6E81A6; padding: 4px 8px 12px 8px; }

/* right area content */
.contentR { background: #FFFFFF url(<tags:imageUrl name="content_BG.gif"/>) repeat-y right; border-left: 1px solid #6E81A6; padding: 4px 8px 12px 8px; }
.contentR hr { height: 0; border-bottom: 1px dashed #CCCCCC; }
.contentR .solid { height: 0; border-bottom: 1px solid #CCCCCC; }
.contentR2 { background: #FFFFFF; border-left: 1px solid #CCCCCC; border-bottom: 4px solid #CCCCCC; padding: 0 0 6px 0; }
.contentR3 { margin: 10px 8px 12px 15px; }
.contentR4 { background: #FFFFFF; border-left: 1px solid #CCCCCC; padding: 0 0 6px 0; }

/* content bottom border */
.content_B { background: #D7D9E3 url(<tags:imageUrl name="content_B.gif"/>) repeat-x top; vertical-align: top; height: 4px; border: none; }

/* general form 1 */
form.standard { margin: 0; padding: 0; }
form.standard input {
    border: 0; border-top: 1px solid #CCCCCC; border-left: 1px solid #CCCCCC;
    height: 15px;
    background: #EBEFF5; 
    margin: 0; padding: 0 3px;
    font-family: arial, verdana, sans-serif; font-size: 10px; font-weight: normal;
    line-height: 15px;
}
form.standard select { font-size: 10px; background: #EBEFF5; }
form.standard .radio { background: none; border: 0; }
form.standard .mi { width: 10px; border: 0; border-top: 1px solid #CCCCCC; border-left: 1px solid #CCCCCC; }
form.standard .long { white-space: normal; }
form.standard .reissue { background: #F5F5CE; }
form.standard .cancel { background: #FCD09D; }
form.standard .disabled { background: #EEEEEE; }
form.standard .blank { background: none; border: 0; }
form.standard .state { padding-right: 8px; }

#right { margin: 0; padding: 0; width: 550px; }

.notesField { width: 538px; height: 100px; border: 0; border-top: 1px solid #CCCCCC; border-left: 1px solid #CCCCCC; background: #EBEFF5; margin: 4px 0 10px 0; font-family: Arial, Verdana, Helvetica, serif; font-size: 11px; color: #000000; line-height: 14px; padding: 5px; }
.commentsField { width: 538px; height: 50px; border: 0; border-top: 1px solid #CCCCCC; border-left: 1px solid #CCCCCC; background: #EBEFF5; margin: 4px 0 10px 0; font-family: Arial, Verdana, Helvetica, serif; font-size: 11px; color: #000000; line-height: 14px; padding: 5px; }
.commentsField2 { width: 300px; height: 50px; border: 0; border-top: 1px solid #CCCCCC; border-left: 1px solid #CCCCCC; background: #EBEFF5; margin: 4px 0 10px 0; font-family: Arial, Verdana, Helvetica, serif; font-size: 11px; color: #000000; line-height: 14px; padding: 5px; }

.red { color: #EE3324; }

.split-pane .contentAreaL { border-right: 1px dashed #CCCCCC; padding: 3px; }
.split-pane .contentAreaR { padding: 3px 3px 3px 16px; }
.split-pane h3 { margin-top: 5px; margin-bottom: 5px; }

.table1 { margin: 0; }
.table1 td { padding-top: 1px; padding-bottom: 1px; }
.table1 .label { font-weight: bold; text-align: right; padding-right: 4px; white-space: nowrap; }
.table1 .label2 { border-bottom: 2px solid #CCCCCC; background: #EEEEEE; padding: 3px 5px 3px 5px; }
.table1 .note { font-weight: normal; font-size: 9px; font-style: italic; }
.table1 .holder { padding: 5px; padding-bottom: 10px; border-bottom: 1px dashed #CCCCCC; }
.table1 .holder2 { padding: 5px; padding-bottom: 10px; }
.table1 .dashed td { border-bottom: 1px dashed #CCCCCC; }
.table1 .dashed tr { border-bottom: 1px dashed #CCCCCC; }

#table2 { border-top: 1px solid #CCCCCC; border-left: 1px solid #CCCCCC; border-bottom: 2px solid #CCCCCC; margin: 0; }
#table2 td { border-bottom: 1px solid #CCCCCC; border-right: 1px solid #CCCCCC; text-align: center; padding: 2px 1px 2px 1px; }
#table2 .label td { border-bottom: 2px solid #CCCCCC; background: #EEEEEE; padding: 3px 1px 3px 1px; }
#table2 .label a:link { color: #000000; text-decoration: none; }
#table2 .label a:visited { color: #000000; text-decoration: none; }
#table2 .label a:hover { color: #000000; text-decoration: none; }
#table2 .label a:active { color: #000000; text-decoration: none; }
#table2 .direct { text-align: left; font-weight: bold; background: #EEEEEE; }
#table2 .top td { border-top: 1px solid #CCCCCC; border-bottom: 1px dashed #CCCCCC; }
#table2 .bottom td { border-bottom: 2px solid #CCCCCC; }
#table2 .bottom .none { border-bottom: 2px solid #CCCCCC;; border-right: 1px dashed #CCCCCC; }

#table3 { border-top: 1px solid #CCCCCC; border-left: 1px solid #CCCCCC; border-right: 1px solid #CCCCCC; border-bottom: 1px solid #CCCCCC; margin: 0; }
#table3 td { padding: 2px 1px 2px 1px; }
#table3 .label td { border-bottom: 2px solid #CCCCCC; border-right: 1px solid #CCCCCC; background: #EEEEEE; padding: 3px 1px 3px 1px; font-weight: bold; text-align:center; }
#table3 .label .last { border-right: none; }
#table3 .label a:link { color: #000000; font-weight: bold; text-decoration: none; }
#table3 .label a:visited { color: #000000; font-weight: bold; text-decoration: none; }
#table3 .label a:hover { color: #000000; font-weight: bold; text-decoration: none; }
#table3 .label a:active { color: #000000; font-weight: bold; text-decoration: none; }
#table3 .row1 td { text-align: center; border-bottom: 1px dashed #CCCCCC; border-right: 1px dashed #CCCCCC; }
#table3 .row1 .last { border-right: none; border-right: none; }
#table3 .row1 .check { border-bottom: 2px solid #CCCCCC; }
#table3 .row1 .blank { border-bottom: none; }
#table3 .row2 td { border-bottom: 2px solid #CCCCCC; }
#table3 .label2 { font-weight: bold; text-align: right; padding-right: 4px; border-right: none; }
#table3 .direct { text-align: left; font-weight: bold; background: #EEEEEE; }
#table3 .top td { border-top: 1px solid #CCCCCC; border-bottom: 1px dashed #CCCCCC; }
#table3 .bottom td { border-bottom: 2px solid #CCCCCC; }
#table3 .bottom .none { border-bottom: 2px solid #CCCCCC;; border-right: 1px dashed #CCCCCC; }

.email { width: 165px; /* TODO INVALID: word-wrap: break-word; */ }

.small { font-size: 10px; }

.blank { background: none; }

/* Controls the height between content in tables */
.heightControl { height: 8px; }

/* additional drivers/vehicles at the bottom of the page */
.additionals { background: #FFFFFF url(<tags:imageUrl name="content_BG.gif"/>) repeat-y right; border-left: 1px solid #6E81A6; padding: 4px 3px 12px 0; }
.additionals hr { height: 0; border-bottom: 1px dashed #CCCCCC; }

.additionals2 { background: #FFFFFF; padding: 10px 10px 15px 15px; }
.additionals2 hr { height: 0; border-bottom: 1px dashed #CCCCCC; }

.additionals3 { background: #FFFFFF; padding: 10px 10px 15px 15px; border-bottom: 3px solid #CCCCCC; }
.additionals3 hr { height: 0; border-bottom: 1px dashed #CCCCCC; }

#additionalList { border-bottom: 1px solid #CCCCCC; }
#additionalList td { padding: 3px 2px 3px 2px; white-space: nowrap; border-bottom: 1px solid #CCCCCC; }
#additionalList select {font-size: 10px; }
#additionalList .label td { font-weight: bold; border-bottom: 2px solid #CCCCCC; background: #EEEEEE; }
#additionalList .current td { border-top: 1px solid #000000; border-bottom: 1px solid #000000; }
#additionalList .results {  color: #6E81A6; background: #FFFFFF; }
#additionalList .resultsOver { color: #6E81A6; background: #EBEFF5; cursor: pointer; }

/* sub menu under the tabs */
#bottomTab { border-bottom: 1px dashed #CCCCCC; padding-left: 10px; color: #6E81A6; font-weight: bold; line-height: 20px; }
#bottomTab .spacer { margin: 0 12px 0 12px; }
#bottomTab a:link { color: #6E81A6; text-decoration: none; font-weight: normal; }
#bottomTab a:visited { color: #6E81A6; text-decoration: none; font-weight: normal; }
#bottomTab a:hover { color: #89ABD5; text-decoration: underline; font-weight: normal; }
#bottomTab a:active { color: #89ABD5; text-decoration: underline; font-weight: normal; }

#tableHistory { border-bottom: 1px solid #CCCCCC; }
#tableHistory td { padding: 3px 4px 3px 4px; }
#tableHistory .label1 td { border-bottom: 2px solid #CCCCCC; background: #EEEEEE; }
#tableHistory .label1 a:link { color: #000000; text-decoration: none; }
#tableHistory .label1 a:visited { color: #000000; text-decoration: none; }
#tableHistory .label1 a:hover { color: #000000; text-decoration: none; }
#tableHistory .label1 a:active { color: #000000; text-decoration: none; }
#tableHistory .closed td { border-bottom: 1px solid #CCCCCC; }
#tableHistory .closed2 { border-bottom: 1px solid #CCCCCC; border-top: 1px dashed #CCCCCC; }
#tableHistory .closed3 { border-top: 1px dashed #CCCCCC; border-bottom: 1px solid #CCCCCC; padding: 0; padding-bottom: 5px; }
#tableHistory .amounts { border-bottom: 1px dashed #CCCCCC; }
#tableHistory #details .label { text-align: right; font-weight: bold; }
#tableHistory #details2 { border-bottom: 1px dashed #CCCCCC; }
#tableHistory #details2 .label { text-align: right; font-weight: bold; }

#tableHistory2 { border-bottom: 1px solid #CCCCCC; }
#tableHistory2 .label1 td { border-bottom: 2px solid #CCCCCC; background: #EEEEEE; }
#tableHistory2 .label1 a:link { color: #000000; text-decoration: none; }
#tableHistory2 .label1 a:visited { color: #000000; text-decoration: none; }
#tableHistory2 .label1 a:hover { color: #000000; text-decoration: none; }
#tableHistory2 .label1 a:active { color: #000000; text-decoration: none; }
#tableHistory2 td { border-bottom: 1px solid #CCCCCC; padding: 2px; }
#tableHistory2 .new td { background: #FFFFDD; }

#tableHistory3 { border-bottom: 1px solid #CCCCCC; }
#tableHistory3 .label1 td { border-bottom: 2px solid #CCCCCC; background: #EEEEEE; }
#tableHistory3 .label1 a:link { color: #000000; text-decoration: none; }
#tableHistory3 .label1 a:visited { color: #000000; text-decoration: none; }
#tableHistory3 .label1 a:hover { color: #000000; text-decoration: none; }
#tableHistory3 .label1 a:active { color: #000000; text-decoration: none; }
#tableHistory3 td { border-bottom: 1px dashed #CCCCCC; padding: 2px; }

#amounts { border-top: 1px solid #CCCCCC; border-left: 1px solid #CCCCCC; margin-bottom: 10px; }
#amounts td { border-right: 1px solid #CCCCCC; border-bottom: 1px solid #CCCCCC; padding: 1px; }
#amounts .label td { white-space: nowrap; font-weight: bold; }

#remarketing h4 { font-size: 14px; font-weight: bold; margin-bottom: 5px; }
#remarketing .details { border: 1px solid #CCCCCC; width: 225px; padding: 3px; }

#remarketing2 { border: 1px solid #CCCCCC; }
#remarketing2 h4 { font-size: 14px; font-weight: bold; margin-bottom: 5px; }
#remarketing2 .fleet { padding: 3px; }
#remarketing2 .fleet2 { padding: 3px; border: 1px solid #CCCCCC; }

.review { margin-left: 20px; }

/* Copyright style */
#copyright { margin-top: 10px; text-align: center; font-size: 10px; color: #999999; }

/* caBIG footer */
#cabig-logo { background: #475B82; border-top: solid #3E3F6B 4px; margin-top: 48px; text-align: right; }
