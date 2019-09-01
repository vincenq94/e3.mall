package cn.itheima.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;





public class BaseController {


    public static final String _RESULT = "RESULT";

    public static final String _MESSAGE = "MESSAGE";

    public static final String _SUCCESS = "SUCCESS";

    public static final String _FAILURE = "FAILURE";

    @Autowired
    protected HttpSession session;


    @ExceptionHandler(Exception.class)
    @ResponseBody
//	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Object handleException(Exception exception) {
        exception.printStackTrace();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(_RESULT, _FAILURE);
        map.put(_MESSAGE, exception.getMessage());
        return map;
    }

//    @ExceptionHandler({ BindException.class })
//    @ResponseStatus(HttpStatus.OK)
//    @ResponseBody
//    public Map<String, Object> handleException(BindException e, HttpServletRequest request) {
//        List<String> retval = new ArrayList<String>();
//        if ((e != null) && (e.hasErrors())) {
//            List<ObjectError> errList = e.getAllErrors();
//            if (errList != null) {
//                for (ObjectError obj : errList) {
//                    retval.add(obj.getDefaultMessage());
//                }
//            }
//        }
//        Map<String, Object> model = new HashMap<String, Object>();
//        model.put(_MESSAGE, "数据验证失败");
//        model.put(_RESULT, _FAILURE);
//        model.put("EXCEPTION", retval);
//        return model;
//    }

//    @InitBinder
//    public void InitBinder(WebDataBinder dataBinder) {
//        dataBinder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
//            public void setAsText(String value) {
//                try {
//                    if(!StringUtils.isEmpty(value)) {
//                        String fmt = "yyyy-MM-dd HH:mm:ss";
//                        setValue(new SimpleDateFormat(fmt.substring(0, value.length())).parse(value));
//                    }else {
//                        setValue(null);
//                    }
//                } catch (ParseException e) {
//                    setValue(null);
//                }
//            }
//
//            public String getAsText() {
//                return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format((Date) getValue());
//            }
//        });
//    }
//
//    public Object getCurrentSessionUser() {
//        return this.session.getAttribute(SessionConstant.SESSION_USER_KEY);
//    }
//
//
//    public void setCurrentSessionUser(Object user) {
//        if (this.session == null) {
//            return;
//        }
//        try {
//            this.session.setAttribute(SessionConstant.SESSION_USER_KEY, user);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void removeCurrentSessionUser() {
//        if (this.session == null) {
//            return;
//        }
//        try {
//            this.session.removeAttribute(SessionConstant.SESSION_USER_KEY);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//    /**
//     * 返回当前用户所在学校id
//     * @return
//     */
//    public String getSchoolId() {
//        Employee employee = (Employee)getCurrentSessionUser();
//        if(BusinessParameterConstant.DeptType.SCHOOL.getId().equals( employee.getOrgDepartment().getDeptType())) {
//            return employee.getOrgDepartment().getId();
//        }
//        return null;
//    }
//    /**
//     * 返回当前用户所在教育局id
//     * @return
//     */
//    public String getEduId() {
//        Employee employee = (Employee)getCurrentSessionUser();
//        if(BusinessParameterConstant.DeptType.EDUCATION_BUREAU.getId().equals(employee.getOrgDepartment().getDeptType())) {
////		if(BusinessParameterConstant.checkDepartmentIsOrg( employee.getOrgDepartment().getDeptType())) {
//            return employee.getOrgDepartment().getId();
//        }else {
//            return employee.getOrgDepartment().getParentId();
//        }
//    }
//
//    public String getOrgId() {
//        Employee employee = (Employee)getCurrentSessionUser();
//        return employee.getOrgDepartment().getId();
//    }
}
