//package cn.itheima.commons.utils;
//
//import com.jhsafe.school.commons.constant.BusinessParameterConstant;
//import com.jhsafe.school.commons.constant.RoleConstant;
//import com.jhsafe.school.commons.constant.SessionConstant;
//import com.jhsafe.school.commons.entity.auth.Department;
//import com.jhsafe.school.commons.entity.auth.Employee;
//import com.jhsafe.school.commons.entity.auth.Resource;
//import com.jhsafe.school.commons.entity.auth.Role;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//import tk.mybatis.mapper.util.StringUtil;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpSession;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * 学校工具类
// * @author Administrator
// *
// */
//public class SchoolUtils
//{
//	private HttpSession session;
//
//	private HttpServletRequest request;
//
//	public static Employee getCurrentUser() {
//		return (Employee)new SchoolUtils().session.getAttribute(SessionConstant.SESSION_USER_KEY);
//	}
//
//	public SchoolUtils() {
//		ServletRequestAttributes requestAttributes =
//				(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//		this.request = requestAttributes.getRequest();
//		this.session = requestAttributes.getRequest().getSession();
//	}
//	/**
//	 * 获取当前用户所有下级部门
//	 * @return
//	 */
//    public static List<String> getSubDeptIdByEmp(){
//    	List<Department> list = getSubDeptByEmp();
//    	List<String> departIdList = new ArrayList<String>();
//    	for (Department department : list) {
//    		departIdList.add(department.getId());
//		}
//    	return departIdList;
//    }
//
//    /**
//	 * 获取当前用户所有下级部门
//	 * @param isMain 是否包含当前登录人组织
//	 * @return
//	 */
//    public static List<Department> getSubDeptByEmp(boolean isMain){
//    	Employee employee = getCurrentUser();
//    	List<Department> list = new ArrayList<Department>(employee.getDepartment().getChildDeptList());
//    	if(isMain) {
//    		list.add(employee.getOrgDepartment());
//    	}
//    	return list;
//    }
//    /**
//	 * 获取当前用户所有下级部门
//	 * @return
//	 */
//    public static List<Department> getSubDeptByEmp(){
//    	return getSubDeptByEmp(false);
//    }
//    /**
//     * 获取当前用户（市教委 教育局）下所有学校
//     * @return
//     */
//    public static List<String> getSchoolIdListByEmp(){
//    	return getSchoolIdListByEmp(false);
//    }
//    /**
//     * 获取当前用户（市教委 教育局）下所有学校
//     * @return
//     */
//    public static List<String> getSchoolIdListByEmp(boolean isMain){
//    	return getDeptListByEmp(BusinessParameterConstant.DeptType.SCHOOL.getId(), isMain);
//    }
//    /**
//     * 获取当前用户（市教委）下所有教育局
//     * @return
//     */
//    public static List<String> getEduIdListByEmp(){
//    	return getDeptListByEmp(BusinessParameterConstant.DeptType.EDUCATION_BUREAU.getId());
//    }
//    /**
//     * 获取下级组织部门
//     * @param deptTypeId
//     * @param isMain 是否包含当前登录人组织
//     * @return
//     */
//    public static List<String> getDeptListByEmp(String deptTypeId, boolean isMain){
//    	List<Department> list = getSubDeptByEmp(isMain);
//    	List<String> departIdList = new ArrayList<String>();
//    	for (Department department : list) {
//    		if(deptTypeId.equals(department.getDeptType()))
//    			departIdList.add(department.getId());
//		}
//    	return departIdList;
//    }
//
//    /**
//     * 获取下级组织部门
//     * @param deptTypeId
//     * @return
//     */
//    public static List<String> getDeptListByEmp(String deptTypeId){
//    	return getDeptListByEmp(deptTypeId, false);
//    }
//
//
//    /**
//     * filterSql = getDataScopeSql(employee, req.getRequest());
//     * @param dataScope范围规则，为 null取角色
//     * @return
//     */
//    public static String getDataScopeSql(String dataScope) {
//		return getDataScopeSql(dataScope, null);
//    }
//
//    /**
//     * filterSql = getDataScopeSql(employee, req.getRequest());
//     * @param dataScope范围规则，为 null取角色
//     * @return
//     */
//    public static String getDataScopeSql(String dataScope, String tableAlis) {
//    	Employee employee = getCurrentUser();
//    	//系统管理员不使用数据过滤
//    	if(employee == null || employee.isJhsafe()){
//    		return null;
//    	}
//		List<Role> roleList = null;
//		if(dataScope != null) {
//			if(RoleConstant.DataScope.NONE.getCode().equals( dataScope )) {
//				return null;
//			}
//		}else {
//			roleList = getScopeByRole(employee);//取角色中数据范围
////    	String scope = RoleConstant.DataScope.DEPT.getCode();//默认下级部门
//		}
//		return dataScopeSql(dataScope, employee, roleList, tableAlis);
//    }
//
//    /**
//     * 多角色获取用户数据范围
//     * @param emp
//     * @return
//     */
//    private static List<Role> getScopeByRole(Employee emp) {
//    	String url = new SchoolUtils().request.getRequestURI();
//    	url = url.substring(url.indexOf("/service"));
////    	System.err.println("当前访问的url--->"+url);
//    	List<Role> roleList = emp.getRoleList();//用户角色
//    	List<Role> resRoleList = filterRoleList(roleList, url);
//    	return resRoleList;
//    }
//
//    /**
//     * 验证用户所有角色中，哪一些可以访问当前资源
//     * @return
//     */
//    private static List<Role> filterRoleList(List<Role> roleList, String url) {
//    	List<Role> resRoleList = new ArrayList<Role>();
//    	for(Role role: roleList) {
//    		List<Resource> resourceList = role.getResourceList();
//    		for(Resource resource: resourceList) {
//    			if(resource.getUrl() !=null &&
//    					resource.getUrl().equals(url)) {
//    				resRoleList.add(role);
//    				break;
//    			}
//    		}
//    	}
//    	return resRoleList;
//    }
//
//    /**
//     * 生成sql过滤条件
//     * @param scope
//     * @param employee
//     * @param roleList
//     * @param tableAils
//     * @return
//     */
//	public static String dataScopeSql(String scope, Employee employee, List<Role> roleList, String tableAils) {
//		String authSql = "";
//		String appoint_rid = "";
//		tableAils = StringUtil.isNotEmpty( tableAils )?tableAils+".":"";
//		if(roleList != null) {
//			scope = "";
//			for (Role role : roleList) {
//				scope = scope+","+role.getDataScope();
//				if(scope.equals(RoleConstant.DataScope.APPOINT_DEPT.getCode())) {
//					appoint_rid += ",'"+role.getId()+"'";
//				}
//			}
//		}
//		if(scope.indexOf(RoleConstant.DataScope.ALLIN.getCode())!=-1) {
////			authSql += " and "+tableAils+"CREATE_DEPT_NUMBER like '"+employee.getOrgDepartment().getDeptNumber()+"%'";//该组织内所有
//			authSql += " and ("+tableAils+"CREATE_DEPT_ID in (select DEPARTMENT_ID from t_department_parent_record where parent_id='"+employee.getOrgDepartment().getId()+"') or CREATE_DEPT_ID='"+employee.getOrgDepartment().getId()+"')";//该部门内所有
//		}
//		else if(scope.indexOf(RoleConstant.DataScope.DEPT.getCode())!=-1) {
////			authSql += " and "+tableAils+"CREATE_DEPT_NUMBER like '"+employee.getDepartment().getDeptNumber()+"%'";//该部门内所有
//			authSql += " and ("+tableAils+" CREATE_DEPT_ID in (select DEPARTMENT_ID from t_department_parent_record where parent_id='"+employee.getDepartment().getId()+"' ) or CREATE_DEPT_ID='"+employee.getDepartment().getId()+"')";//该部门内所有
//		}
//		else if(scope.indexOf(RoleConstant.DataScope.USER.getCode())!=-1) {
//			authSql += " and "+tableAils+"CREATOR_ID = '"+employee.getIdentify().getId()+"'";//该部门内所有
//		}
//		if(scope.indexOf(RoleConstant.DataScope.APPOINT_DEPT.getCode())!=-1) {
//			authSql += " or "+tableAils+"CREATE_DEPT_ID in (select DEPARTMENT_ID from T_ROLE_ORG_FUNC_REL where ROLE_ID in ("+appoint_rid.substring(1)+") )";
//		}
//		return authSql;
//	}
//}
