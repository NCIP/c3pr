/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.utils.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import edu.duke.cabig.c3pr.web.beans.DefaultObjectPropertyReader;

public class RowManager {
    public void handleRowDeletion(HttpServletRequest request, Object command) throws Exception {
        Enumeration enumeration = request.getParameterNames();
        Hashtable<String, List<Integer>> table = new Hashtable<String, List<Integer>>();
        while (enumeration.hasMoreElements()) {
            String param = (String) enumeration.nextElement();
            if (param.startsWith("_deletedRow-")) {
                String[] params = param.split("-");
                if (table.get(params[1]) == null) table.put(params[1], new ArrayList<Integer>());
                table.get(params[1]).add(new Integer(params[2]));
            }
        }
        deleteRows(command, table);
    }

    private void deleteRows(Object command, Hashtable<String, List<Integer>> table)
                    throws Exception {
        Enumeration<String> e = table.keys();
        while (e.hasMoreElements()) {
            String path = e.nextElement();
            List col;
            try {
                col = (List) new DefaultObjectPropertyReader(command, path)
                                .getPropertyValueFromPath();
            }
            catch (Exception e1) {
                e1.printStackTrace();
                continue;
            }

            List<Integer> rowNums = table.get(path);
            Collections.sort(rowNums);
            for (int i = rowNums.size() - 1; i >= 0; i--)
                col.remove(rowNums.get(i).intValue());
        }
    }
}
