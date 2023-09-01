package struts;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class MenuAction extends Action {
    public ActionForward execute(ActionMapping aMapping, ActionForm aForm,
        HttpServletRequest req, HttpServletResponse aResponse) {
        req.setAttribute("def", req.getParameter("def"));

        return aMapping.findForward("success");
    }
}
